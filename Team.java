import java.util.ArrayList;
import java.util.Collections;

public class Team implements Comparable<Team>{
    private String teamName;
	private Employee head;
	private Day dateSetup;
    private ArrayList<Employee> members;

    public Team(String n, Employee hd) {
        teamName = n;
        head = hd;
        dateSetup = SystemDate.getInstance().clone();
        members = new ArrayList<>();
    }

    public String getName(){
        return teamName;
    }

    public Employee getLeader(){
        return head;
    }

    public String toString(){
        if(head != null){
            String teamAndMembers = teamName  + " (" + head.getName();
            for (Employee member : members) {
                teamAndMembers += ", " + member.getName();
            }
            teamAndMembers += ")";
            return teamAndMembers;
        }else{
            return "--";
        }
    }

    public static void list(ArrayList<Team> list) {
                //Learn: "-" means left-aligned
        System.out.printf("%-30s%-10s%-13s\n", "Team Name", "Leader", "Setup Date" );
        for (Team t : list)
            System.out.printf("%-30s%-10s%-13s\n", t.teamName, t.head.getName(), t.dateSetup); //display t.teamName, etc..
    }

    @Override
    public int compareTo(Team another) {
        if (this.teamName.equals(another.teamName)) return 0;
        else if (this.teamName.compareTo(another.teamName)>0) return 1;
        else return -1;
    }

    public void listTeamMembers() {
        System.out.printf("%-10s%-10s%-100s\n", "Role", "Name", "Current / coming leaves" );
        if (head != null) {
            System.out.printf("%-10s%-10s%-100s\n", "Leader", head.getName(), head.listLeaves());
            for (Employee employee : members) {
                // if(employee != head)
                System.out.printf("%-10s%-10s%-100s\n", "Member", employee.getName(), employee.listLeaves());
            }
        }
    }

    public boolean inTeam(Employee employee){
        for (Employee member : members) {
            if(employee.getName().equals(member.getName()))
                return true;
        }
        return false;
    }

    public void addMember(Employee employee){
        members.add(employee);
        Collections.sort(members);
    }

    public void removeMember(Employee employee){
        members.remove(employee);
    }

    public double getManpower(Project project){
        double manpower = head.getManpower(project);
        for (Employee member : members) {
            manpower += member.getManpower(project);
        }
        return manpower;
    }
}
