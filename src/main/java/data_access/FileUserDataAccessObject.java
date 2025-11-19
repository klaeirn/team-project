package data_access;

import use_cases.login.LoginUserDataAccessInterface;
import use_cases.change_username.ChangeUsernameDataAccessInterface;
import use_cases.create_quiz.CreateQuizDataAccessInterface; // TODO: remove this import when we have a different dao for quizzes
import use_cases.create_quiz.UserDataAccessInterface; // TODO: remove this import when we have a different dao for quizzes

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import entities.User;
import entities.UserFactory;
import entities.Quiz; // TODO: remove this import when we have a different dao for quizzes

public class FileUserDataAccessObject implements LoginUserDataAccessInterface, ChangeUsernameDataAccessInterface, 
        CreateQuizDataAccessInterface, UserDataAccessInterface{ // TODO: remove CreateQuizDataAccessInterface and UserDataAccessInterface when we have a different dao for quizzes

    private final File csvFile;
    private UserFactory userFactory;
    private static final String HEADER = "username,password";

    private final Map<String, Integer> headers = new LinkedHashMap<>();
    private final Map<String, User> accounts = new HashMap<>();

    private String currentUsername;


    /**
     * Construct this DAO for saving to and reading from a local file.
     *
     * @param csvFile     the path of the file to save to
     * @param userFactory factory for creating user objects
     * @throws RuntimeException if there is an IOException when accessing the file
     */
    public FileUserDataAccessObject(String csvFile, UserFactory userFactory) {
        this.csvFile = new File(csvFile);
        this.userFactory = userFactory;

        headers.put("username", 0);
        headers.put("password", 1);

        if (this.csvFile.length() == 0) {
            save();
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                final String header = reader.readLine();

                if (!header.equals(HEADER)) {
                    throw new RuntimeException(String.format("header should be%n: %s%n but was:%n%s", HEADER, header));
                }

                String row;
                while ((row = reader.readLine()) != null) {

                    final String[] col = row.split(",");
                    final String username = String.valueOf(col[headers.get("username")]);
                    final String password = String.valueOf(col[headers.get("password")]);
                    final User user = userFactory.createUser(username, password);
                    accounts.put(username, user);


                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void save() {
        final BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(csvFile));
            writer.write(String.join(",", headers.keySet()));
            writer.newLine();

            for (User user : accounts.values()) {
                final String line = String.format("%s,%s",
                        user.getUserName(), user.getPassword());
                writer.write(line);
                writer.newLine();
            }

            writer.close();

        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    public String getCurrentUsername() {
        return this.currentUsername;
    }
    
    public void setCurrentUsername(String name){
        this.currentUsername = name;
    }

    public User getUser(String username) {
        return this.accounts.get(username);
    }

    public void save(User user) {
        this.accounts.put(user.getUserName(), user);
        this.save();
    }

    public void replace(String username) {

        if (this.accounts.containsKey(username)) {
            throw new RuntimeException(String.format("username %s is already in use!", username));
        }

        User account = this.accounts.get(currentUsername);
        account.setUserName(username);

        save();

    }

    public boolean existsByName(String username) {

        return this.accounts.containsKey(username);
    }

    public User get(String username) {
        return this.accounts.get(username);
    }
    
    @Override
    public void saveUserQuiz(Quiz quiz) {
        // TODO: implement quiz persistence
        // for now, this is a temporary implementation
        // in the future, we will use a different dao for quizzes
    }
}