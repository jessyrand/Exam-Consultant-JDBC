import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}