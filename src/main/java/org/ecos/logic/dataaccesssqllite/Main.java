package org.ecos.logic.dataaccesssqllite;

import lombok.extern.slf4j.Slf4j;
import org.ecos.logic.dataaccesssqllite.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("CallToPrintStackTrace")
@Slf4j
public class Main {
    private static Connection connection;

    public static void main(String[] args)  {
        try {
            connection = openConnection();

            String name = "john";
            List<Student> studentList = getBy(name);
            System.out.println(studentList);
            System.out.println(studentList.size());
            insertInClass(new Student(54, "john", "doe", 180, 24));
            studentList = getBy(name);
            System.out.println(studentList);
            System.out.println(studentList.size());

        } catch (ClassNotFoundException |SQLException e) {
            System.out.println("There's a problem trying to open the connection or trying to invoke an SQL execution");
            e.printStackTrace();
        }finally {
            try {
                closeConnection(connection);
            } catch (SQLException e) {
                System.out.println("There's a problem trying to close the connection");
                e.printStackTrace();
            }
        }



    }

    private static Connection openConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection("jdbc:sqlite::resource:instituto.sqlite");
    }

    private static List<Student> getBy(String name) throws SQLException {
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
        }

        return result;
    }

    private static void insertInClass(Student student) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("Insert into alumnos (nombre, apellidos, altura, aula) values (?,?,?,?);")) {
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getLastname());
            preparedStatement.setInt(3, student.getHeight());
            preparedStatement.setInt(4, student.getClassId());

            preparedStatement.executeUpdate();

        }

    }

    private static void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

}
