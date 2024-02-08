public class ExEmployeeAlreadyExists extends Exception {
    public ExEmployeeAlreadyExists() { super("Employee already exists!"); }
    public ExEmployeeAlreadyExists(String message) { super(message); }    
}