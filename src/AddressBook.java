import java.util.ArrayList;

public class AddressBook {

    private ArrayList<BuddyInfo> myBuddies;

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

    public static void main(String[] args) {
        BuddyInfo buddy = new BuddyInfo("Tom", "Carleton", "613");
        AddressBook addressbook = new AddressBook();
        addressbook.addBuddy(buddy);
        addressbook.removeBuddy(buddy);
    }

    // testing changes
}
