package ApplicationLogic.Utils;

import org.json.simple.JSONObject;

/**
 * Usato per trasformare un oggetto in {@code JSON}
 */
public interface JSONSerializable {
    JSONObject toJson();
}
