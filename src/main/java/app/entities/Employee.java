package app.entities;

public class
Employee {
    private int id;
    private String Email;
    private String FamilyName;
    private String Name;
    private int DayOfBirth;
    private int MonthOfBirth;
    private int YearOfBirth;

    public Employee(int id, String Email, String FamilyName, String Name, int DayOfBirth, int MonthOfBirth, int YearOfBirth) {
        this.FamilyName = FamilyName;
        this.Name = Name;
        this.DayOfBirth = DayOfBirth;
        this.MonthOfBirth = MonthOfBirth;
        this.YearOfBirth = YearOfBirth;
    }

    public Employee(){
    }

    public int id() {
        return id;
    }

    public void id(int id) {
        this.id = id;
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

    public int getDayOfBirth() {
        return DayOfBirth;
    }

    public void setDayOfBirth(int DayOfBirth) {
        this.DayOfBirth = DayOfBirth;
    }

    public int getMonthOfBirth() {
        return MonthOfBirth;
    }

    public void setMonthOfBirth(int MonthOfBirth) {
        this.MonthOfBirth = MonthOfBirth;
    }

    public int getYearOfBirth() {
        return YearOfBirth;
    }

    public void setYearOfBirth(int YearOfBirth) {
        this.YearOfBirth = YearOfBirth;
    }

}
