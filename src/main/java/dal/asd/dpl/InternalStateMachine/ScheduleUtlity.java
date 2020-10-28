package dal.asd.dpl.InternalStateMachine;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScheduleUtlity {

    private Calendar seasonCalendar;
    private int currentSeason;
    private String currentYear;
    private int regularSeasonYear;
    private int playoffYear;
    private boolean isSeasonOver; // Stanley cup winner decided
    private String lastSeasonDay;
    private String seasonWinner;

    public ScheduleUtlity(int season){
        this.currentSeason = season;
        this.seasonCalendar = Calendar.getInstance();

        setYears();
    }

    private void setYears(){
        int currentYear = this.seasonCalendar.get(Calendar.YEAR);
        currentYear += this.currentSeason;
        this.regularSeasonYear = currentYear;
        this.playoffYear = currentYear+1;
        this.currentYear = String.valueOf(currentYear);
    }

    public String getRegularSeasonStartDay() {
        String startDate = "30-09-" + this.currentYear;
        return startDate;
    }

    public String getRegularSeasonFirstDay() {
        String firstDay = "01-10-" + this.currentYear;
        return firstDay;
    }

    public String getPlayoffFirstDay() {
        String firstDay = getFirstDayOfPlayoff() + "-04-" + String.valueOf(playoffYear);
        return firstDay;
    }

    public String getRegularSeasonLastDay() {
        String LastDay = getFinalDayOfRegularSeason() + "-04-" + String.valueOf(Integer.parseInt(this.currentYear)+1);
        return LastDay;
    }

    public String getPlayoffLastDay() {
        String lastDay = "01-06-" + String.valueOf(playoffYear);
        return lastDay;
    }

    public String getNextRegularSeasonStartDay() {
        String nextSeasonStartDay = "30-09-" + this.playoffYear;
        return nextSeasonStartDay;
    }

    public String getSeasonWinner() {
        return seasonWinner;
    }

    public void setSeasonWinner(String seasonWinner) {
        this.seasonWinner = seasonWinner;
    }

    public void setSeasonOverStatus(boolean over) {
        this.isSeasonOver = over;
    }

    public boolean getSeasonOverStatus() {
        return isSeasonOver;
    }

    public void setLastSeasonDay(String date) {
        this.lastSeasonDay = date;
    }

    public String getLastSeasonDay() {
        return this.lastSeasonDay;
    }

    private String getFinalDayOfRegularSeason() {
        seasonCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        seasonCalendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
        seasonCalendar.set(Calendar.MONTH, Calendar.APRIL);
        seasonCalendar.set(Calendar.YEAR, regularSeasonYear+1);
        return "0"+String.valueOf(seasonCalendar.get(Calendar.DATE));
    }

    private String getFirstDayOfPlayoff() {
        seasonCalendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        seasonCalendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 2);
        seasonCalendar.set(Calendar.MONTH, Calendar.APRIL);
        seasonCalendar.set(Calendar.YEAR, playoffYear);
        return String.valueOf(seasonCalendar.get(Calendar.DATE));
    }

    public boolean isTradeDeadlinePending(String currentDate) {
        seasonCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        seasonCalendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 4);
        seasonCalendar.set(Calendar.MONTH, Calendar.FEBRUARY);
        seasonCalendar.set(Calendar.YEAR, playoffYear);
        String tradeDeadlineDay = String.valueOf(seasonCalendar.get(Calendar.DATE));
        String tradeDeadline = tradeDeadlineDay + "-02-" + String.valueOf(playoffYear);

        try {
            Date start = new SimpleDateFormat("dd-MM-yyyy").parse(currentDate);
            Date end = new SimpleDateFormat("dd-MM-yyyy").parse(tradeDeadline);
            if (start.compareTo(end) < 0) {
                return true;
            }
            if (start.compareTo(end) >= 0) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isLastDayOfSeason(String currentDate) {
        try {
            Date start = new SimpleDateFormat("dd-MM-yyyy").parse(currentDate);
            Date end = new SimpleDateFormat("dd-MM-yyyy").parse(getPlayoffLastDay());
            if (start.compareTo(end) > 0) {
                return true;
            }
            if (start.compareTo(end) <= 0) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
