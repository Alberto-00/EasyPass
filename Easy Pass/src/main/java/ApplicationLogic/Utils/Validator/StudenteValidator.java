package ApplicationLogic.Utils.Validator;

public class StudenteValidator {

    public static String checkGP(String str) {
        if (str.isEmpty()){
            return "Nessun Green Pass inserito.";
        }else if (!str.startsWith("HC1:")){
            return "Errore! Formato non valido.";
        }
        return "Green Pass inviato correttamente.";
    }
}
