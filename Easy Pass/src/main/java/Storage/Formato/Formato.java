package Storage.Formato;

/**
 * La classe crea degli oggetti {@code Formato}. L'{@code ID} del {@code Formato}
 * coincide con l'{@code ID} del {@code Dipartimento} a cui il {@code Formato} è associato.
 */
public class Formato {

    /* L'id del formato è uguale all'id del dipartimento a cui appartiene */
    private String id;
    private boolean numStudenti;
    private boolean nomeCognome;
    private boolean data;
    private boolean numGPValidi;
    private boolean numGPNonValidi;

    /**
     * Crea un oggetto {@code Formato} con tutte le informazioni passate in input.
     *
     * @param id {@code ID} del {@code Formato} che coincide con l'{@code ID} del {@code Dipartimento}
     * @param numStudenti {@code true} se si vogliono visualizzare il numero di Studenti validati in una
     *                                {@code Sessione di Validazione}, altrimenti {@code false}
     * @param nomeCognome {@code true} se si vuole vogliono visualizzare i cognomi degli Studenti validati in una
     *                                {@code Sessione di Validazione}, altrimenti {@code false}
     * @param data {@code true} se si se si vuole vogliono visualizzare le date di nascita degli Studenti validati in una
     *                                {@code Sessione di Validazione}, altrimenti {@code false}
     * @param numGPValidi se si vuole visualizzare il numero di Green Pass validi controllati in una
     *                                {@code Sessione di Validazione}, altrimenti {@code false}
     * @param numGPNonValidi se si vuole visualizzare il numero di Green Pass non validi controllati in una
     *                                {@code Sessione di Validazione}, altrimenti {@code false}
     */
    public Formato(String id, boolean numStudenti, boolean nomeCognome,
                   boolean data, boolean numGPValidi, boolean numGPNonValidi) {
        this.id = id;
        this.numStudenti = numStudenti;
        this.nomeCognome = nomeCognome;
        this.data = data;
        this.numGPValidi = numGPValidi;
        this.numGPNonValidi = numGPNonValidi;
    }

    /**
     * Costruttore vuoto.
     */
    public Formato(){
        this.id = "";
        this.numStudenti = false;
        this.nomeCognome = false;
        this.data = false;
        this.numGPValidi = false;
        this.numGPNonValidi = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isNumStudenti() {
        return numStudenti;
    }

    public void setNumStudenti(boolean numStudenti) {
        this.numStudenti = numStudenti;
    }

    public boolean isNomeCognome() {
        return nomeCognome;
    }

    public void setNomeCognome(boolean nomeCognome) {
        this.nomeCognome = nomeCognome;
    }

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }

    public boolean isNumGPValidi() {
        return numGPValidi;
    }

    public void setNumGPValidi(boolean numGPValidi) {
        this.numGPValidi = numGPValidi;
    }

    public boolean isNumGPNonValidi() {
        return numGPNonValidi;
    }

    public void setNumGPNonValidi(boolean numGPNonValidi) {
        this.numGPNonValidi = numGPNonValidi;
    }

    @Override
    public String toString() {
        return "Formato{" +
                "id=" + id +
                ", numStudenti=" + numStudenti +
                ", nomeCognome=" + nomeCognome +
                ", data=" + data +
                ", numGPValidi=" + numGPValidi +
                ", numGPNonValidi=" + numGPNonValidi +
                '}';
    }
}
