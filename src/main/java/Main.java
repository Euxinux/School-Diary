import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        // Connect with local database MySQL in localhost with Java DataBase Connectivity
        ConnectWithDataBase cWDB = new ConnectWithDataBase("ConnectWithDataBase","jdbc:mysql://localhost:3306/school","root","");
        // Create object 'connection' used to makes changes in database
        Connection connection = cWDB.getConnection();
        // Start JavaAPI
        UserPanel uPanel = new UserPanel(connection);
        uPanel.LoginPanel();
    }
}
