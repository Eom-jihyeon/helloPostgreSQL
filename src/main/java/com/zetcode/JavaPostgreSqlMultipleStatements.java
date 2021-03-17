package com.zetcode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaPostgreSqlMultipleStatements {
	public static void main(String[] args) {

		String url = "jdbc:postgresql://localhost:5432/testdb";
        String user = "postgres";
        String password = "mytest";

        String query = "SELECT id, name FROM authors WHERE Id=1;"
                + "SELECT id, name FROM authors WHERE Id=2;"
                + "SELECT id, name FROM authors WHERE Id=3";

        try (Connection con = DriverManager.getConnection(url, user, password);
                PreparedStatement pst = con.prepareStatement(query)) {

            boolean isResult = pst.execute();

            do {
                try (ResultSet rs = pst.getResultSet()) {

                    while (rs.next()) {
                    
                        System.out.print(rs.getInt(1));
                        System.out.print(": ");
                        System.out.println(rs.getString(2));
                    }

                    isResult = pst.getMoreResults();
                }
            } while (isResult);

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(
                    JavaPostgreSqlMultipleStatements.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
