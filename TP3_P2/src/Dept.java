public class Dept {
    private int Deptno;
    private String Dname;
    private String loc;

    public int getDeptno() {
        return Deptno;
    }

    public void setDeptno(int deptno) {
        Deptno = deptno;
    }

    public String getDname() {
        return Dname;
    }

    public void setDname(String dname) {
        Dname = dname;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String toString(){
        return "DeptNo " + Deptno + "Department Name " + Dname + " Department location " + loc;
    }
}