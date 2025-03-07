package Main;

import Accounts.SavingsAccount;

public class SavingsAccountLauncher extends AccountLauncher {

    public SavingsAccount getLoggedAccount() {
        if (loggedAccount instanceof SavingsAccount) {
            return (SavingsAccount) loggedAccount;
        }
        return null;
    }

    public void savingsAccountMenu() {
        if (!isLoggedIn() || getLoggedAccount() == null) {
            System.out.println("No Savings Account logged in.");
            return;
        }
        // Display menu options and handle user input here.
        System.out.println("Savings Account Menu for " + getLoggedAccount().getAccountNumber());
    }

    public void depositProcess(double amount) {
        SavingsAccount sa = getLoggedAccount();
        if (sa != null) {
            sa.cashDeposit(amount);
        }
    }

    public void withdrawProcess(double amount) {
        SavingsAccount sa = getLoggedAccount();
        if (sa != null) {
            sa.withdrawal(amount);
        }
    }

    public void fundTransferProcess(String targetAccNum, double amount) {
        SavingsAccount sa = getLoggedAccount();
        if (sa != null) {
            try {
                sa.transfer(assocBank.getBankAccount(targetAccNum, assocBank), amount);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
