import java.util.ArrayList;

public class CmdSuggestProjectTeam implements Command {

    @Override
    public void execute(String[] cmdParts)
    {
        try{
            if (cmdParts.length < 2)
                throw new ExInsufficientArguments();

            Company c = Company.getInstance();
            Project p = c.findProjectByName(cmdParts[1]);
            ArrayList<Power> powers = c.getPowers(p);
            System.out.println("During the period of project " + p + " (" + p.getStartDate() + " to " + p.getEndDate() + "):");
            System.out.println("    Average manpower (m) and count of existing projects (p) of each team:");
            for (Power power : powers) {
                System.out.printf("        " + power.getTeam().getName() + ": m=%.2f workers, p=%.2f projects\n", power.getManpower(), power.getProjectsRate());
            }
            System.out.println("    Projected loading factor when a team takes this project " + p + ":");
            double min = Integer.MAX_VALUE;
            Team t = null;
            for (Power power : powers) {
                System.out.printf("        " + power.getTeam().getName() + ": (p+1)/m = %.2f\n", (power.getProjectsRate() + 1.0)/power.getManpower());
                if(min > (power.getProjectsRate() + 1.0)/power.getManpower()){
                    min = (power.getProjectsRate() + 1.0)/power.getManpower();
                    t = power.getTeam();
                }
            }
            System.out.printf("Conclusion: %s should be assigned to %s for best balancing of loading",p, t.getName());
        } catch (ExInsufficientArguments e){
            System.out.println(e.getMessage());
        } catch (ExProjectNotFound e) {
            System.out.println(e.getMessage());
        }
    }
}
