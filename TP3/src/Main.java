import java.sql.*;
import java.util.Scanner;

public class Main {



    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace();
        }
        String url = "jdbc:postgresql://localhost/Lab1";
        String user = "postgres";
        String pass = "Shane@123";
        Connection connexion = null;
        try {
            connexion = DriverManager.getConnection( url, user, pass );

            /* Requests to bdd will be here */
            System.out.println("Bdd Connected");
            //displayDepartment(connexion);
            Scanner sc = new Scanner(System.in);
            //System.out.println("Enter emp ID");
            //int empid = sc.nextInt();
            //System.out.println("Enter dept ID");
            //int deptid = sc.nextInt();
            //moveDepartment(connexion, empid, deptid);
            displayTable(connexion,"emp");


        } catch ( SQLException e ) {
            e.printStackTrace();
        } finally {
            if ( connexion != null )
                try {
                    connexion.close();
                } catch ( SQLException ignore ) {
                    ignore.printStackTrace();
                }
        }

    }
    public static void displayDepartment(Connection connexion) throws SQLException {
        Statement statement = connexion.createStatement();
        ResultSet resultat = statement.
                executeQuery( "SELECT deptno, dname ,loc FROM dept" );

        while ( resultat.next() ) {
            int deptno = resultat.getInt( "deptno");
            String dname = resultat.getString( "dname" );
            String loc = resultat.getString("loc" );

            System.out.println("Department " + deptno + " is for "
                    + dname + " and located in " + loc);
        }
        resultat.close();
    }
    public static void moveDepartment(Connection connexion, int empno, int deptno) throws SQLException{
        String updateQuery = "UPDATE emp SET deptno = ? WHERE empno = ?";
        try(PreparedStatement updateEmp = connexion.prepareStatement(updateQuery)){
            updateEmp.setLong(1,deptno);
            updateEmp.setLong(2,empno);
            updateEmp.execute();

                System.out.println("Employee No :" + empno + "is now in the Dept No " + deptno);
            }
        catch (Exception err){
            err.printStackTrace();

        }
    }
    public static void displayTable(Connection connexion, String tableName) throws SQLException {
        Statement statement = connexion.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM " + tableName);
        ResultSetMetaData metaData = result.getMetaData();
        int columnCount = metaData.getColumnCount();


        for (int i = 1; i <= columnCount; i++) {
            System.out.print(metaData.getColumnName(i) + " | ");
        }
        System.out.println();


        while (result.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(result.getString(i) + " | ");
            }
            System.out.println();
        }

        result.close();
        statement.close();
    }


}
