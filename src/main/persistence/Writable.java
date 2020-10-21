package persistence;

import org.json.JSONObject;

// JsonSerializationDemo.persistence
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
