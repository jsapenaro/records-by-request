package com.opussolutionsgroup.recordsbyrequest.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.opussolutionsgroup.recordsbyrequest.global.Global;
import com.opussolutionsgroup.recordsbyrequest.data.Row;

public class MySQLConnection {

	private String host;
	private String port;

	private Connection connection;

	private boolean connected;

	public MySQLConnection(String host, String port) {
		this.host = host;
		this.port = port;
		this.connected = false;
	}

	/**
	 * The method used to establish the connection
	 */
	public void connect() {
		String connectionURL = "jdbc:mysql://".concat(host).concat(":")
				.concat(port).concat("/")
				.concat(Global.projectConfig.getValue("DB_NAME"));

		try {
			Class.forName("com.mysql.jdbc.Driver");

			connection = DriverManager.getConnection(connectionURL,
					Global.projectConfig.getValue("MYSQL_USER"),
					Global.projectConfig.getValue("MYSQL_PASS"));

		} catch (Exception e) {
			System.err.printf("Error connecting to database: %s%n", e);
			System.exit(1);

		}

		this.connected = true;
	}

	/**
	 * The method used to destroy the connection
	 */
	public void disconnect() {
		if (connected) {
			try {
				connection.close();
				connected = false;
			} catch (Exception e) {
				System.err.printf("Error closing database connetion: %s%n", e);
			}
		} else {
			System.err
					.println("Attempted to close a connection that was not open.");
		}
	}

	/**
	 * Method used to execute a Query with a ResultSet
	 * 
	 * SELECTION MUST ALWAYS BE '*'
	 * 
	 * @param table
	 *            The table the query should be executed on
	 * @param query
	 *            The query to be executed
	 * @param searchWords
	 *            Key words used to perform a search query
	 */
	public void executeQuery(String table, String query, String[] searchWords) {

		Global.displayedRows.clear();

		String sqlQuery = null;
		Statement statement = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;

		if (searchWords == null) {

			sqlQuery = query.concat(" ").concat("FROM").concat(" ")
					.concat(table);
			
			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(sqlQuery);
				resultSetMetaData = resultSet.getMetaData();
			} catch (Exception e) {
				System.out.println(sqlQuery);
				System.err.printf("Error executing query: %s%n", e);
			}

			if (table.equals(Global.requestTable)) {

				try {
					while (resultSet.next()) {
						ArrayList<String> data = new ArrayList<String>();
						ArrayList<String> visibleData = new ArrayList<String>();

						for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
							data.add(i, resultSet.getString(i + 1));
						}

						for (int i = 0; i < Global.visibleColumnNames.length; i++) {
							visibleData.add(i, resultSet
									.getString(Global.visibleColumnNames[i]));
						}

						Row row = new Row(
								(String[]) data
										.toArray(new String[data.size()]),
								(String[]) visibleData
										.toArray(new String[visibleData.size()]),
								resultSet.findColumn("request_tracking_id"));
						Global.displayedRows.add(row);
					}
				} catch (Exception e) {
					System.err.printf("Error parsing query ResultSet: %s%n", e);
				}

			} else if (table.equals(Global.manifestTable)) {

			}

			// performing a search in the UI search bar
		} else if (searchWords != null) {

			// loop through all of the keywords
			for (String word : searchWords) {

				sqlQuery = query.concat(" FROM ").concat(table)
						.concat(" WHERE ");

				// insert all of the columns defined in config to the query
				for (int i = 0; i < Global.requestsColumnConfig.getKeys()
						.size(); i++) {
					sqlQuery = sqlQuery
							+ (Global.requestsColumnConfig.getKeys().get(i)
									+ " LIKE '%" + word + "%'");
					// if not end of query, add ' OR '
					if (i != (Global.requestsColumnConfig.getKeys().size()) - 1) {
						sqlQuery = sqlQuery.concat(" OR ");
					}
				}

				try {
					statement = connection.createStatement();
					resultSet = statement.executeQuery(sqlQuery);
					resultSetMetaData = resultSet.getMetaData();

				} catch (Exception e) {
					System.err.printf("Error executing query: %s%n", e);
				}

				try {
					while (resultSet.next()) {

						ArrayList<String> data = new ArrayList<String>();
						ArrayList<String> visibleData = new ArrayList<String>();

						for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
							data.add(i, resultSet.getString(i + 1));
						}

						for (int i = 0; i < Global.visibleColumnNames.length; i++) {
							visibleData.add(i, resultSet
									.getString(Global.visibleColumnNames[i]));
						}

						int requestTrackingID = resultSet.getInt(resultSet
								.findColumn(("request_tracking_id")));

						// indicates whether the Row is already present, since
						// you can have duplicates
						boolean found = false;
						for (Row row : Global.displayedRows) {
							if (row.getRequestID() == requestTrackingID) {
								found = true;
								break;
							}
						}

						// Row is not a duplicate
						if (found == false) {

							Row row = new Row(
									(String[]) data.toArray(new String[data
											.size()]),
									(String[]) visibleData
											.toArray(new String[visibleData
													.size()]),
									requestTrackingID);
							Global.displayedRows.add(row);

						}
					}

				} catch (Exception e) {
					System.err.printf("Error parsing query ResultSet: %s%n", e);
				}

			}

			for (Row row : Global.displayedRows) {
				System.out.println(row.getRequestID());
			}

		}
	}

}
