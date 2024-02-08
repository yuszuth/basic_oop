public class CmdJoinTeam extends RecordedCommand {
    private Employee e;
    private Team t;
    Company c = Company.getInstance();

    @Override
    public void execute(String[] cmdParts)
    {
        try{
            if(cmdParts.length < 3)
                throw new ExInsufficientArguments();
            e = c.findEmployee(cmdParts[1]);
            t = c.findTeamByName(cmdParts[2]);
            if(c.checkForAnotherTeam(e))
                throw new ExEmployeeAlreadyInAnotherTeam(e.getName(), e.getTeamName());
            t.addMember(e);
            e.changeTeam(t.getName());
            addUndoCommand(this); //<====== store this command (addUndoCommand is implemented in RecordedCommand.java)
            clearRedoList(); //<====== There maybe some commands stored in the redo list.  Clear them.
            System.out.println("Done.");
        } catch (ExEmployeeNotFound e1) {
            System.out.println(e1.getMessage());
        } catch (ExTeamNotFound e1){
            System.out.println(e1.getMessage());
        } catch (ExEmployeeAlreadyInAnotherTeam e1){
            System.out.println(e1.getMessage());
        } catch (ExInsufficientArguments e1) {
            System.out.println(e1.getMessage());
        }
        
    }
     
    @Override
    public void undoMe()
    {
        e.changeTeam(null);
        t.removeMember(e);
        addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
    }
     
    @Override
    public void redoMe()
    {
        e.changeTeam(t.getName());
        t.addMember(e);
        addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
    }
}
