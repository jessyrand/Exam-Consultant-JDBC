import java.time.LocalDate;

public class TimesheetEntry {
    private LocalDate date;
    private String missionId;
    private double dayFraction;

    public TimesheetEntry(LocalDate date, String missionId, double dayFraction) {
        this.date = date;
        this.missionId = missionId;
        this.dayFraction = dayFraction;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public double getDayFraction() {
        return dayFraction;
    }

    public void setDayFraction(double dayFraction) {
        this.dayFraction = dayFraction;
    }
}
