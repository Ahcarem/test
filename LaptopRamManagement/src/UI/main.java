/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import ActionService.RAMManagementSystem;
import DataObject.IRAMManagement;
import DataObject.RAMItem;
import java.util.Scanner;

/**
 *
 * @author giakh
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        RAMManagementSystem ramSystem = new RAMManagementSystem();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n---------- RAM MANAGEMENT MENU ----------");
            
            System.out.printf("| %-4s | %-30s |\n", "No.", "        Action");
            System.out.println("|------|--------------------------------|");
            System.out.printf("| %-4s | %-30s |\n", "1", "Add RAM");
            System.out.printf("| %-4s | %-30s |\n", "2", "Search RAM");
            System.out.printf("| %-4s | %-30s |\n", "3", "Update RAM Information");
            System.out.printf("| %-4s | %-30s |\n", "4", "Delete RAM");
            System.out.printf("| %-4s | %-30s |\n", "5", "Display All RAM");
            System.out.printf("| %-4s | %-30s |\n", "6", "Save Data");
            System.out.printf("| %-4s | %-30s |\n", "7", "Exit");
            System.out.println("|------|--------------------------------|");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1:
                    ramSystem.addRAMItem();
                    break;
                case 2:
                    ramSystem.searchItems();
                    break;
                case 3:
                    ramSystem.updateRAMItem();
                    break;
                case 4:
                    ramSystem.deleteRAMItem();
                    break;
                case 5:
                    ramSystem.showAllRAMItems();
                    break;
                case 6:
                    ramSystem.storeDataToFile();
                    break;
                case 7:
                    ramSystem.storeDataToFile();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 7);
        sc.close();
    }
    
}
