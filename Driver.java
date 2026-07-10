import java.util.Scanner;

public class Driver {
    
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        int choice;
        
        System.out.println("==========================");
        System.out.println("Vending Machine Simulator");
        System.out.println("==========================");

        System.out.println("What would you like to do?");
        System.out.println("[1] Create a Vending Machine");
        System.out.println("[2] Test a Vending Machine");
        System.out.println("[3] Exit");
        choice = sc.nextInt();

        if (choice == 1) {
            System.out.println("Choose a Vending Machine to Create:");
            System.out.println("[1] Regular Vedning Machine");
            System.out.println("[2] Special Vending Machine");
            choice = sc.nextInt();

            if (choice == 1) {
                // call regular vending machine
            }
            else if (choice == 2) {
                System.out.println("Not yet available!");
            }
        }

    }
}