package model;

import model.Dish;
import model.AdditionalOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DishTest {

    private Dish dish;
    private AdditionalOptions s1;
    private AdditionalOptions s2;
    private AdditionalOptions s3;

    @BeforeEach
    public void setUp() {
        dish = new Dish("Salad", 10);
        s1 = new AdditionalOptions("Steak", 6);
        s2 = new AdditionalOptions("Tuna", 5);
        s3 = new AdditionalOptions("Chicken", 4);
    }

    @Test
    public void testConstructor() {
        assertEquals("Salad", dish.getName());
        assertEquals(10, dish.getPrice());
        assertEquals(0, dish.getOptions().size());
        assertNull(dish.getSelected());
    }

    @Test
    public void testAddSideToAddOnOptions() {
        // add s1 to addOnOptions
        dish.addSideToOptions(s1);

        //check s1 was added
        assertEquals(1, dish.getOptions().size());
        assertTrue(dish.getOptions().contains(s1));

        //add multiple sides to addOnOptions
        dish.addSideToOptions(s2);
        dish.addSideToOptions(s3);

        //check contains s2 and s3
        assertEquals(3, dish.getOptions().size());
        assertTrue(dish.getOptions().contains(s1));
        assertTrue(dish.getOptions().contains(s2));
        assertTrue(dish.getOptions().contains(s3));
    }

    @Test
    public void testSelectAddOnsEmpty() {
        assertFalse(dish.selectAddOn(s1));

        //check no add-on is selected and price is unchanged
        assertNull(dish.getSelected());
        assertEquals(10, dish.getPrice());
    }

    @Test
    public void testSelectAddOnsDoesNotContain() {
        //add s1 and s2 to addOnOptions
        dish.addSideToOptions(s1);
        dish.addSideToOptions(s2);

        //select s3
        assertFalse(dish.selectAddOn(s3));

        //check no add-on is selected and price is unchanged
        assertNull(dish.getSelected());
        assertEquals(10, dish.getPrice());
    }

    @Test
    public void testSelectAddOnsContains() {
        //add all to addOnOptions
        dish.addSideToOptions(s1);
        dish.addSideToOptions(s2);
        dish.addSideToOptions(s3);

        //select s1
        assertTrue(dish.selectAddOn(s1));
        //check s1 is selected and price is added
        assertEquals(s1, dish.getSelected());
        assertEquals(16, dish.getPrice());

        //reselect add-on to s2
        assertTrue(dish.selectAddOn(s2));
        //check s2 is selected and price is added
        assertEquals(s2, dish.getSelected());
        assertEquals(15, dish.getPrice());
    }

    @Test
    public void testUnselectAddOnNull() {
        assertFalse(dish.unselectAddOn());
        assertNull(dish.getSelected());
        assertEquals(10, dish.getPrice());
    }

    @Test
    public void testUnselectAddOnNotNull() {
        //add all to addOnOptions
        dish.addSideToOptions(s1);
        dish.addSideToOptions(s2);
        dish.addSideToOptions(s3);
        //select s1
        assertTrue(dish.selectAddOn(s1));

        //unselect the add-on
        assertTrue(dish.unselectAddOn());

        //check selected is null and price of s1 has been subtracted
        assertNull(dish.getSelected());
        assertEquals(10, dish.getPrice());
    }
}
