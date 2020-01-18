package by.javatr.financetracker.dao.impl;

import by.javatr.financetracker.bean.Account;
import by.javatr.financetracker.bean.User;
import by.javatr.financetracker.dao.AccountDAO;
import by.javatr.financetracker.dao.exception.*;

import java.io.*;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class FileAccountDAO implements AccountDAO {

    private File accountsFile;
    private String dataSeparator;

    public FileAccountDAO(File file, String dataSeparator) {
        this.accountsFile = file;
        this.dataSeparator = dataSeparator;
    }

    public FileAccountDAO(String path, String dataSeparator) {
        this.accountsFile = new File(path);
        this.dataSeparator = dataSeparator;
    }

    @Override
    public void addAccount(User user, Account account) throws DAOException {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(accountsFile, "rw");
            RandomAccessFile transfer = new RandomAccessFile("transfer.txt", "rw");

            FileChannel sourceChannel = randomAccessFile.getChannel();
            FileChannel transferChannel = transfer.getChannel();
            String currentLine, userId;
            int id = user.getId();

            while ((currentLine = randomAccessFile.readLine()) != null) {
                userId = currentLine.split(dataSeparator)[0];
                if (!userId.equals(String.valueOf(id))) {
                    continue;
                }

                long position = randomAccessFile.getFilePointer();
                sourceChannel.position(position); // sets the pointer at @position to start transferring info from it
                transferChannel.transferFrom(sourceChannel, 0, sourceChannel.size() - position);
                randomAccessFile.setLength(position);
                randomAccessFile.writeBytes(id + dataSeparator + account.getId() + dataSeparator + account.getName() + dataSeparator + account.getBalance() + "\n");
                sourceChannel.transferFrom(transferChannel, randomAccessFile.getFilePointer(), transferChannel.size());
                break;
            }

            if (currentLine == null) {

                randomAccessFile.writeBytes(id + dataSeparator + account.getId() + dataSeparator + account.getName() + dataSeparator + account.getBalance() + "\n");
            }
            transfer.setLength(0);
        } catch (IOException e) {
            //TODO create message
            throw new FailedAddAccountException(e);
        }
    }

    @Override
    public void editAccount(User user, Account account) throws DAOException {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(accountsFile, "rw");
            RandomAccessFile transfer = new RandomAccessFile("transfer.txt", "rw");

            FileChannel sourceChannel = randomAccessFile.getChannel();
            FileChannel transferChannel = transfer.getChannel();
            String currentLine, userId, userAccountId;
            int id = user.getId();

            while ((currentLine = randomAccessFile.readLine()) != null) {
                userId = currentLine.split(dataSeparator)[0];
                userAccountId = currentLine.split(dataSeparator)[1];
                if (!(userId.equals(String.valueOf(id)) && userAccountId.equals(String.valueOf(account.getId())))) {
                    continue;
                }
                break;
            }
            if (currentLine == null) {
                //TODO create message
                throw new DAOException();
            }
            long position = randomAccessFile.getFilePointer();
            sourceChannel.position(position); // sets the pointer at @position to start transferring info from it
            transferChannel.transferFrom(sourceChannel, 0, sourceChannel.size() - position);
            randomAccessFile.setLength(position - currentLine.length() - 1);
            randomAccessFile.seek(position - currentLine.length() - 1);
            randomAccessFile.writeBytes(id + dataSeparator + account.getId() + dataSeparator + account.getName() + dataSeparator + account.getBalance().doubleValue() + "\n");
            sourceChannel.transferFrom(transferChannel, randomAccessFile.getFilePointer(), transferChannel.size());

            transfer.setLength(0);
        } catch (IOException e) {
            //TODO create message
            throw new FailedEditAccountException(e);
        }
    }

    @Override
    public void deleteAccount(User user, int accountId) throws DAOException {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(accountsFile, "rw");
            RandomAccessFile transfer = new RandomAccessFile("transfer.txt", "rw");

            FileChannel sourceChannel = randomAccessFile.getChannel();
            FileChannel transferChannel = transfer.getChannel();
            String currentLine, userId, userAccountId;
            int id = user.getId();

            while ((currentLine = randomAccessFile.readLine()) != null) {
                userId = currentLine.split(dataSeparator)[0];
                userAccountId = currentLine.split(dataSeparator)[1];
                if (!(userId.equals(String.valueOf(id)) && userAccountId.equals(String.valueOf(accountId)))) {
                    continue;
                }
                break;
            }
            if (currentLine == null) {
                //TODO create message
                throw new DAOException();
            }
            long position = randomAccessFile.getFilePointer();
            sourceChannel.position(position); // sets the pointer at @position to start transferring info from it
            transferChannel.transferFrom(sourceChannel, 0, sourceChannel.size() - position);
            randomAccessFile.setLength(position - currentLine.length() - 1);
            randomAccessFile.seek(position - currentLine.length() - 1);
            sourceChannel.transferFrom(transferChannel, randomAccessFile.getFilePointer(), transferChannel.size());

            transfer.setLength(0);
        } catch (IOException e) {
            //TODO create message
            throw new FailedDeleteAccountException(e);
        }
    }

    @Override
    public ArrayList<Account> getAllAccounts(User user) throws DAOException {

        try {
            boolean isFound = false;
            ArrayList<Account> accounts = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(accountsFile));
            String currentLine, userId;
            String[] accountInfo;
            int id = user.getId();
            while ((currentLine = bufferedReader.readLine()) != null) {
                accountInfo = currentLine.split(dataSeparator);
                userId = accountInfo[0];
                if (!userId.equals(String.valueOf(id))) {
                    continue;
                }
                isFound = true;
                accounts.add(new Account(accountInfo[2], new BigDecimal(Double.parseDouble(accountInfo[3])), Integer.parseInt(accountInfo[1])));
            }

            if (!isFound) {
                //TODO create message
                throw new UserNotFoundException();
            }
            return accounts;
        } catch (IOException e) {
            //TODO create message
            throw new DAOException(e);
        }
    }

    @Override
    public boolean hasAccount(User user, int accountId) throws DAOException {
        boolean isFound = false;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(accountsFile));
            String currentLine, currentAccountUserId, currentAccountId;
            String[] accountInfo;
            int userId = user.getId();
            while ((currentLine = bufferedReader.readLine()) != null) {
                accountInfo = currentLine.split(dataSeparator);
                currentAccountUserId = accountInfo[0];
                currentAccountId = accountInfo[1];
                if (!(currentAccountUserId.equals(String.valueOf(userId)) && currentAccountId.equals(String.valueOf(accountId)))) {
                    continue;
                }
                isFound = true;
            }
        } catch (IOException e) {
            //TODO create message
            throw new DAOException(e);
        }
        return isFound;
    }
}
