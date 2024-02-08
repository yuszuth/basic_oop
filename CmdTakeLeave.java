public class CmdTakeLeave extends RecordedCommand {
    
    private Employee e;
    private Leave l;
    private Team t;
    Company c = Company.getInstance();

    @Override
    public void execute(String[] cmdParts)
    {
        try{
            if (cmdParts.length < 4)
                throw new ExInsufficientArguments();
            e = c.findEmployee(cmdParts[1]);
            l = new Leave(cmdParts[2], cmdParts[3]);
            if(l.getDaysBetween() > e.getRemainingAnnualLeaves())
                throw new ExInsufficientBalanceOfAnnualLeaves(e.getRemainingAnnualLeaves());

            
            if(!e.findOverlap(l)){
                t = c.findTeamByName(e.getTeamName());
                if(c.checkProjectFinalStage(t, l)){
                    e.changeRemainingAnnualLeaves(l.getDaysBetween());
                    e.addLeave(l);
                    addUndoCommand(this); //<====== store this command (addUndoCommand is implemented in RecordedCommand.java)
                    clearRedoList(); //<====== There maybe some commands stored in the redo list.  Clear them.
                    System.out.println("Done. " + e.getName() + "'s remaining annual leave: " + e.getRemainingAnnualLeaves() + " days");
                }
            }
        } catch (ExEmployeeNotFound e1){
            System.out.println(e1.getMessage());
        } catch (ExInsufficientBalanceOfAnnualLeaves e1){
            System.out.println(e1.getMessage());
        } catch (ExLeaveOverlap e1){
            System.out.println(e1.getMessage());
        } catch (ExInsufficientArguments e1){
            System.out.println(e1.getMessage());
        } catch (ExTeamNotFound e1) {
            e.changeRemainingAnnualLeaves(l.getDaysBetween());
            e.addLeave(l);
            addUndoCommand(this); //<====== store this command (addUndoCommand is implemented in RecordedCommand.java)
            clearRedoList(); //<====== There maybe some commands stored in the redo list.  Clear them.
            System.out.println("Done. " + e.getName() + "'s remaining annual leave: " + e.getRemainingAnnualLeaves() + " days");
        } catch (ExFinalStage e1) {
            System.out.println(e1.getMessage());
        }
    }
     
    @Override
    public void undoMe()
    {
        e.removeLeave(l);
        e.changeRemainingAnnualLeaves(-l.getDaysBetween());
        addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
    }
     
    @Override
    public void redoMe()
    {
        e.addLeave(l);
        e.changeRemainingAnnualLeaves(l.getDaysBetween());
        addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
    }
}
