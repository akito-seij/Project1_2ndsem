package Accounts;

import Bank.Bank;

/**
 * Concrete CreditAccount implementation.
 * Implements Payment and Recompense interfaces.
 */
public class CreditAccount extends Account implements Payment, Recompense {
    private double loanAmount;   // Current debt
    private double creditLimit;  // Maximum allowed debt

    /**
     * Constructor now requires accountPin.
     */
    public CreditAccount(Bank bank, String accountNumber, String ownerName, double creditLimit, String accountPin) {
        super(bank, accountNumber, accountPin);
        this.loanAmount = 0;
        this.creditLimit = creditLimit;
    }

    /**
     * Returns the available credit (creditLimit minus current debt).
     */
    @Override
    public double getBalance() {
        return creditLimit - loanAmount;
    }

    public double getCurrentDebt() {
        return loanAmount;
    }

    public boolean canCredit(double amount) {
        return (loanAmount + amount) <= creditLimit;
    }

    public void adjustLoanAmount(double amountAdjustment) {
        double newLoan = loanAmount + amountAdjustment;
        loanAmount = Math.max(newLoan, 0);
    }

    public boolean charge(double amount) {
        if (amount <= 0) {
            System.out.println("Charge must be positive.");
            return false;
        }
        if (!canCredit(amount)) {
            System.out.println("Charge exceeds credit limit!");
            return false;
        }
        loanAmount += amount;
        addNewTransaction(accountNumber, Transaction.Transactions.Withdraw, "Charged " + amount);
        return true;
    }

    @Override
    public boolean pay(Account fromAccount, double amount) throws IllegalAccountType {
        if (!(fromAccount instanceof SavingsAccount)) {
            throw new IllegalAccountType("Payment must come from a SavingsAccount!");
        }
        SavingsAccount saver = (SavingsAccount) fromAccount;
        if (amount <= 0) {
            System.out.println("Payment must be positive.");
            return false;
        }
        if (amount > saver.getBalance()) {
            System.out.println("Insufficient funds in SavingsAccount for payment!");
            return false;
        }
        saver.withdrawal(amount);
        double oldLoan = loanAmount;
        loanAmount = Math.max(0, loanAmount - amount);
        addNewTransaction(accountNumber, Transaction.Transactions.Payment,
                String.format("Payment of %.2f from %s (old debt=%.2f, new debt=%.2f)",
                        amount, saver.getAccountNumber(), oldLoan, loanAmount));
        return true;
    }

    @Override
    public boolean recompense(double amount) {
        if (amount <= 0) {
            System.out.println("Recompense must be positive.");
            return false;
        }
        if (amount > loanAmount) {
            System.out.println("Cannot recompense more than current debt!");
            return false;
        }
        loanAmount -= amount;
        addNewTransaction(accountNumber, Transaction.Transactions.Recompense, "Recompensed " + amount);
        return true;
    }

    @Override
    public String toString() {
        return String.format("CreditAccount[%s, loan=%.2f/%.2f, available=%.2f]", 
                super.toString(), loanAmount, creditLimit, getBalance());
    }

    @Override
    public String getAccountDetails() {
        return String.format("Account Number: %s, Loan: %.2f, Credit Limit: %.2f, Available Credit: %.2f",
                accountNumber, loanAmount, creditLimit, getBalance());
    }
}
