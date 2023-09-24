public class BuddyInfo {
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
    public String getPhone() {
        return phone;
    }


    public BuddyInfo(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    private String name;
    private String phone;
    private String address;


    public static void main(String[] args) {
        String name = "Mike";
        System.out.print("Hello ");
        System.out.println(name);
    }
}
