public class CmdListLeaves implements Command {
    @Override
    public void execute(String[] cmdParts){
        Company c = Company.getInstance();
        if (cmdParts.length == 1)
            c.listLeaves();
        else{
            try {
                c.listLeaves(cmdParts[1]);
            } catch (ExEmployeeNotFound e) {
                System.out.println(e.getMessage());
            }
        }
    }
}