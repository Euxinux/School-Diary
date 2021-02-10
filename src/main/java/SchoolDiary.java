import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SchoolDiary {
    // Local variable used in method
    Scanner scanner = new Scanner(System.in);
    Statement statement = null;

    // addStudent method allows to add student to school diary,
    // this method is available only for account with priority 'Admin' or 'Teacher'
    public void AddStudent(Connection connection) {
        // Set all needed variable
        String name, lastName, stringBirth, sql;
        // Get information about student (Name, Lastname, Date of Birth)
        System.out.println("Student name: ");
        name = scanner.nextLine();
        System.out.println("Student lastname: ");
        lastName = scanner.nextLine();
        System.out.println("Day of Birth in format 'yyyy-MM-dd'");
        stringBirth = scanner.next();

        // Save student into DataBase
        // Create statement, create command in SQL and execute update
        try {
            statement = connection.createStatement();
            sql = "INSERT INTO `schooldiary`(`StudentID`, `Name`, `LastName`, `DateOfBirth`) " +
                    "VALUES (NULL,'" + name + "','" + lastName + "','" + stringBirth + "')";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // deleteStudent method allows to delete student from school diary
    // this method is available only for account with priority 'Admin' or 'Teacher'
    public void DeleteStudent(Connection connection) {
        // ArrayList includes all unique student's IDs in DataBase
        ArrayList<Integer> studentsIDsDB = DisplayStudent(connection);
        int studentID;
        boolean acceptedID = false;
        String sql;

        System.out.println("Which student you want delete from school diary? Enter ID: ");
        studentID = scanner.nextInt();
        // for each loop check entered value is exist in data base and return boolean value
        for (Integer allUsersID : studentsIDsDB) {
            if (allUsersID == studentID) {
                acceptedID = true;
                break;
            }
        }
        // If entered value didn't exist in database start again delete method.
        if (!acceptedID) {
            System.out.println("Your entered StudentID was wrong! Try one more time.");
            DeleteStudent(connection);
        }
        // Deleting student from school diary using SQL command
        try {
            System.out.println("Deleting student: " + studentID + " from school diary");
            statement = connection.createStatement();
            sql = "DELETE FROM schooldiary WHERE StudentID = " + studentID;
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // show all students in school diary
    // this method is available for all type of account
    // method return ArrayList with all student's IDs
    public ArrayList<Integer> DisplayStudent(Connection connection) {
        int i = 1;
        ArrayList<Integer> studentsID = new ArrayList<>();
        String sql;

        try {
            statement = connection.createStatement();
            sql = "SELECT `StudentID`,  `Name`, `LastName`, `DateOfBirth` FROM `schooldiary` ORDER BY LastName ASC";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                int idStudent = rs.getInt("StudentID");
                String name = rs.getString("Name");
                String lastName = rs.getString("LastName");
                String dateOfBirth = rs.getString("DateOfBirth");
                System.out.println(i + ". " + lastName + " " + name + " " + dateOfBirth + " Personal ID: " + idStudent);
                studentsID.add(idStudent);
                i++;
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentsID;
    }

    // method allows change all personal parameters like Name, Lastname or Date of Birth
    // this method is available for teacher and admin account
    public void EditStudent(Connection connection) {
        int studentID = 0, studentEdit = 0;
        String correctParameter, sql = " ";
        boolean acceptedID;

        System.out.println("Get personal ID student which you want edit: ");
        //using display method in order to compare user enter variable with local data base
        ArrayList<Integer> allUsersIDs = DisplayStudent(connection);
        try {
            studentID = scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine();
        }

        acceptedID = new AccountType().CheckID(studentID, allUsersIDs);
        if (!acceptedID) {
            System.out.println("Your entered StudentID was wrong! Try one more time.");
            EditStudent(connection);
        }
        // while loop using to accept only values 1,2,3
        while (studentEdit != 1 && studentEdit != 2 && studentEdit != 3) {
            System.out.println("Which parameter you want edit?");
            System.out.println("1. Name");
            System.out.println("2. Lastname");
            System.out.println("3. Date of Birth");

            try {
                studentEdit = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine();
            }
            // case loop used to create few options SQL command depending on the entered value
            switch (studentEdit) {
                case 1: {
                    System.out.println("Enter new name: ");
                    correctParameter = scanner.next();
                    sql = "UPDATE schooldiary SET Name = '" + correctParameter + "' WHERE StudentID = " + studentID;
                    break;
                }
                case 2: {
                    System.out.println("Enter new lastname: ");
                    correctParameter = scanner.next();
                    sql = "UPDATE schooldiary SET LastName = '" + correctParameter + "' WHERE StudentID = " + studentID;
                    break;
                }
                case 3: {
                    System.out.println("Enter new Date of Birth in format YYYY-MM-DD: ");
                    correctParameter = scanner.next();
                    sql = "UPDATE schooldiary SET DateOfBirth = '" + correctParameter + "' WHERE StudentID = " + studentID;
                    break;
                }
            }
            // create statement
            try {
                Statement statement;
                statement = connection.createStatement();
                statement.executeUpdate(sql);
                statement.close();
            } catch (SQLException ignored) {
            }
        }
    }
}

