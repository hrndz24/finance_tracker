package by.javatr.financetracker.dao.impl;

import by.javatr.financetracker.entity.Account;
import by.javatr.financetracker.dao.AccountDAO;
import by.javatr.financetracker.dao.exception.*;
import by.javatr.financetracker.dao.constants.StringProperty;

import java.io.*;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class FileAccountDAO implements AccountDAO {

    private File accountsFile;
    private String dataSeparator;
    private File transferFile = new File(StringProperty.getStringValue("transferFilePath"));

    public FileAccountDAO(File file, String dataSeparator) {
        this.accountsFile = file;
        this.dataSeparator = dataSeparator;
    }

    public FileAccountDAO(String path, String dataSeparator) {
        this.accountsFile = new File(path);
        this.dataSeparator = dataSeparator;
    }

    @Override
    public void addAccount(int userId, Account account) throws DAOException {
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(accountsFile, "rw");
            RandomAccessFile transfer = new RandomAccessFile(transferFile, "rw");) {

            FileChannel sourceChannel = randomAccessFile.getChannel();
            FileChannel transferChannel = transfer.getChannel();
            String currentLine, currentUserId;

            while ((currentLine = randomAccessFile.readLine()) != null) {
                currentUserId = currentLine.split(dataSeparator)[0];
                if (!currentUserId.equals(String.valueOf(userId))) {
                    continue;
                }

                long position = randomAccessFile.getFilePointer();
                sourceChannel.position(position);
                transferChannel.transferFrom(sourceChannel, 0, sourceChannel.size() - position);
                randomAccessFile.setLength(position);
                randomAccessFile.writeBytes(userId + dataSeparator + account.getId()
                        + dataSeparator + account.getName() + dataSeparator + account.getBalance() + "\n");
                sourceChannel.transferFrom(transferChannel, randomAccessFile.getFilePointer(), transferChannel.size());
                break;
            }

            if (currentLine == null) {
                randomAccessFile.writeBytes(userId + dataSeparator + account.getId()
                        + dataSeparator + account.getName() + dataSeparator + account.getBalance() + "\n");
            }
            transfer.setLength(0);
        } catch (IOException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void editAccount(int userId, Account editedAccount) throws DAOException {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(accountsFile, "rw");
             RandomAccessFile transfer = new RandomAccessFile(transferFile, "rw");){

            FileChannel sourceChannel = randomAccessFile.getChannel();
            FileChannel transferChannel = transfer.getChannel();
            String currentLine, currentUserId, userAccountId;

            while ((currentLine = randomAccessFile.readLine()) != null) {
                currentUserId = currentLine.split(dataSeparator)[0];
                userAccountId = currentLine.split(dataSeparator)[1];
                if (!(currentUserId.equals(String.valueOf(userId)) && userAccountId.equals(String.valueOf(editedAccount.getId())))) {
                    continue;
                }
                break;
            }
            if (currentLine == null) {
                throw new DAOException("Account not found");
            }
            long position = randomAccessFile.getFilePointer();
            sourceChannel.position(position);
            transferChannel.transferFrom(sourceChannel, 0, sourceChannel.size() - position);
            randomAccessFile.setLength(position - currentLine.length() - 1);
            randomAccessFile.seek(position - currentLine.length() - 1);
            randomAccessFile.writeBytes(userId + dataSeparator + editedAccount.getId() + dataSeparator + editedAccount.getName() + dataSeparator + editedAccount.getBalance().doubleValue() + "\n");
            sourceChannel.transferFrom(transferChannel, randomAccessFile.getFilePointer(), transferChannel.size());

            transfer.setLength(0);
        } catch (IOException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteAccount(int userId, int accountId) throws DAOException {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(accountsFile, "rw");
             RandomAccessFile transfer = new RandomAccessFile(transferFile, "rw");){

            FileChannel sourceChannel = randomAccessFile.getChannel();
            FileChannel transferChannel = transfer.getChannel();
            String currentLine, currentUserId, userAccountId;

            while ((currentLine = randomAccessFile.readLine()) != null) {
                currentUserId = currentLine.split(dataSeparator)[0];
                userAccountId = currentLine.split(dataSeparator)[1];
                if ((currentUserId.equals(String.valueOf(userId)) && userAccountId.equals(String.valueOf(accountId)))) {
                    long position = randomAccessFile.getFilePointer();
                    sourceChannel.position(position);
                    transferChannel.transferFrom(sourceChannel, 0, sourceChannel.size() - position);
                    randomAccessFile.setLength(position - currentLine.length() - 1);
                    randomAccessFile.seek(position - currentLine.length() - 1);
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
    public Account[] getAllAccounts(int userId) throws DAOException {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(accountsFile));) {
            ArrayList<Account> accounts = new ArrayList<>();
            String currentLine, currentUserId;
            String[] accountInfo;
            while ((currentLine = bufferedReader.readLine()) != null) {
                accountInfo = currentLine.split(dataSeparator);
                currentUserId = accountInfo[0];
                if (!currentUserId.equals(String.valueOf(userId))) {
                    continue;
                }
                accounts.add(new Account(accountInfo[2], new BigDecimal(Double.parseDouble(accountInfo[3])), Integer.parseInt(accountInfo[1])));
            }

            Account[] accountsArray = new Account[accounts.size()];
            return accounts.toArray(accountsArray);
        } catch (IOException e) {
            throw new DAOException(e);
        }
    }


    @Override
    public Account getAccount(int userId, int accountId) throws DAOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(accountsFile));){
            boolean isFound = false;
            String currentLine, currentUserAccountId, currentUserId;
            String[] accountInfo;
            Account account = new Account();
            while ((currentLine = bufferedReader.readLine()) != null) {
                accountInfo = currentLine.split(dataSeparator);
                currentUserId = accountInfo[0];
                currentUserAccountId = accountInfo[1];
                if (currentUserId.equals(String.valueOf(userId))&&currentUserAccountId.equals(String.valueOf(accountId))) {
                    account = new Account(accountInfo[2], new BigDecimal(Double.parseDouble(accountInfo[3])), Integer.parseInt(accountInfo[1]));
                    isFound = true;
                    break;
                }
            }

            if (!isFound) {
                throw new DAOException("Account not found");
            }
            return account;
        } catch (IOException e) {
            throw new DAOException(e);
        }
    }


    @Override
    public boolean hasAccount(int userId, int accountId) throws DAOException {
        boolean isFound = false;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(accountsFile));){
            String currentLine, currentAccountUserId, currentAccountId;
            String[] accountInfo;
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
            throw new DAOException(e);
        }
        return isFound;
    }
}
