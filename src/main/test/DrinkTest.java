package test;

import model.Drink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DrinkTest {

    private Drink drink;

    @BeforeEach
    public void setUp() {
        drink = new Drink("Coffee", 3);
    }

    @Test
    public void testConstructor() {
        assertEquals("Coffee", drink.getName());
        assertEquals(3, drink.getPrice());
        assertEquals(drink.REGULAR, drink.getSize());
        assertEquals(drink.COLD, drink.getTemperature());
    }

    @Test
    public void testIsSizeCustomizable() {
        //set to large
        drink.setSize(drink.LARGE);
        //check size is customizable
        assertTrue(drink.isSizeCustomizable());

        //set to not customizable
        drink.setSize(drink.SIZE_NOT_CUSTOMIZABLE);
        //check size is not customizable
        assertEquals(drink.SIZE_NOT_CUSTOMIZABLE, drink.getSize());
        assertFalse(drink.isSizeCustomizable());
    }

    @Test
    public void testIsTemperatureCustomizable() {
        drink.setTemperature(drink.HOT);
        assertTrue(drink.isTemperatureCustomizable());

        drink.setTemperature(drink.TEMPERATURE_NOT_CUSTOMIZABLE);
        assertEquals(drink.TEMPERATURE_NOT_CUSTOMIZABLE, drink.getTemperature());
        assertFalse(drink.isTemperatureCustomizable());
    }
}
