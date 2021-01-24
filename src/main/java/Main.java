import java.sql.Connection;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main {
    public static void main(String[] args) {
        ConnectWithDataBase cWDB = new ConnectWithDataBase("ConnectWithDataBase","jdbc:mysql://localhost:3306/school","root","");
        Connection connection = cWDB.getConnection();
        Student student = new Student();
        student.addStudent(connection);

        /*
        GregorianCalendar calendar = new GregorianCalendar(1999,2,27);
        System.out.println(calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
        */
    }

}
