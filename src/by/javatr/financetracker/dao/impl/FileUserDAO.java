package by.javatr.financetracker.dao.impl;

import by.javatr.financetracker.bean.User;
import by.javatr.financetracker.dao.UserDAO;
import by.javatr.financetracker.dao.exception.*;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public class FileUserDAO implements UserDAO {

    private File usersFile;
    private String dataSeparator;

    public FileUserDAO(){

    }

    public FileUserDAO(File file, String dataSeparator) {
        this.usersFile = file;
        this.dataSeparator = dataSeparator;
    }

    public FileUserDAO(String path, String dataSeparator) {
        this.usersFile = new File(path);
        this.dataSeparator = dataSeparator;
    }

    public void setUsersFile(File usersFile) {
        this.usersFile = usersFile;
    }

    public void setUsersFile(String path) {
        this.usersFile = new File(path);
    }

    public void setDataSeparator(String dataSeparator) {
        this.dataSeparator = dataSeparator;
    }

    @Override
    public void addUser(User user, char[] password) throws DAOException {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(usersFile, true));
            int hashedPassword = passwordHashCode(password);
            bufferedWriter.write(user.getLogIn() + dataSeparator
                    + hashedPassword + dataSeparator + user.getId() + "\n");
            bufferedWriter.close();
        } catch (IOException e) {
            //TODO create message
            throw new FailedAddException(e);
        }
    }

    @Override
    public boolean hasUser(String logIn, char[] password) throws DAOException {
        try {
            Scanner scanner = new Scanner(usersFile);
            int hashedPassword = passwordHashCode(password);
            String[] userInfo;
            while (scanner.hasNextLine()) {
                userInfo = scanner.nextLine().split(dataSeparator);
                if (logIn.equals(userInfo[0]) && hashedPassword == Integer.parseInt(userInfo[1])) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            //TODO create message
            throw new FailedFindException("Failed to access file", e);
        }
        return false;
    }

    @Override
    public boolean hasUser(String logIn) throws DAOException {
        try {
            Scanner scanner = new Scanner(usersFile);
            String[] userInfo;
            while (scanner.hasNextLine()) {
                userInfo = scanner.nextLine().split(dataSeparator);
                if (logIn.equals(userInfo[0])) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            //TODO create message
            throw new FailedFindException("Failed to access file", e);
        }
        return false;
    }

    @Override
    public void deleteUser(User user) throws DAOException {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(usersFile, "rw");
            RandomAccessFile transfer = new RandomAccessFile("transfer.txt", "rw");

            FileChannel sourceChannel = randomAccessFile.getChannel();
            FileChannel transferChannel = transfer.getChannel();
            String currentLine, userLogIn;
            String logIn = user.getLogIn();
            boolean userFound = false;
            while ((currentLine = randomAccessFile.readLine()) != null) {
                userLogIn = currentLine.split(dataSeparator)[0];
                if (!userLogIn.equals(logIn)) {
                    continue;
                }
                userFound = true;
                long position = randomAccessFile.getFilePointer();
                sourceChannel.position(position); // sets the pointer at @position to start transferring info from it
                transferChannel.transferFrom(sourceChannel, 0, sourceChannel.size() - position);
                randomAccessFile.setLength(position - currentLine.length() - 1);
                sourceChannel.transferFrom(transferChannel, randomAccessFile.getFilePointer(), transferChannel.size());
                break;
            }

            if (!userFound) {
                //TODO create message
                throw new UserNotFoundException();
            }

            transfer.setLength(0);
        } catch (IOException e) {
            //TODO create message
            throw new FailedDeleteException(e);
        }
    }

    @Override
    public void editLogIn(User user, String newLogIn) throws DAOException {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(usersFile, "rw");
            RandomAccessFile transfer = new RandomAccessFile("transfer.txt", "rw");

            FileChannel sourceChannel = randomAccessFile.getChannel();
            FileChannel transferChannel = transfer.getChannel();
            String currentLine;
            String[] userInfo;
            boolean userFound = false;
            while ((currentLine = randomAccessFile.readLine()) != null) {
                userInfo = currentLine.split(dataSeparator);
                if (!userInfo[2].equals(String.valueOf(user.getId()))) {
                    continue;
                }
                userFound = true;
                long position = randomAccessFile.getFilePointer();
                sourceChannel.position(position); // sets the pointer at @position to start transferring info from it
                transferChannel.transferFrom(sourceChannel, 0, sourceChannel.size() - position);
                randomAccessFile.setLength(position - currentLine.length() - 1);
                randomAccessFile.seek(position - currentLine.length() - 1);
                randomAccessFile.writeBytes(newLogIn + dataSeparator
                        + userInfo[1] + dataSeparator + userInfo[2] + "\n");
                sourceChannel.transferFrom(transferChannel, randomAccessFile.getFilePointer(), transferChannel.size());
                break;
            }

            if (!userFound) {
                //TODO create message
                throw new UserNotFoundException();
            }

            transfer.setLength(0);
        } catch (IOException e) {
            //TODO create message
            throw new FailedEditInfoException(e);
        }
    }

    @Override
    public void editPassword(User user, char[] newPassword) throws DAOException {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(usersFile, "rw");
            RandomAccessFile transfer = new RandomAccessFile("transfer.txt", "rw");

            FileChannel sourceChannel = randomAccessFile.getChannel();
            FileChannel transferChannel = transfer.getChannel();
            String currentLine;
            String[] userInfo;
            boolean userFound = false;
            while ((currentLine = randomAccessFile.readLine()) != null) {
                userInfo = currentLine.split(dataSeparator);
                if (!userInfo[2].equals(String.valueOf(user.getId()))) {
                    continue;
                }
                userFound = true;
                long position = randomAccessFile.getFilePointer()-14;
                sourceChannel.position(position); // sets the pointer at @position to start transferring info from it
                transferChannel.transferFrom(sourceChannel, 0, sourceChannel.size() - position);
                //TODO hash should have a constant size instead of 5 here
                randomAccessFile.setLength(position - 5 );
                randomAccessFile.seek(position - 5 );
                int hashedPassword = passwordHashCode(newPassword);
                randomAccessFile.writeBytes(String.valueOf(hashedPassword));
                sourceChannel.transferFrom(transferChannel, randomAccessFile.getFilePointer(), transferChannel.size());
                break;
            }

            if (!userFound) {
                //TODO create message
                throw new UserNotFoundException();
            }

            transfer.setLength(0);
        } catch (IOException e) {
            //TODO create message
            throw new FailedEditInfoException(e);
        }
    }

    @Override
    public User getUser(String logIn) throws DAOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(usersFile));
            String line;
            String[] userInfo;
            while((line = bufferedReader.readLine())!=null){
                userInfo = line.split(dataSeparator);
                if(logIn.equals(userInfo[0])){
                    int id = Integer.parseInt(userInfo[2]);
                    return new User(logIn, id);
                }
            }
        } catch (IOException e) {
            //TODO create message
            throw new DAOException(e);
        }
        //TODO create message
        throw new UserNotFoundException();
    }

    //TODO improve this to look like a normal thing
    private int passwordHashCode(char[] password) {
        int result = 1;
        int key = 5;
        for (int i = 0; i < password.length; i++) {
            result *= password[i] * Math.pow(key, i);
        }
        return result;
    }
}
