import java.sql.Connection;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main {
    public static void main(String[] args) {
        ConnectWithDataBase cWDB = new ConnectWithDataBase("ConnectWithDataBase","jdbc:mysql://localhost:3306/school","root","");
        Connection connection = cWDB.getConnection();
        Student student = new Student();
      //student.addStudent(connection);
        student.deleteStudent(connection);

    }

}
