public class CmdListTeamMembers implements Command {
    @Override
    public void execute(String[] cmdParts){
    Company c = Company.getInstance();
        try {
            c.listTeamMembers(cmdParts[1]);
        } catch (ExTeamNotFound e) {
            System.out.println(e.getMessage());
        }
    }
}