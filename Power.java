public class Power {
    
    private Team team;
    private double manpower = 0.0;
    private double projectsRate = 1.0;

    public Power(Team aTeam, double totalManpower, double totalProjectsRate) {
        team = aTeam;
        manpower = totalManpower;
        projectsRate = totalProjectsRate;
    }

    public Team getTeam(){
        return team;
    }

    public double getManpower(){
        return manpower;
    }

    public double getProjectsRate(){
        return projectsRate;
    }
}
