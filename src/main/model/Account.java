package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

public class Account implements Writable {

    private String name;
    private List<Order> history;
//    private String address;

    public Account(String name) {
        this.name = name;
        history = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds order to history
    public void addOrder(Order order) {
        history.add(order);
    }

    //getters
    public String getName() {
        return name;
    }

    public List<Order> getHistory() {
        return history;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("history", historyToJson());
        return json;
    }

    // JsonSerializationDemo.model.WorkRoom
    // EFFECTS: returns history in this Account as a JSON array
    private JSONArray historyToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Order order : history) {
            jsonArray.put(order.toJson());
        }

        return jsonArray;
    }
}
