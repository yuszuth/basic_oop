import java.util.ArrayList;

public class Project implements Comparable<Project>{
    private String projectName;
    private Day startDate;
    private Day endDate;
    private int daysToComplete;
    private Team team;

    public Project(String projectName, Day startDate,int daysToComplete){
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = this.startDate.addDays(daysToComplete - 1);
        this.daysToComplete = daysToComplete;
        this.team = new Team("--", null);
    }

    public void changeTeam(Team newTeam){
        team = newTeam; 
    }

    public Day getStartDate(){
        return startDate;
    }

    public Day getEndDate(){
        return endDate;
    }

    public int getDaysToComplete(){
        return daysToComplete;
    }

    public String getName() { 
        return projectName;
    }

    public Team getTeam(){
        return team;
    }

    public String toString(){
        return projectName;
    }

    public static void list(ArrayList<Project> list) {
                //Learn: "-" means left-aligned
        System.out.printf("%-15s%-15s%-15s%-30s\n", "Project", "Start Day", "End Day", "Team");
        for (Project p : list)
            System.out.printf("%-15s%-15s%-15s%-30s\n", p.projectName, p.startDate, p.endDate, p.team); //display t.teamName, etc..
    }

    @Override
    public int compareTo(Project another){
        if(this.projectName.equals(another.projectName)) return 0;
        else if(this.projectName.compareTo(another.projectName) > 0) return 1;
        else return -1;
    }

    public double getProjectsRate(Project another){
        if(Day.bigger(this.startDate, another.startDate).toOneNumber() > Day.smaller(this.endDate, another.endDate).toOneNumber())
            return 0.0;
        else    
            return Math.max(0.0, (double) Day.countDaysBetween(Day.bigger(this.startDate, another.startDate), Day.smaller(this.endDate, another.endDate))/(double) this.daysToComplete);
    }
}
