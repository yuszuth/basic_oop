public class CmdAssignProject extends RecordedCommand {
    
    private Project p;
    Company c = Company.getInstance();
    Team oldTeam;
    Team newTeam;
    Employee leader;

    @Override
    public void execute(String[] cmdParts)
    {
        try{
            if(cmdParts.length < 3)
                throw new ExInsufficientArguments();
            newTeam = c.findTeamByLeader(cmdParts[2]);
            leader = c.findEmployee(newTeam.getLeader().getName());
            p = c.findProjectByName(cmdParts[1]);
            oldTeam = p.getTeam();
            c.removeProject(p);
            p.changeTeam(newTeam);
            leader.changeTeam(newTeam.getName());
            c.addProject(p);
            addUndoCommand(this); //<====== store this command (addUndoCommand is implemented in RecordedCommand.java)
            clearRedoList(); //<====== There maybe some commands stored in the redo list.  Clear them.
    
            System.out.println("Done.");
        } catch (ExEmployeeNotFound e){
            System.out.println(e.getMessage());
        } catch (ExProjectNotFound e){
            System.out.println(e.getMessage());
        } catch (ExTeamNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExInsufficientArguments e){
            System.out.println(e.getMessage());
        } 
    }
     
    @Override
    public void undoMe()
    {
        c.removeProject(p);
        p.changeTeam(oldTeam);
        leader.changeTeam(oldTeam.getName());
        c.addProject(p);
        addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
    }
     
    @Override
    public void redoMe()
    {
        c.removeProject(p);
        p.changeTeam(newTeam);
        leader.changeTeam(newTeam.getName());
        c.addProject(p);
        addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
    }
}
