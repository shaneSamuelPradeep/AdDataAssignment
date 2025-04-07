import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DeptDAO extends DAO<Dept> {
    public DeptDAO(Connection connect) {
        super(connect);
    }

    public Dept find(int Deptno){
        Dept dept = new Dept();
        try {
            PreparedStatement findDept = connect.prepareStatement("select * from dept where deptno = ?");
            findDept.setInt(1, Deptno);
            ResultSet rs = findDept.executeQuery();
            if(rs.next()){
                dept.setDeptno(rs.getInt("deptno"));
                dept.setDname(rs.getString("dname"));
                dept.setLoc(rs.getString("loc"));
                rs.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dept;
    }
    public boolean create(Dept object){
        return false;
    }

    public boolean update(Dept object){
        return false;
    }

    public boolean delete(Dept object){
        return false;
    }
}




