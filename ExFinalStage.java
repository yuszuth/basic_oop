public class ExFinalStage extends Exception {
    public ExFinalStage() { super("The leave is invalid. Reason: Project will be in its final stage!"); }
    public ExFinalStage(Project project, Day start, Day end) { super("The leave is invalid. Reason: Project " + project +" will be in its final stage during " + start + " to " + end + "."); }
}