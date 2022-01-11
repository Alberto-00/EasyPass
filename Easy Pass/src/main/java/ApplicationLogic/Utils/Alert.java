package ApplicationLogic.Utils;

import java.util.List;

/**
 * La classe crea una lista di messaggi di errore e li associa al tipo
 * corrispondente di errore.
 */
public class Alert {

    private final List<String> messages;
    private final String type;

    /**
     * @param messages lista di messaggi di errore
     * @param type tipo di errore
     */
    public Alert(List<String> messages, String type){
        this.messages = messages;
        this.type = type;
    }

    public List<String> getMessages() {
        return messages;
    }

    public String getType() {
        return type;
    }
}