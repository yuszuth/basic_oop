public class CmdSetupTeam extends RecordedCommand {
    
    private Team t;
    Employee leader;
    Company c = Company.getInstance();

    @Override
    public void execute(String[] cmdParts)
    {
        try{
            if (cmdParts.length < 3)
                throw new ExInsufficientArguments();
            t = new Team(cmdParts[1], c.findEmployee(cmdParts[2]));
            leader = c.findEmployee(cmdParts[2]);
            if (c.findTeamByLeader(t.getName()) != null)
                throw new ExTeamAlreadyExists();  
        } catch (ExTeamNotFound e){                
            try {
                if  (c.findLeader(cmdParts[2]) != null){ 
                    try {
                        throw new ExEmployeeAlreadyInAnotherTeam(cmdParts[2], c.findLeader(cmdParts[2]).getName());
                    } catch (ExEmployeeAlreadyInAnotherTeam e1) {
                        System.out.println(e1.getMessage());
                    }
                }
            } catch (ExTeamNotFound e1) {
                leader.changeTeam(t.getName());
                c.addTeam(t);
                addUndoCommand(this); //<====== store this command (addUndoCommand is implemented in RecordedCommand.java)
                clearRedoList(); //<====== There maybe some commands stored in the redo list.  Clear them.
                System.out.println("Done.");
            }
        } catch (ExEmployeeNotFound e){
            System.out.println(e.getMessage());
        } catch (ExTeamAlreadyExists e){
            System.out.println(e.getMessage());
        } catch (ExInsufficientArguments e){
            System.out.println(e.getMessage());
        }
    }
     
    @Override
    public void undoMe()
    {
        leader.changeTeam(null);
        c.removeTeam(t);
        addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
    }
     
    @Override
    public void redoMe()
    {
        leader.changeTeam(t.getName());
        c.addTeam(t);
        addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
    }
}
