/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.plv.teytunescashreg;

/**
 *
 * @author teya
 */
import java.util.*;
import java.util.ArrayList;
import java.util.regex.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class TeyTunesCashReg {
        
    static final Scanner jungkook = new Scanner(System.in);
    
    static final ArrayList<String[]> users = new ArrayList<>();
    static final ArrayList<Integer> albumRank = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    static final ArrayList<String> albumName = new ArrayList<>(List.of("Glide", "Asterum", "No Genre", "Awe", "Go in Blind", "Requiem", "HOT", "Be", "Jazz", "Justice"));
    static final ArrayList<String> albumArtist = new ArrayList<>(List.of("Fiji Blue", "PLAVE", "BOYNEXTDOOR", "XG", "&TEAM", "Keshi", "LE SSERAFIM", "BTS", "Queen", "Justin Bieber"));
    static final ArrayList<Double> albumPrice = new ArrayList<>(List.of(640.0, 725.0, 1180.0, 970.0, 890.0, 1120.0, 1060.0, 1480.0, 1250.0, 1390.0));
    static ArrayList <String> cartItem = new ArrayList <> ();
    static ArrayList <Integer> cartQuantity = new ArrayList <> ();
    static ArrayList <Double> cartPrice = new ArrayList <> ();
    static double grandTotal = 0;
    
    private static void signUp() {
        System.out.print("\n  Enter username: ");
        String username = jungkook.next();
        jungkook.nextLine();
        System.out.print("  Enter password: ");
        String password = jungkook.nextLine();
    
        if (validateUname(username) && validatePword(password)) {
            users.add(new String[]{username, password});
            System.out.println("Signup successful!");
        } else {
            System.out.println("I'm sorry, that's invalid! Please recheck the requirements!");
            signUp();
        }
    }
    
    private static boolean validateUname(String username) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{5,15}$");
        Matcher matcher = pattern.matcher (username);
        boolean matchFound = matcher.find();
        if (matchFound) {
            return true;
        } else {
            return false;
        }
    }
    
    private static boolean validatePword(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*\\d).{8,20}$");
        Matcher matcher = pattern.matcher (password);
        boolean matchFound = matcher.find();
        if (matchFound) {
            return true;
        } else {
            return false;
        }
    }

    private static String[] logIn() {
        System.out.print("  Enter username: ");
        String username = jungkook.next();
        jungkook.nextLine();
        System.out.print("  Enter password: ");
        String password = jungkook.nextLine();
        for (int i = 0; i < users.size(); i++) {
            String[] user = users.get(i);
            if (user[0].equals(username) && user[1].equals(password)) {
                return users.get(i);
            }
        }
        System.out.println("Incorrect username or password, please try that again!");
        return logIn(); 
    }
    
    public static void albumsList () {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("\t\t~~ This week's Top 10 Albums ~~");
        System.out.println("RANK\tPRICE\t\t\tALBUM TITLE - ARTIST");
        for (int i = 0; i < albumRank.size(); i++) {
            System.out.println(albumRank.get(i) + "\t" + "PHP " + albumPrice.get(i) + "\t\t" + albumName.get(i) + " - " + albumArtist.get(i));
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
    
    public static void addItem () {
        String case1Choice = "y";
        do {
            System.out.print("  Enter the album rank you'd like to add to your cart: ");
            int item = jungkook.nextInt();
                            
            if (item >= 1 && item <= 10) {                          
                int itemIndex = item - 1;                           
                String albumNames = albumName.get(itemIndex);       
                String albumArtists = albumArtist.get(itemIndex);   
                double albumPrices = albumPrice.get(itemIndex);     
                
                cartItem.add(albumNames + " - " + albumArtists);    
                System.out.print("  Enter the quantity: ");
                int quantity = jungkook.nextInt();                  
                cartQuantity.add(quantity);                         

                double totalPrice = quantity * albumPrices;
                cartPrice.add(totalPrice);
                grandTotal += totalPrice;                           

                System.out.println("  Added to cart: " + quantity + "x " + albumNames + " by " + albumArtists + " - PHP " + totalPrice);
            } else { System.out.println("  Invalid rank :( Please choose a rank between 1 and 10!"); }

            System.out.print("Would you like to add another album? (y/n): ");
            case1Choice = jungkook.next();
        } while (case1Choice.equalsIgnoreCase("y"));
        System.out.printf("\nGrand Total: PHP %.2f\n", grandTotal);
    }
    
    public static void removeItem () {
        for (int i = 0; i < cartItem.size(); i++) {
            System.out.println("  " + (i+1) + ". " + cartQuantity.get(i) + "x " + cartItem.get(i) + " - PHP " + cartPrice.get(i));
        }
        
        System.out.print("Enter the number of the album/s you wish to remove: ");
        int findingOut = jungkook.nextInt();
        findingOut = findingOut - 1;
        if (findingOut >= 0 && findingOut < cartItem.size()) {
            double itemPrice = cartQuantity.get(findingOut) * albumPrice.get(albumRank.indexOf(findingOut + 1));
            grandTotal -= itemPrice;

            System.out.println("Removal successful: " + cartItem.get(findingOut));
            cartItem.remove(findingOut);
            cartQuantity.remove(findingOut);
            cartPrice.remove(findingOut);
            System.out.printf("\nNew Grand Total: PHP %.2f\n", grandTotal);
        } else {
            System.out.println("Invalid number! Please enter a valid number from the list.");
        }
    }
    
    public static void payCart () {
        System.out.print("Your Cart:\n");
        for (int i = 0; i < cartItem.size(); i++) {
           System.out.println("  " + (i+1) + ". " + cartQuantity.get(i) + "x " + cartItem.get(i) + " - PHP " + cartPrice.get(i));
        }
        System.out.printf("Grand Total: PHP %.2f\n", grandTotal);
        while (true) {
            System.out.print("  Enter the amount of your payment: ");
            double payment = jungkook.nextDouble();
            double change = payment-grandTotal;
            
            if (change>=0) { 
                System.out.printf("  Thank you for your purchase! Your change is %.2f.\n", change);
                LocalDateTime myDateObj = LocalDateTime.now();
                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss");
                    String formattedDate = myDateObj.format(myFormatObj);
                    
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt", true))) {
                        writer.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                        writer.write("                       TEYTUNES\n");
                        writer.write("                    DLSL, LIPA CITY\n");
                        writer.write("                      Cashier: Tey\n");
                        writer.write("                 " + formattedDate + "\n");
                        writer.write("   -----------------------------------------------\n");
                        writer.write("                  ORDER DETAILS\n\n");
                        for (int i = 0; i < cartItem.size(); i++) {
                            writer.write ("   PHP " + cartPrice.get(i) + " | " + cartQuantity.get(i) + "x | " + cartItem.get(i) + "\n");}
                        writer.write("   -----------------------------------------------\n");
                        writer.write("   GRAND TOTAL: PHP " + grandTotal + "\n\n");
                        writer.write("   CASH: PHP " + payment + "\n");
                        writer.write("   CHANGE: PHP " + change + "\n");
                        writer.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                    } catch (IOException w) {
                        w.printStackTrace();
                    }
                    System.out.println("  Transaction Recorded!");
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    break;
                } else {
                    System.out.println("  Insufficient payment. Please try that again!!");
                }
            }
    }

    private static void TeyTunes() {
        String overallChoice = "y";
        
        while (overallChoice.equals("y")) {
            albumsList();
            System.out.println("Press 1: ADD an item to your cart");
            System.out.println("Press 2: REMOVE an item from your cart");
            System.out.println("Press 3: PAY for this transaction");
            
            while (true){
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.print("What would you like to do?: ");
                int action = jungkook.nextInt();
                
                switch (action) {
                    case 1:
                        addItem();
                        break;
                    case 2:
                        jungkook.nextLine();
                        removeItem();
                        break;
                    case 3:
                        payCart();
                        break;
                    default:
                        System.out.println("  Please try that again.");
                }
                if (action==3) { break; }
            }
            System.out.print("Would you like to perform another transaction? (y/n): ");
            jungkook.nextLine();
            overallChoice=jungkook.nextLine().toLowerCase();
        }

    }

    public static void main(String[] args) {
        System.out.println("Welcome! We're excited to have you here. Let's get you started!");
        System.out.println("  Press 1: SIGN-UP for an account\n  Press 2: LOG-IN to an account\n  Press 3: EXIT TeyTunes");
        while (true) {
            System.out.print("What would you like to do today?: ");
            int choice = jungkook.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    System.out.println("SIGNUP for an account!");
                    System.out.print("  Username Requirements:\n    > Alphanumeric\n    > 5-15 characters long\n");
                    System.out.print("  Password Requirements:\n    > At least one uppercase letter\n    > At least one number\n    > 8-20 characters long");
                    signUp();
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    break;
                case 2:
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    System.out.println("LOG-IN to an account!");
                    logIn();
                    System.out.println("Login successful! Welcome to the store!");
                    TeyTunes();
                case 3:
                    System.out.println("Thank you for visiting TeyTunes! See you again soon!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
