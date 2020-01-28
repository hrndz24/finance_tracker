package by.javatr.financetracker.dao.impl;

import by.javatr.financetracker.bean.User;
import by.javatr.financetracker.dao.UserDAO;
import by.javatr.financetracker.dao.exception.DAOException;
import by.javatr.financetracker.dao.stringvalues.StringProperty;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Scanner;

public class FileUserDAO implements UserDAO {

    private File usersFile;
    private String dataSeparator;
    private File transferFile = new File(StringProperty.getStringValue("transferFilePath"));

    public FileUserDAO(File file, String dataSeparator) {
        this.usersFile = file;
        this.dataSeparator = dataSeparator;
    }

    public FileUserDAO(String path, String dataSeparator) {
        this.usersFile = new File(path);
        this.dataSeparator = dataSeparator;
    }

    @Override
    public void addUser(User user, char[] password) throws DAOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(usersFile, true));) {
            int hashedPassword = passwordHashCode(password);
            bufferedWriter.write(user.getLogIn() + dataSeparator
                    + hashedPassword + dataSeparator + user.getId() + "\n");
        } catch (IOException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean hasUser(String logIn, char[] password) throws DAOException {
        try (Scanner scanner = new Scanner(usersFile);){
            int hashedPassword = passwordHashCode(password);
            String[] userInfo;
            while (scanner.hasNextLine()) {
                userInfo = scanner.nextLine().split(dataSeparator);
                if (logIn.equals(userInfo[0]) && hashedPassword == Integer.parseInt(userInfo[1])) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            throw new DAOException(e);
        }
        return false;
    }

    @Override
    public boolean hasUser(String logIn) throws DAOException {
        try (Scanner scanner = new Scanner(usersFile);){
            String[] userInfo;
            while (scanner.hasNextLine()) {
                userInfo = scanner.nextLine().split(dataSeparator);
                if (logIn.equals(userInfo[0])) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            throw new DAOException(e);
        }
        return false;
    }

    @Override
    public void deleteUser(int userId) throws DAOException {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(usersFile, "rw");
             RandomAccessFile transfer = new RandomAccessFile(transferFile, "rw");){

            FileChannel sourceChannel = randomAccessFile.getChannel();
            FileChannel transferChannel = transfer.getChannel();
            String currentLine, currentUserId;

            boolean userFound = false;
            while ((currentLine = randomAccessFile.readLine()) != null) {
                currentUserId = currentLine.split(dataSeparator)[2];
                if (!currentUserId.equals(String.valueOf(userId))) {
                    continue;
                }
                userFound = true;
                long position = randomAccessFile.getFilePointer();
                sourceChannel.position(position);
                transferChannel.transferFrom(sourceChannel, 0, sourceChannel.size() - position);
                randomAccessFile.setLength(position - currentLine.length() - 1);
                sourceChannel.transferFrom(transferChannel, randomAccessFile.getFilePointer(), transferChannel.size());
                break;
            }

            if (!userFound) {
                throw new DAOException("User not found");
            }

            transfer.setLength(0);
        } catch (IOException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void editLogIn(int userId, String newLogIn) throws DAOException {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(usersFile, "rw");
             RandomAccessFile transfer = new RandomAccessFile(transferFile, "rw");){

            FileChannel sourceChannel = randomAccessFile.getChannel();
            FileChannel transferChannel = transfer.getChannel();
            String currentLine;
            String[] userInfo;
            boolean userFound = false;
            while ((currentLine = randomAccessFile.readLine()) != null) {
                userInfo = currentLine.split(dataSeparator);
                if (!userInfo[2].equals(String.valueOf(userId))) {
                    continue;
                }
                userFound = true;
                long position = randomAccessFile.getFilePointer();
                sourceChannel.position(position);
                transferChannel.transferFrom(sourceChannel, 0, sourceChannel.size() - position);
                randomAccessFile.setLength(position - currentLine.length() - 1);
                randomAccessFile.seek(position - currentLine.length() - 1);
                randomAccessFile.writeBytes(newLogIn + dataSeparator
                        + userInfo[1] + dataSeparator + userInfo[2] + "\n");
                sourceChannel.transferFrom(transferChannel, randomAccessFile.getFilePointer(), transferChannel.size());
                break;
            }

            if (!userFound) {
                throw new DAOException("User not found");
            }

            transfer.setLength(0);
        } catch (IOException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void editPassword(int userId, char[] newPassword) throws DAOException {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(usersFile, "rw");
             RandomAccessFile transfer = new RandomAccessFile(transferFile, "rw");){

            FileChannel sourceChannel = randomAccessFile.getChannel();
            FileChannel transferChannel = transfer.getChannel();
            String currentLine;
            String[] userInfo;
            boolean userFound = false;
            while ((currentLine = randomAccessFile.readLine()) != null) {
                userInfo = currentLine.split(dataSeparator);
                if (!userInfo[2].equals(String.valueOf(userId))) {
                    continue;
                }
                userFound = true;
                long position = randomAccessFile.getFilePointer() - userInfo[2].length()-3;
                sourceChannel.position(position);
                transferChannel.transferFrom(sourceChannel, 0, sourceChannel.size() - position);

                randomAccessFile.setLength(position - userInfo[1].length());
                randomAccessFile.seek(position - userInfo[1].length());
                int hashedPassword = passwordHashCode(newPassword);
                randomAccessFile.writeBytes(String.valueOf(hashedPassword));
                sourceChannel.transferFrom(transferChannel, randomAccessFile.getFilePointer(), transferChannel.size());
                break;
            }

            if (!userFound) {
                throw new DAOException("User not found");
            }

            transfer.setLength(0);
        } catch (IOException e) {
            throw new DAOException(e);
        }
    }


    @Override
    public User getUser(String logIn) throws DAOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(usersFile));){
            String line;
            String[] userInfo;
            while ((line = bufferedReader.readLine()) != null) {
                userInfo = line.split(dataSeparator);
                if (logIn.equals(userInfo[0])) {
                    int id = Integer.parseInt(userInfo[2]);
                    return new User(logIn, id);
                }
            }
        } catch (IOException e) {
            throw new DAOException(e);
        }
        throw new DAOException("User not found");
    }

    private int passwordHashCode(char[] password) {
        return Arrays.hashCode(password);
    }
}
