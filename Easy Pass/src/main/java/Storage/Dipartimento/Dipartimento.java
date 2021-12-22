package Storage.Dipartimento;

import Storage.Formato.Formato;

import java.text.Normalizer;

public class Dipartimento {

    private String nome;
    private String codice;
    private Formato formato;

    public Dipartimento(String nome, String codice) {
        this.nome = nome;
        this.codice = codice;
        //this.formato = new Formato();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public Formato getFormato() {
        return formato;
    }

    //Serve?
    /*public void setFormato(Formato formato) {
        this.formato = formato;
    }*/

    public Formato impostaFormato (Formato formato) {
        return this.formato=formato;
    }

    @Override
    public String toString() {
        return "Dipartimento{" +
                "nome='" + nome + '\'' +
                ", codice='" + codice + '\'' +
                ", formato=" + formato +
                '}';
    }
}
