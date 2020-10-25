package model;

import model.Account;
import model.Order;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonTest;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//JsonSerializationDemo.model.JsonWriterTest
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Account account = new Account("Vanessa");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyOrderHistory() {
        try {
            Account account = new Account("Vanessa");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyOrderHistory.json");
            writer.open();
            writer.write(account);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyOrderHistory.json");
            Account parsedAccount = reader.read();
            assertEquals("Vanessa", parsedAccount.getName());
            assertEquals(0, parsedAccount.getHistory().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralAccount() {
        try {
            Account account = new Account("Vanessa");
            account.addOrder(o1);
            account.addOrder(o2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAccount.json");
            writer.open();
            writer.write(account);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAccount.json");
            Account parsedAccount = reader.read();
            assertEquals("Vanessa", parsedAccount.getName());
            List<Order> orderHistory = parsedAccount.getHistory();
            assertEquals(2, orderHistory.size());
            checkOrder(o1.getTotal(), o1.getItemList(), orderHistory.get(0));
            checkOrder(o2.getTotal(), o2.getItemList(), orderHistory.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}