package Main;

import java.util.ArrayList;
import java.util.List;

import Bank.Bank;

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
}
