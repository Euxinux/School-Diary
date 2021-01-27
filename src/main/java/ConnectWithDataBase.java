import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectWithDataBase {

        String className, URL, user, password;

        public ConnectWithDataBase(String className, String URL, String user, String password)
        {
            this.className = className;
            this.URL = URL;
            this.user = user;
            this.password = password;
        }

        public ConnectWithDataBase()
        {
        }
        public Connection getConnection()
        {
            //Load the driver class
            try {
                Class.forName(className);
            } catch (ClassNotFoundException ex) {
                System.out.println("Unable to load the class. Terminating the program");
                System.exit(-1);
            }
            //get the connection
            try {
                connection = DriverManager.getConnection(URL, user, password);
               // System.out.println("Connection successful");
            } catch (SQLException ex) {
                System.out.println("Error getting connection: " + ex.getMessage());
                System.exit(-1);
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
                System.exit(-1);
            }
            return connection;
        }
        public Connection connection;
}

