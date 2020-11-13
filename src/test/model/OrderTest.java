package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    private Order order;
    private MenuItem i1;
    private MenuItem i2;
    private MenuItem i3;

    @BeforeEach
    public void setUp() {
        order = new Order();
        i1 = new Beverage("Ginger Beer", 3.00, Beverage.NOT_CUSTOMIZABLE, Beverage.NOT_CUSTOMIZABLE);
        i2 = new Dish("Croissant Sandwich", 7.00);
        i3 = new Dish("Fruit Tart", 5.00);
    }

    @Test
    public void testConstructor() {
        assertEquals(order.getPreviousOrderId(), order.getId());
        assertEquals(0, order.size());
        assertEquals(0, order.getTotal());
    }

    @Test
    public void testAddItems() {
        order.addItem(i1);

        //check i1 has been added and price of i1 has been added to total
        assertEquals(1, order.size());
        assertTrue(order.contains(i1));
        assertEquals(3.00, order.getTotal());

        //add multiple items
        order.addItem(i2);
        order.addItem(i3);

        //check all three items are in order and prices have been added
        assertEquals(3, order.size());
        assertTrue(order.contains(i2));
        assertTrue(order.contains(i3));
        assertEquals(15.00, order.getTotal());
    }

    @Test
    public void testRemoveItemEmptyList() {
        order.removeItem(i1);

        assertEquals(0, order.size());
        assertEquals(0, order.getTotal());
    }

    @Test
    public void testRemoveItemNotInList() {
        //add i1 and i2
        order.addItem(i1);
        order.addItem(i2);

        //try to remove i3
        order.removeItem(i3);

        //check that order only contains i1 and i2, not i3 and price is unchanged
        assertEquals(2, order.size());
        assertTrue(order.contains(i1));
        assertTrue(order.contains(i2));
        assertFalse(order.contains(i3));
        assertEquals(10.00, order.getTotal());
    }

    @Test
    public void testRemoveItemsInList() {
        //add i1, i2, and i3
        order.addItem(i1);
        order.addItem(i2);
        order.addItem(i3);

        //remove i1
        order.removeItem(i1);

        //check that order only contains i2 and i3, not i1 and price is subtracted from total
        assertEquals(2, order.size());
        assertTrue(order.contains(i2));
        assertTrue(order.contains(i3));
        assertFalse(order.contains(i1));
        assertEquals(12.00, order.getTotal());


        //remove multiple
        order.removeItem(i2);
        order.removeItem(i3);

        //check that order contains no items and total is 0
        assertEquals(0, order.size());
        assertFalse(order.contains(i2));
        assertFalse(order.contains(i3));
        assertEquals(0.00, order.getTotal());
    }

    //EFFECTS: returns the item in itemList if already there,
    //         if not, returns null
    @Test
    public void testItemNameNoItemMatch() {
        //check empty itemList returns null
        assertNull(order.getItemByName("Ginger Beer"));

        //add i1, and i2
        order.addItem(i1);
        order.addItem(i2);

        //check i3 name returns null
        assertNull(order.getItemByName("Fruit Tart"));
    }

    @Test
    public void testItemNameHasItemMatch() {
        //add i1, i2, and i3
        order.addItem(i1);
        order.addItem(i2);
        order.addItem(i3);

        //get croissant sandwich
        assertEquals(i2, order.getItemByName("Croissant Sandwich"));
    }

    @Test
    public void testGetItemNames() {
        //add i1, i2, and i3
        order.addItem(i1);
        order.addItem(i2);
        order.addItem(i3);
        List<String> names1 = new ArrayList<>();
        for (MenuItem i : order.getItemList()) {
            names1.add(i.getName());
        }

        //check names2 has size 3 and contains all elements of names1
        List<String> names2 = order.getItemNames();
        assertEquals(3, names2.size());
        for (String n : names1) {
            assertTrue(names2.contains(n));
        }
    }

    @Test
    public void testGetItemPrices() {
        //add i1, i2, and i3
        order.addItem(i1);
        order.addItem(i2);
        order.addItem(i3);
        List<Double> prices1 = new ArrayList<>();
        for (MenuItem i : order.getItemList()) {
            prices1.add(i.getPrice());
        }

        //check prices2 has size 3 and contains all elements of prices1
        List<Double> prices2 = order.getItemPrices();
        assertEquals(3, prices2.size());
        for (Double p : prices1) {
            assertTrue(prices2.contains(p));
        }
    }

    @Test
    public void testSetDate() {
        //set the date
        order.setDate();

        //check day of week and AM/PM
        Calendar testDate = new GregorianCalendar();
        assertEquals(testDate.get(Calendar.DAY_OF_WEEK) - 1, order.getDayOfWeek());
        assertEquals(testDate.get(Calendar.AM_PM) == Calendar.AM, order.isDateAM());
    }

    @Test
    public void testToString() {
        //add i1, i2, and i3
        order.addItem(i1);
        order.addItem(i2);
        order.addItem(i3);
        //set date
        order.setDate();
        //expected string format of item
        String items = "";
        for (MenuItem item : order.getItemList()) {
            items = items + item.toString() + "\n";
        }

        assertEquals("Your " + Order.days[order.getDayOfWeek()] + " " + order.getAmPm() + " order:\n" + items
                + "\nTotal: $15.00", order.toString());

    }
}
