import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
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
        System.out.println("Welcome: " + infoDB[0]);

        switch (Integer.parseInt(infoDB[2]))
        {
            case 3:
                System.out.println("Your account is 'Student'");
                System.out.println("1. Show all students in school dairy");
                System.out.println("2. Change password to account");
                System.out.println("0. Logout. ");
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
                break;
        }
        try {
            userAnswer = scanner.nextInt();
        }
        catch (InputMismatchException e)
        {
            scanner.nextLine();
            Users(infoDB);
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
                Users(infoDB);
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
                Users(infoDB);
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
                    System.out.println("Old password is different!");
                    ChangePassword(infoDB);
                }
                statement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        Users(infoDB);
    }
    public void CreateAccount (String[] infoDB)
    {
        int userChoice;
        Statement statement;
        boolean isFree;


        System.out.println("Hello which type of account you want create ?: ");
        System.out.println("3. Student");
        if (Integer.parseInt(infoDB[2]) == 1)
        {
            System.out.println("2. Teacher");
            System.out.println("1. Admin");
        }
            try
            {
                userChoice = scanner.nextInt();
                if (Integer.parseInt(infoDB[2]) == 2)
                {
                    switch(userChoice){
                        case 3:
                            new UserPanel(connection).createAccount(userChoice);
                            break;
                        default:
                            CreateAccount(infoDB);
                            break;
                    }
                }
                else
                {
                    switch (userChoice){
                        case 3:
                        case 2:
                        case 1:
                            new UserPanel(connection).createAccount(userChoice);
                            break;
                        default:
                            CreateAccount(infoDB);
                            break;
                    }
                }

            }
            catch (InputMismatchException e) {
                scanner.nextLine();
               // CreateAccount(infoDB);
            }
        }
    public void EditAccount ()
    {
        int userChoice;
        boolean acceptedID = false;
        int userChoiceParameter = 0;

        System.out.println("Which account you want edit? Enter ID: ");
        ArrayList<Integer> allUsersIDs = ShowAccounts();
        userChoice = scanner.nextInt();

        acceptedID = CheckID(userChoice, allUsersIDs);
        while (userChoiceParameter != 1 || userChoiceParameter != 2)
        {
            if (!acceptedID) {
                System.out.println("Your entered UsersID was wrong! Try one more time.");
                EditAccount();
            } else {
                try {
                    System.out.println("Which parameter you want edit?: ");
                    System.out.println("1. Password ");
                    System.out.println("2. Priority ");
                    userChoiceParameter = scanner.nextInt();
                } catch (InputMismatchException e) {
                    scanner.nextLine();
                }
            }
        }

    }
    public void DeleteAccount ()
    {
        Statement statement;
        int userID = 0;
        boolean acceptedID;
        String sql;
        ArrayList<Integer> usersIDs = ShowAccounts();
        System.out.println("Which account you want delete from school dairy? Get ID: ");
        userID = scanner.nextInt();
        acceptedID = CheckID(userID, usersIDs);

        if (!acceptedID)
        {
            System.out.println("Your entered UsersID was wrong! Try one more time.");
            DeleteAccount();
        }
        else {
            try {
                System.out.println("Deleting users: " + userID);
                statement = connection.createStatement();
                sql = "DELETE FROM users WHERE UsersID = " + userID;
                statement.executeUpdate(sql);
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public ArrayList<Integer> ShowAccounts ()
    {
        Statement statement;
        ArrayList<Integer> allUsersIDs = new ArrayList<Integer>();
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
                allUsersIDs.add(usersID);
                i++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allUsersIDs;
    }
    public boolean CheckID(int IDToCheck, ArrayList<Integer> usersIDs)
    {
        boolean acceptedID = false;
        for (int i = 0; i < usersIDs.size(); i++)
        {
            if (usersIDs.get(i) == IDToCheck)
            {
                return true;
            }
        }
        return false;
    }

}
