

package scd.mid.project;
final public class Account {

    //attributes with the default values
     private String accountNumber="MU12345";
     private String name="MUHAMMAD MUBASHIR SAEED AHMAD";
     private double balance=1234.56;

     //parametrized constructor
    public Account(String accountNumber, String name, double initialBalance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = initialBalance;
    }

    //deposit amount function
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        balance += amount;
    }
    //withdraw amount function
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal.");
        }
        if(amount<=0){
            System.out.println("Withdraw amount must be greater then 0.");
            return;
        }
        balance -= amount;
    }

    // getters functions for the attributes
    //getter for account number
    public String getAccountNumber() {
        return accountNumber;
    }
//getter for name of user
    public String getName() {
        return name;
    }

    //getter for the balance
    public double getBalance() {
        return balance;
    }
}

