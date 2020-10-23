package model;

import model.Account;
import model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    private Account account;
    private Order o1;
    private Order o2;
    private Order o3;

    @BeforeEach
    public void setUp() {
        account = new Account("Vanessa");
        o1 = new Order();
        o2 = new Order();
        o3 = new Order();
    }

    @Test
    public void testConstructor() {
        assertEquals("Vanessa", account.getName());
        assertEquals(0, account.getHistory().size());
        assertEquals("./data/Vanessa.json", account.getFile());
    }

    @Test
    public void testAddOrder() {
        account.addOrder(o1);

        //check history contains o1 and size is 1
        assertEquals(1, account.getHistory().size());
        assertTrue(account.getHistory().contains(o1));

        //add multiple orders
        account.addOrder(o2);
        account.addOrder(o3);

        //check history contains o1, o2, o3 and size is 3
        assertEquals(3, account.getHistory().size());
        assertTrue(account.getHistory().contains(o1));
        assertTrue(account.getHistory().contains(o2));
        assertTrue(account.getHistory().contains(o3));
    }
}
