import java.sql.Connection;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main {
    public static void main(String[] args) {
        System.out.println("Elo");
        ConnectWithDataBase cWDB = new ConnectWithDataBase("ConnectWithDataBase","jdbc:mysql://localhost:3306/school","root","");
        Connection connection = cWDB.getConnection();

        /*
        GregorianCalendar calendar = new GregorianCalendar(1999,2,27);
        System.out.println(calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
        */
    }

}
