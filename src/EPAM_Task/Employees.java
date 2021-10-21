package EPAM_Task;

public class Employees {
    private String EmName, last_name, EmPos, work_exp, wage;
    Employees (String name, String ln, String wage, String po, String work_exp){
        this.EmName = name;
        this.last_name = ln;
        this.wage = wage;
        this.EmPos = po;
        this.work_exp = work_exp;
    }
    public String getEmName(){
        return EmName;
    }
    public String getLast_name(){
        return last_name;
    }
    public String getWage(){
        return wage;
    }
    public String getEmPos(){
        return EmPos;
    }
    public int getWork_exp(){
        return Integer.parseInt(this.work_exp.trim());
    }
    public String getWork(){
        return work_exp;
    }
    public int countWage(){
        Integer i1 = Integer.valueOf(wage);
        int d_wage = i1 / 20; //Counts the daily wage (20 working days a month);
        return d_wage;
    }
}
