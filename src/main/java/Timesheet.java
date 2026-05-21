import java.time.Instant;
import java.util.List;

public class Timesheet {
    private String consultantId;
    private String week;
    private TimesheetStatus status;
    private Instant submittedDate;
    private List<TimesheetEntry> entries;

    public Timesheet() {}

    public Timesheet(String consultantId, String week, TimesheetStatus status, Instant submittedDate, List<TimesheetEntry> entries) {
        this.consultantId = consultantId;
        this.week = week;
        this.status = status;
        this.submittedDate = submittedDate;
        this.entries = entries;
    }

    public String getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(String consultantId) {
        this.consultantId = consultantId;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public TimesheetStatus getStatus() {
        return status;
    }

    public void setStatus(TimesheetStatus status) {
        this.status = status;
    }

    public Instant getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Instant submittedDate) {
        this.submittedDate = submittedDate;
    }

    public List<TimesheetEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<TimesheetEntry> entries) {
        this.entries = entries;
    }

    public double totalDaysOnMission(String missionId) {
        if (missionId == null || entries == null) {
            return 0;
        }

        return entries.stream()
                .filter(entry -> missionId.equals(entry.getMissionId()))
                .mapToDouble(TimesheetEntry::getDayFraction)
                .sum();
    }

    public double totalDays() {
        if (entries == null) {
            return 0;
        }

        return entries.stream()
                .mapToDouble(TimesheetEntry::getDayFraction)
                .sum();
    }

    public boolean hasOverloadedDay() {
        if (entries == null) {
            return false;
        }

        return entries.stream()
                .map(TimesheetEntry::getDate)
                .distinct()
                .anyMatch(date -> {
                    double totalForDate = entries.stream()
                            .filter(entry -> date.equals(entry.getDate()))
                            .mapToDouble(TimesheetEntry::getDayFraction)
                            .sum();

                    return totalForDate > 1.0;
                });
    }

}
