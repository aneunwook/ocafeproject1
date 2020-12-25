package persistence;

import org.json.JSONObject;

// JsonSerializationDemo.model.persistence
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
