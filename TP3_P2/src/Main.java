import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Load JDBC Driver
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found!");
            e.printStackTrace();
            return;
        }

        String url = "jdbc:postgresql://localhost/LAB1";
        String user = "postgres";
        String pass = "Shane@123";
        Connection connection = null;
        Scanner scanner = new Scanner(System.in);


        try {
            // Connect to database
            connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Database connected successfully!");

            /*

            EmpDAO employeeDAO = new EmpDAO(connection);

            while (true) {
                System.out.println("\nEmployee Lookup System");
                System.out.println("1. Find Employee by ID");
                System.out.println("2. Exit");
                System.out.print("Enter your choice: ");

                int choice;
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number!");
                    continue;
                }

                switch (choice) {
                    case 1:
                        System.out.print("\nEnter Employee ID: ");
                        try {
                            int empId = Integer.parseInt(scanner.nextLine());

                            // Find employee recursively with manager chain
                            Emp employee = employeeDAO.find(empId);

                            if (employee != null) {
                                System.out.println("\nEmp Details:");
                                printEmployeeDetails(employee, 0);
                            } else {
                                System.out.println("Employee not found!");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid employee ID!");
                        }
                        break;

                    case 2:
                        System.out.println("Exiting program...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
             */


            DAOFactory factory = new DAOFactory();
            EmpDAO employeeDAO = factory.getEmployeeDAO();

            Emp emp = employeeDAO.find(7839);
            System.out.println(emp);

            factory.close();

        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
        } finally {
            // Close resources
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    // Helper method to print employee details recursively
    private static void printEmployeeDetails(Emp employee, int level) {
        String indent = "  ".repeat(level);

        System.out.println(indent + "ID: " + employee.getEmpNo());
        System.out.println(indent + "Name: " + employee.getEfirst() + " " + employee.getEname());
        System.out.println(indent + "Job: " + employee.getJob());
        System.out.println(indent + "Salary: " + employee.getSal());
        System.out.println(indent + "Department: " +
                (employee.getDepartment() != null ?
                        employee.getDepartment().getDname() : "None"));

        if (employee.getMgr() != null) {
            System.out.println(indent + "Manager:");
            printEmployeeDetails(employee.getMgr(), level + 1);
        }
    }

}