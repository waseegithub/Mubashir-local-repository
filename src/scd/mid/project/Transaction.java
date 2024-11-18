

package scd.mid.project;
import java.util.Date;

public class Transaction {
    //
    //attributes with default values
    private String transactionId="12334";
    private String accountNumber="1231Mubashir";
    private double amount=12.34;
    // deposit or withdrawl
    private String type="Deposit";
    //time and date for transaction
    private Date timestamp;

    //parametrized constructor
    public Transaction(String transactionId, String accountNumber, double amount, String type, Date timestamp) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
    }

    //getters functions for attributes get
    public String getTransactionId() {
        return transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
