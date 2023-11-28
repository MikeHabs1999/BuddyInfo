import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

public class AddressBookTest {

    private AddressBook addressBook;
    private BuddyInfo buddy1, buddy2;

    @BeforeEach
    public void setUp() {
        addressBook = new AddressBook();
        buddy1 = new BuddyInfo("Alice", "123 Maple Street", "555-1234");
        buddy2 = new BuddyInfo("Bob", "456 Oak Street", "555-5678");
        addressBook.addBuddy(buddy1);
        addressBook.addBuddy(buddy2);
    }

    @Test
    public void testSaveAndLoad() throws IOException {
        String filename = "testAddressBook.txt";
        addressBook.save(filename);

        AddressBook newAddressBook = new AddressBook();
        newAddressBook.importAddressBook(filename);

        assertEquals(addressBook.myBuddies.size(), newAddressBook.myBuddies.size());
        for (int i = 0; i < addressBook.myBuddies.size(); i++) {
            assertEquals(addressBook.myBuddies.get(i).toString(), newAddressBook.myBuddies.get(i).toString());
        }

        // Clean up the test file
        new File(filename).delete();
    }

    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        String filename = "testAddressBookSerialization.ser";
        addressBook.serialize(filename);

        AddressBook deserializedAddressBook = AddressBook.deserialize(filename);
        assertEquals(addressBook.myBuddies.size(), deserializedAddressBook.myBuddies.size());
        for (int i = 0; i < addressBook.myBuddies.size(); i++) {
            BuddyInfo original = addressBook.myBuddies.get(i);
            BuddyInfo deserialized = deserializedAddressBook.myBuddies.get(i);
            assertEquals(original.getName(), deserialized.getName());
            assertEquals(original.getAddress(), deserialized.getAddress());
            assertEquals(original.getPhone(), deserialized.getPhone());
        }

        // Clean up the test file
        new File(filename).delete();
    }

    @Test
    public void testXmlSerializationAndDeserialization() throws IOException, ParserConfigurationException, SAXException {
        String filename = "testAddressBook.xml";
        addressBook.exportToXmlFile(filename);

        AddressBook newAddressBook = new AddressBook();
        newAddressBook.importFromXmlFile(filename);

        assertEquals(addressBook.myBuddies.size(), newAddressBook.myBuddies.size());
        for (int i = 0; i < addressBook.myBuddies.size(); i++) {
            assertEquals(addressBook.myBuddies.get(i).getName(), newAddressBook.myBuddies.get(i).getName());
            assertEquals(addressBook.myBuddies.get(i).getAddress(), newAddressBook.myBuddies.get(i).getAddress());
            assertEquals(addressBook.myBuddies.get(i).getPhone(), newAddressBook.myBuddies.get(i).getPhone());
        }

        // Clean up the test file
        new File(filename).delete();
    }
}
