public class Day implements Cloneable{
	
	private int year;
	private int month;
	private int day;
	
    private static final String MonthNames="JanFebMarAprMayJunJulAugSepOctNovDec";

	//Constructor
	public void set(String sDay) //Set year,month,day based on a string like 01-Mar-2022
    {   
        String[] sDayParts = sDay.split("-");
        this.day = Integer.parseInt(sDayParts[0]); //Apply Integer.parseInt for sDayParts[0];
        this.year = Integer.parseInt(sDayParts[2]);
        this.month = MonthNames.indexOf(sDayParts[1])/3+1;
    }

	public int toOneNumber(){
		return year * 10000 + month * 100 + day;
	}

    public Day(String sDay) {
        set(sDay);
    } //Constructor, simply call set(sDay)
	
	// check if a given year is a leap year
	static public boolean isLeapYear(int y)
	{
		if (y%400==0)
			return true;
		else if (y%100==0)
			return false;
		else if (y%4==0)
			return true;
		else
			return false;
	}
	
	// check if y,m,d valid
	static public boolean valid(int y, int m, int d)
	{
		if (m<1 || m>12 || d<1) return false;
		switch(m){
			case 1: case 3: case 5: case 7:
			case 8: case 10: case 12:
					 return d<=31; 
			case 4: case 6: case 9: case 11:
					 return d<=30; 
			case 2:
					 if (isLeapYear(y))
						 return d<=29; 
					 else
						 return d<=28; 
		}
		return false;
	}

	public Day addDays(int n) {
        Day newDay = this.clone(); 
        while (n > 0) {
            newDay.day++;
            if (!valid(newDay.year, newDay.month, newDay.day)) {
                newDay.day = 1;
                newDay.month++;
                if (newDay.month > 12) {
                    newDay.month = 1;
                    newDay.year++;
                }
            }
            n--;
        }
        return newDay;
    }

    public Day subtractDay(int n) {
        Day newDay = this.clone();
        while (n > 0) {
            newDay.day--;
            if (newDay.day < 1) {
                newDay.month--;
                if (newDay.month < 1) {
                    newDay.month = 12;
                    newDay.year--;
                }
                switch (newDay.month) {
                    case 1: case 3: case 5: case 7:
			        case 8: case 10: case 12:
                        newDay.day = 31;
                        break;
                    case 4: case 6: case 9: case 11:
                        newDay.day = 30;
                        break;
                    case 2:
                        if (isLeapYear(newDay.year))
                            newDay.day = 29;
                        else
                            newDay.day = 28;
                        break;
                }
            }
            n--;
        }
        return newDay;
    }

	public static boolean equal(Day day1, Day day2){
		if (day1.day == day2.day && day1.month == day2.month && day1.year == day2.year) return true;
		else return false;
	}

    public static Day bigger(Day day1, Day day2){
        if(day1.toOneNumber() > day2.toOneNumber()) return day1;
        else return day2;
    }

    public static Day smaller(Day day1, Day day2){
        if(day1.toOneNumber() > day2.toOneNumber()) return day2;
        else return day1;
    }

	public static int countDaysBetween(Day startDate, Day endDate) {
        int count = 1;
        Day currentDay = startDate.clone();
        while (!(equal(currentDay, endDate))) {
            currentDay = currentDay.addDays(1);
            count++;
        }
        return count;
    }

	// Return a string for the day like dd MMM yyyy
	@Override
    public String toString()
    { 
        return day+"-"+ MonthNames.substring((month-1)*3,month*3) + "-"+ year; // (month-1)*3,(month)*3
    }

    @Override
    public Day clone()
    {
        Day copy=null;
        try {
            copy = (Day) super.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return copy;
    }
}
