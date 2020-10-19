package test;

import model.Beverage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BeverageTest {

    private Beverage beverage;

    @BeforeEach
    public void setUp() {
        beverage = new Beverage("Coffee", 3, Beverage.REGULAR, Beverage.REGULAR);
    }

    @Test
    public void testConstructor() {
        assertEquals("Coffee", beverage.getName());
        assertEquals(3, beverage.getPrice());
        assertEquals(beverage.REGULAR, beverage.getSize());
        assertEquals(beverage.REGULAR, beverage.getTemperature());
    }

//    @Test
//    public void testIsSizeCustomizable() {
//        //check size is customizable
//        assertTrue(beverage.isSizeCustomizable());
//
//        //set to not customizable
//        beverage.setSize(beverage.SIZE_NOT_CUSTOMIZABLE);
//
//        //check size is set to not customizable
//        assertFalse(beverage.isSizeCustomizable());
//    }
//
//    @Test
//    public void testIsTemperatureCustomizable() {
//        //check temperature is customizable
//        assertTrue(beverage.isTemperatureCustomizable());
//
//        //set to not customizable
//        beverage.setTemperature(beverage.TEMPERATURE_NOT_CUSTOMIZABLE);
//
//        //check temperature is set to not customizable
//        assertFalse(beverage.isTemperatureCustomizable());
//    }

    @Test
    public void testSizeIsCustomizableNoChange() {
        assertFalse(beverage.setSize(Beverage.REGULAR));

        //check that size and price are unchanged
        assertEquals(beverage.REGULAR, beverage.getSize());
        assertEquals(3, beverage.getPrice());
    }

    @Test
    public void testSizeIsChanged() {
        //set size to large
        assertTrue(beverage.setSize(beverage.EXTRA));

        //check size is large and price has increased by UPGRADE_PRICE
        assertEquals(beverage.EXTRA, beverage.getSize());
        assertEquals(3 + beverage.UPGRADE_PRICE, beverage.getPrice());

        //set size to regular
        assertTrue(beverage.setSize(beverage.REGULAR));

        //check that size is regular and price has decreased by UPGRADE_PRICE
        assertEquals(beverage.REGULAR, beverage.getSize());
        assertEquals(3, beverage.getPrice());
    }

    @Test
    public void testSizeNotCustomizable() {
        //set size to not customizable
        assertTrue(beverage.setSize(Beverage.NOT_CUSTOMIZABLE));
        assertEquals(beverage.NOT_CUSTOMIZABLE, beverage.getSize());

        //try to set size to large
        assertFalse(beverage.setSize(Beverage.EXTRA));

        //check that size and price are unchanged
        assertEquals(beverage.NOT_CUSTOMIZABLE, beverage.getSize());
        assertEquals(3, beverage.getPrice());
    }

    @Test
    public void testTemperatureIsCustomizableNoChange() {
        assertFalse(beverage.setTemperature(Beverage.REGULAR));

        //check that temperature and price are unchanged
        assertEquals(beverage.REGULAR, beverage.getTemperature());
        assertEquals(3, beverage.getPrice());
    }

    @Test
    public void testTemperatureIsChanged() {
        //set temperature to iced
        assertTrue(beverage.setTemperature(beverage.EXTRA));

        //check temperature is iced and price has increased by UPGRADE_PRICE
        assertEquals(beverage.EXTRA, beverage.getTemperature());
        assertEquals(3 + beverage.UPGRADE_PRICE, beverage.getPrice());

        //set temperature to hot
        assertTrue(beverage.setTemperature(beverage.REGULAR));

        //check that temperature is hot and price has decreased by UPGRADE_PRICE
        assertEquals(beverage.REGULAR, beverage.getTemperature());
        assertEquals(3, beverage.getPrice());
    }

    @Test
    public void testTemperatureNotCustomizable() {
        //set temperature to not customizable
        assertTrue(beverage.setTemperature(Beverage.NOT_CUSTOMIZABLE));
        assertEquals(beverage.NOT_CUSTOMIZABLE, beverage.getTemperature());

        //try to set temperature to cold
        assertFalse(beverage.setTemperature(Beverage.EXTRA));

        //check that temperature and price are unchanged
        assertEquals(beverage.NOT_CUSTOMIZABLE, beverage.getTemperature());
        assertEquals(3, beverage.getPrice());
    }



}
