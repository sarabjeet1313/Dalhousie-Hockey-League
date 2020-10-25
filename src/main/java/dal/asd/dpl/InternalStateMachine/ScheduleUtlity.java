package dal.asd.dpl.InternalStateMachine;

import java.util.Calendar;

public class ScheduleUtlity {

    private Calendar seasonCalendar;
    private String tradeDeadline;
    private

    ScheduleUtlity(){
        this.seasonCalendar = Calendar.getInstance();
    }

//    private void setTradeDeadline() {
//        seasonCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//        seasonCalendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 4);
//        seasonCalendar.set(Calendar.MONTH, Calendar.FEBRUARY);
//        seasonCalendar.set(Calendar.YEAR, this.currentYear + 1);
//        this.tradeDeadline = String.valueOf(seasonCalendar.get(Calendar.DATE));
//    }

}
