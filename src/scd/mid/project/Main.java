
package scd.mid.project;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //banking system class account
        BankingSystem bankingSystem = new BankingSystem();
        Scanner scanner = new Scanner(System.in);

        //loop for display menu
        // remains running in exit not

        while (true) {//
            //menu options
            System.out.println("\n===== Simple Banking System =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. View Transaction History");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1: {
                        //attributes for creating account
                        System.out.print("Enter Account Number: ");
                        String accountNumber = scanner.nextLine();
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Initial Balance: ");
                        double balance = scanner.nextDouble();
                        //create account function call
                        bankingSystem.createAccount(accountNumber, name, balance);

                        break;
                    }
                    case 2:{
                        //attributes for deposit amount
                        System.out.print("Enter Account Number: ");
                        String accountNumber = scanner.nextLine();
                        System.out.print("Enter Deposit Amount: ");
                        double amount = scanner.nextDouble();
                        //deposit amount function
                        bankingSystem.deposit(accountNumber, amount);
                       // System.out.println("Deposit successful!");
                        break;
                    }
                    case 3:{
                        //attributes for thr withdraw amount
                        System.out.print("Enter Account Number: ");
                        String accountNumber = scanner.nextLine();
                        System.out.print("Enter Withdrawal Amount: ");
                        double amount = scanner.nextDouble();
                        //withdraw amount function call
                        bankingSystem.withdraw(accountNumber, amount);
                        //System.out.println("Withdrawal successful!");
                        break;
                    }
                    case 4 : {
                        //attributes for view history
                        System.out.print("Enter Account Number: ");
                        String accountNumber = scanner.nextLine();
                        System.out.println("Transaction History:");
                        //view transaction history
                        bankingSystem.getTransactionHistory(accountNumber);
                        break;
                    }
                    case 5 : {
                        ///
                        System.out.println("Thank you for using the banking system!");
                        scanner.close();
                        System.exit(0);
                        break;
                    }
                    //case for the invalid input
                    default :
                        System.out.println("Invalid choice! Please try again.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
