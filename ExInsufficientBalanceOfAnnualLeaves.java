public class ExInsufficientBalanceOfAnnualLeaves extends Exception {
    public ExInsufficientBalanceOfAnnualLeaves() { super("Insufficient balance of annual leave."); }
    public ExInsufficientBalanceOfAnnualLeaves(int daysCount) { super("Insufficient balance of annual leave. " + daysCount + " days left only!"); }
}