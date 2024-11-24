/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import DataObject.RAMItem;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author giakh
 */
public class ValidUtil {
    private static final Scanner sc = new Scanner(System.in);
    
    public static String getString(String data){
        while (true) {
            System.out.println(data);
            String string = sc.nextLine().trim();
            if (string.isEmpty()) {
                System.out.println("Input not empty.");
            }else{
                return string;
            }
        }
    }


    
    public static int getInt(String data){
    System.out.println(data);
//    while (!sc.hasNextInt()) { /
//        System.out.println("Invalid input. Please enter a valid integer.");
//        sc.next(); 
//    }  
    int value = sc.nextInt();
    sc.nextLine();

    // Check số lớn hơn 0
    while (value < 0) {
        System.out.println("The number must be greater than 0. Please enter a valid integer:");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer.");
            sc.next(); // bỏ qua các giá trị không hợp lệ
        }
        value = sc.nextInt();
        sc.nextLine();
    }
    
    return value;
}

    
    public static int getIntChoice(String string , int min, int max){
       while (true) {
            System.out.println(string);
            try {
                int x = Integer.parseInt(sc.nextLine());
                if (x < min ||x > max ) {
                    throw new Exception();
                }
                return x;
            } catch (Exception e) {
                System.out.println("Invalid selection : ");
            }
        }
    }

    public static  boolean isValidType(String type){
        return type.matches("[A-Z]+\\d+");
    }

    public static boolean isValidBus(String bus){
         return bus.matches("\\d+MHz");
    }


    
    
    
}
