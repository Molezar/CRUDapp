package app.entities;

import java.util.Objects;
import java.util.Date;

public class
Employee {
    private int EmpID;
    private String Email;
    private String FamilyName;
    private String Name;
    private Date Date;
//    private int DayOfBirth;
//    private int MonthOfBirth;
//    private int YearOfBirth;
    private int ZP;


    public Employee(int EmpID,  String Name,  String FamilyName, String Email, Date Date,  int ZP) {
        this.EmpID = EmpID;
        this.Email = Email;
        this.FamilyName = FamilyName;
        this.Name = Name;
        this.Date = Date;
        this.ZP = ZP;
    }

    public Employee(){
    }

    public int getEmpID() {
        return EmpID;
    }

    public void setEmpID(int id) {
        this.EmpID = EmpID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getFamilyName() {
        return FamilyName;
    }

    public void setFamilyName(String FamilyName) {
        this.FamilyName = FamilyName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getFullName() {
        return  this.Name + ' ' + this.FamilyName;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }

    public Date getDate() {
        return  this.Date;
    }

//    public int getDayOfBirth() {
//        return DayOfBirth;
//    }
//
//    public void setDayOfBirth(int DayOfBirth) {
//        this.DayOfBirth = DayOfBirth;
//    }
//
//    public int getMonthOfBirth() {
//        return MonthOfBirth;
//    }
//
//    public void setMonthOfBirth(int MonthOfBirth) {
//        this.MonthOfBirth = MonthOfBirth;
//    }
//
//    public int getYearOfBirth() {
//        return YearOfBirth;
//    }
//
//    public void setYearOfBirth(int YearOfBirth) {
//        this.YearOfBirth = YearOfBirth;
//    }
//
//    public int getZP() {return YearOfBirth; }

    public void setZP(int ZP) {
        this.ZP = ZP;
    }

    @Override
    public String toString() {
        return "Employee{name='" + Name + " email=" + Email + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee emp = (Employee) o;

        if (!Objects.equals(Email, emp.Email)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = Email != null ? Email.hashCode() : 0;
        result = 31 * result;
        return result;
    }

}
