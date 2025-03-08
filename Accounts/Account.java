package Accounts;

import Bank.Bank;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a general bank account.
 */
public abstract class Account {
    protected Bank bank;                  // Associated bank
    protected String accountNumber;       // Unique account number
    protected String accountPin;          // New: PIN for the account
    protected List<Transaction> transactions; // Transaction history

    /**
     * Constructor now takes an extra parameter for the PIN.
     */
    public Account(Bank bank, String accountNumber, String accountPin) {
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.accountPin = accountPin;
        this.transactions = new ArrayList<>();
    }

    public Bank getBank() {
        return bank;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String getAccountPin() {
        return accountPin;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addNewTransaction(String accountNumber, Transaction.Transactions transactionType, String description) {
        Transaction t = new Transaction(accountNumber, transactionType, description);
        transactions.add(t);
    }

    /**
     * Get all transaction information in a formatted list.
     * @return List of transaction info strings.
     */
    public List<String> getTransactionsInfo() {
        List<String> infoList = new ArrayList<>();
        for (Transaction t : transactions) {
            String info = String.format("Acct#: %s | Type: %s | Desc: %s",
                    t.accountNumber, t.transactionType, t.description);
            infoList.add(info);
        }
        return infoList;
    }

    /**
     * Abstract method to return the account's balance (or available credit).
     * Subclasses must implement this method.
     */
    public abstract double getBalance();

    /**
     * Abstract method to return the account details as a string.
     * Subclasses must implement this method.
     */
    public abstract String getAccountDetails();

    @Override
    public String toString() {
        return String.format("%s@%s", accountNumber, bank != null ? bank.getBankName() : "NoBank");
    }
}
