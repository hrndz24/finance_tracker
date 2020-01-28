package by.javatr.financetracker.service.impl;

import by.javatr.financetracker.bean.*;
import by.javatr.financetracker.dao.AccountDAO;
import by.javatr.financetracker.dao.ExpenseDAO;
import by.javatr.financetracker.dao.IncomeDAO;
import by.javatr.financetracker.dao.exception.DAOException;
import by.javatr.financetracker.dao.factory.DAOFactory;
import by.javatr.financetracker.service.FinanceTrackerService;
import by.javatr.financetracker.service.exception.FinanceTrackerServiceException;
import by.javatr.financetracker.service.validation.FinanceTrackerServiceValidator;

import java.math.BigDecimal;
import java.util.*;

public class FinanceTrackerServiceImpl implements FinanceTrackerService {

    private DAOFactory daoFactory = DAOFactory.getInstance();
    private AccountDAO accountDAO = daoFactory.getAccountDAO();
    private ExpenseDAO expenseDAO = daoFactory.getExpenseDAO();
    private IncomeDAO incomeDAO = daoFactory.getIncomeDAO();

    @Override
    public void addExpense(int userId, Expense expense) throws FinanceTrackerServiceException {

        if (expense == null) {
            throw new FinanceTrackerServiceException("Null expense.");
        }

        if (!FinanceTrackerServiceValidator.isValidTransactionSum(expense.getSum())) {
            throw new FinanceTrackerServiceException("Invalid transaction sum.");
        }

        if (!FinanceTrackerServiceValidator.isValidTransactionDate(expense.getDate())) {
            throw new FinanceTrackerServiceException("Invalid transaction date.");
        }

        if (!FinanceTrackerServiceValidator.isValidNote(expense.getNote())) {
            throw new FinanceTrackerServiceException("Null note.");
        }

        try {
            if (!accountDAO.hasAccount(userId, expense.getAccountId())) {
                throw new FinanceTrackerServiceException("User does not have such account.");
            }
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to access accounts' data.", e);
        }

        try {
            expenseDAO.addExpense(userId, expense);
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to access expenses' data.", e);
        }
    }


    @Override
    public void editExpense(int userId, Expense editedExpense) throws FinanceTrackerServiceException {

        if (editedExpense == null) {
            throw new FinanceTrackerServiceException("Null expense.");
        }

        if (!FinanceTrackerServiceValidator.isValidTransactionSum(editedExpense.getSum())) {
            throw new FinanceTrackerServiceException("Invalid transaction sum.");
        }

        if (!FinanceTrackerServiceValidator.isValidTransactionDate(editedExpense.getDate())) {
            throw new FinanceTrackerServiceException("Invalid transaction date.");
        }

        if (!FinanceTrackerServiceValidator.isValidNote(editedExpense.getNote())) {
            throw new FinanceTrackerServiceException("Null note.");
        }

        try {
            if (!accountDAO.hasAccount(userId, editedExpense.getAccountId())) {
                throw new FinanceTrackerServiceException("User does not have such account.");
            }
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to access accounts' data.", e);
        }

        Expense expense;
        try {
            expense = expenseDAO.getExpense(userId, editedExpense.getId());
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to access expenses' data", e);
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
            throw new FinanceTrackerServiceException("Failed to access accounts' data.", e);
        }

        try {
            expenseDAO.editExpense(userId, editedExpense);
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to access expenses' data.", e);
        }

    }

    @Override
    public void deleteExpense(int userId, int expenseId) throws FinanceTrackerServiceException {
        try {
            expenseDAO.deleteExpense(userId, expenseId);
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to access expenses' data.", e);
        }
    }

    @Override
    public void addIncome(int userId, Income income) throws FinanceTrackerServiceException {

        if (income == null) {
            throw new FinanceTrackerServiceException("Null income.");
        }

        if (!FinanceTrackerServiceValidator.isValidTransactionSum(income.getSum())) {
            throw new FinanceTrackerServiceException("Invalid transaction sum.");
        }

        if (!FinanceTrackerServiceValidator.isValidTransactionDate(income.getDate())) {
            throw new FinanceTrackerServiceException("Invalid transaction date.");
        }

        if (!FinanceTrackerServiceValidator.isValidNote(income.getNote())) {
            throw new FinanceTrackerServiceException("Null note.");
        }


        try {
            if (!accountDAO.hasAccount(userId, income.getAccountId())) {
                throw new FinanceTrackerServiceException("User does not have such account.");
            }
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to access accounts' data.", e);
        }

        try {
            incomeDAO.addIncome(userId, income);
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to access incomes' data.", e);
        }
    }

    @Override
    public void editIncome(int userId, Income editedIncome) throws FinanceTrackerServiceException {
        if (editedIncome == null) {
            throw new FinanceTrackerServiceException("Null income.");
        }

        if (!FinanceTrackerServiceValidator.isValidTransactionSum(editedIncome.getSum())) {
            throw new FinanceTrackerServiceException("Invalid transaction sum.");
        }

        if (!FinanceTrackerServiceValidator.isValidTransactionDate(editedIncome.getDate())) {
            throw new FinanceTrackerServiceException("Invalid transaction date.");
        }

        if (!FinanceTrackerServiceValidator.isValidNote(editedIncome.getNote())) {
            throw new FinanceTrackerServiceException("Null note.");
        }

        try {
            if (!accountDAO.hasAccount(userId, editedIncome.getAccountId())) {
                throw new FinanceTrackerServiceException("User does not have such account.");
            }
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to access accounts' data.", e);
        }

        Income income;
        try {
            income = incomeDAO.getIncome(userId, editedIncome.getId());
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to access incomes' data.", e);
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
            throw new FinanceTrackerServiceException("Failed to access accounts' data.", e);
        }

        try {
            incomeDAO.editIncome(userId, editedIncome);
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to access incomes' data.", e);
        }
    }

    @Override
    public void deleteIncome(int userId, int incomeId) throws FinanceTrackerServiceException {
        try {
            incomeDAO.deleteIncome(userId, incomeId);
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to access incomes' data.", e);
        }
    }

    @Override
    public void addAccount(int userId, Account account) throws FinanceTrackerServiceException {

        if (account == null) {
            throw new FinanceTrackerServiceException("Null account.");
        }

        if (!FinanceTrackerServiceValidator.isValidAccountName(account.getName())) {
            throw new FinanceTrackerServiceException("Null account name.");
        }

        try {
            accountDAO.addAccount(userId, account);
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to access accounts' data.", e);
        }
    }

    @Override
    public void editAccount(int userId, Account editedAccount) throws FinanceTrackerServiceException {
        if (editedAccount == null) {
            throw new FinanceTrackerServiceException("Null account.");
        }

        if (!FinanceTrackerServiceValidator.isValidAccountName(editedAccount.getName())) {
            throw new FinanceTrackerServiceException("Null account name.");
        }

        try {
            accountDAO.editAccount(userId, editedAccount);
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to access accounts' data.", e);
        }
    }

    @Override
    public void deleteAccount(int userId, int accountId) throws FinanceTrackerServiceException {
        try {
            accountDAO.deleteAccount(userId, accountId);
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to access accounts' data.", e);
        }
    }

    @Override
    public Account[] getAccounts(int userId) throws FinanceTrackerServiceException {
        Account[] accounts;
        try {
            accounts = accountDAO.getAllAccounts(userId);
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to access accounts' data.", e);
        }
        return accounts;
    }

    @Override
    public Transaction[] getTransactionsHistory(int userId) throws FinanceTrackerServiceException {

        ArrayList<Transaction> transactions = new ArrayList<>();

        try {
            transactions.addAll(Arrays.asList(expenseDAO.getAllExpenses(userId)));
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to access expenses' data.", e);
        }

        try {
            transactions.addAll(Arrays.asList(incomeDAO.getAllIncomes(userId)));
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to access incomes' data.", e);
        }

        transactions.sort(new TransactionByDateComparator());
        Transaction[] transactionsArray = new Transaction[transactions.size()];
        return transactions.toArray(transactionsArray);
    }

    @Override
    public Transaction[] getTransactionsHistory(int userId, Date date) throws FinanceTrackerServiceException {

        ArrayList<Transaction> transactions = new ArrayList<>();

        try {
            transactions.addAll(Arrays.asList(expenseDAO.getAllExpenses(userId)));
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to access expenses' data.", e);
        }

        try {
            transactions.addAll(Arrays.asList(incomeDAO.getAllIncomes(userId)));
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to access incomes' data.", e);
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
    public void transferMoney(int userId, Account accountSender, Account accountReceiver, BigDecimal sum) throws FinanceTrackerServiceException {

        try {
            if (!accountDAO.hasAccount(userId, accountSender.getId())) {
                throw new FinanceTrackerServiceException("User does not have such account " + accountSender.getName());
            }
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to access accounts' data.", e);
        }

        try {
            if (!accountDAO.hasAccount(userId, accountReceiver.getId())) {
                throw new FinanceTrackerServiceException("User does not have such account " + accountReceiver.getName());
            }
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to access accounts' data.", e);
        }

        if (accountSender.getId() == accountReceiver.getId()) {
            throw new FinanceTrackerServiceException("Attempt to transfer money to the same account");
        }

        if (!FinanceTrackerServiceValidator.isValidTransactionSum(sum)) {
            throw new FinanceTrackerServiceException("Invalid transaction sum");
        }

        accountSender.setBalance(accountSender.getBalance().subtract(sum));
        accountReceiver.setBalance(accountReceiver.getBalance().add(sum));

        try {
            accountDAO.editAccount(userId, accountSender);
            accountDAO.editAccount(userId, accountReceiver);
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to access accounts' data.", e);
        }
    }

    @Override
    public BigDecimal getCurrentBalance(int userId) throws FinanceTrackerServiceException {
        Account[] accounts;
        try {
            accounts = accountDAO.getAllAccounts(userId);
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to access accounts' data.", e);
        }

        BigDecimal currentBalance = new BigDecimal(0.0);
        for (Account account : accounts) {
            currentBalance = currentBalance.add(account.getBalance());
        }
        return currentBalance;
    }

    @Override
    public Map<ExpenseCategory, BigDecimal> getExpensesByCategory(int userId) throws FinanceTrackerServiceException {
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
            throw new FinanceTrackerServiceException("Failed to access expenses' data.", e);
        }
        return expensesByCategory;
    }

    @Override
    public Map<IncomeCategory, BigDecimal> getIncomesByCategory(int userId) throws FinanceTrackerServiceException {
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
            throw new FinanceTrackerServiceException("Failed to access incomes' data.", e);
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
