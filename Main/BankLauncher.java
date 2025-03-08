package Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Bank.Bank;
import Accounts.Account;
import Accounts.CreditAccount;
import Accounts.SavingsAccount;

public class BankLauncher {
    private List<Bank> banksList;
    private Bank loggedBank;

    public BankLauncher() {
        this.banksList = new ArrayList<>();
        this.loggedBank = null;
    }

    public void createNewBank(String bankId, String bankName) {
        Bank bank = new Bank(bankId, bankName);
        banksList.add(bank);
        System.out.println("Created new bank: " + bankName);
    }

    public void bankLogin(String bankIdOrName) {
        Bank found = getBank(bankIdOrName);
        if (found != null) {
            loggedBank = found;
            System.out.println("Logged into bank: " + found.getBankName());
        } else {
            System.out.println("Bank not found.");
        }
    }

    public void bankLogout() {
        if (loggedBank != null) {
            System.out.println("Logging out of bank: " + loggedBank.getBankName());
        }
        loggedBank = null;
    }

    public Bank getLoggedBank() {
        return loggedBank;
    }

    public List<Bank> getBanksList() {
        return banksList;
    }

    private Bank getBank(String bankIdOrName) {
        for (Bank b : banksList) {
            if (b.getBankId().equals(bankIdOrName) || b.getBankName().equalsIgnoreCase(bankIdOrName)) {
                return b;
            }
        }
        return null;
    }

    public void showAccounts() {
        if (loggedBank == null) {
            System.out.println("No bank is currently logged in.");
            return;
        }
        System.out.println("Accounts in " + loggedBank.getBankName() + ":");
        loggedBank.getAccounts().forEach(account -> {
            System.out.println(account.getAccountDetails());
        });
        System.out.println("Press Enter to return to the bank menu...");
        new Scanner(System.in).nextLine(); // Wait for user input to return to the bank menu
    }

    public void createNewAccount(String accountType, String accountNumber, String ownerName, double initialBalanceOrCreditLimit, String accountPin) {
        if (loggedBank == null) {
            System.out.println("No bank is currently logged in.");
            return;
        }
        if (accountType.equalsIgnoreCase("Savings")) {
            loggedBank.createNewSavingsAccount(accountNumber, ownerName, initialBalanceOrCreditLimit, accountPin);
            System.out.println("Savings Account created.");
        } else if (accountType.equalsIgnoreCase("Credit")) {
            loggedBank.createNewCreditAccount(accountNumber, ownerName, initialBalanceOrCreditLimit, accountPin);
            System.out.println("Credit Account created.");
        } else {
            System.out.println("Invalid account type.");
        }
        System.out.println("Press Enter to return to the bank menu...");
        new Scanner(System.in).nextLine(); // Wait for user input to return to the bank menu
    }
}
