import java.sql.Connection;
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
    Connection connection;
    Statement statement = null;

    public void addStudent(Connection connection) {
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
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

}

