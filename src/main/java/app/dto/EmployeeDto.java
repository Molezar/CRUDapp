package app.dto;

public class EmployeeDto {
    private Integer EmpID;
    private String Email;
    private String FamilyName;
    private String Name;
    private String Date;
    private String ZP;
    private String DepID;

    public Integer getEmpID() {
        return EmpID;
    }

    public void setEmpID(Integer empID) {
        EmpID = empID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFamilyName() {
        return FamilyName;
    }

    public void setFamilyName(String familyName) {
        FamilyName = familyName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getZP() {
        return ZP;
    }

    public void setZP(String ZP) {
        this.ZP = ZP;
    }

    public String getDepID() {
        return DepID;
    }

    public void setDepID(String depID) {
        DepID = depID;
    }
}
