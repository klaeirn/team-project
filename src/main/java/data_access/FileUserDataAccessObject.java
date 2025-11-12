package data_access;

import Entities.User;
import Entities.UserFactory;
import Use_cases.login.LoginUserDataAccessInterface;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileUserDataAccessObject implements LoginUserDataAccessInterface {

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

    public boolean existsByName(String username) {

        return this.accounts.containsKey(username);
    }

    public User get(String username) {
        return this.accounts.get(username);
    }
}