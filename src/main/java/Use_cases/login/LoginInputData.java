package use_cases.login;

public class LoginInputData {


        private final String username;
        private final String password;

        public LoginInputData(String username, String password) {
            this.username = username;
            this.password = password;
        }

        String getUsername() {
            return username;
        }

        String getPassword() {
            return password;
        }

}
    

