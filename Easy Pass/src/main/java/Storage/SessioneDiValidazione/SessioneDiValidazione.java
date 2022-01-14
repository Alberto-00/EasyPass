package Storage.SessioneDiValidazione;

import ApplicationLogic.Utils.ServletLogic;
import Storage.Esito.Esito;
import Storage.PersonaleUnisa.Docente.Docente;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import javax.imageio.ImageIO;

import io.nayuki.qrcodegen.QrCode;

/**
 * La classe crea delle Sessioni di Validazione e i QR Code che lo Studente
 * può scansionare, inoltre, sono previste operazioni per validare i Green Pass
 * e di conseguenza salvare gli esiti di tale validazione.
 *
 * @see io.nayuki.qrcodegen.QrCode
 */
public class SessioneDiValidazione {

    private String QRCode;
    private boolean isInCorso;
    private Docente docente;
    private ArrayList<Esito> listaEsiti;
    private static final String url = "http://localhost:8080/EasyPass/sessioneServlet/InvioGP?sessionId=";

    /**
     * Viene creata una {@code SessioneDiValidazione} assegnandola a un Docente e creato
     * il QR code che si trover&agrave; nella cartella apposita di Tomcat.
     *
     * @param isInCorso stabilisce se una {@code SessioneDiValidazione} &egrave; in corso
     *                  o &egrave; terminata
     * @param docente Docente che ha avviato la {@code SessioneDiValidazione}
     */
    public SessioneDiValidazione(boolean isInCorso, Docente docente) throws IOException {
        Random r = new Random();
        int sessionId;
        SessioneDiValidazione foundSession;
        SessioneDiValidazioneDAO sessioneDAO = new SessioneDiValidazioneDAO();
        File file;
        do {
            sessionId = r.nextInt(100000);
            foundSession = sessioneDAO.doRetrieveById(sessionId);
        } while (foundSession != null);

        int numberOfDigits = 5 - String.valueOf(sessionId).length();
        String parsedSessionId = String.valueOf(sessionId);
        if (numberOfDigits > 0) {
            for (int i = 0; i < numberOfDigits; i++) {
                parsedSessionId = 0 + parsedSessionId;
            }
        }
        BufferedImage qrImg = creaQRCode(url + parsedSessionId);
        String uploadPath = ServletLogic.getUploadPath() + "QRcodes" + File.separator;

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(qrImg, "jpg", os);
        try (InputStream is = new ByteArrayInputStream(os.toByteArray())) {
            file = new File(uploadPath + parsedSessionId + ".jpg");
            Files.copy(is, file.toPath());
        }

        this.isInCorso = isInCorso;
        this.docente = docente;
        this.QRCode = parsedSessionId + ".jpg";
    }

    /**
     * Costruttore vuoto.
     */
    public SessioneDiValidazione() {
        this.QRCode ="";
        this.isInCorso=false;
        this.docente=null;
        listaEsiti = new ArrayList<>();
    }

    private BufferedImage creaQRCode(String url) {
        QrCode qr0 = QrCode.encodeText(url, QrCode.Ecc.MEDIUM);
        return toImage(qr0, 4, 10); // See QrCodeGeneratorDemo
    }

    public ArrayList<Esito> getListaEsiti() {
        return listaEsiti;
    }

    public void setListaEsiti(ArrayList<Esito> listaEsiti) {
        this.listaEsiti = listaEsiti;
    }

    public String getQRCode() {
        return QRCode;
    }

    public void setQRCode(String QRCode) {
        this.QRCode = QRCode;
    }

    public boolean isInCorso() {
        return isInCorso;
    }

    public void setInCorso(boolean inCorso) {
        isInCorso = inCorso;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    /**
     * Viene validata la stringa del Green Pass.
     *
     * @param esito Green Pass
     * @return {@code Esito}
     */
    public Esito validaGreenPass(Esito esito) throws IOException, ParseException {
        String GP = esito.getStringaGP();
        String encodedDGC = URLEncoder.encode(GP, StandardCharsets.UTF_8.toString());
        URL urldemo = new URL("http://localhost:3000/?dgc=" + encodedDGC);
        URLConnection yc = urldemo.openConnection();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Not validdd");
        }

        String inputLine;
        inputLine = in.readLine();

        Scanner s = new Scanner(inputLine).useDelimiter(";");
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0 -> esito.setValidita(s.next().compareTo("Valid") == 0);
                case 1 -> esito.setCognomeStudente(s.next());
                case 2 -> esito.setNomeStudente(s.next());
                case 3 -> esito.setDataDiNascitaStudente(new SimpleDateFormat("yyyy-MM-dd").parse(s.next()));
                default -> {
                }
            }
        }
        esito.setSessione(this);
        in.close();
        return esito;
    }

    //Metodo importato dalla libreria QRCodegenerator
    private static BufferedImage toImage(QrCode qr, int scale, int border, int lightColor, int darkColor) {
        Objects.requireNonNull(qr);
        if (scale <= 0 || border < 0)
            throw new IllegalArgumentException("Value out of range");
        if (border > Integer.MAX_VALUE / 2 || qr.size + border * 2L > Integer.MAX_VALUE / scale)
            throw new IllegalArgumentException("Scale or border too large");

        BufferedImage result = new BufferedImage((qr.size + border * 2) * scale, (qr.size + border * 2) * scale, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < result.getHeight(); y++) {
            for (int x = 0; x < result.getWidth(); x++) {
                boolean color = qr.getModule(x / scale - border, y / scale - border);
                result.setRGB(x, y, color ? darkColor : lightColor);
            }
        }
        return result;
    }

    //Metodo importato dalla libreria QRCodegenerator
    private static BufferedImage toImage(QrCode qr, int scale, int border) {
        return toImage(qr, scale, border, 0xFFFFFF, 0x000000);
    }

    @Override
    public String toString() {
        return "SessioneDiValidazione{" +
                "qRCode='" + QRCode + '\'' +
                ", isInCorso=" + isInCorso +
                ", docente=" + docente +
                ", listaEsiti=" + listaEsiti +
                '}';
    }
}
