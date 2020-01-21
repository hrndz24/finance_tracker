package by.javatr.financetracker.dao.impl;

import by.javatr.financetracker.bean.Income;
import by.javatr.financetracker.bean.IncomeCategory;
import by.javatr.financetracker.dao.IncomeDAO;
import by.javatr.financetracker.dao.exception.DAOException;
import by.javatr.financetracker.dao.stringvalues.StringProperty;

import java.io.*;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class FileIncomeDAO implements IncomeDAO {

    private File transactionsFile;
    private String dataSeparator;
    private File transferFile = new File(StringProperty.getStringValue("transferFilePath"));

    public FileIncomeDAO(File transactionsFile, String dataSeparator) {
        this.transactionsFile = transactionsFile;
        this.dataSeparator = dataSeparator;
    }

    public FileIncomeDAO(String path, String dataSeparator) {
        this.transactionsFile = new File(path);
        this.dataSeparator = dataSeparator;
    }

    @Override
    public void addIncome(int userId, Income income) throws DAOException {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(transactionsFile, "rw");
            RandomAccessFile transfer = new RandomAccessFile(transferFile, "rw");

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
                randomAccessFile.writeBytes(userId + dataSeparator + income.getId() + dataSeparator + "+" + income.getSum()
                        + dataSeparator + income.getCategory() + dataSeparator + income.getNote() + dataSeparator
                        + income.getDate() + dataSeparator + income.getAccountId() + "\n");
                sourceChannel.transferFrom(transferChannel, randomAccessFile.getFilePointer(), transferChannel.size());
                break;
            }

            if (currentTransaction == null) {
                randomAccessFile.writeBytes(userId + dataSeparator + income.getId() + dataSeparator + "+" + income.getSum()
                        + dataSeparator + income.getCategory() + dataSeparator + income.getNote() + dataSeparator
                        + income.getDate() + dataSeparator + income.getAccountId() + "\n");
            }
            transfer.setLength(0);
        } catch (IOException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void editIncome(int userId, Income income) throws DAOException {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(transactionsFile, "rw");
            RandomAccessFile transfer = new RandomAccessFile(transferFile, "rw");

            FileChannel sourceChannel = randomAccessFile.getChannel();
            FileChannel transferChannel = transfer.getChannel();
            String currentTransaction, currentTransactionUserId, currentTransactionId;

            while ((currentTransaction = randomAccessFile.readLine()) != null) {
                currentTransactionUserId = currentTransaction.split(dataSeparator)[0];
                currentTransactionId = currentTransaction.split(dataSeparator)[1];
                if (!(currentTransactionUserId.equals(String.valueOf(userId)) && currentTransactionId.equals(String.valueOf(income.getId())))) {
                    continue;
                }
                break;
            }
            if (currentTransaction == null) {
                throw new DAOException("Income not found");
            }
            long position = randomAccessFile.getFilePointer();
            sourceChannel.position(position);
            transferChannel.transferFrom(sourceChannel, 0, sourceChannel.size() - position);
            randomAccessFile.setLength(position - currentTransaction.length() - 1);
            randomAccessFile.seek(position - currentTransaction.length() - 1);
            randomAccessFile.writeBytes(userId + dataSeparator + income.getId() + dataSeparator + "+" + income.getSum()
                    + dataSeparator + income.getCategory() + dataSeparator + income.getNote() + dataSeparator
                    + income.getDate() + dataSeparator + income.getAccountId() + "\n");
            sourceChannel.transferFrom(transferChannel, randomAccessFile.getFilePointer(), transferChannel.size());

            transfer.setLength(0);
        } catch (IOException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteIncome(int userId, int incomeId) throws DAOException {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(transactionsFile, "rw");
            RandomAccessFile transfer = new RandomAccessFile(transferFile, "rw");

            FileChannel sourceChannel = randomAccessFile.getChannel();
            FileChannel transferChannel = transfer.getChannel();
            String currentTransaction, currentTransactionUserId, currentTransactionId;

            while ((currentTransaction = randomAccessFile.readLine()) != null) {
                currentTransactionUserId = currentTransaction.split(dataSeparator)[0];
                currentTransactionId = currentTransaction.split(dataSeparator)[1];
                if ((currentTransactionUserId.equals(String.valueOf(userId)) && currentTransactionId.equals(String.valueOf(incomeId)))) {
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
            throw new DAOException(e);
        }
    }

    @Override
    public Income getIncome(int userId, int incomeId) throws DAOException {
        try {
            boolean isFound = false;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(transactionsFile));
            String currentTransaction, currentTransactionUserId, currentTransactionId;
            String[] transactionInfo;
            Income income = new Income();
            while ((currentTransaction = bufferedReader.readLine()) != null) {
                transactionInfo = currentTransaction.split(dataSeparator);
                currentTransactionUserId = transactionInfo[0];
                currentTransactionId = transactionInfo[1];
                if (currentTransactionUserId.equals(String.valueOf(userId)) && currentTransactionId.equals(String.valueOf(incomeId))) {
                    income = new Income(new BigDecimal(Math.abs(Double.parseDouble(transactionInfo[2]))),
                            IncomeCategory.valueOf(transactionInfo[3]), Integer.parseInt(transactionInfo[6]),
                            new SimpleDateFormat(StringProperty.getStringValue("dateFormat"), Locale.ENGLISH).parse(transactionInfo[5]), transactionInfo[4],
                            Integer.parseInt(transactionInfo[1]));
                    isFound = true;
                    break;
                }
            }

            if (!isFound) {
                throw new DAOException("User not found");
            }
            return income;
        } catch (IOException | ParseException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Income[] getAllIncomes(int userId) throws DAOException {
        try {

            ArrayList<Income> incomes = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(transactionsFile));
            String currentTransaction, currentTransactionUserId;
            String[] transactionInfo;
            while ((currentTransaction = bufferedReader.readLine()) != null) {
                transactionInfo = currentTransaction.split(dataSeparator);
                currentTransactionUserId = transactionInfo[0];
                if (!(currentTransactionUserId.equals(String.valueOf(userId)) && Double.parseDouble(transactionInfo[2]) > 0)) {
                    continue;
                }

                incomes.add(new Income(new BigDecimal(Math.abs(Double.parseDouble(transactionInfo[2]))),
                        IncomeCategory.valueOf(transactionInfo[3]), Integer.parseInt(transactionInfo[6]),
                        new SimpleDateFormat(StringProperty.getStringValue("dateFormat"), Locale.ENGLISH).parse(transactionInfo[5]), transactionInfo[4],
                        Integer.parseInt(transactionInfo[1])));
            }

            Income[] incomesArray = new Income[incomes.size()];
            return incomes.toArray(incomesArray);
        } catch (IOException | ParseException e) {
            throw new DAOException(e);
        }
    }
}
