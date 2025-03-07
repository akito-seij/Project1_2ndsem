package Main;

import Accounts.Account;
import Bank.Bank;

/**
 * A base class for account login and common functions.
 */
public abstract class AccountLauncher {
    protected boolean loggedIn;
    protected Bank assocBank;        // The bank associated with this launcher
    protected Account loggedAccount; // The currently logged-in account

    public AccountLauncher() {
        loggedIn = false;
        assocBank = null;
        loggedAccount = null;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void bankLogin(Bank bank) {
        if (bank == null) {
            System.out.println("Invalid bank.");
            return;
        }
        assocBank = bank;
        System.out.println("Bank login successful: " + bank.getBankName());
    }

    /**
     * Logs in to an account using the account number and verifies the PIN.
     */
    public void accountLogin(String accountNumber, String pin) {
        if (assocBank == null) {
            System.out.println("No bank selected!");
            return;
        }
        Account acc = assocBank.getBankAccount(accountNumber, assocBank);
        if (acc == null) {
            System.out.println("Account not found!");
            return;
        }
        // Verify the PIN
        if (!acc.getAccountPin().equals(pin)) {
            System.out.println("Incorrect PIN!");
            return;
        }
        loggedAccount = acc;
        loggedIn = true;
        System.out.println("Account login successful: " + accountNumber);
    }

    public void logout() {
        loggedIn = false;
        loggedAccount = null;
        System.out.println("Logged out of account.");
    }
}
