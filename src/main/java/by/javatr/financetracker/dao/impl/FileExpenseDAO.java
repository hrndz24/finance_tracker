package by.javatr.financetracker.dao.impl;

import by.javatr.financetracker.bean.Expense;
import by.javatr.financetracker.bean.ExpenseCategory;
import by.javatr.financetracker.dao.ExpenseDAO;
import by.javatr.financetracker.dao.exception.DAOException;
import by.javatr.financetracker.dao.stringvalues.StringProperty;

import java.io.*;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class FileExpenseDAO implements ExpenseDAO {

    private File transactionsFile;
    private String dataSeparator;
    private File transferFile = new File(StringProperty.getStringValue("transferFilePath"));

    public FileExpenseDAO(File transactionsFile, String dataSeparator) {
        this.transactionsFile = transactionsFile;
        this.dataSeparator = dataSeparator;
    }

    public FileExpenseDAO(String path, String dataSeparator) {
        this.transactionsFile = new File(path);
        this.dataSeparator = dataSeparator;
    }

    @Override
    public void addExpense(int userId, Expense expense) throws DAOException {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(transactionsFile, "rw");
             RandomAccessFile transfer = new RandomAccessFile(transferFile, "rw");){

            FileChannel sourceChannel = randomAccessFile.getChannel();
            FileChannel transferChannel = transfer.getChannel();
            String currentTransaction, currentTransactionUserId;

            while ((currentTransaction = randomAccessFile.readLine()) != null) {
                currentTransactionUserId = currentTransaction.split(dataSeparator)[0];
                if (!currentTransactionUserId.equals(String.valueOf(userId))) {
                    continue;
                }

                long position = randomAccessFile.getFilePointer();
                sourceChannel.position(position);
                transferChannel.transferFrom(sourceChannel, 0, sourceChannel.size() - position);
                randomAccessFile.setLength(position);
                randomAccessFile.writeBytes(userId + dataSeparator + expense.getId() + dataSeparator + "-" + expense.getSum()
                        + dataSeparator + expense.getCategory() + dataSeparator + expense.getNote() + dataSeparator
                        + expense.getDate() + dataSeparator + expense.getAccountId() + "\n");
                sourceChannel.transferFrom(transferChannel, randomAccessFile.getFilePointer(), transferChannel.size());
                break;
            }

            if (currentTransaction == null) {
                randomAccessFile.writeBytes(userId + dataSeparator + expense.getId() + dataSeparator + "-" + expense.getSum()
                        + dataSeparator + expense.getCategory() + dataSeparator + expense.getNote() + dataSeparator
                        + expense.getDate() + dataSeparator + expense.getAccountId() + "\n");
            }
            transfer.setLength(0);
        } catch (IOException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void editExpense(int userId, Expense editedExpense) throws DAOException {

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(transactionsFile, "rw");
             RandomAccessFile transfer = new RandomAccessFile(transferFile, "rw");){

            FileChannel sourceChannel = randomAccessFile.getChannel();
            FileChannel transferChannel = transfer.getChannel();
            String currentTransaction, currentTransactionUserId, currentTransactionId;

            while ((currentTransaction = randomAccessFile.readLine()) != null) {
                currentTransactionUserId = currentTransaction.split(dataSeparator)[0];
                currentTransactionId = currentTransaction.split(dataSeparator)[1];
                if (!(currentTransactionUserId.equals(String.valueOf(userId)) && currentTransactionId.equals(String.valueOf(editedExpense.getId())))) {
                    continue;
                }
                break;
            }
            if (currentTransaction == null) {
                throw new DAOException("Expense not found");
            }
            long position = randomAccessFile.getFilePointer();
            sourceChannel.position(position);
            transferChannel.transferFrom(sourceChannel, 0, sourceChannel.size() - position);
            randomAccessFile.setLength(position - currentTransaction.length() - 1);
            randomAccessFile.seek(position - currentTransaction.length() - 1);
            randomAccessFile.writeBytes(userId + dataSeparator + editedExpense.getId() + dataSeparator + "-" + editedExpense.getSum()
                    + dataSeparator + editedExpense.getCategory() + dataSeparator + editedExpense.getNote() + dataSeparator
                    + editedExpense.getDate() + dataSeparator + editedExpense.getAccountId() + "\n");
            sourceChannel.transferFrom(transferChannel, randomAccessFile.getFilePointer(), transferChannel.size());

            transfer.setLength(0);
        } catch (IOException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteExpense(int userId, int expenseId) throws DAOException {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(transactionsFile, "rw");
             RandomAccessFile transfer = new RandomAccessFile(transferFile, "rw");){

            FileChannel sourceChannel = randomAccessFile.getChannel();
            FileChannel transferChannel = transfer.getChannel();
            String currentTransaction, currentTransactionUserId, currentTransactionId;

            while ((currentTransaction = randomAccessFile.readLine()) != null) {
                currentTransactionUserId = currentTransaction.split(dataSeparator)[0];
                currentTransactionId = currentTransaction.split(dataSeparator)[1];
                if ((currentTransactionUserId.equals(String.valueOf(userId)) && currentTransactionId.equals(String.valueOf(expenseId)))) {
                    long position = randomAccessFile.getFilePointer();
                    sourceChannel.position(position);
                    transferChannel.transferFrom(sourceChannel, 0, sourceChannel.size() - position);
                    randomAccessFile.setLength(position - currentTransaction.length() - 1);
                    randomAccessFile.seek(position - currentTransaction.length() - 1);
                    sourceChannel.transferFrom(transferChannel, randomAccessFile.getFilePointer(), transferChannel.size());
                    break;
                }
            }

            transfer.setLength(0);
        } catch (IOException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public Expense getExpense(int userId, int expenseId) throws DAOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(transactionsFile));) {
            boolean isFound = false;
            String currentTransaction, currentTransactionUserId, currentTransactionId;
            String[] transactionInfo;
            Expense expense = new Expense();
            while ((currentTransaction = bufferedReader.readLine()) != null) {
                transactionInfo = currentTransaction.split(dataSeparator);
                currentTransactionUserId = transactionInfo[0];
                currentTransactionId = transactionInfo[1];
                if (currentTransactionUserId.equals(String.valueOf(userId)) && currentTransactionId.equals(String.valueOf(expenseId))) {
                    expense = new Expense(new BigDecimal(Math.abs(Double.parseDouble(transactionInfo[2]))),
                            ExpenseCategory.valueOf(transactionInfo[3]), Integer.parseInt(transactionInfo[6]),
                            new SimpleDateFormat(StringProperty.getStringValue("dateFormat"), Locale.ENGLISH).parse(transactionInfo[5]), transactionInfo[4],
                            Integer.parseInt(transactionInfo[1]));
                    isFound = true;
                    break;
                }
            }

            if (!isFound) {
                throw new DAOException("User not found");
            }
            return expense;
        } catch (IOException | ParseException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Expense[] getAllExpenses(int userId) throws DAOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(transactionsFile));){
            ArrayList<Expense> expenses = new ArrayList<>();
            String currentTransaction, currentTransactionUserId;
            String[] transactionInfo;
            while ((currentTransaction = bufferedReader.readLine()) != null) {
                transactionInfo = currentTransaction.split(dataSeparator);
                currentTransactionUserId = transactionInfo[0];
                if (!(currentTransactionUserId.equals(String.valueOf(userId)) && Double.parseDouble(transactionInfo[2]) < 0)) {
                    continue;
                }
                expenses.add(new Expense(new BigDecimal(Math.abs(Double.parseDouble(transactionInfo[2]))),
                        ExpenseCategory.valueOf(transactionInfo[3]), Integer.parseInt(transactionInfo[6]),
                        new SimpleDateFormat(StringProperty.getStringValue("dateFormat"), Locale.ENGLISH).parse(transactionInfo[5]), transactionInfo[4],
                        Integer.parseInt(transactionInfo[1])));
            }

            Expense[] expensesArray = new Expense[expenses.size()];
            return expenses.toArray(expensesArray);
        } catch (IOException | ParseException e) {
            throw new DAOException(e);
        }
    }
}
