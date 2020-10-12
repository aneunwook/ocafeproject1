package model;

public abstract class MenuItem {

    protected String name;
    protected int price;        // price in dollars

    // constructs a MenuItem with a name and price
    public MenuItem(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    //EFFECTS: returns item name
    public String getName() {
        return name;
    }

    //EFFECTS: returns item price
    public int getPrice() {
        return price;
    }

}
