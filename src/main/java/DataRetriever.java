import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {

    public Consultant findConsultantById(String id) {
        DBConnection dbConnection = new DBConnection();

        try (Connection connection = dbConnection.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("""
                    select id, name, grade
                    from consultant
                    where id = ?
                    """);

            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Consultant consultant = new Consultant();

                consultant.setId(resultSet.getString("id"));
                consultant.setName(resultSet.getString("name"));
                consultant.setGrade(Grade.valueOf(resultSet.getString("grade")));

                return consultant;
            }

            throw new RuntimeException("Consultant not found with id " + id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Consultant> findAllConsultants() {
        DBConnection dbConnection = new DBConnection();

        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    select id, name, grade
                    from consultant
                    """);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Consultant> consultants = new ArrayList<>();

            while (resultSet.next()) {
                Consultant consultant = new Consultant();

                consultant.setId(resultSet.getString("id"));
                consultant.setName(resultSet.getString("name"));
                consultant.setGrade(Grade.valueOf(resultSet.getString("grade")));

                consultants.add(consultant);
            }

            return consultants;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Consultant> findConsultantsByGrade(Grade grade) {
        DBConnection dbConnection = new DBConnection();

        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    select id, name, grade
                    from consultant
                    where grade = ?
                    """);

            preparedStatement.setString(1, grade.name());

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Consultant> consultants = new ArrayList<>();

            while (resultSet.next()) {
                Consultant consultant = new Consultant();

                consultant.setId(resultSet.getString("id"));
                consultant.setName(resultSet.getString("name"));
                consultant.setGrade(Grade.valueOf(resultSet.getString("grade")));

                consultants.add(consultant);
            }

            return consultants;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Mission findMissionById(String id) {
        DBConnection dbConnection = new DBConnection();

        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    select id, description, start_date, end_date
                    from mission
                    where id = ?
                    """);

            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Mission mission = new Mission();

                mission.setId(resultSet.getString("id"));
                mission.setDescription(resultSet.getString("description"));
                mission.setStartDate(resultSet.getDate("start_date").toLocalDate());

                if (resultSet.getDate("end_date") != null) {
                    mission.setEndDate(resultSet.getDate("end_date").toLocalDate());
                }

                return mission;
            }

            throw new RuntimeException("Mission not found with id " + id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Mission> findAllMissions() {
        DBConnection dbConnection = new DBConnection();

        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    select id, description, start_date, end_date
                    from mission
                    """);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Mission> missions = new ArrayList<>();

            while (resultSet.next()) {
                Mission mission = new Mission();

                mission.setId(resultSet.getString("id"));
                mission.setDescription(resultSet.getString("description"));
                mission.setStartDate(resultSet.getDate("start_date").toLocalDate());

                if (resultSet.getDate("end_date") != null) {
                    mission.setEndDate(resultSet.getDate("end_date").toLocalDate());
                }

                missions.add(mission);
            }

            return missions;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Mission> findMissionsActiveOn(LocalDate date) {
        DBConnection dbConnection = new DBConnection();

        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    select id, description, start_date, end_date
                    from mission
                    where start_date <= ?
                    and (end_date is null or end_date >= ?)
                    """);

            preparedStatement.setDate(1, java.sql.Date.valueOf(date));
            preparedStatement.setDate(2, java.sql.Date.valueOf(date));

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Mission> missions = new ArrayList<>();

            while (resultSet.next()) {
                Mission mission = new Mission();

                mission.setId(resultSet.getString("id"));
                mission.setDescription(resultSet.getString("description"));
                mission.setStartDate(resultSet.getDate("start_date").toLocalDate());

                if (resultSet.getDate("end_date") != null) {
                    mission.setEndDate(resultSet.getDate("end_date").toLocalDate());
                }

                missions.add(mission);
            }

            return missions;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Assignment findAssignmentByMissionAndConsultant(String missionId, String consultantId) {
        DBConnection dbConnection = new DBConnection();

        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    select mission_id, consultant_id, planned_days, negotiated_daily_rate,
                           start_date, end_date, status, created_at
                    from assignment
                    where mission_id = ?
                    and consultant_id = ?
                    """);

            preparedStatement.setString(1, missionId);
            preparedStatement.setString(2, consultantId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Assignment assignment = new Assignment();

                assignment.setMission(findMissionById(resultSet.getString("mission_id")));
                assignment.setConsultant(findConsultantById(resultSet.getString("consultant_id")));
                assignment.setPlannedDays(resultSet.getInt("planned_days"));
                assignment.setNegotiatedDailyRate(resultSet.getLong("negotiated_daily_rate"));
                assignment.setStartDate(resultSet.getDate("start_date").toLocalDate());
                assignment.setEndDate(resultSet.getDate("end_date").toLocalDate());
                assignment.setStatus(AssignmentStatus.valueOf(resultSet.getString("status")));
                assignment.setCreatedAt(resultSet.getTimestamp("created_at").toInstant());

                return assignment;
            }

            throw new RuntimeException("Assignment not found with missionId " + missionId + " and consultantId " + consultantId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Assignment> findAssignmentsByMission(String missionId) {
        DBConnection dbConnection = new DBConnection();

        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    select mission_id, consultant_id, planned_days, negotiated_daily_rate,
                           start_date, end_date, status, created_at
                    from assignment
                    where mission_id = ?
                    """);

            preparedStatement.setString(1, missionId);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Assignment> assignments = new ArrayList<>();

            while (resultSet.next()) {
                Assignment assignment = new Assignment();

                assignment.setMission(findMissionById(resultSet.getString("mission_id")));
                assignment.setConsultant(findConsultantById(resultSet.getString("consultant_id")));
                assignment.setPlannedDays(resultSet.getInt("planned_days"));
                assignment.setNegotiatedDailyRate(resultSet.getLong("negotiated_daily_rate"));
                assignment.setStartDate(resultSet.getDate("start_date").toLocalDate());
                assignment.setEndDate(resultSet.getDate("end_date").toLocalDate());
                assignment.setStatus(AssignmentStatus.valueOf(resultSet.getString("status")));
                assignment.setCreatedAt(resultSet.getTimestamp("created_at").toInstant());

                assignments.add(assignment);
            }

            return assignments;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Assignment> findAssignmentsByConsultant(String consultantId) {
        DBConnection dbConnection = new DBConnection();

        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    select mission_id, consultant_id, planned_days, negotiated_daily_rate,
                           start_date, end_date, status, created_at
                    from assignment
                    where consultant_id = ?
                    """);

            preparedStatement.setString(1, consultantId);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Assignment> assignments = new ArrayList<>();

            while (resultSet.next()) {
                Assignment assignment = new Assignment();

                assignment.setMission(findMissionById(resultSet.getString("mission_id")));
                assignment.setConsultant(findConsultantById(resultSet.getString("consultant_id")));
                assignment.setPlannedDays(resultSet.getInt("planned_days"));
                assignment.setNegotiatedDailyRate(resultSet.getLong("negotiated_daily_rate"));
                assignment.setStartDate(resultSet.getDate("start_date").toLocalDate());
                assignment.setEndDate(resultSet.getDate("end_date").toLocalDate());
                assignment.setStatus(AssignmentStatus.valueOf(resultSet.getString("status")));
                assignment.setCreatedAt(resultSet.getTimestamp("created_at").toInstant());

                assignments.add(assignment);
            }

            return assignments;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Assignment> findAllActiveAssignments() {
        DBConnection dbConnection = new DBConnection();

        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    select mission_id, consultant_id, planned_days, negotiated_daily_rate,
                           start_date, end_date, status, created_at
                    from assignment
                    """);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Assignment> assignments = new ArrayList<>();

            while (resultSet.next()) {
                Assignment assignment = new Assignment();

                assignment.setMission(findMissionById(resultSet.getString("mission_id")));
                assignment.setConsultant(findConsultantById(resultSet.getString("consultant_id")));
                assignment.setPlannedDays(resultSet.getInt("planned_days"));
                assignment.setNegotiatedDailyRate(resultSet.getLong("negotiated_daily_rate"));
                assignment.setStartDate(resultSet.getDate("start_date").toLocalDate());
                assignment.setEndDate(resultSet.getDate("end_date").toLocalDate());
                assignment.setStatus(AssignmentStatus.valueOf(resultSet.getString("status")));
                assignment.setCreatedAt(resultSet.getTimestamp("created_at").toInstant());

                if (assignment.isActiveOn(LocalDate.now())) {
                    assignments.add(assignment);
                }
            }

            return assignments;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}