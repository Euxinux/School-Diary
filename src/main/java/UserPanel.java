import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserPanel {
    Connection connection;
    int choiceUser = 0; // Sing Up or Register
    Scanner scanner = new Scanner(System.in);

    // public constructor
    public UserPanel(Connection connection)
    {
        this.connection = connection;
    }

    // First/ Main screen JAVA API
    // Two possible options to choose, easy option to expansion
    // 1. Create new account to login in school dairy
    // 2. Sing up with using already existing account
    public void LoginPanel()
    {
        System.out.println("Welcome in SchoolDairy by Dijkstra");
        System.out.println("                                  ");
        // While loop to accept only options 1 or 2
        while (choiceUser != 1 && choiceUser !=2) {
            try {
                System.out.println("Please choose 1 of 2 possible choice 1 or 2 :)");
                System.out.println("1. Register new Account (User)");
                System.out.println("2. Log In");
                choiceUser = scanner.nextInt();
            }
            catch (InputMismatchException e) {
                scanner.nextLine();
            }
            switch (choiceUser) {
                case 1:
                    choiceUser = 0;
                    int priority = 3;
                    CreateAccount(priority);
                case 2:
                    logIn();
            }
        }
    }

    // This method is triggered to create new account
    // All account whit all priority can use this method
    public void CreateAccount(int priority)
    {
        String login, password1, password2, sql;
        Statement statement;
        boolean isEmpty;
        // Get from users information like: login, password and repeated password
        // for user safety
        System.out.println("Enter your new personal login. Remember letter size is matter.");
        login = scanner.next();
        System.out.println("Enter your password: ");
        password1 = scanner.next();
        System.out.println("Repeat your password: ");
        password2 = scanner.next();
        isEmpty = freeName(login);

        // While loop check:
        // * login is not used
        // * password in longer than 4 sign
        // * passwords are the same
        // * password is different than login
        while (password1.equals(login) || password1.length() < 4 || !password1.equals(password2) || isEmpty)
        {
            if (password1.equals(login))
            {
                System.out.println("Login and password cannot be the same!");
                System.out.println("Enter your new personal login. Remember letter size is matter.");
            }
            else if (password1.length() < 4)
                System.out.println("Password is too short, minimum 4 characters");
            else if (!password1.equals(password2))
                System.out.println("Password are different try again!");
            else if (isEmpty)
            {
                System.out.println("Login is hired! Try another one!");
                System.out.println("Enter your new personal login. Remember letter size is matter.");
                login = scanner.next();
            }

            System.out.println("Enter your password: ");
            password1 = scanner.next();
            System.out.println("Repeat your password: ");
            password2 = scanner.next();
            isEmpty = freeName(login);

        }
            System.out.println("Your account has been created! Now you can log in to you school dairy!");
        try {
            statement = connection.createStatement();
            sql = "INSERT INTO users (UsersID,Login,Password,Priority) VALUES (NULL,'" + login + "','"
                    + password1 + "',"  + priority + ")";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException ignored) {
        }
    }

    // method checks if the login is never used, and check boolean value
    // necessary login entered by user
    public boolean freeName(String login)
    {
        Statement statement;
        String sql;
        try {
            statement = connection.createStatement();
            sql = "SELECT Login FROM users";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next())
            {
                String login2 = rs.getString("Login");
                if (login.equals(login2))
                {
                    return true;
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    // Method used to verify login and password provided by user
    public void logIn()
    {
        String login, password;
        int priority;

        System.out.println("Enter your login, and password or press 9 if you want back to main menu!");
        System.out.println("Enter your login: ");
        login = scanner.next();

        // back to main menu when login or password is equals 9
        if (login.equals("9"))
            LoginPanel();
        System.out.println("Enter your password: ");
        password = scanner.next();
        // back to main menu when login or password is equals 9
        if (password.equals("9"))
            LoginPanel();
        // check what type of account is used
        // * 0 - wrong login or password
        // * 1 - admin account
        // * 2 - teacher account
        // * 3 - student account
        priority = checkAccount(login,password);
        if (priority != 0)
        {
            new AccountType(login,connection).checkPriority();
        }
        else
        {
            choiceUser = 0;
            LoginPanel();
        }
    }

    // method verify entered login/password with database password
    public int checkAccount(String login, String password)
    {
        Statement statement;
        String sql;
        try
        {
            statement = connection.createStatement();
            sql = "SELECT Password, Priority FROM users WHERE Login = '" + login + "'";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next())
            {
                String password2 = rs.getString("Password");
                int priority = rs.getInt("Priority");
                if (password2.equals(password))
                {
                    System.out.println("Login successful");
                    return priority;
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        System.out.println("Login or password is incorrect! Try again!");
        return 0;
    }

}


