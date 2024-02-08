public class CmdStartDay extends RecordedCommand {
    
    SystemDate prev = SystemDate.getInstance();
    SystemDate cur;

    @Override
    public void execute(String[] cmdParts)
    {
        try{
            if (cmdParts.length < 2) 
                throw new ExInsufficientArguments();
            SystemDate.createTheInstance(cmdParts[1]);
            cur = SystemDate.getInstance();
            addUndoCommand(this); //<====== store this command (addUndoCommand is implemented in RecordedCommand.java)
            clearRedoList();
            System.out.println("Done.");
        }catch (ExInsufficientArguments e){
            System.out.println(e.getMessage());
        }
    }
     
    @Override
    public void undoMe()
    {
        SystemDate.update(prev);
        addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
    }
     
    @Override
    public void redoMe()
    {
        SystemDate.update(cur); 
        addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
    }
}