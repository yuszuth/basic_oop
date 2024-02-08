public class Leave implements Comparable<Leave>{
    private int startDate;
    private Day start;
    private int endDate;
    private Day end;
    private int daysInBetween;

    public Leave(String start, String end){
        this.start = new Day(start);
        this.end = new Day(end);
         startDate = this.start.toOneNumber();
        endDate = this.end.toOneNumber();
        daysInBetween = Day.countDaysBetween(this.start, this.end);
    }

    public int getDaysBetween(){
        return daysInBetween;
    }

    public int getStartDate(){
        return startDate;
    }

    public int getEndDate(){
        return endDate;
    }

    public Day getStart(){
        return start;
    }

    public Day getEnd(){
        return end;
    }

    @Override
    public int compareTo(Leave another) {
        if (this.startDate > another.startDate) return 1;
        else return -1;
    }
}
