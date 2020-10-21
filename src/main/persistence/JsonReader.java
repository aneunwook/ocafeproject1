package persistence;

import model.Account;
import model.Order;
import org.json.JSONArray;
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

        try (Stream<String> stream = Files.lines( Paths.get(source), StandardCharsets.UTF_8)) {
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
        for (Object order : jsonArray) {
            JSONObject jsonObjectOrder = (JSONObject) order;
            addOrder(account, jsonObjectOrder);
        }
    }

    // MODIFIES: account
    // EFFECTS: parses an order from JSON object and adds it to account
    private void addOrder(Account account, JSONObject jsonObjectOrder) {
        int total = Integer.parseInt(jsonObjectOrder.getString("total"));   // total gets augmented when items get added
        JSONArray jsonArray = jsonObjectOrder.getJSONArray("itemList");
        Order order = new Order();
        order.
        for (Object item : jsonArray) {
            JSONObject jsonObjectItem = (JSONObject) item;
            addItem(account, jsonObjectItem);
        }

        account.addOrder(order);
    }
}
