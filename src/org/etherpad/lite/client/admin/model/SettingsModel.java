package org.etherpad.lite.client.admin.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SettingsModel {

	Connection con;
	Statement st;
	String[] tables = new String[] { "serverSettings" };

	public SettingsModel() {
		connect();

	}

	public void connect() {
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");

			con = DriverManager.getConnection("jdbc:hsqldb:file:store.db", "SA", "");
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			createSchema();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	public boolean dbSchemaCreated() {
		boolean created = true;
		for (int i = 0; i < tables.length; i++) {
			try {
				PreparedStatement pSt = con
						.prepareStatement("SELECT count(*) FROM information_schema.system_tables WHERE table_scheme = 'public' AND table_name = ?");
				pSt.setString(1, tables[i]);
				ResultSet resSet = pSt.executeQuery();
				resSet.next();
				if (resSet.getInt(0) == 0) {
					created = false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return created;
	}

	private void createSchema() {
		try {
			st.execute(/* "CREATE SCHEMA ACCOUNTS AUTHORIZATION DBA;" + */
			"CREATE TABLE IF NOT EXISTS serverSettings(id INTEGER PRIMARY KEY, url VARCHAR(500), apiKey VARCHAR(100))");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String[] getServerConfig() {
		String[] ret = null;
		try {
			PreparedStatement pSt = con.prepareStatement("SELECT id,url,apiKey FROM serverSettings WHERE id = ?");
			pSt.setInt(1, 1);
			ResultSet resSet = pSt.executeQuery();
			if (resSet.next()) {
				ret = new String[] { resSet.getString(2), resSet.getString(3) };
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public void saveServerConfig(String url, String apiKey) {
		try {
			st.execute("DELETE FROM serverSettings WHERE id = 1");
			PreparedStatement pSt = con.prepareStatement("INSERT INTO serverSettings (id, url, apiKey) VALUES (?, ?, ?)");
			pSt.setInt(1, 1);
			pSt.setString(2, url);
			pSt.setString(3, apiKey);
			pSt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
