public class CmdCreateProject extends RecordedCommand {
    
    private Project p;
    Company c = Company.getInstance();

    @Override
    public void execute(String[] cmdParts)
    {
        try{
            if(cmdParts.length < 4)
                throw new ExInsufficientArguments();
            p = new Project(cmdParts[1], new Day(cmdParts[2]), Integer.parseInt(cmdParts[3]));
            if (c.findProjectByName(p.getName()) != null)
                throw new ExProjectAlreadyExists();   
        } catch (NumberFormatException e){
            System.out.println("Wrong number format for project duration!");
        } catch (ExProjectNotFound e){
            c.addProject(p);
            addUndoCommand(this); //<====== store this command (addUndoCommand is implemented in RecordedCommand.java)
            clearRedoList(); //<====== There maybe some commands stored in the redo list.  Clear them.
            System.out.println("Done.");
        } catch (ExProjectAlreadyExists e){
            System.out.println(e.getMessage());
        } catch (ExInsufficientArguments e){
            System.out.println(e.getMessage());
        }
    }
     
    @Override
    public void undoMe()
    {
        c.removeProject(p);
        addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
    }
     
    @Override
    public void redoMe()
    {
        c.addProject(p);
        addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
    }
}
