package org.ecos.logic.dataaccesssqllite;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try (Connection conexion = DriverManager.getConnection("jdbc:sqlite::resource:database.sqlite")) {
            try (Statement statement = conexion.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT toy_id,name,provider,price FROM toy;");
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt(1) + "\t" + resultSet.getString("name") + "\t" + resultSet.getFloat("price"));
                }
            }
        }
    }

}
