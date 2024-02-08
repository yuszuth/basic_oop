import java.util.ArrayList;
import java.util.Collections;

public class Company {
    private ArrayList<Employee> allEmployees;
    private ArrayList<Team> allTeams;
    private ArrayList<Project> allProjects;

    private static Company instance = new Company() ;

    private Company() {
        allEmployees = new ArrayList<>();
        allTeams = new ArrayList<>();
        allProjects = new ArrayList<>();
    }

    public static Company getInstance() {
        return instance;
    }

    public void listProjects() {
        Project.list(allProjects);
    }

    public Project findProjectByName(String name) throws ExProjectNotFound {
        for (Project project : allProjects) {
            if(project.getName().equals(name)){
                return project;
            }
        }
        throw new ExProjectNotFound();
    }

    public boolean checkProjectFinalStage (Team team, Leave l) throws ExFinalStage {
        ArrayList<Project> projects = new ArrayList<>();
        for (Project project : allProjects) {
            if(project.getTeam().getName().equals(team.getName())){
                projects.add(project);
            }
        }
        
        for (Project project : projects) {
            if (l.getStartDate() <= project.getEndDate().toOneNumber() && l.getEndDate() >= Math.max(project.getStartDate().toOneNumber(), project.getEndDate().subtractDay(4).toOneNumber())) {
                throw new ExFinalStage(project, Day.bigger(project.getStartDate(), project.getEndDate().subtractDay(4)), project.getEndDate());
            }
        }

        return true;
        // true - for no final stage errors
    }

    public void listTeams() {
        Team.list(allTeams);
    }

     public Team findTeamByLeader(String name) throws ExTeamNotFound {
        for (Team team : allTeams) {
            if(team.getName().equals(name)){
                return team;
            }
        }
        throw new ExTeamNotFound();
    }

    public Team findTeamByName(String name) throws ExTeamNotFound{
        for (Team team : allTeams) {
            if(team.getName().equals(name))
                return team;
        }
        throw new ExTeamNotFound();
    }

    public boolean checkForAnotherTeam(Employee employee){
        for (Team team : allTeams) {
            if(team.inTeam(employee) || team.getLeader().getName().equals(employee.getName()))
                return true;
        }
        return false;
    }

    public void listLeaves(){
        for (Employee employee : allEmployees) {
            System.out.println(employee.getName() + ": " + employee.listLeaves());
        }
    }

    public void listLeaves(String employeeName) throws ExEmployeeNotFound {
        Employee employee = findEmployee(employeeName);
        System.out.println(employee.getName() + ": " + employee.listLeaves());
    }

    public void listEmployees() {
        for (Employee employee : allEmployees) {
            System.out.printf("%s (Entitled Annual Leaves: %s days)\n", employee.getName(), employee.getEntitledAnnualLeaves());
        }
    }

    public Employee findEmployee(String name) throws ExEmployeeNotFound{
        for (Employee employee : allEmployees) {
            if(employee.getName().equals(name)){
                return employee;
            }
        }
        throw new ExEmployeeNotFound();
    }

    public Team findLeader(String name) throws ExTeamNotFound{
        for (Team team : allTeams) {
            if(team.getLeader().getName().equals(name))
                return team;
        }
        throw new ExTeamNotFound();
    }

    public void addEmployee(Employee e) {
        allEmployees.add(e);
        // System.out.println(e.getName() + " " + e.getAnnualLeaves());
        Collections.sort(allEmployees);
    }

    public void removeEmployee(Employee e) {
        allEmployees.remove(e); //.remove is a method of ArrayList
    }
    
     public void addTeam(Team t) {
        allTeams.add(t);
        // System.out.println(e.getName() + " " + e.getAnnualLeaves());
        Collections.sort(allTeams);
    }

    public void removeTeam(Team t) {
        allTeams.remove(t); //.remove is a method of ArrayList
    }

    public void addProject(Project p) {
        allProjects.add(p);
        Collections.sort(allProjects);
    }

    public void removeProject(Project p) {
        allProjects.remove(p); //.remove is a method of ArrayList
    }

    public void listTeamMembers(String teamName) throws ExTeamNotFound {
        Team team = findTeamByLeader(teamName);
        team.listTeamMembers();
    }

    public ArrayList<Power> getPowers(Project p){
        ArrayList<Power> powers = new ArrayList<>();
        for (Team team : allTeams) {
            double totalManpower = team.getManpower(p);
            double totalProjectsRate = 0.0;
            for (Project project : allProjects) {
                if(team.getName().equals(project.getTeam().getName())){   
                    if(!p.getName().equals(project.getName()))
                        totalProjectsRate += p.getProjectsRate(project);
                }
            }
            powers.add(new Power(team, totalManpower, totalProjectsRate));
        }
        return powers;
    }
}
