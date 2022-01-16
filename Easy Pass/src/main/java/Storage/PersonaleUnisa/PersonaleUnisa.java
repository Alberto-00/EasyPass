package Storage.PersonaleUnisa;

import Storage.Dipartimento.Dipartimento;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Superclasse astratta che modella un persona che fa parte del personale dell'Università
 * degli Studi di Salerno.
 *
 * @see Storage.PersonaleUnisa.Docente.Docente
 * @see Storage.PersonaleUnisa.Direttore.DirettoreDiDipartimento
 */
public abstract class PersonaleUnisa {

    private String nome;
    private String cognome;
    private String username;
    private String password;
    private Dipartimento dipartimento;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = DigestUtils.sha256Hex(password);
    }

    public Dipartimento getDipartimento() {
        return dipartimento;
    }

    public void setDipartimento(Dipartimento dipartimento) {
        this.dipartimento = dipartimento;
    }

    @Override
    public String toString() {
        return getClass().getName() + "{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", dipartimento=" + dipartimento +
                '}';
    }
}
