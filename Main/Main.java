package Main;

import Bank.Bank;
import java.util.Scanner;

public class Main
{

    private static final Scanner input = new Scanner(System.in);
    /**
     * Option field used when selection options during menu prompts. Do not create a different
     * option variable in menus. Just use this instead. <br>
     * As to how to utilize Field objects properly, refer to the following:
     * 
     * @see #prompt(String, boolean)
     * @see #setOption() How Field objects are used.
     */
    public static Field<Integer, Integer> option = new Field<>("Option",
            Integer.class, -1, new Field.IntegerFieldValidator());

    // Global launcher objects for managing banks and current bank session.
    private static BankLauncher bankLauncher = new BankLauncher();
    private static Bank currentBank = null;

    public static void main(String[] args)
    {
        // For testing, you may create a default bank.
        bankLauncher.createNewBank("001", "Alpha Bank");

        while (true)
        {
            showMenuHeader("Main Menu");
            // Display main menu options (refer to Menu enum index 1)
            // [1] Accounts Login, [2] Bank Login, [3] Create New Bank, [4] Exit
            showMenu(1);
            setOption();
            int mainChoice = getOption();
            // Account Option
            if (mainChoice == 1)
            {
                // READ ME: Refer to this code block on how one should properly utilize
                // showMenuHeader(), showMenu(), setOption(), and getOption() methods...
                if (currentBank == null)
                {
                    System.out.println("No bank is currently logged in. Please login to a bank first.");
                }
                else
                {
                    accountLoginMenu();
                }
            }
            // Bank Option
            else if (mainChoice == 2)
            {
                bankLoginProcess();
            }
            // Create New Bank
            else if (mainChoice == 3)
            {
                createNewBankProcess();
            }
            else if (mainChoice == 4)
            {
                System.out.println("Exiting. Thank you for banking!");
                break;
            }
            else
            {
                System.out.println("Invalid option!");
            }
        }
    }
    
    /**
     * Processes bank login.
     */
    private static void bankLoginProcess()
    {
        showMenuHeader("Bank Login");
        System.out.print("Enter Bank ID or Name: ");
        String bankIdOrName = input.nextLine();
        bankLauncher.bankLogin(bankIdOrName);
        currentBank = bankLauncher.getLoggedBank();
        if (currentBank != null)
        {
            System.out.println("Bank logged in: " + currentBank.getBankName());
            bankOperations(); // Call bankOperations after successful login
        }
        else
        {
            System.out.println("Bank not found.");
        }
    }

    /**
     * Provides operations for a logged-in bank.
     */
    private static void bankOperations() {
        boolean exit = false;
        while (!exit) {
            showMenuHeader("Bank Menu");
            showMenu(31); // Display BankMenu options
            setOption();
            int choice = getOption();
            switch (choice) {
                case 1:
                    bankLauncher.showAccounts(); // Call showAccounts from BankLauncher
                    break;
                case 2:
                    createNewAccount(); // Ensure this method is correctly implemented
                    break;
                case 3:
                    bankLauncher.bankLogout();
                    currentBank = null;
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void createNewAccount() {
        if (currentBank == null) {
            System.out.println("No bank is currently logged in.");
            return;
        }
        showMenuHeader("Create New Account");
        System.out.println("[1] Savings Account");
        System.out.println("[2] Credit Account");
        System.out.print("Select an option: ");
        int accChoice = input.nextInt();
        input.nextLine();

        System.out.print("Enter Account Number: ");
        String accNum = input.nextLine();
        System.out.print("Enter Owner Name: ");
        String ownerName = input.nextLine();
        System.out.print("Enter Account PIN: ");
        String pin = input.nextLine();

        if (accChoice == 1) {
            System.out.print("Enter Initial Balance: ");
            double initialBalance = input.nextDouble();
            input.nextLine();
            bankLauncher.createNewAccount("Savings", accNum, ownerName, initialBalance, pin);
        } else if (accChoice == 2) {
            System.out.print("Enter Credit Limit: ");
            double creditLimit = input.nextDouble();
            input.nextLine();
            bankLauncher.createNewAccount("Credit", accNum, ownerName, creditLimit, pin);
        } else {
            System.out.println("Invalid option.");
        }
    }
    
    /**
     * Processes new bank creation.
     */
    private static void createNewBankProcess()
    {
        showMenuHeader("Create New Bank");
        System.out.print("Enter new Bank ID: ");
        String id = input.nextLine();
        System.out.print("Enter new Bank Name: ");
        String name = input.nextLine();
        bankLauncher.createNewBank(id, name);
        System.out.println("New bank created: " + name);
    }
    
    /**
     * Presents a submenu for account login and then directs to account operations.
     */
    private static void accountLoginMenu()
    {
        showMenuHeader("Account Login Menu");
        System.out.println("[1] Savings Account");
        System.out.println("[2] Credit Account");
        System.out.println("[3] Go Back");
        System.out.print("Select an option: ");
        int accChoice = input.nextInt();
        input.nextLine(); // consume newline
        
        if (accChoice == 1)
        {
            // Savings Account Login
            SavingsAccountLauncher saLauncher = new SavingsAccountLauncher();
            saLauncher.bankLogin(currentBank);
            System.out.print("Enter Savings Account Number: ");
            String accNum = input.nextLine();
            System.out.print("Enter PIN: ");
            String pin = input.nextLine(); // In a real system, verify the PIN properly.
            saLauncher.accountLogin(accNum, pin);
            if (saLauncher.isLoggedIn())
            {
                savingsOperations(saLauncher);
            }
        }
        else if (accChoice == 2)
        {
            // Credit Account Login
            CreditAccountLauncher caLauncher = new CreditAccountLauncher();
            caLauncher.bankLogin(currentBank);
            System.out.print("Enter Credit Account Number: ");
            String accNum = input.nextLine();
            System.out.print("Enter PIN: ");
            String pin = input.nextLine();
            caLauncher.accountLogin(accNum, pin);
            if (caLauncher.isLoggedIn())
            {
                creditOperations(caLauncher);
            }
        }
        else if (accChoice == 3)
        {
            // Go back to main menu
            return;
        }
        else
        {
            System.out.println("Invalid option.");
        }
    }
    
    /**
     * Provides operations for a logged-in Savings Account.
     */
    private static void savingsOperations(SavingsAccountLauncher saLauncher)
    {
        boolean exit = false;
        while (!exit)
        {
            showMenuHeader("Savings Account Menu");
            System.out.println("[1] Deposit");
            System.out.println("[2] Withdraw");
            System.out.println("[3] Fund Transfer");
            System.out.println("[4] Show Balance");
            System.out.println("[5] Logout");
            System.out.print("Select an option: ");
            int choice = input.nextInt();
            input.nextLine();
            switch (choice)
            {
                case 1:
                    System.out.print("Enter deposit amount: ");
                    double deposit = input.nextDouble();
                    input.nextLine();
                    saLauncher.depositProcess(deposit);
                    break;
                case 2:
                    System.out.print("Enter withdrawal amount: ");
                    double withdraw = input.nextDouble();
                    input.nextLine();
                    saLauncher.withdrawProcess(withdraw);
                    break;
                case 3:
                    System.out.print("Enter recipient account number: ");
                    String targetAcc = input.nextLine();
                    System.out.print("Enter transfer amount: ");
                    double transfer = input.nextDouble();
                    input.nextLine();
                    saLauncher.fundTransferProcess(targetAcc, transfer);
                    break;
                case 4:
                    System.out.println("Current Balance: " + saLauncher.getLoggedAccount().getBalance());
                    break;
                case 5:
                    saLauncher.logout();
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
    
    /**
     * Provides operations for a logged-in Credit Account.
     */
    private static void creditOperations(CreditAccountLauncher caLauncher)
    {
        boolean exit = false;
        while (!exit)
        {
            showMenuHeader("Credit Account Menu");
            System.out.println("[1] Charge Account");
            System.out.println("[2] Payment (from Savings Account)");
            System.out.println("[3] Recompense");
            System.out.println("[4] Show Available Credit and Current Debt");
            System.out.println("[5] Logout");
            System.out.print("Select an option: ");
            int choice = input.nextInt();
            input.nextLine();
            switch (choice)
            {
                case 1:
                    System.out.print("Enter charge amount: ");
                    double charge = input.nextDouble();
                    input.nextLine();
                    caLauncher.getLoggedAccount().charge(charge);
                    break;
                case 2:
                    System.out.print("Enter Savings Account Number for payment: ");
                    String savAcc = input.nextLine();
                    System.out.print("Enter payment amount: ");
                    double payAmount = input.nextDouble();
                    input.nextLine();
                    caLauncher.paymentProcess(savAcc, payAmount);
                    break;
                case 3:
                    System.out.print("Enter recompense amount: ");
                    double recompense = input.nextDouble();
                    input.nextLine();
                    caLauncher.recompenseProcess(recompense);
                    break;
                case 4:
                    System.out.println("Available Credit: " + caLauncher.getLoggedAccount().getBalance());
                    System.out.println("Current Debt: " + caLauncher.getLoggedAccount().getCurrentDebt());
                    break;
                case 5:
                    caLauncher.logout();
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
    
    /**
     * Show menu based on index given. <br>
     * Refer to Menu enum for more info about menu indexes. <br>
     * Use this method if you want a single menu option every line.
     * 
     * @param menuIdx Main.Menu index to be shown
     */
    public static void showMenu(int menuIdx)
    {
        showMenu(menuIdx, 1);
    }

    /**
     * Show menu based on index given. <br>
     * Refer to Menu enum for more info about menu indexes.
     * 
     * @param menuIdx Main.Menu index to be shown
     * @param inlineTexts Number of menu options in a single line. Set to 1 if you only want a
     *        single menu option every line.
     * @see Menu
     */
    public static void showMenu(int menuIdx, int inlineTexts)
    {
        String[] menu = Menu.getMenuOptions(menuIdx);
        if (menu == null)
        {
            System.out.println("Invalid menu index given!");
        }
        else
        {
            String space = inlineTexts == 0 ? "" : "%-20s";
            String fmt = "[%d] " + space;
            int count = 0;
            for (String s : menu)
            {
                count++;
                System.out.printf(fmt, count, s);
                if (count % inlineTexts == 0)
                {
                    System.out.println();
                }
            }
        }
    }

    /**
     * Prompt some input to the user. Only receives on non-space containing String. This string can
     * then be parsed into targeted data type using DataTypeWrapper.parse() method.
     * 
     * @param phrase Prompt to the user.
     * @param inlineInput A flag to ask if the input is just one entire String or receive an entire
     *        line input. <br>
     *        Set to <b>true</b> if receiving only one String input without spaces. <br>
     *        Set to <b>false</b> if receiving an entire line of String input.
     * @return Value of the user's input.
     * @see Field#setFieldValue(String, boolean) How prompt is utilized in Field.
     */
    public static String prompt(String phrase, boolean inlineInput)
    {
        System.out.print(phrase);
        if (inlineInput)
        {
            String val = input.next();
            input.nextLine();
            return val;
        }
        return input.nextLine();
    }

    /**
     * Prompts user to set an option based on menu outputted.
     * 
     * @throws NumberFormatException May happen if the user attempts to input something other than
     *         numbers.
     */
    public static void setOption() throws NumberFormatException
    {
        option.setFieldValue("Select an option: ");
    }

    /**
     * @return Recently inputted option by the user.
     */
    public static int getOption()
    {
        return Main.option.getFieldValue();
    }

    /**
     * Used for printing the header whenever a new menu is accessed.
     * 
     * @param menuTitle Title of the menu to be outputted.
     */
    public static void showMenuHeader(String menuTitle)
    {
        System.out.printf("<---- %s ----->\n", menuTitle);
    }
}
