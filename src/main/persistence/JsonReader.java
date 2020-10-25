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
// Represents a reader that reads account from JSON data stored in file
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

        Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8);
        try {
            stream.forEach(s -> contentBuilder.append(s));
        } finally {
            stream.close();
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses account from JSON object and returns it
    private Account parseAccount(JSONObject jsonAccount) {
        String name = jsonAccount.getString("name");
        Account account = new Account(name);
        addOrders(account, jsonAccount);
        return account;
    }

    // MODIFIES: account
    // EFFECTS: parses order history from JSON object and adds them to account
    private void addOrders(Account account, JSONObject jsonAccount) {
        JSONArray jsonArray = jsonAccount.getJSONArray("history");
        for (Object json : jsonArray) {
            JSONObject jsonOrder = (JSONObject) json;
            addOrder(account, jsonOrder);
        }
    }

    // MODIFIES: account
    // EFFECTS: parses an order from JSON object and adds it to account
    private void addOrder(Account account, JSONObject jsonOrder) {
        Order order = new Order();
        setOrderDate(jsonOrder, order);

        JSONArray jsonArray = jsonOrder.getJSONArray("itemList");
        for (Object json : jsonArray) {
            JSONObject jsonItem = (JSONObject) json;
            addItem(order, jsonItem);
        }
        account.addOrder(order);
    }

    // MODIFIES: account
    // EFFECTS: parses a Calendar from JSON object and sets it to order
    private void setOrderDate(JSONObject jsonOrder, Order order) {
        JSONObject jsonDate = jsonOrder.getJSONObject("date");
        int year = jsonDate.getInt("year");
        int month = jsonDate.getInt("month");
        int day = jsonDate.getInt("day");
        int hour = jsonDate.getInt("hour");
        int minute = jsonDate.getInt("minute");

        order.setDate(year, month, day, hour, minute);
    }

    // MODIFIES: order
    // EFFECTS: parses a menu item from JSON object and adds it to order
    private void addItem(Order order, JSONObject jsonItem) {
        String name = jsonItem.getString("name");
        int price = jsonItem.getInt("price");
        String type = jsonItem.getString("type");
        if (type.equals("beverage")) {
            int size = jsonItem.getInt("size");
            int temperature = jsonItem.getInt("temperature");
            Beverage beverage = new Beverage(name, price, size, temperature);
            order.addItem(beverage);
        } else {
            Dish dish = new Dish(name, price);
            try {
                JSONObject jsonSelected = jsonItem.getJSONObject("selected");
                AdditionalOptions selected = parseAdditionalOptions(jsonSelected);
                dish.selectAddOn(selected);
            } catch (JSONException e) {
                // do nothing
            }
            addOptions(jsonItem, dish);
            order.addItem(dish);
        }
    }

    // MODIFIES: dish
    // EFFECTS: parses add-ons from JSON object and adds it to dish
    private void addOptions(JSONObject jsonItem, Dish dish) {
        JSONArray jsonArray = jsonItem.getJSONArray("options");
        for (Object json : jsonArray) {
            JSONObject jsonAddOn = (JSONObject) json;
            AdditionalOptions addOn = parseAdditionalOptions(jsonAddOn);
            dish.addSideToOptions(addOn);
        }
    }

    // EFFECTS: parses an add-on from JSON object and returns it
    private AdditionalOptions parseAdditionalOptions(JSONObject jsonAddOn) {
        String name = jsonAddOn.getString("name");
        int price = jsonAddOn.getInt("price");
        AdditionalOptions addOn = new AdditionalOptions(name, price);
        return addOn;
    }


}
