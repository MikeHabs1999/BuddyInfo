import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class AddressBookGUI {
    private AddressBook addressBook;
    private JList<BuddyInfo> buddyList;
    private DefaultListModel<BuddyInfo> listModel;
    private JFrame frame;
    private JMenuItem exportItem;
    private JMenuItem importItem;

    public AddressBookGUI() {
        addressBook = new AddressBook();
        listModel = new DefaultListModel<>();
        buddyList = new JList<>(listModel);

        JMenuItem serializeItem = new JMenuItem("Serialize AddressBook");
        JMenuItem deserializeItem = new JMenuItem("Deserialize AddressBook");
        // Inside AddressBookGUI constructor

        JMenuItem exportXmlItem = new JMenuItem("Export to XML");
        JMenuItem importXmlItem = new JMenuItem("Import from XML");

        serializeItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    addressBook.serialize(file.getAbsolutePath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        deserializeItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    addressBook = AddressBook.deserialize(file.getAbsolutePath());
                    listModel.clear();
                    for (BuddyInfo buddy : addressBook.myBuddies) {
                        listModel.addElement(buddy);
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame = new JFrame("Address Book");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JMenuBar menuBar = new JMenuBar();
        JMenu addressBookMenu = new JMenu("File");
        JMenu buddyInfoMenu = new JMenu("Edit");

        JMenuItem createAddressBookItem = new JMenuItem("Create New AddressBook");
        JMenuItem displayAddressBookItem = new JMenuItem("Display AddressBook");
        JMenuItem addBuddyItem = new JMenuItem("Add BuddyInfo");
        JMenuItem removeBuddyItem = new JMenuItem("Remove BuddyInfo");
        exportItem = new JMenuItem("Export AddressBook");
        importItem = new JMenuItem("Import AddressBook");

        createAddressBookItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addressBook = new AddressBook();
                listModel.clear();
            }
        });

        displayAddressBookItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Display the AddressBook in the JList
                listModel.clear();
                for (BuddyInfo buddy : addressBook.myBuddies) {
                    listModel.addElement(buddy);
                }
            }
        });

        addBuddyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add a new BuddyInfo
                String name = JOptionPane.showInputDialog("Enter Name:");
                String address = JOptionPane.showInputDialog("Enter Address:");
                String phone = JOptionPane.showInputDialog("Enter Phone:");
                BuddyInfo buddy = new BuddyInfo(name, address, phone);
                addressBook.addBuddy(buddy);
                listModel.addElement(buddy);
            }
        });

        removeBuddyItem.addActionListener(e -> {
            // Remove a selected BuddyInfo
            int selectedIndex = buddyList.getSelectedIndex();
            if (selectedIndex != -1) {
                BuddyInfo buddyToRemove = listModel.get(selectedIndex);
                listModel.remove(selectedIndex);
                addressBook.removeBuddy(buddyToRemove);
            }
        });

        exportXmlItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    addressBook.exportToXmlFile(file.getAbsolutePath());
                } catch (IOException ex) {
                    ex.printStackTrace(); // Consider showing an error dialog
                }
            }
        });

        importXmlItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    addressBook.importFromXmlFile(file.getAbsolutePath());

                    // This is where you update the GUI list
                    listModel.clear();
                    for (BuddyInfo buddy : addressBook.myBuddies) {
                        listModel.addElement(buddy);
                    }

                } catch (ParserConfigurationException | SAXException | IOException ex) {
                    ex.printStackTrace(); // Consider showing an error dialog
                }
            }
        });

        addressBookMenu.add(createAddressBookItem);
        addressBookMenu.add(displayAddressBookItem);
        addressBookMenu.add(exportXmlItem);
        addressBookMenu.add(importXmlItem);
        buddyInfoMenu.add(addBuddyItem);
        buddyInfoMenu.add(removeBuddyItem);
        menuBar.add(addressBookMenu);
        menuBar.add(buddyInfoMenu);
        addressBookMenu.add(exportItem);
        addressBookMenu.add(importItem);
        addressBookMenu.add(serializeItem);
        addressBookMenu.add(deserializeItem);

        frame.setJMenuBar(menuBar);

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(buddyList), BorderLayout.CENTER);

        frame.setVisible(true);

        exportItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                addressBook.save(file.getAbsolutePath());
            }
        });

        importItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                addressBook.importAddressBook(file.getAbsolutePath());
                listModel.clear();
                for (BuddyInfo buddy : addressBook.myBuddies) {
                    listModel.addElement(buddy);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddressBookGUI());
    }

}

