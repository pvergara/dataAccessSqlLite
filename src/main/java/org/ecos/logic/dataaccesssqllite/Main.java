package org.ecos.logic.dataaccesssqllite;

import lombok.extern.slf4j.Slf4j;
import org.ecos.logic.dataaccesssqllite.model.Student;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Main {
    private static Connection connection;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        connection = openConnection();

        String name = "a";
        List<Student> studentList = getBy(name);
        System.out.println(studentList);
        System.out.println(studentList.size());

        closeConnection(connection);

    }

    private static List<Student> getBy(String name) {
        List<Student> result = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT codigo,nombre,apellidos,altura,aula from alumnos WHERE nombre like ?;")) {
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet resultSet= preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(new Student(resultSet.getInt("codigo"),
                    resultSet.getString("nombre"),
                    resultSet.getString("apellidos"),
                    resultSet.getInt("altura"),
                    resultSet.getInt("aula"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    private static Connection openConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection("jdbc:sqlite::resource:instituto.sqlite");
    }

    private static void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

}
