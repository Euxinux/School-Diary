import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Student {

    Scanner scanner = new Scanner(System.in);
    Statement statement = null;

    public void addStudent(Connection connection)
    {
        // Set all needed variable
        String name;
        String lastName;
        String stringBrith;
        String sql;
        // Get from teacher student name/lastname and date of Brith
        System.out.println("Student name: "); name = scanner.nextLine();
        System.out.println("Student lastname: "); lastName = scanner.nextLine();
        System.out.println(name + " " + lastName);
        System.out.println("Day of Birth in format 'yyyy-MM-dd'");
        stringBrith = scanner.next();
        System.out.println(stringBrith);

        // Save student into DataBase
        try
        {
            System.out.println("Inserting records into the table...");
            statement = connection.createStatement();
            sql = "INSERT INTO `schooldairy`(`StudentID`, `Name`, `LastName`, `DateOfBrith`) " +
                    "VALUES (NULL,'" + name + "','" + lastName+"','" + stringBrith +"')";
            statement.executeUpdate(sql);
            statement.close();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    public void deleteStudent(Connection connection)
    {
        int studentID;
        String sql;
        System.out.println("Which student you want delete from school dairy? Get ID: ");
        studentID = scanner.nextInt();
        try
        {
            System.out.println("Deleting student: " + studentID + " from school dairy");
            statement = connection.createStatement();
            sql = "DELETE FROM schooldairy WHERE StudentID = " + studentID;
            statement.executeUpdate(sql);
            statement.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void displayStudent(Connection connection)
    {
        int i = 1;
        String sql;
        try {
            statement = connection.createStatement();
            sql = "SELECT `Name`, `LastName`, `DateOfBrith` FROM `schooldairy` ORDER BY LastName ASC";
            ResultSet rs  = statement.executeQuery(sql);

            while(rs.next())
            {
                String name = rs.getString("Name");
                String lastName = rs.getString("LastName");
                String dateOfBrith = rs.getString("DateOfBrith");
                System.out.println(i + ". " + lastName + " " + name + " " + dateOfBrith);
                i++;
            }
            statement.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}

