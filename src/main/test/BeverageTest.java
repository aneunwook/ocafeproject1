package test;

import model.Beverage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BeverageTest {

    private Beverage beverage;

    @BeforeEach
    public void setUp() {
        beverage = new Beverage("Coffee", 3);
    }

    @Test
    public void testConstructor() {
        assertEquals("Coffee", beverage.getName());
        assertEquals(3, beverage.getPrice());
        assertEquals(beverage.REGULAR, beverage.getSize());
        assertEquals(beverage.COLD, beverage.getTemperature());
    }

    @Test
    public void testIsSizeCustomizable() {
        //set to large
        beverage.setSize(beverage.LARGE);
        //check size is customizable
        assertTrue(beverage.isSizeCustomizable());

        //set to not customizable
        beverage.setSize(beverage.SIZE_NOT_CUSTOMIZABLE);
        //check size is not customizable
        assertEquals(beverage.SIZE_NOT_CUSTOMIZABLE, beverage.getSize());
        assertFalse(beverage.isSizeCustomizable());
    }

    @Test
    public void testIsTemperatureCustomizable() {
        beverage.setTemperature(beverage.HOT);
        assertTrue(beverage.isTemperatureCustomizable());

        beverage.setTemperature(beverage.TEMPERATURE_NOT_CUSTOMIZABLE);
        assertEquals(beverage.TEMPERATURE_NOT_CUSTOMIZABLE, beverage.getTemperature());
        assertFalse(beverage.isTemperatureCustomizable());
    }
}
