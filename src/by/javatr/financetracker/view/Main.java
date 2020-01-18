package by.javatr.financetracker.view;

import by.javatr.financetracker.controller.Controller;

import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        /*
        BigDecimal bigDecimal = new BigDecimal(12.20);
        Account account = new Account();
        Date date = new Date();
        Income expense = new Income(bigDecimal, IncomeCategory.SALARY, account, date);
        Income expense1 = new Income(bigDecimal, IncomeCategory.SALARY, account, date);
        System.out.println(expense.hashCode()==expense1.hashCode());
        System.out.println(expense.equals(expense1));
         */
        //System.out.println(new Date().hashCode());
        //FileUserDAO fileUserDAO = new FileUserDAO("users.txt", ", ");
        //fileUserDAO.addUser(new User("bdsfdgfhh"), new char[]{'f', 'd'});
        //System.out.println(fileUserDAO.hasUser("mdsfdgfhh", new char[]{'b', 'a'}));
        //fileUserDAO.deleteUser(new User("mdsfdgfhh"));
        //fileUserDAO.editLogIn("sdsfdgfhh", "natik");
        //fileUserDAO.editPassword("jdsfdgfhh", new char[]{'n', 'a'});
        //System.out.println(fileUserDAO.getUser("fdsfdgfhh"));

        //FileAccountDAO fileAccountDAO = new FileAccountDAO("accounts.txt", ", ");
        //fileAccountDAO.addAccount(new User("nit@nat.com"), new Account("student card", new BigDecimal(24.25)));
        //Account account = new Account("natik card", new BigDecimal(24.25), -2041586231);
        //account.setBalance(new BigDecimal(18.35));
        //account.setName("not a student anymore card");
        //fileAccountDAO.editAccount(fileUserDAO.getUser("pak@nat.com"), account);
        //fileAccountDAO.deleteAccount(fileUserDAO.getUser("pak@nat.com"), account);
        //fileAccountDAO.addAccount(new User("hashick"), account);
        //System.out.println(fileAccountDAO.getAllAccounts(fileUserDAO.getUser("pak@nat.com")));
        //System.out.println(fileAccountDAO.getAllAccounts(new User("j")));
        //System.out.println(ExpenseCategory.valueOf("BILLS"));
        //System.out.println(new Date().hashCode());

        /*
        FileExpenseDAO fileExpenseDAO = new FileExpenseDAO("transactions.txt", ", ");
        User user = fileUserDAO.getUser("nat@nat.com");
        Account account1 = fileAccountDAO.getAllAccounts(fileUserDAO.getUser("nat@nat.com")).get(0);
        Expense expense = new Expense(new BigDecimal(212.23), ExpenseCategory.CLOTHES, account1.getId(), new Date(), -2037529841);

        //expense.setCategory(ExpenseCategory.EATING_OUT);

        //expense.setNote("Poor but stunning");
        //fileExpenseDAO.addExpense(fileUserDAO.getUser("nat@nat.com"), expense);
        //fileExpenseDAO.editExpense(fileUserDAO.getUser("nat@nat.com"), expense);
        //fileExpenseDAO.deleteExpense(fileUserDAO.getUser("nat@nat.com"), expense);
        //System.out.println(fileExpenseDAO.getAllExpenses(user));
        Income income = new Income(new BigDecimal(450.90), IncomeCategory.DEPOSITS, account1.getId(), new Date(), -2024269231);
        Income income1 = (Income) income.clone();
        System.out.println(income.getId()+" "+income1.getId());
        //FileIncomeDAO fileIncomeDAO = new FileIncomeDAO("transactions.txt", ", ");
        //fileIncomeDAO.addIncome(user, income);
        //income.setAccount(-2109005335);
        //fileIncomeDAO.editIncome(user, income);
        //fileIncomeDAO.deleteIncome(user, income);
        //System.out.println(fileIncomeDAO.getAllIncomes(user));
        //FinanceTrackerServiceImpl financeTracker = new FinanceTrackerServiceImpl(user);
        //financeTracker.addExpense(null);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        User user1 = new User();  */
        /*
        try {
             //user1 = serviceFactory.getClientService().logIn("nat@nat.com", new char[]{'f', 'd'});
        } catch (ClientServiceException e) {
            e.printStackTrace();
        } */

        //FinanceTrackerService financeTrackerService = serviceFactory.getFinanceTrackerService();
        //financeTrackerService.setUser(user1);
        //financeTrackerService.addExpense(expense);
        //System.out.println(Arrays.toString(financeTrackerService.getTransactionsHistory().toArray()));


        Controller controller = new Controller();
        //it should return user logIn and id


        //String logIn = response.split(" ")[0];
        //String id = response.split(" ")[1];

        //logIn id sum category accountId date note
        //String response2 = controller.executeTask("get_accounts " + logIn + " " + id);
        //String accountId = response2.split(" ")[0];
       // String date = new Date().toString().replace(' ', '_');
        //String response1 = controller.executeTask("add_expense " + logIn + " " + id + " 124.67 children " +
          //      accountId + " " + date);


        printStartMenu();
        Scanner scanner = new Scanner(System.in);
        while(!scanner.hasNextInt()){
            scanner.next();
        }
        int choice = scanner.nextInt();
        boolean loggedIn;
        String response;
        switch (choice){
            case 1:
                response = controller.executeTask("sign_up raat@nat.com Nosequepuedoescribiraqui");
                loggedIn = true;
                break;
            case 2:
                response = controller.executeTask("log_in rat@nat.com Nosequepuedoescribiraqui");
                loggedIn = true;
                break;
            default:
                response = "Enter number from the range above";
                loggedIn = false;
        }
        if(loggedIn) {
            System.out.println(response);
        }
    }

    private static void printStartMenu(){
        System.out.println("Choose your destiny:\n" +
                "1 - Sign Up\n" +
                "2 - Log in\n");
    }

    private static void printTrackerMenu(){
        System.out.println("Choose your destiny again:\n" +
                "1 - add expense\n" +
                "2 - add income\n" +
                "3 - get transactions history\n" +
                "4 - add account\n" +
                "5 - edit expense\n" +
                "6 - edit income\n" +
                "7 - get current balance\n" +
                "8 - deactivate your account\n" +
                "9 - edit logIn\n" +
                "10 - change password");

    }
}
