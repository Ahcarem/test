/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActionService;

import DataObject.IRAMManagement;
import DataObject.RAMItem;
import Utility.ValidUtil;
import static Utility.ValidUtil.isValidBus;
import static Utility.ValidUtil.isValidType;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
/**
 *
 * @author giakh
 */
public class RAMManagementSystem implements IRAMManagement{
    private List<RAMItem> ramItems;
    

    public RAMManagementSystem() {
        ramItems = new ArrayList<>();
        loadFromFile();
    }

    // them RAM moi
    @Override
    public void addRAMItem() {
        while (true) {            
           String code ;
            do {
                code = ValidUtil.getString("Enter Ram code: ");
                if (findRamByCode(code) == null) {
                    break;
                }else{
                    System.out.println("This code already exists.");
                }
            } while (true);
            
             String type;
            do {
                type = ValidUtil.getString("Enter RAM type (e.g., LPDDR5, DDR5, LPDDR4, DDR4): ");
                if (isValidType(type)) {  
                    break;
                } else {
                    System.out.println("Invalid RAM type.");
                }
            } while (true);
    
            
            String bus;
            do {
                bus = ValidUtil.getString("Enter RAM bus speed (e.g., 5600MHz, 4800MHz): ");
                if (isValidBus(bus)) {  
                    break;
                } else {
                    System.out.println("Invalid bus speed.");
                }
            } while (true);
    
            String brand = ValidUtil.getString("Enter RAM brand: ");
            
            int quantity = ValidUtil.getInt("Enter RAM quantity: ");
            
            
            LocalDate currenDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");  // dinh dang format
            String stringDate = currenDate.format(formatter); // luu thanh string

            RAMItem newItem = new RAMItem(code, type, bus, brand, quantity, stringDate, true);
            ramItems.add(newItem);
            
            System.out.println("RAM added successfully.");
            String askContinue = ValidUtil.getString("Would you like to add more products? (y/n): ");
            
            if (!askContinue.equalsIgnoreCase("y")) {
                break;
            }
        }
    }

    
    // search item
    @Override
    public void searchItems(){
           while (true) {
            System.out.println("\n+---------------------------+");
            System.out.println("|         Search Menu       |");
            System.out.println("+---------------------------+");
            System.out.printf("| %-25s |\n", "1. Search by Type");
            System.out.printf("| %-25s |\n", "2. Search by Bus");
            System.out.printf("| %-25s |\n", "3. Search by Brand");
            System.out.printf("| %-25s |\n", "4. Return to Main Menu");
            System.out.println("+---------------------------+");

            int choice = ValidUtil.getIntChoice("Enter your choice: ",1,4);
            List<RAMItem> results = new ArrayList<>();

            switch (choice) {
                case 1:
                    String type = ValidUtil.getString("Please enter type: ");
                    for (RAMItem item : ramItems) {
                        if (item.isActive() && item.getType().toLowerCase().contains(type.toLowerCase())) {
                            results.add(item);
                        }
                    }
                    displayOfResults(results, "Type");
                    break;
                case 2:
                    String bus = ValidUtil.getString("Please enter Bus: ");
                    for (RAMItem item : ramItems) {
                        if (item.isActive() && item.getBus().toLowerCase().contains(bus.toLowerCase())) {
                            results.add(item);
                        }
                    }
                    displayOfResults(results, "Bus");
                    break;
                case 3:
                    String brand = ValidUtil.getString("Enter Brand to search: ");
                    for (RAMItem item : ramItems) {
                        if (item.isActive() && item.getBrand().toLowerCase().contains(brand.toLowerCase())) {
                            results.add(item);
                        }
                    }
                    displayOfResults(results, "Brand");
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }

    // uppdate by code
    @Override
    public void updateRAMItem() {
        String code = ValidUtil.getString("Enter RAM code to update: ");
            RAMItem getItem = findRamByCode(code);

            if (getItem == null) {
                System.out.println("RAM item with code " + code + " not found.");
                return;
            }
            
            String newType = ValidUtil.getString("Enter new RAM type (e.g., LPDDRx, DDRx) or Enter BLANK to keep old data: ");
            if (!newType.trim().isEmpty()) {
                if (isValidType(newType)) {
                    getItem.setType(newType);
                } else {
                    System.out.println("Invalid RAM type format, keep old data.");
                }
            }

            String newBus = ValidUtil.getString("Enter new RAM bus speed (e.g., 5600MHz) or Enter BLANK to keep old data: ");
            if (!newBus.trim().isEmpty()) {
                if (isValidBus(newBus)) {
                    getItem.setBus(newBus);
            } else {
                System.out.println("Invalid bus speed format, keep old data.");
            }

            String newBrand = ValidUtil.getString("Enter new RAM brand or Enter BLANK to keep old data:");
            if (!newBrand.trim().isEmpty()) {
                getItem.setBrand(newBrand);
            }
            
            String newQuantityStr = ValidUtil.getString("Enter new RAM quantity or Enter BLANK to keep old data: ");
            
            if (!newQuantityStr.trim().isEmpty()) {
                try {
                    int newQuantity = Integer.parseInt(newQuantityStr);
                    getItem.setQuantity(newQuantity);
                } catch (NumberFormatException e) {  // bat loi khi chuyen sang int, null, ko phai int
                    System.out.println("Invalid quantity format, keep old data.");
                }
            }
            System.out.println("The product has been updated");
       }
    }

    // delete itemp
    @Override
    public void deleteRAMItem() {
        String code = ValidUtil.getString("Enter RAM code to delete: ");

        RAMItem itemToDelete = findRamByCode(code);

        if (itemToDelete == null) {
            System.out.println("RAM item code not found");
            return;
        }

        System.out.println("Do you want to delete this item? ");
        String confirmAction = ValidUtil.getString("Enter 'yes' to confirm: ");
        if (confirmAction.equalsIgnoreCase("yes")) {
                
            itemToDelete.setActive(false);
                
            System.out.println("RAM deleted successfully");
            saveToFile(ramItems);
        }else{
            System.out.println("Delete canceled.");
        }
    }
    
    //storeDataToFile
    public void storeDataToFile(){
        saveToFile(ramItems);
        System.out.println("Data saved to file successfully!");
    }

    //
    public void printTable(List<RAMItem> ramItems) {
        System.out.println("Display All RAM :");
        String format = "| %-10s | %-10s | %-10s | %-10s | %-8s | %-15s | %-6s |%n";
        System.out.println("+------------+------------+------------+------------+----------+-----------------+--------+");
        System.out.printf(format, "Code", "Type", "Bus", "Brand", "Qty", "Production Date", "Active");
        System.out.println("+------------+------------+------------+------------+----------+-----------------+--------+");

        
        for (RAMItem item : ramItems) {
            System.out.printf(format, item.getCode(), item.getType(), item.getBus(),
                    item.getBrand(), item.getQuantity(), item.getProductionMonthYear(), item.isActive() ? "Yes" : "No");
        }

        
        System.out.println("+------------+------------+------------+------------+----------+-----------------+--------+");
    }

    @Override
    public void showAllRAMItems() {
        List<RAMItem> activeItems = new ArrayList<>();
                for (RAMItem item : ramItems) {
                    if (item.isActive()) {
                        activeItems.add(item);
                    }
                }
                printTable(activeItems);
    }
    

    
    
    public String FILE_PATH = "RAMModule.dat";

    public void saveToFile(List<RAMItem> ramItems) {
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(ramItems);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    
    @Override
    public List<RAMItem> loadFromFile() {
        ramItems = new ArrayList<>();
        java.io.File file = new java.io.File(FILE_PATH);

        if (!file.exists() || file.length() == 0) {
            System.out.println("No saved data found or file is empty.");
            return ramItems;  // Trả về danh sách rỗng nếu file không tồn tại hoặc rỗng
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                ramItems = (List<RAMItem>) obj; // ep kieu
                System.out.println("Data loaded successfully.");
            } else {
                System.out.println("File does not contain valid data.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
            e.printStackTrace();  // In lỗi chi tiết ra console
        }

        return ramItems;

    }
    
    
    private RAMItem findRamByCode(String code) {
          for (RAMItem ramItem : ramItems) {
            if (ramItem.getCode().equalsIgnoreCase(code)) {
                return ramItem;
            }
        }
        return null;
    }
    
    private void displayOfResults(List<RAMItem> results, String name) {
        if (results.isEmpty()) {
            System.out.println("RAM of " + name + "could not be found.");
        } else {
            System.out.println("\nSearch Results by " + name + ":");
            for (RAMItem item : results) {
                System.out.println("Code: " + item.getCode() + ", " + name + ": "
                        + (name.equals("Type") ? item.getType()
                        : name.equals("Bus") ? item.getBus() : item.getBrand())
                        + ", Production Date: " + item.getProductionMonthYear()
                        + ", Quantity: " + item.getQuantity());
            }
        }
    }
    
    
}
