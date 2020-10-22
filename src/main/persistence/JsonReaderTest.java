package persistence;

import model.Account;
import model.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//JsonSerializationDemo.persistence.JsonReaderTest
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Account account = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyOrderHistory() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyOrderHistory.json");
        try {
            Account account = reader.read();
            assertEquals("Vanessa", account.getName());
            assertEquals(0, account.getHistory().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAccount() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralAccount.json");
        try {
            Account account = reader.read();
            assertEquals("Vanessa", account.getName());
            List<Order> orderHistory = account.getHistory();
            assertEquals(2, orderHistory.size());
            checkOrder(o1.getTotal(), o1.getItemList(), orderHistory.get(0));
            checkOrder(o2.getTotal(), o2.getItemList(), orderHistory.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}