package Storage.Formato;

public class Formato {

    private int id;
    private boolean numStudenti;
    private boolean nomeCognome;
    private boolean data;
    private boolean numGPValidi;
    private boolean numGPNonValidi;

    public Formato(int id, boolean numStudenti, boolean nomeCognome, boolean data, boolean numGPValidi, boolean numGPNonValidi) {
        this.id = id;
        this.numStudenti = numStudenti;
        this.nomeCognome = nomeCognome;
        this.data = data;
        this.numGPValidi = numGPValidi;
        this.numGPNonValidi = numGPNonValidi;
    }

    public Formato(){
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
