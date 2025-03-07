package Accounts;

import Bank.Bank;

/**
 * Concrete SavingsAccount implementation.
 * Implements Deposit, Withdrawal, and FundTransfer interfaces.
 */
public class SavingsAccount extends Account implements Deposit, Withdrawal, FundTransfer {
    private double balance;
    private String ownerName; // Add this line

    /**
     * Constructor now requires accountPin.
     */
    public SavingsAccount(Bank bank, String accountNumber, String ownerName, double initialBalance, String accountPin) {
        super(bank, accountNumber, accountPin);
        this.balance = initialBalance;
        this.ownerName = ownerName; // Add this line
    }

    @Override
    public boolean cashDeposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit must be positive.");
            return false;
        }
        if (amount > bank.depositLimit) {
            System.out.println("Deposit exceeds bank deposit limit!");
            return false;
        }
        double newBalance = balance + amount;
        if (newBalance > bank.maxBalance) {
            System.out.println("Exceeds maximum balance allowed!");
            return false;
        }
        balance = newBalance;
        addNewTransaction(accountNumber, Transaction.Transactions.Deposit, "Deposited " + amount);
        return true;
    }

    @Override
    public boolean withdrawal(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal must be positive.");
            return false;
        }
        if (amount > bank.withdrawLimit) {
            System.out.println("Withdrawal exceeds bank withdraw limit!");
            return false;
        }
        if (amount > balance) {
            System.out.println("Insufficient funds!");
            return false;
        }
        double newBalance = balance - amount;
        if (newBalance < bank.minBalance) {
            System.out.println("Cannot go below minimum balance!");
            return false;
        }
        balance = newBalance;
        addNewTransaction(accountNumber, Transaction.Transactions.Withdraw, "Withdrew " + amount);
        return true;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public boolean transfer(Bank otherBank, Account account, double amount) throws IllegalAccountType {
        if (!(account instanceof SavingsAccount)) {
            throw new IllegalAccountType("Transfer allowed only between SavingsAccounts.");
        }
        if (amount > balance) {
            System.out.println("Insufficient balance to transfer!");
            return false;
        }
        double fee = (otherBank != null && otherBank != this.bank) ? amount * 0.01 : 0;
        double total = amount + fee;
        if (total > balance) {
            System.out.println("Not enough to cover amount plus fee!");
            return false;
        }
        balance -= total;
        addNewTransaction(accountNumber, Transaction.Transactions.FundTransfer,
                String.format("Transferred %.2f to %s (fee=%.2f)", amount, account.getAccountNumber(), fee));
        ((SavingsAccount) account).cashDeposit(amount);
        return true;
    }

    @Override
    public boolean transfer(Account account, double amount) throws IllegalAccountType {
        return transfer(this.bank, account, amount);
    }

    @Override
    public String toString() {
        return String.format("SavingsAccount[%s, balance=%.2f]", super.toString(), balance);
    }

    /**
     * Returns account details as a string.
     */
    public String getAccountDetails() {
        return String.format("Account Number: %s, Owner: %s, Balance: %.2f", accountNumber, ownerName, balance);
    }
}
