package com.example.leetdoce.service;

import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

@Service
public class JDBC_Service {

    private static final String url ="jdbc:postgresql://ec2-34-236-100-103.compute-1.amazonaws.com:5432/dbhd86bgfjlndh";
    private static final String username="vahvkwcnxcjlgi";
    private static final String password="5ccc3acf747abde8de2f3e29ed894f705456522bc8461fb00394dd34d1b2990b";



    public static Connection CreateConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTableViaSchema(String newSchema, String ddl, String dml) {
        Statement statement = null;
        try (Connection connection = JDBC_Service.CreateConnection()) {
            statement = connection.createStatement();
            String createSchemaQuery = "CREATE SCHEMA " + newSchema;
            statement.executeUpdate(createSchemaQuery);
            String setSearchPathQuery = "SET search_path TO " + newSchema;
            statement.execute(setSearchPathQuery);
            statement.execute(ddl);
            String[] queryArray = separateQueryToInsert(dml);
            for (int i = 0; i < queryArray.length; i++) {
                statement.execute(queryArray[i]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private String[] separateQueryToInsert(String dml) {
        return dml.split(";");
    }

    public void deleteTableViaSchema(String newSchema) {
        Statement statement = null;
        try (Connection connection = JDBC_Service.CreateConnection()) {
            statement = connection.createStatement();
            String deleteTablesQuery = "DROP SCHEMA " + newSchema + " CASCADE";
            statement.executeUpdate(deleteTablesQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public List<String> getSchemasList(){
        Statement statement = null;
        try (Connection connection = JDBC_Service.CreateConnection()) {
            statement = connection.createStatement();
            String schemas = "SELECT count(*) AS schema_count, array_agg(nspname) AS schema_names FROM pg_namespace";
            ResultSet resultSet = statement.executeQuery(schemas);
            if (resultSet.next()) {
                String[] schemaNames = (String[]) resultSet.getArray("schema_names").getArray();

                return Arrays.stream(schemaNames)
                        .filter(schemaName -> !schemaName.equals("pg_toast") &&
                                !schemaName.equals("pg_catalog") &&
                                !schemaName.equals("information_schema")).toList();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }
}
