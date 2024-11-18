package scd.mid.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class BankingSystem {

    // Transaction record saving file name (can be removed if logging to DB only)
    private static final String TRANSACTION_LOG = "transactions.log";
    private static final String ERROR_LOG = "error.log";

    // Create an account
    public void createAccount(String accountNumber, String name, double initialBalance) {
        String query = "INSERT INTO accounts (account_number, name, balance) VALUES (?, ?, ?)";
        try {
            int rowsAffected = DB.executeUpdate(query, accountNumber, name, initialBalance);
            if (rowsAffected > 0) {
                System.out.println("Account created successfully!");
            } else {
                logError("Account creation failed: Unknown error.");
                throw new IllegalArgumentException("Account creation failed.");
            }
        } catch (SQLException e) {
            logError("SQL Error during account creation: " + e.getMessage());
            throw new RuntimeException("Account already Exist!.",e);
        }
    }

    // Deposit amount by account number
    public void deposit(String accountNumber, double amount) {
        String selectQuery = "SELECT balance FROM accounts WHERE account_number = ?";
        String updateQuery = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
        String logQuery = "INSERT INTO transactions (account_number, amount, transaction_type) VALUES (?, ?, ?)";

        try (Connection conn = DB.getConnection()) {
            // Check if account exists
            ResultSet rs = DB.executeQuery(selectQuery, accountNumber);
            if (!rs.next()) {
                logError("Invalid Account Number: " + accountNumber);
                throw new InvalidAccountNumberException("Invalid account number.");
            }

            // Deposit money
            DB.executeUpdate(updateQuery, amount, accountNumber);
            // Log the transaction in databse
            DB.executeUpdate(logQuery, accountNumber, amount, "Deposit");
            //log transaction to file
            logTransaction("Deposit "+amount+" to Account Number: "+accountNumber);
            System.out.println("Deposit successful!");
        } catch (SQLException e) {
            logError("SQL Error during deposit: " + e.getMessage());
            throw new RuntimeException("Database error during deposit.", e);
        }
    }

    // Withdraw amount by account number
    public void withdraw(String accountNumber, double amount) {
        String selectQuery = "SELECT balance FROM accounts WHERE account_number = ?";
        String updateQuery = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
        String logQuery = "INSERT INTO transactions (account_number, amount, transaction_type) VALUES (?, ?, ?)";

        try (Connection conn = DB.getConnection()) {
            // Check if account exists and has sufficient balance
            ResultSet rs = DB.executeQuery(selectQuery, accountNumber);
            if (!rs.next()) {
                logError("Invalid Account Number: " + accountNumber);
                throw new InvalidAccountNumberException("Invalid account number.");
            }

            double currentBalance = rs.getDouble("balance");
            if (currentBalance < amount) {
                logError("Insufficient funds for withdrawal.");
                throw new InsufficientFundsException("Insufficient funds for withdrawal.");
            }
            else {
                // Withdraw money
                DB.executeUpdate(updateQuery, amount, accountNumber);
                // Log the transaction
                DB.executeUpdate(logQuery, accountNumber, amount, "Withdraw");
                System.out.println("Withdrawal successful!");
            }
        } catch (SQLException e) {
            logError("SQL Error during withdrawal: " + e.getMessage());
            throw new RuntimeException("Database error during withdrawal.", e);
        }
    }

    // Get transaction history by account number
    public void getTransactionHistory(String accountNumber) {
        String query = "SELECT * FROM transactions WHERE account_number = ? ORDER BY timestamp DESC";
        try (ResultSet rs = DB.executeQuery(query, accountNumber)) {
            boolean found = false;
            while (rs.next()) {
                found = true;
                String transactionId = rs.getString("transaction_id");
                double amount = rs.getDouble("amount");
                String type = rs.getString("transaction_type");
                Timestamp timestamp = rs.getTimestamp("timestamp");
                System.out.println(transactionId + " | " + type + " | " + amount + " | " + timestamp);
            }
            if (!found) {
                System.out.println("No transactions found.");
            }
        } catch (SQLException e) {
            logError("SQL Error while retrieving transaction history: " + e.getMessage());
            throw new RuntimeException("Database error during transaction history retrieval.", e);
        }
    }

    // Save transaction to the database (this can be used in deposit and withdrawal methods)
    private void logTransaction(String message) {
        logToFile(TRANSACTION_LOG, message); // Optional: Can be removed if logging to DB only
    }

    // Save errors to the database
    private void logError(String message) {
       logToFile(ERROR_LOG,message);
    }

    // This function can be removed if you prefer logging to the database only
    private void logToFile(String fileName, String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            Date defaultDate = new Date();
            writer.write(defaultDate + "-" + message);
            writer.newLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
