package Main;

import Accounts.CreditAccount;
import Accounts.SavingsAccount;

public class CreditAccountLauncher extends AccountLauncher {

    public CreditAccount getLoggedAccount() {
        if (loggedAccount instanceof CreditAccount) {
            return (CreditAccount) loggedAccount;
        }
        return null;
    }

    public void creditAccountMenu() {
        if (!isLoggedIn() || getLoggedAccount() == null) {
            System.out.println("No Credit Account logged in.");
            return;
        }
        // Display menu options and handle user input here.
        System.out.println("Credit Account Menu for " + getLoggedAccount().getAccountNumber());
    }

    public void paymentProcess(String savingsAccNum, double amount) {
        CreditAccount ca = getLoggedAccount();
        if (ca != null) {
            SavingsAccount saver = (SavingsAccount) assocBank.getBankAccount(savingsAccNum, assocBank);
            try {
                ca.pay(saver, amount);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void recompenseProcess(double amount) {
        CreditAccount ca = getLoggedAccount();
        if (ca != null) {
            ca.recompense(amount);
        }
    }
}
