package org.openshift;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class InsultGenerator {
	public String generateInsult() {
		String vowels = "AEIOU";
		String article = "an";
		String theInsult = "";
		try {
			String databaseURL = "jdbc:postgresql://";
			databaseURL = databaseURL + System.getenv("POSTGRESQL_SERVICE_HOST");
			databaseURL = databaseURL + System.getenv("POSTGRESQL_SERVICE_HOST");
			
			String username = System.getenv("POSTGRESQL_USER");
			String password = System.getenv("PGPASSWORD");
			
			Connection connection = DriverManager.getConnection(databaseURL, username, password);
			
			if(connection != null) {
				String query = "select a.string AS first, b.string AS second, c.string AS noun from"
						+ "short_adjective a, long_adjective b, noun c ORDER BY random() limit 1";
				
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next()) {
					if(vowels.indexOf(rs.getString("first").charAt(0)) == -1) {
						article = "a";
					}
					theInsult = String.format("Thou art %s %s %s %s!", article, rs.getString("first"),
							rs.getString("second"), rs.getString("noun"));
				}
				rs.close();
				connection.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			return "Database connection problem!";
		}
		return theInsult;
	}

}
