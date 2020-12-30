package model;

import persistence.Writable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

// Represents an order with specified menu items
public class Order implements Writable {

    private static int PreviousOrderId = 0;     // latest Order ID
    protected static final String[] days = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    private int id;                             // this order ID
    private List<MenuItem> itemList;            // list of items in order
    private Double total;                       // total value of items in itemList in dollars
    private Calendar date;

    // constructs an order with an empty itemList and zero total;
    // order id is unique; PreviousOrderId is incremented
    public Order() {
        itemList = new ArrayList<>();
        total = 0.00;
        id = ++PreviousOrderId;
    }

    //MODIFIES: this
    //EFFECTS: if itemList does not contain item, then adds item to itemList
    //         otherwise, increases the quantity of the item in itemList
    public void addItem(MenuItem item) {
        for (MenuItem i : itemList) {
            if (i.equals(item)) {
                i.setQuantity(i.getQuantity() + item.getQuantity());
            }
        }
        if (!itemList.contains(item)) {
            itemList.add(item);
        }
    }

    //MODIFIES: this
    //EFFECTS: if item is in the itemList, then removes item from the itemList
    //         and subtracts its price from the total, does nothing otherwise
    public void removeItem(MenuItem item) {
        if (contains(item)) {
            itemList.remove(item);
        }
    }

    //MODIFIES: this
    //EFFECTS: sets date to the date when called,
    //         date is set as order payment is processed
    public void setDate() {
        date = new GregorianCalendar();
    }

    //MODIFIES: this
    //EFFECTS: sets date to year, month, day of month, hour of day, and minute
    public void setDate(int year, int month, int dayOfMonth, int hourOfDay, int minute) {
        date = new GregorianCalendar();
        date.set(year, month, dayOfMonth, hourOfDay, minute);
    }

    //EFFECTS: returns order in string format
    public String toString() {
        String weekDay = days[getDayOfWeek()];
        String items = "";
        for (MenuItem item : itemList) {
            items = items + item.toString() + "\n";
        }
        return String.format(
                "Your " + weekDay + " " + getAmPm() + " order:\n\n" + items + "\n%-60s $%.2f","Total:", getTotal());
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);             //!!!
        json.put("itemList", itemListToJson());
        json.put("date", dateToJson());
        json.put("total", total);
        return json;
    }

    // JsonSerializationDemo.model.WorkRoom
    // EFFECTS: returns itemList as a JSON array
    private JSONArray itemListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (MenuItem item : itemList) {
            jsonArray.put(item.toJson());
        }

        return jsonArray;
    }

    //EFFECTS: returns date as a JSONObject
    private JSONObject dateToJson() {
        JSONObject json = new JSONObject();
        json.put("year", date.get(Calendar.YEAR));
        json.put("month", date.get(Calendar.MONTH));
        json.put("day", date.get(Calendar.DAY_OF_MONTH));
        json.put("hour", date.get(Calendar.HOUR_OF_DAY));
        json.put("minute", date.get(Calendar.MINUTE));
        return json;
    }

    //getters

    public MenuItem getItemByName(String name) {
        for (MenuItem i : itemList) {
            if (name.equals(i.name)) {
                return i;
            }
        }
        return null;
    }

    public int getDayOfWeek() {
        return date.get(Calendar.DAY_OF_WEEK) - 1;
    }

    private boolean isDateAM() {
        return date.get(Calendar.AM_PM) == Calendar.AM;
    }

    public String getAmPm() {
        if (isDateAM()) {
            return "AM";
        } else {
            return "PM";
        }
    }

    //EFFECTS: adds the prices of items in itemList to total and returns it
    public Double getTotal() {
        total = 0.00;
        for (MenuItem i : itemList) {
            total += i.price;
        }
        return total;
    }

    public int getPreviousOrderId() {
        return PreviousOrderId;
    }

    public int getId() {
        return id;
    }

    public List<MenuItem> getItemList() {
        return itemList;
    }

    public List<String> getItemNames() {
        List<String> names = new ArrayList<>();
        for (MenuItem i : itemList) {
            names.add(i.name);
        }
        return names;
    }

    public List<Double> getItemPrices() {
        List<Double> prices = new ArrayList<>();
        for (MenuItem i : itemList) {
            prices.add(i.price);
        }
        return prices;
    }

    public int size() {
        return itemList.size();
    }

    public boolean contains(MenuItem item) {
        return itemList.contains(item);
    }
}
