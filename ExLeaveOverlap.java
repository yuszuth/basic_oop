public class ExLeaveOverlap extends Exception {
    public ExLeaveOverlap() { super("Leave overlapped!"); }
    public ExLeaveOverlap(Day from, Day to) { super("Leave overlapped: The leave period " + from + " to " + to + " is found!"); }
}