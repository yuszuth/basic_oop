public class ExWrongNumberFormat extends Exception {
    public ExWrongNumberFormat() { super("Wrong number format!"); }
    public ExWrongNumberFormat(String message) { super("Wrong number format for " + message); }
}