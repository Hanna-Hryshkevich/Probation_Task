package EPAM_Task;

public class Project {
    int b, w;
    double d;
    String name;
    Object [] n;
    Project (int workers, String name, int budget, double deadline){
        this.w = workers;
        this.name = name;
        this.b = budget;
        this.d = deadline;
    }
    Project(Object [] m){
        this.n = m;
    }
    public String getName(){
        return name;
    }
    public double getD(){
        double k = d * 5; //We count the quantity of working days according to the deadline
        return k;
    }
    public String getDeadline(){
        String str_d = Double.toString(d);
        return str_d;
    }
    public String getBudget(){
        String str_b = Integer.toString(b);
        return str_b;
    }
    public int getB(){
        return b;
    }
    public int getW(){
        return w;
    }
}
