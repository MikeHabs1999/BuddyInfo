import java.io.*;

public class BuddyInfo implements Serializable {
    private String name;
    private String phone;
    private String address;

    public BuddyInfo(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    // No-argument constructor
    public BuddyInfo() {
        // This constructor allows instantiation without setting properties initially.
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
    public String getPhone() {
        return phone;
    }


    public static void main(String[] args) {
        String name = "Mike";
        System.out.print("Hello " + name);
    }

    public String toString() {
        return name + "#" + address + "#" + phone;
    }


    public static BuddyInfo importBuddyInfo(String str) {
        String[] parts = str.split("#");
        return new BuddyInfo(parts[0], parts[1], parts[2]);
    }

    public String toXML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<BuddyInfo>");
        sb.append("<Name>").append(name).append("</Name>");
        sb.append("<Address>").append(address).append("</Address>");
        sb.append("<Phone>").append(phone).append("</Phone>");
        sb.append("</BuddyInfo>");
        return sb.toString();
    }

}
