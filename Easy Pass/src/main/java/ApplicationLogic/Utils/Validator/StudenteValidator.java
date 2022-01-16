package ApplicationLogic.Utils.Validator;

public class StudenteValidator {

    /**
     * Verifica se il file inserito è un Green Pass con un formato valido
     *
     * @param str stringa del Green Pass
     * @return messaggio di errore o di successo
     */
    public static String checkGP(String str) {
        if (str.isEmpty())
            return "Nessun Green Pass inserito.";

        else if (!str.startsWith("HC1:"))
            return "Errore! Formato non valido.";

        return "Green Pass inviato correttamente.";
    }
}
