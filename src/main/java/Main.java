import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        ConnectWithDataBase cWDB = new ConnectWithDataBase("ConnectWithDataBase","jdbc:mysql://localhost:3306/school","root","");
        Connection connection = cWDB.getConnection();
        SchoolDairy schoolDairy = new SchoolDairy();
        UserPanel uPanel = new UserPanel(connection);
        uPanel.LoginPanel();



    }

}
