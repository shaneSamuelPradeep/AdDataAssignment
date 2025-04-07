import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class EmpDAO extends DAO<Emp> {

    public EmpDAO(Connection connect) {
        super(connect);
    }

    @Override
    public Emp find(int id) {
        Emp employee = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // 1. Prepare SQL query to find employee by ID
            String sql = "SELECT empno, ename, efirst, job, mgr, hiredate, sal, comm, tel, deptno FROM emp WHERE empno = ?";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            // 2. Execute query
            resultSet = preparedStatement.executeQuery();

            // 3. Process results if found
            if (resultSet.next()) {
                employee = new Emp();

                // Set basic employee attributes
                employee.setEmpNo(resultSet.getLong("empno"));
                employee.setEname(resultSet.getString("ename"));
                employee.setEfirst(resultSet.getString("efirst"));
                employee.setJob(resultSet.getString("job"));
                employee.setHireDate(resultSet.getDate("hiredate"));
                employee.setSal(resultSet.getInt("sal"));
                employee.setComm(resultSet.getInt("comm"));
                employee.setTel(resultSet.getInt("tel"));

                // 4. Handle manager relationship (recursive call)
                int managerId = resultSet.getInt("mgr");
                if (!resultSet.wasNull()) {  // Check if mgr was NULL in database
                    EmpDAO empDAO = new EmpDAO(connect);
                    Emp manager = empDAO.find(managerId);
                    employee.setMgr(manager);
                }

                // 5. Handle department relationship
                int deptNo = resultSet.getInt("deptno");
                DeptDAO deptDAO = new DeptDAO(connect);
                Dept department = deptDAO.find(deptNo);
                employee.setDepartment(department);
            }

        } catch (SQLException e) {
            System.err.println("Error finding employee with ID " + id);
            e.printStackTrace();
        } finally {
            // 6. Close resources in reverse order of creation
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources");
                e.printStackTrace();
            }
        }

        return employee;
    }

    @Override
    public boolean create(Emp object) {
        // Implementation would go here
        return false;
    }

    @Override
    public boolean update(Emp object) {
        // Implementation would go here
        return false;
    }

    @Override
    public boolean delete(Emp object) {
        // Implementation would go here
        return false;
    }
}