import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AddressBook implements Serializable{

    public final ArrayList<BuddyInfo> myBuddies;

    public AddressBook() {
        myBuddies = new ArrayList<>();
    }

    public void addBuddy(BuddyInfo aBuddy) {
        if (aBuddy != null) {
            myBuddies.add(aBuddy);
        }
    }

    public void removeBuddy(BuddyInfo aBuddy) {
        if (aBuddy != null) {
            myBuddies.remove(aBuddy);
        }
    }
    public void add2Buddies(BuddyInfo aBuddy, BuddyInfo a2ndBuddy) {
        myBuddies.add(aBuddy);
        myBuddies.add(a2ndBuddy);
    }

    public static void main(String[] args) {
        BuddyInfo buddy = new BuddyInfo("Tom", "Carleton", "613");
        AddressBook addressbook = new AddressBook();
        addressbook.addBuddy(buddy);
        addressbook.removeBuddy(buddy);
    }

    public void save(String filename) {
        try (PrintWriter out = new PrintWriter(filename)) {
            for (BuddyInfo buddy : myBuddies) {
                out.println(buddy.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void importAddressBook(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                this.addBuddy(BuddyInfo.importBuddyInfo(line));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void serialize(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
        }
    }

    public static AddressBook deserialize(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (AddressBook) in.readObject();
        }
    }

    public String toXML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<AddressBook>");
        for (BuddyInfo buddy : myBuddies) {
            sb.append(buddy.toXML());
        }
        sb.append("</AddressBook>");
        return sb.toString();
    }

    public void exportToXmlFile(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(this.toXML());
        }
    }

    public void importFromXmlFile(String filename) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(new File(filename), new AddressBookHandler(this));
    }

}
