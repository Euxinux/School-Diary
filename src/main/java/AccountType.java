import jdk.swing.interop.SwingInterOpUtils;

import java.io.IOException;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccountType {
    String login;
    Connection connection;
    Scanner scanner = new Scanner(System.in);

    public AccountType(String login, Connection connection)
    {
        this.login = login;
        this.connection = connection;
    }
    public void checkPriority()
    {
        String[] infoDB = getInfo(login);
        Users(infoDB);

    }
    public void Users (String[] infoDB)
    {
        int userAnswer = 0;
        System.out.println("...");
        System.out.println("Welcome: " + infoDB[0]);

        switch (Integer.parseInt(infoDB[2]))
        {
            case 3:
                System.out.println("Your account is 'Student'");
                System.out.println("1. Show all students in school dairy");
                System.out.println("2. Change password to account");
                System.out.println("0. Logout. ");
                userAnswer = scanner.nextInt();
                break;
            case 2:
                System.out.println("Your account is 'Teacher'");
                System.out.println("1. Show all students in school dairy");
                System.out.println("2. Change password to account");
                System.out.println("3. Add student do school dairy");
                System.out.println("4. Delete student from the school dairy");
                System.out.println("5. Edit student's personal data");
                System.out.println("6. Create new account - 'Student'");
                System.out.println("0. Logout. ");
                userAnswer = scanner.nextInt();
                break;
            case 1:
                System.out.println("Your account is 'Admin'");
                System.out.println("1. Show all students in school dairy");
                System.out.println("2. Change password to account");
                System.out.println("3. Add student do school dairy");
                System.out.println("4. Delete student from the school dairy");
                System.out.println("5. Edit student's personal data");
                System.out.println("6. Create new account - 'Student'/'Teacher'/'Admin'");
                System.out.println("7. Edit exist account");
                System.out.println("8. Delete exist account");
                System.out.println("9. Show users all users:");
                System.out.println("0. Logout ");
                userAnswer = scanner.nextInt();
                break;
        }
        switch (userAnswer)
        {
            case 1:
                new SchoolDairy().displayStudent(connection);
                BackToMenu(infoDB);
                break;
            case 2:
                ChangePassword(infoDB);
                break;
            case 3:
                if (Integer.parseInt(infoDB[2]) <3)
                    new SchoolDairy().addStudent(connection);
                BackToMenu(infoDB);
                break;
            case 4:
                if (Integer.parseInt(infoDB[2]) <3)
                    new SchoolDairy().deleteStudent(connection);
                BackToMenu(infoDB);
            case 5:
                if (Integer.parseInt(infoDB[2]) <3)
                    new SchoolDairy().editStudent(connection);
                BackToMenu(infoDB);
            case 6:
                if (Integer.parseInt(infoDB[2]) <3)
                    CreateAccount(infoDB);
                BackToMenu(infoDB);
                break;
            case 7:
                if (Integer.parseInt(infoDB[2]) <2)
                    EditAccount();
                BackToMenu(infoDB);
                break;
            case 8:
                if (Integer.parseInt(infoDB[2]) <2)
                    DeleteAccount();
                BackToMenu(infoDB);
            case 9:
                if (Integer.parseInt(infoDB[2]) <2)
                    ShowAccounts();
                BackToMenu(infoDB);
            case 0:
                new UserPanel(connection).LoginPanel();
                break;
            default:
                BackToMenu(infoDB);
        }
    }

    public String[] getInfo (String login)
    {
        Statement statement;
        String sql;
        String[] arrayInfo = new String[3];

        try {
            statement = connection.createStatement();
            sql = "SELECT * from users WHERE Login = '" + login +"'";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next())
            {
                String passwordDB = rs.getString("Password");
                int priorityDB = rs.getInt("Priority");
                String loginDB = rs.getString("Login");
                arrayInfo[0] = loginDB;
                arrayInfo[1] = passwordDB;
                arrayInfo[2] = String.valueOf(priorityDB);
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return arrayInfo;
    }
    public void BackToMenu(String[] infoDB)
    {
        try {
        System.in.read();
    } catch (IOException e) {
        e.printStackTrace();
    }
        Users(infoDB);
    }
    public void ChangePassword (String[] infoDB)
    {
        Statement statement;
        String oldPassword;
        String password1;
        String password2;
        String sql;
        String oldPasswordDB = null;

        System.out.println("Enter your old password: ");
        oldPassword = scanner.next();
        System.out.println("Enter your new password: ");
        password1 = scanner.next();
        System.out.println("Repeat your new password: ");
        password2 = scanner.next();
        try {
            statement = connection.createStatement();
            sql = "SELECT Password FROM users WHERE Login = '" + infoDB[0] + "'";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next())
            {
                oldPasswordDB = rs.getString("Password");
            }
            rs.close();
                if (oldPasswordDB.equals(oldPassword))
                {
                    if (password1.equals(password2))
                    {
                        sql = "UPDATE users SET Password = '" + password1 + "' WHERE Login = '" + infoDB[0] + "'";
                        statement.executeUpdate(sql);
                        System.out.println("Password are change correctly!");
                    }
                    else
                    {
                        System.out.println("New passwords are different!");
                        ChangePassword(infoDB);
                    }
                } else
                    {
                    System.out.println("Passwords are different!");
                    ChangePassword(infoDB);
                }
                statement.close();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        BackToMenu(infoDB);
    }
    public void CreateAccount (String[] infoDB)
    {
        int userChoice = 0;
        Statement statement;
        boolean isFree;

        while (userChoice!= 1 && userChoice != 2 && userChoice !=3)
        {
        System.out.println("Hello which type of account you want create ?: ");
        System.out.println("3. Student");
        if (Integer.parseInt(infoDB[2]) == 1)
        {
            System.out.println("2. Teacher");
            System.out.println("1. Admin");
        }
            try {
                userChoice = scanner.nextInt();
                System.out.println("Enter login: ");
                String login = scanner.next();
                isFree = new UserPanel(connection).freeName(login);
                    if (!isFree)
                    {
                        System.out.println("Enter password: ");
                        String password = scanner.next();
                        try
                        {
                        statement = connection.createStatement();
                        String sql = "INSERT INTO users (UsersID, Login, Password, Priority) VALUES (null, '" + login + "','"
                                + password + "'," + userChoice + ")";
                        statement.executeUpdate(sql);
                        statement.close();
                        }
                        catch (SQLException throwables)
                        {
                        throwables.printStackTrace();
                        }
                    }
                    else
                    {
                        System.out.println("Login is hired!");
                        CreateAccount(infoDB);
                    }


            } catch (InputMismatchException e) {
                System.out.println("Please chose number 1-3: ");
                scanner.nextLine();
            }
        }
    }
    public void EditAccount ()
    {
        int userChoice;

        System.out.println("Which account you want edit? Enter ID: ");
        ShowAccounts();
        userChoice = scanner.nextInt();



    }
    public void DeleteAccount ()
    {
        Statement statement;
        ShowAccounts();
        int userID;
        String sql;
        System.out.println("Which account you want delete from school dairy? Get ID: ");
        userID = scanner.nextInt();
        try
        {
            System.out.println("Deleting users: " + userID);
            statement = connection.createStatement();
            sql = "DELETE FROM users WHERE UsersID = " + userID;
            statement.executeUpdate(sql);
            statement.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void ShowAccounts ()
    {
        Statement statement;
        try {
            statement = connection.createStatement();
            String sql = "SELECT * FROM users";
            ResultSet rs = statement.executeQuery(sql);
            int i = 1;
            while (rs.next())
            {
                String login = rs.getNString("Login");
                String password = rs.getNString("Password");
                int priority = rs.getInt("Priority");
                int usersID = rs.getInt("UsersID");
                System.out.println(i +". LOGIN:  " + login + " PASSWORD: " + password + " PRIORITY: " + priority
                                   + " USERSID: " + usersID);
                i++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
