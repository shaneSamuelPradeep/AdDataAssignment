import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOFactory {
    private static final String DB_URL = "jdbc:postgresql://localhost/LAB1";
    private static final String USER = "postgres";
    private static final String PASSWORD = "kusuma01";

    private Connection connection;

    public DAOFactory() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public EmpDAO getEmployeeDAO() {
        return new EmpDAO(connection);
    }

    public DeptDAO getDepartmentDAO() {
        return new DeptDAO(connection);
    }
    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

