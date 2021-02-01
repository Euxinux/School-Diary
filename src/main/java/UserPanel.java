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

    public UserPanel(Connection connection)
    {
        this.connection = connection;
    }

    public void LoginPanel()
    {
        System.out.println("Welcome in SchoolDairy by Dijkstra");
        System.out.println("                                  ");

        while (choiceUser != 1 && choiceUser !=2)
        {
            try
            {
                System.out.println("Please choose 1 of 2 possible choice 1 or 2 :)");
                System.out.println("1. Register new Account (User)");
                System.out.println("2. Log In");
                choiceUser = scanner.nextInt();
            }
            catch (InputMismatchException e)
            {
                scanner.nextLine();
            }
            switch (choiceUser)
            {
                case 1:
                    choiceUser = 0;
                    int priority = 3;
                    createAccount(priority);
                case 2:
                    logIn();
            }
        }
    }

    public void createAccount(int priority)
    {
        String login;
        String password1;
        String password2;
        Statement statement;
        String sql;
        boolean isEmpty;

        System.out.println("Enter your new personal login. Remember letter size is matter.");
        login = scanner.next();
        System.out.println("Enter your password: ");
        password1 = scanner.next();
        System.out.println("Repeat your password: ");
        password2 = scanner.next();
        isEmpty = freeName(login);

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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        // LoginPanel();
    }

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

    public void logIn()
    {
        String login;
        String password;
        int priority;

        System.out.println("Enter your login, and password or press 9 if you want back to main menu!");
        System.out.println("Enter your login: ");
        login = scanner.next();
        System.out.println("Enter your password: ");
        password = scanner.next();
        if (login.equals("9") || password.equals("9"))
        {
            LoginPanel();
        }
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
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        System.out.println("Login or password is incorrect! Try again!");
        return 0;
    }

}


