package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// JsonSerializationDemo.persistence.JsonReader
// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads account from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Account read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAccount(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses account from JSON object and returns it
    private Account parseAccount(JSONObject jsonObjectAccount) {
        String name = jsonObjectAccount.getString("name");
        Account account = new Account(name);
        addOrders(account, jsonObjectAccount);
        return account;
    }

    // MODIFIES: account
    // EFFECTS: parses order history from JSON object and adds them to account
    private void addOrders(Account account, JSONObject jsonObjectAccount) {
        JSONArray jsonArray = jsonObjectAccount.getJSONArray("history");
        for (Object json : jsonArray) {
            JSONObject jsonObjectOrder = (JSONObject) json;
            addOrder(account, jsonObjectOrder);
        }
    }

    // MODIFIES: account
    // EFFECTS: parses an order from JSON object and adds it to account
    private void addOrder(Account account, JSONObject jsonObjectOrder) {
        Order order = new Order();
        JSONArray jsonArray = jsonObjectOrder.getJSONArray("itemList");
        for (Object json : jsonArray) {
            JSONObject jsonObjectItem = (JSONObject) json;
            addItem(order, jsonObjectItem);
        }
        account.addOrder(order);
    }

    // MODIFIES: order
    // EFFECTS: parses a menu item from JSON object and adds it to order
    private void addItem(Order order, JSONObject jsonObjectItem) {
        String name = jsonObjectItem.getString("name");
        int price = jsonObjectItem.getInt("price");
        String type = jsonObjectItem.getString("type");
        if (type.equals("beverage")) {
            int size = jsonObjectItem.getInt("size");
            int temperature = jsonObjectItem.getInt("temperature");
            Beverage beverage = new Beverage(name, price, size, temperature);
            order.addItem(beverage);
        } else {
            Dish dish = new Dish(name, price);
            try {
                JSONObject jsonObjectSelected = jsonObjectItem.getJSONObject("selected");
                AdditionalOptions selected = parseAdditionalOptions(jsonObjectSelected);
                dish.selectAddOn(selected);
            } catch (JSONException e) {
                // do nothing
            }
            addOptions(jsonObjectItem, dish);
            order.addItem(dish);
        }
    }

    // MODIFIES: dish
    // EFFECTS: parses add-ons from JSON object and adds it to dish
    private void addOptions(JSONObject jsonObject, Dish dish) {
        JSONArray jsonArray = jsonObject.getJSONArray("options");
        for (Object json : jsonArray) {
            JSONObject jsonObjectAddOn = (JSONObject) json;
            AdditionalOptions addOn = parseAdditionalOptions(jsonObjectAddOn);
            dish.addSideToOptions(addOn);
        }
    }

    // EFFECTS: parses an add-on from JSON object and returns it
    private AdditionalOptions parseAdditionalOptions(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int price = jsonObject.getInt("price");
        AdditionalOptions addOn = new AdditionalOptions(name, price);
        return addOn;
    }


}
