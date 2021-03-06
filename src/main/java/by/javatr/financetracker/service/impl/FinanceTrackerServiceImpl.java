package by.javatr.financetracker.service.impl;

import by.javatr.financetracker.entity.*;
import by.javatr.financetracker.dao.AccountDAO;
import by.javatr.financetracker.dao.ExpenseDAO;
import by.javatr.financetracker.dao.IncomeDAO;
import by.javatr.financetracker.exception.DAOException;
import by.javatr.financetracker.factory.DAOFactory;
import by.javatr.financetracker.service.FinanceTrackerService;
import by.javatr.financetracker.exception.ServiceException;
import by.javatr.financetracker.service.validation.FinanceTrackerServiceValidator;

import java.math.BigDecimal;
import java.util.*;

public class FinanceTrackerServiceImpl implements FinanceTrackerService {

    private DAOFactory daoFactory = DAOFactory.getInstance();
    private AccountDAO accountDAO = daoFactory.getAccountDAO();
    private ExpenseDAO expenseDAO = daoFactory.getExpenseDAO();
    private IncomeDAO incomeDAO = daoFactory.getIncomeDAO();

    private FinanceTrackerServiceValidator validator = new FinanceTrackerServiceValidator();

    @Override
    public void addExpense(int userId, Expense expense) throws ServiceException {

        if (expense == null) {
            throw new ServiceException("Null expense.");
        }

        if (!validator.isValidTransactionSum(expense.getSum())) {
            throw new ServiceException("Invalid transaction sum.");
        }

        if (!validator.isValidTransactionDate(expense.getDate())) {
            throw new ServiceException("Invalid transaction date.");
        }

        if (!validator.isValidNote(expense.getNote())) {
            throw new ServiceException("Null note.");
        }

        try {
            if (!accountDAO.hasAccount(userId, expense.getAccountId())) {
                throw new ServiceException("User does not have such account.");
            }
        } catch (DAOException e) {
            throw new ServiceException("Failed to access accounts' data.", e);
        }

        try {
            expenseDAO.addExpense(userId, expense);
        } catch (DAOException e) {
            throw new ServiceException("Failed to access expenses' data.", e);
        }
    }


    @Override
    public void editExpense(int userId, Expense editedExpense) throws ServiceException {

        if (editedExpense == null) {
            throw new ServiceException("Null expense.");
        }

        if (!validator.isValidTransactionSum(editedExpense.getSum())) {
            throw new ServiceException("Invalid transaction sum.");
        }

        if (!validator.isValidTransactionDate(editedExpense.getDate())) {
            throw new ServiceException("Invalid transaction date.");
        }

        if (!validator.isValidNote(editedExpense.getNote())) {
            throw new ServiceException("Null note.");
        }

        try {
            if (!accountDAO.hasAccount(userId, editedExpense.getAccountId())) {
                throw new ServiceException("User does not have such account.");
            }
        } catch (DAOException e) {
            throw new ServiceException("Failed to access accounts' data.", e);
        }

        Expense expense;
        try {
            expense = expenseDAO.getExpense(userId, editedExpense.getId());
        } catch (DAOException e) {
            throw new ServiceException("Failed to access expenses' data", e);
        }

        BigDecimal sumChange = editedExpense.getSum().subtract(expense.getSum());
        try {
            if (editedExpense.getAccountId() != expense.getAccountId()) {
                Account oldAccount = accountDAO.getAccount(userId, expense.getAccountId());
                Account newAccount = accountDAO.getAccount(userId, editedExpense.getAccountId());
                oldAccount.setBalance(oldAccount.getBalance().add(expense.getSum()));

                newAccount.setBalance(newAccount.getBalance().subtract(editedExpense.getSum()));
                accountDAO.editAccount(userId, oldAccount);
                accountDAO.editAccount(userId, newAccount);
            } else {
                Account account = accountDAO.getAccount(userId, editedExpense.getAccountId());
                account.setBalance(account.getBalance().subtract(sumChange));
                accountDAO.editAccount(userId, account);
            }
        } catch (DAOException e) {
            throw new ServiceException("Failed to access accounts' data.", e);
        }

        try {
            expenseDAO.editExpense(userId, editedExpense);
        } catch (DAOException e) {
            throw new ServiceException("Failed to access expenses' data.", e);
        }

    }

    @Override
    public void deleteExpense(int userId, int expenseId) throws ServiceException {
        try {
            expenseDAO.deleteExpense(userId, expenseId);
        } catch (DAOException e) {
            throw new ServiceException("Failed to access expenses' data.", e);
        }
    }

    @Override
    public void addIncome(int userId, Income income) throws ServiceException {

        if (income == null) {
            throw new ServiceException("Null income.");
        }

        if (!validator.isValidTransactionSum(income.getSum())) {
            throw new ServiceException("Invalid transaction sum.");
        }

        if (!validator.isValidTransactionDate(income.getDate())) {
            throw new ServiceException("Invalid transaction date.");
        }

        if (!validator.isValidNote(income.getNote())) {
            throw new ServiceException("Null note.");
        }


        try {
            if (!accountDAO.hasAccount(userId, income.getAccountId())) {
                throw new ServiceException("User does not have such account.");
            }
        } catch (DAOException e) {
            throw new ServiceException("Failed to access accounts' data.", e);
        }

        try {
            incomeDAO.addIncome(userId, income);
        } catch (DAOException e) {
            throw new ServiceException("Failed to access incomes' data.", e);
        }
    }

    @Override
    public void editIncome(int userId, Income editedIncome) throws ServiceException {
        if (editedIncome == null) {
            throw new ServiceException("Null income.");
        }

        if (!validator.isValidTransactionSum(editedIncome.getSum())) {
            throw new ServiceException("Invalid transaction sum.");
        }

        if (!validator.isValidTransactionDate(editedIncome.getDate())) {
            throw new ServiceException("Invalid transaction date.");
        }

        if (!validator.isValidNote(editedIncome.getNote())) {
            throw new ServiceException("Null note.");
        }

        try {
            if (!accountDAO.hasAccount(userId, editedIncome.getAccountId())) {
                throw new ServiceException("User does not have such account.");
            }
        } catch (DAOException e) {
            throw new ServiceException("Failed to access accounts' data.", e);
        }

        Income income;
        try {
            income = incomeDAO.getIncome(userId, editedIncome.getId());
        } catch (DAOException e) {
            throw new ServiceException("Failed to access incomes' data.", e);
        }

        BigDecimal sumChange = editedIncome.getSum().subtract(income.getSum());
        try {
            if (editedIncome.getAccountId() != income.getAccountId()) {
                Account oldAccount = accountDAO.getAccount(userId, income.getAccountId());
                Account newAccount = accountDAO.getAccount(userId, editedIncome.getAccountId());
                oldAccount.setBalance(oldAccount.getBalance().add(income.getSum()));

                newAccount.setBalance(newAccount.getBalance().subtract(editedIncome.getSum()));
                accountDAO.editAccount(userId, oldAccount);
                accountDAO.editAccount(userId, newAccount);
            } else {
                Account account = accountDAO.getAccount(userId, editedIncome.getAccountId());
                account.setBalance(account.getBalance().subtract(sumChange));
                accountDAO.editAccount(userId, account);
            }
        } catch (DAOException e) {
            throw new ServiceException("Failed to access accounts' data.", e);
        }

        try {
            incomeDAO.editIncome(userId, editedIncome);
        } catch (DAOException e) {
            throw new ServiceException("Failed to access incomes' data.", e);
        }
    }

    @Override
    public void deleteIncome(int userId, int incomeId) throws ServiceException {
        try {
            incomeDAO.deleteIncome(userId, incomeId);
        } catch (DAOException e) {
            throw new ServiceException("Failed to access incomes' data.", e);
        }
    }

    @Override
    public void addAccount(int userId, Account account) throws ServiceException {

        if (account == null) {
            throw new ServiceException("Null account.");
        }

        if (!validator.isValidAccountName(account.getName())) {
            throw new ServiceException("Null account name.");
        }

        try {
            accountDAO.addAccount(userId, account);
        } catch (DAOException e) {
            throw new ServiceException("Failed to access accounts' data.", e);
        }
    }

    @Override
    public void editAccount(int userId, Account editedAccount) throws ServiceException {
        if (editedAccount == null) {
            throw new ServiceException("Null account.");
        }

        if (!validator.isValidAccountName(editedAccount.getName())) {
            throw new ServiceException("Null account name.");
        }

        try {
            accountDAO.editAccount(userId, editedAccount);
        } catch (DAOException e) {
            throw new ServiceException("Failed to access accounts' data.", e);
        }
    }

    @Override
    public void deleteAccount(int userId, int accountId) throws ServiceException {
        try {
            accountDAO.deleteAccount(userId, accountId);
        } catch (DAOException e) {
            throw new ServiceException("Failed to access accounts' data.", e);
        }
    }

    @Override
    public Account[] getAccounts(int userId) throws ServiceException {
        Account[] accounts;
        try {
            accounts = accountDAO.getAllAccounts(userId);
        } catch (DAOException e) {
            throw new ServiceException("Failed to access accounts' data.", e);
        }
        return accounts;
    }

    @Override
    public Transaction[] getTransactionsHistory(int userId) throws ServiceException {

        ArrayList<Transaction> transactions = new ArrayList<>();

        try {
            transactions.addAll(Arrays.asList(expenseDAO.getAllExpenses(userId)));
        } catch (DAOException e) {
            throw new ServiceException("Failed to access expenses' data.", e);
        }

        try {
            transactions.addAll(Arrays.asList(incomeDAO.getAllIncomes(userId)));
        } catch (DAOException e) {
            throw new ServiceException("Failed to access incomes' data.", e);
        }

        transactions.sort(new TransactionByDateComparator());
        Transaction[] transactionsArray = new Transaction[transactions.size()];
        return transactions.toArray(transactionsArray);
    }

    @Override
    public Transaction[] getTransactionsHistory(int userId, Date date) throws ServiceException {

        ArrayList<Transaction> transactions = new ArrayList<>();

        try {
            transactions.addAll(Arrays.asList(expenseDAO.getAllExpenses(userId)));
        } catch (DAOException e) {
            throw new ServiceException("Failed to access expenses' data.", e);
        }

        try {
            transactions.addAll(Arrays.asList(incomeDAO.getAllIncomes(userId)));
        } catch (DAOException e) {
            throw new ServiceException("Failed to access incomes' data.", e);
        }

        ArrayList<Transaction> transactionsFromSpecificDate = new ArrayList<>();
        Calendar cal1, cal2;
        for (Transaction transaction : transactions) {
            cal1 = Calendar.getInstance();
            cal2 = Calendar.getInstance();
            cal1.setTime(transaction.getDate());
            cal2.setTime(date);
            boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);

            if (sameDay) {
                transactionsFromSpecificDate.add(transaction);
            }
        }
        transactionsFromSpecificDate.sort(new TransactionByDateComparator());
        Transaction[] transactionsArray = new Transaction[transactionsFromSpecificDate.size()];
        return transactionsFromSpecificDate.toArray(transactionsArray);
    }

    @Override
    public void transferMoney(int userId, Account accountSender, Account accountReceiver, BigDecimal sum) throws ServiceException {

        try {
            if (!accountDAO.hasAccount(userId, accountSender.getId())) {
                throw new ServiceException("User does not have such account " + accountSender.getName());
            }
        } catch (DAOException e) {
            throw new ServiceException("Failed to access accounts' data.", e);
        }

        try {
            if (!accountDAO.hasAccount(userId, accountReceiver.getId())) {
                throw new ServiceException("User does not have such account " + accountReceiver.getName());
            }
        } catch (DAOException e) {
            throw new ServiceException("Failed to access accounts' data.", e);
        }

        if (accountSender.getId() == accountReceiver.getId()) {
            throw new ServiceException("Attempt to transfer money to the same account");
        }

        if (!validator.isValidTransactionSum(sum)) {
            throw new ServiceException("Invalid transaction sum");
        }

        accountSender.setBalance(accountSender.getBalance().subtract(sum));
        accountReceiver.setBalance(accountReceiver.getBalance().add(sum));

        try {
            accountDAO.editAccount(userId, accountSender);
            accountDAO.editAccount(userId, accountReceiver);
        } catch (DAOException e) {
            throw new ServiceException("Failed to access accounts' data.", e);
        }
    }

    @Override
    public BigDecimal getCurrentBalance(int userId) throws ServiceException {
        Account[] accounts;
        try {
            accounts = accountDAO.getAllAccounts(userId);
        } catch (DAOException e) {
            throw new ServiceException("Failed to access accounts' data.", e);
        }

        BigDecimal currentBalance = new BigDecimal(0.0);
        for (Account account : accounts) {
            currentBalance = currentBalance.add(account.getBalance());
        }
        return currentBalance;
    }

    @Override
    public Map<ExpenseCategory, BigDecimal> getExpensesByCategory(int userId) throws ServiceException {
        Map<ExpenseCategory, BigDecimal> expensesByCategory = new HashMap<>();
        try {
            Expense[] expenses = expenseDAO.getAllExpenses(userId);
            for (Expense expense : expenses) {
                if (expensesByCategory.containsKey((ExpenseCategory) expense.getCategory())) {
                    expensesByCategory.put((ExpenseCategory) expense.getCategory(),
                            expensesByCategory.get(expense.getCategory()).add(expense.getSum()));
                } else {
                    expensesByCategory.put((ExpenseCategory) expense.getCategory(), expense.getSum());
                }
            }
        } catch (DAOException e) {
            throw new ServiceException("Failed to access expenses' data.", e);
        }
        return expensesByCategory;
    }

    @Override
    public Map<IncomeCategory, BigDecimal> getIncomesByCategory(int userId) throws ServiceException {
        Map<IncomeCategory, BigDecimal> incomesByCategory = new HashMap<>();
        try {
            Income[] incomes = incomeDAO.getAllIncomes(userId);
            for (Income income : incomes) {
                if (incomesByCategory.containsKey((IncomeCategory) income.getCategory())) {
                    incomesByCategory.put((IncomeCategory) income.getCategory(),
                            incomesByCategory.get(income.getCategory()).add(income.getSum()));
                } else {
                    incomesByCategory.put((IncomeCategory) income.getCategory(), income.getSum());
                }
            }
        } catch (DAOException e) {
            throw new ServiceException("Failed to access incomes' data.", e);
        }
        return incomesByCategory;
    }

    private class TransactionByDateComparator implements Comparator<Transaction> {

        @Override
        public int compare(Transaction transaction1, Transaction transaction2) {
            return transaction2.getDate().compareTo(transaction1.getDate());
        }
    }
}
