package model;

import exceptions.IllegalQuantityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class MenuItemTest {

    private MenuItem item;

    @BeforeEach
    public void setUp() {
        item = new Beverage("Coffee", 3.00, Beverage.REGULAR, Beverage.REGULAR);
    }

    @Test
    public void testConstructor() {
        assertEquals("Coffee", item.getName());
        assertEquals(3.00, item.getPrice());
        assertEquals(1, item.getQuantity());
    }

//    @Test
//    public void testSetIllegalQuantity() {
//        try {
//            item.setQuantity(0);
//            fail("IllegalQuantityException should have been thrown");
//        } catch (IllegalQuantityException e) {
//            //pass
//        }
//    }

    @Test
    public void testSetLegalQuantity() {
//        try {
            item.setQuantity(2);
            //pass
//        } catch (IllegalQuantityException e) {
//            fail("IllegalQuantityException should not have been thrown");
//        }
        assertEquals(2, item.getQuantity());
        assertEquals(6.00, item.getPrice());
    }
}

