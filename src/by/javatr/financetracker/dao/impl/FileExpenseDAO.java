package by.javatr.financetracker.dao.impl;

import by.javatr.financetracker.bean.Expense;
import by.javatr.financetracker.bean.ExpenseCategory;
import by.javatr.financetracker.bean.User;
import by.javatr.financetracker.dao.ExpenseDAO;
import by.javatr.financetracker.dao.exception.DAOException;
import by.javatr.financetracker.dao.exception.ExpenseNotFoundException;
import by.javatr.financetracker.dao.exception.UserNotFoundException;

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

    public FileExpenseDAO(File transactionsFile, String dataSeparator) {
        this.transactionsFile = transactionsFile;
        this.dataSeparator = dataSeparator;
    }

    public FileExpenseDAO(String path, String dataSeparator) {
        this.transactionsFile = new File(path);
        this.dataSeparator = dataSeparator;
    }

    @Override
    public void addExpense(User user, Expense expense) throws DAOException {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(transactionsFile, "rw");
            RandomAccessFile transfer = new RandomAccessFile("transfer.txt", "rw");

            FileChannel sourceChannel = randomAccessFile.getChannel();
            FileChannel transferChannel = transfer.getChannel();
            String currentTransaction, currentTransactionUserId;
            int userId = user.getId();

            while ((currentTransaction = randomAccessFile.readLine()) != null) {
                currentTransactionUserId = currentTransaction.split(dataSeparator)[0];
                if (!currentTransactionUserId.equals(String.valueOf(userId))) {
                    continue;
                }

                long position = randomAccessFile.getFilePointer();
                sourceChannel.position(position); // sets the pointer at @position to start transferring info from it
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
            //TODO create message
            throw new DAOException(e);
        }
    }

    @Override
    public void editExpense(User user, Expense expense) throws DAOException {

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(transactionsFile, "rw");
            RandomAccessFile transfer = new RandomAccessFile("transfer.txt", "rw");

            FileChannel sourceChannel = randomAccessFile.getChannel();
            FileChannel transferChannel = transfer.getChannel();
            String currentTransaction, currentTransactionUserId, currentTransactionId;
            int userId = user.getId();

            while ((currentTransaction = randomAccessFile.readLine()) != null) {
                currentTransactionUserId = currentTransaction.split(dataSeparator)[0];
                currentTransactionId = currentTransaction.split(dataSeparator)[1];
                if (!(currentTransactionUserId.equals(String.valueOf(userId)) && currentTransactionId.equals(String.valueOf(expense.getId())))) {
                    continue;
                }
                break;
            }
            if (currentTransaction == null) {
                //TODO create message
                throw new ExpenseNotFoundException();
            }
            long position = randomAccessFile.getFilePointer();
            sourceChannel.position(position); // sets the pointer at @position to start transferring info from it
            transferChannel.transferFrom(sourceChannel, 0, sourceChannel.size() - position);
            randomAccessFile.setLength(position - currentTransaction.length() - 2);
            randomAccessFile.seek(position - currentTransaction.length() - 2);
            randomAccessFile.writeBytes(userId + dataSeparator + expense.getId() + dataSeparator + "-" + expense.getSum()
                    + dataSeparator + expense.getCategory() + dataSeparator + expense.getNote() + dataSeparator
                    + expense.getDate() + dataSeparator + expense.getAccountId() + "\n");
            sourceChannel.transferFrom(transferChannel, randomAccessFile.getFilePointer(), transferChannel.size());

            transfer.setLength(0);
        } catch (IOException e) {
            //TODO create message
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteExpense(User user, Expense expense) throws DAOException {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(transactionsFile, "rw");
            RandomAccessFile transfer = new RandomAccessFile("transfer.txt", "rw");

            FileChannel sourceChannel = randomAccessFile.getChannel();
            FileChannel transferChannel = transfer.getChannel();
            String currentTransaction, currentTransactionUserId, currentTransactionId;
            int userId = user.getId();

            while ((currentTransaction = randomAccessFile.readLine()) != null) {
                currentTransactionUserId = currentTransaction.split(dataSeparator)[0];
                currentTransactionId = currentTransaction.split(dataSeparator)[1];
                if (!(currentTransactionUserId.equals(String.valueOf(userId)) && currentTransactionId.equals(String.valueOf(expense.getId())))) {
                    continue;
                }
                break;
            }
            if (currentTransaction == null) {
                //TODO create message
                throw new ExpenseNotFoundException();
            }
            long position = randomAccessFile.getFilePointer();
            sourceChannel.position(position); // sets the pointer at @position to start transferring info from it
            transferChannel.transferFrom(sourceChannel, 0, sourceChannel.size() - position);
            randomAccessFile.setLength(position - currentTransaction.length() - 2);
            randomAccessFile.seek(position - currentTransaction.length() - 2);
            sourceChannel.transferFrom(transferChannel, randomAccessFile.getFilePointer(), transferChannel.size());

            transfer.setLength(0);
        } catch (IOException e) {
            //TODO create message
            throw new DAOException(e);
        }
    }

    @Override
    public ArrayList<Expense> getAllExpenses(User user) throws DAOException {
        try {
            boolean isFound = false;
            ArrayList<Expense> expenses = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(transactionsFile));
            String currentTransaction, currentTransactionUserId;
            String[] transactionInfo;
            int id = user.getId();
            while ((currentTransaction = bufferedReader.readLine()) != null) {
                transactionInfo = currentTransaction.split(dataSeparator);
                currentTransactionUserId = transactionInfo[0];
                if (!(currentTransactionUserId.equals(String.valueOf(id)) && Double.parseDouble(transactionInfo[2]) < 0)) {
                    continue;
                }
                isFound = true;
                expenses.add(new Expense(new BigDecimal(Math.abs(Double.parseDouble(transactionInfo[2]))),
                        ExpenseCategory.valueOf(transactionInfo[3]), Integer.parseInt(transactionInfo[6]),
                        new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).parse(transactionInfo[5]), transactionInfo[4],
                        Integer.parseInt(transactionInfo[1])));
            }

            if (!isFound) {
                //TODO create message
                throw new UserNotFoundException();
            }
            return expenses;
        } catch (IOException | ParseException e) {
            //TODO create message
            throw new DAOException(e);
        }
    }
}
