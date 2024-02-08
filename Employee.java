import java.util.ArrayList;
import java.util.Collections;

public class Employee implements Comparable<Employee>{
    private String name;
    private int entitledAnnualLeaves;
    private int remainingAnnualLeaves; 
    private ArrayList<Leave> leaves;
    private String teamName = "";

    public Employee(String n, int yle){
        name = n;
        entitledAnnualLeaves = yle;
        remainingAnnualLeaves = yle;
        leaves = new ArrayList<>();
    }

    public String getName() { return name;}

    public int getRemainingAnnualLeaves(){return remainingAnnualLeaves;}

    public int getEntitledAnnualLeaves(){return entitledAnnualLeaves;}

    public void changeRemainingAnnualLeaves(int vacationDays){
            remainingAnnualLeaves -= vacationDays;
    }

    public void changeTeam(String newTeamName){
        teamName = newTeamName;
    }

    public String getTeamName(){
        return teamName;
    }

    public static Employee searchEmployee(ArrayList<Employee> list,String nameToSearch)throws ExEmployeeNotFound {
        for (Employee e : list) {
            if(e.getName().equals(nameToSearch))return e;
        }
        throw new ExEmployeeNotFound();
    }

    public void addLeave(Leave l){
        leaves.add(l);
        Collections.sort(leaves);
    }

    public void removeLeave(Leave l){
        leaves.remove(l);
    }

    public String listLeaves(){
        String leavesOutput = "";
        SystemDate currentDay = SystemDate.getInstance();
        
        for (int i = 0; i < leaves.size(); i++) {
            if(currentDay.toOneNumber() <= leaves.get(i).getEndDate()){
                leavesOutput += leaves.get(i).getStart() + " to " + leaves.get(i).getEnd();
                if (i != leaves.size() - 1) {
                    leavesOutput += ", ";
                }
            }
        }
        if(leavesOutput.equals(""))
            leavesOutput = "--";

        return leavesOutput;
    }

    public boolean findOverlap(Leave l) throws ExLeaveOverlap{
        for (Leave leave : leaves) {
            if (l.getStartDate() > leave.getEndDate() || l.getEndDate() < leave.getStartDate()) {
                continue;
            }else{
                throw new ExLeaveOverlap(leave.getStart(), leave.getEnd());
            }
        }
        return false;
    }

    @Override
    public int compareTo(Employee another) {
        if (this.name.equals(another.name)) return 0;
        else if (this.name.compareTo(another.name)>0) return 1;
        else return -1;
    }

    public double getManpower(Project p){
        double totalProjectDays = p.getDaysToComplete();
        double totalLeaveDays = 0.0;
        for (Leave leave : leaves) {
            if (!(p.getEndDate().toOneNumber() < leave.getStartDate() || p.getStartDate().toOneNumber() > leave.getEndDate())) {
                totalLeaveDays += Day.countDaysBetween(Day.bigger(p.getStartDate(), leave.getStart()), Day.smaller(p.getEndDate(), leave.getEnd()));
            }
        }
        return 1.0 - (totalLeaveDays / totalProjectDays);
    }
}