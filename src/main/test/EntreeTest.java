package test;

import model.Entree;
import model.Side;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntreeTest {

    private Entree entree;
    private Side s1;
    private Side s2;
    private Side s3;

    @BeforeEach
    public void setUp() {
        entree = new Entree("Salad", 10);
        s1 = new Side("Steak", 6);
        s2 = new Side("Tuna", 5);
        s3 = new Side("Chicken", 5);
    }

    @Test
    public void testConstructor() {
        assertEquals("Salad", entree.getName());
        assertEquals(10, entree.getPrice());
        assertEquals(0, entree.getAddOnOptions().size());
        assertNull(entree.getSelected());
    }

    @Test
    public void testAddSideToAddOnOptions() {
        entree.addSideToAddOns(s1);

        assertEquals(1, entree.getAddOnOptions().size());
        assertTrue(entree.getAddOnOptions().contains(s1));

        //add multiple sides to addOnOptions
        entree.addSideToAddOns(s2);
        entree.addSideToAddOns(s3);

        assertEquals(3, entree.getAddOnOptions().size());
        assertTrue(entree.getAddOnOptions().contains(s2));
        assertTrue(entree.getAddOnOptions().contains(s3));
    }
}
