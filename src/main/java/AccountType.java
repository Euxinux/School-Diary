import java.io.IOException;
import java.sql.*;
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
                System.out.println("9 Logout. ");
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
                System.out.println("9 Logout. ");
                userAnswer = scanner.nextInt();
                break;
            case 1:
                System.out.println("Your account is 'Admin'");
                System.out.println("1. Show all students in school dairy");
                System.out.println("2. Change password to account");
                System.out.println("3. Add student do school dairy");
                System.out.println("4. Delete student from the school dairy");
                System.out.println("5. Edit student's personal data");
                System.out.println("6. Create new account - 'Student'");
                System.out.println("7. Create new account - 'Teacher'");
                System.out.println("7. Create new account - 'Admin'");
                System.out.println("9. Logout ");
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
                //changePassword();
                break;
            case 3:
                if (Integer.parseInt(infoDB[2]) <3)
                    new SchoolDairy().addStudent(connection);
                //else
                    //BackTOMenu();
            case 4:
                if (Integer.parseInt(infoDB[2]) <3)
                    new SchoolDairy().deleteStudent(connection);
                //else
                //BackToMenu();
            case 5:
                if (Integer.parseInt(infoDB[2]) <3)
                    new SchoolDairy().editStudent(connection);
                //else
                //BackToMenu();
            case 6:
                //createAccount(3)
                //BackToMenu();
            case 7:
                //createAccount(2)
                //BackToMenu();
            case 8:
                //createAccount(1)
                //BackToMenu();
            case 9:
                new UserPanel(connection).LoginPanel();
            default:
                //BackToMenu();
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
}
