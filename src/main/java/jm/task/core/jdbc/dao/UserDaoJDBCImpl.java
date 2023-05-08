package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

   Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS USERS " + "(ID BIGINT AUTO_INCREMENT PRIMARY KEY, " + "NAME VARCHAR(255), " +
                "LASTNAME VARCHAR(255), " + "AGE TINYINT)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS USERS;";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO USERS (NAME, LASTNAME, AGE) VALUES ('"+name+"', '"+lastName+"', "+age+")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM USERS WHERE ID = " + id;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
      List<User> usersList = new ArrayList<>();
      String sql = "SELECT ID, NAME, LASTNAME, AGE FROM USERS";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
           ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                usersList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersList;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM USERS";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
