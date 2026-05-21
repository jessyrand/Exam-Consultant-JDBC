import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}