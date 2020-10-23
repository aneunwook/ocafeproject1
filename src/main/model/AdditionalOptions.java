package model;

public class AdditionalOptions extends MenuItem {

    public AdditionalOptions(String name, Integer price) {
        super(name, price);
    }

    public String toString() {
        return String.format("\n$" + price + "\t\t" + name);
    }

}
