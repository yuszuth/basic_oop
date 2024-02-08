public class CmdHire extends RecordedCommand {
    private Employee e;
    Company c = Company.getInstance();

    @Override
    public void execute(String[] cmdParts)
    {
        try{
            if(cmdParts.length < 3)
                throw new ExInsufficientArguments();
            e = new Employee(cmdParts[1], Integer.parseInt(cmdParts[2]));
            if (c.findEmployee(e.getName()) != null)
                throw new ExEmployeeAlreadyExists();   
        } catch (ExEmployeeNotFound e1) {
            c.addEmployee(e);
            addUndoCommand(this); //<====== store this command (addUndoCommand is implemented in RecordedCommand.java)
            clearRedoList(); //<====== There maybe some commands stored in the redo list.  Clear them.
            System.out.println("Done.");
        } catch (NumberFormatException e1){
            System.out.println("Wrong number format for annual leaves!");
        } catch (ExEmployeeAlreadyExists e1){
            System.out.println(e1.getMessage());
        } catch (ExInsufficientArguments e1) {
            System.out.println(e1.getMessage());
        }
        
    }
     
    @Override
    public void undoMe()
    {
        c.removeEmployee(e);
        addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
    }
     
    @Override
    public void redoMe()
    {
        c.addEmployee(e);
        addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
    }
}
