import java.time.Instant;
import java.time.LocalDate;

public class Assignment {
    private Mission mission;
    private Consultant consultant;
    private int plannedDays;
    private long negotiatedDailyRate;
    private LocalDate startDate;
    private LocalDate endDate;
    private AssignmentStatus status;
    private Instant createdAt;

    public Assignment() {}

    public Assignment(Mission mission, Consultant consultant, int plannedDays, long negotiatedDailyRate, LocalDate startDate, LocalDate endDate, AssignmentStatus status, Instant createdAt) {
        this.mission = mission;
        this.consultant = consultant;
        this.plannedDays = plannedDays;
        this.negotiatedDailyRate = negotiatedDailyRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getPlannedDays() {
        return plannedDays;
    }

    public void setPlannedDays(int plannedDays) {
        this.plannedDays = plannedDays;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }

    public long getNegotiatedDailyRate() {
        return negotiatedDailyRate;
    }

    public void setNegotiatedDailyRate(long negotiatedDailyRate) {
        this.negotiatedDailyRate = negotiatedDailyRate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public AssignmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssignmentStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public long computeMaxBillableAmount() {
        return plannedDays * negotiatedDailyRate;
    }

    public boolean isActiveOn(LocalDate date) {
        if (date == null || startDate == null || endDate == null || status == null) {
            return false;
        }

        return status == AssignmentStatus.ACTIVE
                && !date.isBefore(startDate)
                && !date.isAfter(endDate);
    }

}
