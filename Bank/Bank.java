package Bank;

import Accounts.Account;
import Accounts.CreditAccount;
import Accounts.SavingsAccount;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bank that manages multiple accounts.
 */
public class Bank {
    public double depositLimit = 10000;
    public double withdrawLimit = 5000;
    public double maxBalance = 100000;
    public double minBalance = 0;

    private String bankId;
    private String bankName;
    private List<Account> accounts;

    public Bank(String bankId, String bankName) {
        this.bankId = bankId;
        this.bankName = bankName;
        this.accounts = new ArrayList<>();
    }

    public String getBankId() {
        return bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public Account getBankAccount(String accountNumber, Bank bank) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accountNumber)) {
                return acc;
            }
        }
        return null;
    }

    public CreditAccount createNewCreditAccount(String accountNumber, String ownerName, double creditLimit, String accountPin) {
        CreditAccount c = new CreditAccount(this, accountNumber, ownerName, creditLimit, accountPin);
        accounts.add(c);
        return c;
    }

    public SavingsAccount createNewSavingsAccount(String accountNumber, String ownerName, double initialBalance, String accountPin) {
        SavingsAccount s = new SavingsAccount(this, accountNumber, ownerName, initialBalance, accountPin);
        accounts.add(s);
        return s;
    }

    @Override
    public String toString() {
        return "Bank{" + "bankId='" + bankId + '\'' + ", bankName='" + bankName + '\'' + '}';
    }

    // --- Comparators as inner classes ---
    public static class BankComparator implements java.util.Comparator<Bank> {
        @Override
        public int compare(Bank o1, Bank o2) {
            return o1.bankId.compareTo(o2.bankId);
        }
    }

    public static class BankNameComparator implements java.util.Comparator<Bank> {
        @Override
        public int compare(Bank o1, Bank o2) {
            return o1.bankName.compareTo(o2.bankName);
        }
    }

    public static class BankCreditsBalanceComparator implements java.util.Comparator<Bank> {
        @Override
        public int compare(Bank b1, Bank b2) {
            double sum1 = b1.getAccounts().stream()
                    .filter(a -> a instanceof Accounts.CreditAccount)
                    .mapToDouble(a -> ((Accounts.CreditAccount) a).getCurrentDebt())
                    .sum();
            double sum2 = b2.getAccounts().stream()
                    .filter(a -> a instanceof Accounts.CreditAccount)
                    .mapToDouble(a -> ((Accounts.CreditAccount) a).getCurrentDebt())
                    .sum();
            return Double.compare(sum1, sum2);
        }
    }

    public static class BankSavingsBalanceComparator implements java.util.Comparator<Bank> {
        @Override
        public int compare(Bank b1, Bank b2) {
            double sum1 = b1.getAccounts().stream()
                    .filter(a -> a instanceof Accounts.SavingsAccount)
                    .mapToDouble(a -> a.getBalance())
                    .sum();
            double sum2 = b2.getAccounts().stream()
                    .filter(a -> a instanceof Accounts.SavingsAccount)
                    .mapToDouble(a -> a.getBalance())
                    .sum();
            return Double.compare(sum1, sum2);
        }
    }

    public static class BankCreditsAndSavingsBalanceComparator implements java.util.Comparator<Bank> {
        @Override
        public int compare(Bank b1, Bank b2) {
            double sumCredit1 = b1.getAccounts().stream()
                    .filter(a -> a instanceof Accounts.CreditAccount)
                    .mapToDouble(a -> ((Accounts.CreditAccount) a).getCurrentDebt())
                    .sum();
            double sumSavings1 = b1.getAccounts().stream()
                    .filter(a -> a instanceof Accounts.SavingsAccount)
                    .mapToDouble(a -> a.getBalance())
                    .sum();
            double sumCredit2 = b2.getAccounts().stream()
                    .filter(a -> a instanceof Accounts.CreditAccount)
                    .mapToDouble(a -> ((Accounts.CreditAccount) a).getCurrentDebt())
                    .sum();
            double sumSavings2 = b2.getAccounts().stream()
                    .filter(a -> a instanceof Accounts.SavingsAccount)
                    .mapToDouble(a -> a.getBalance())
                    .sum();
            double total1 = sumCredit1 + sumSavings1;
            double total2 = sumCredit2 + sumSavings2;
            return Double.compare(total1, total2);
        }
    }
}
