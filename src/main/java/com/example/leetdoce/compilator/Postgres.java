package com.example.leetdoce.compilator;

import com.example.leetdoce.entity.QueryEntity;
import com.example.leetdoce.service.JDBC_Service;
import lombok.experimental.UtilityClass;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@UtilityClass
public class Postgres {

    private static final String url = "jdbc:postgresql://localhost:5432/postgres1";
    private static final String username = "postgres";
    private static final String password = "admin";

    public ResponseCompilator run_Postgres(String userQuery, QueryEntity queryEntity) {
        ResponseCompilator responseCompilator = new ResponseCompilator();

        Statement statementForUser = null;
        Statement statementForDB = null;
        try (Connection connection = JDBC_Service.CreateConnection()) {
            statementForUser = connection.createStatement();
            statementForDB = connection.createStatement();
            long start_Time = System.currentTimeMillis();
            String setSearchPathQuery = "SET search_path TO " + queryEntity.getSchemaName();
            statementForUser.execute(setSearchPathQuery);
            statementForDB.execute(setSearchPathQuery);

            ResultSet userResult = statementForUser.executeQuery(userQuery);
            ResultSet dbResult = statementForDB.executeQuery(queryEntity.getCorrectAnswer());

            boolean isEqual = true;

            int userColumn = userResult.getMetaData().getColumnCount();
            int dbColumn = dbResult.getMetaData().getColumnCount();
            long finish_Time = System.currentTimeMillis();
            List<String[]> userDataList = getDataFromResult(userResult);
            List<String[]> dbDataList = getDataFromResult(dbResult);

            if (userDataList.size() != dbDataList.size()) {
                isEqual = false;
            } else {
                for (int i = 0; i < userDataList.size(); i++) {
                    String[] userRow = userDataList.get(i);
                    String[] dbRow = dbDataList.get(i);

                    if (!Arrays.equals(userRow, dbRow)) {
                        isEqual = false;
                        break;
                    }
                }
            }


            if (isEqual) {
                System.out.println("The ResultSets are equal.");
                responseCompilator.setPassed(true);
                long runtime = finish_Time - start_Time;
                responseCompilator.setRuntime(runtime);
                responseCompilator.setPassedTestCases("1/1");
                String[][] dbData = makeDoubleArray(dbColumn,dbDataList);
                responseCompilator.setExpected(drawTableAsString(dbData));
                return responseCompilator;
            } else {
                System.out.println("The ResultSets are not equal.");
                responseCompilator.setPassed(false);
                String[][] userData = makeDoubleArray(userColumn,userDataList);
                responseCompilator.setYouResult(drawTableAsString(userData));
                String[][] dbData = makeDoubleArray(dbColumn,dbDataList);
                responseCompilator.setExpected(drawTableAsString(dbData));
                return responseCompilator;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            responseCompilator.setError(e.getMessage());
            responseCompilator.setPassed(false);
            return responseCompilator;
        } finally {
            if (statementForDB != null) {
                try {
                    statementForDB.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (statementForUser != null) {
                try {
                    statementForUser.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public String drawTableAsString(String[][] tableData) {
        StringBuilder tableString = new StringBuilder();

        int columnCount = tableData[0].length;
        int[] columnWidths = new int[columnCount];

        for (int colIdx = 0; colIdx < columnCount; colIdx++) {
            for (int rowIdx = 0; rowIdx < tableData.length; rowIdx++) {
                String cellData = tableData[rowIdx][colIdx];
                int width = (cellData != null) ? cellData.length() : 0;
                if (width > columnWidths[colIdx]) {
                    columnWidths[colIdx] = width;
                }
            }
        }

        tableString.append('+');
        for (int width : columnWidths) {
            tableString.append(padString("", width + 2, '-'));
            tableString.append('+');
        }
        tableString.append('\n');

        tableString.append('|');
        for (int i = 0; i < columnCount; i++) {
            tableString.append(padString(tableData[0][i], columnWidths[i] + 2, ' '));
            tableString.append('|');
        }
        tableString.append('\n');

        tableString.append('+');
        for (int width : columnWidths) {
            tableString.append(padString("", width + 2, '-'));
            tableString.append('+');
        }
        tableString.append('\n');

        for (int rowIdx = 1; rowIdx < tableData.length; rowIdx++) {
            // Add the row values
            tableString.append('|');
            for (int colIdx = 0; colIdx < columnCount; colIdx++) {
                tableString.append(padString(
                        tableData[rowIdx][colIdx] != null ? tableData[rowIdx][colIdx] : "null",
                        columnWidths[colIdx] + 2, ' '));
                tableString.append('|');
            }
            tableString.append('\n');
        }

        tableString.append('+');
        for (int width : columnWidths) {
            tableString.append(padString("", width + 2, '-'));
            tableString.append('+');
        }
        tableString.append('\n');

        return tableString.toString();
    }

    private String padString(String str, int length, char paddingChar) {
        StringBuilder paddedString = new StringBuilder(str);
        while (paddedString.length() < length) {
            paddedString.append(paddingChar);
        }
        return paddedString.toString();
    }

    public List<String[]> getDataFromResult(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metadata = resultSet.getMetaData();
        int columnCount = metadata.getColumnCount();

        String[] columnArray =new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnArray[i]=metadata.getColumnName(i+1);
        }
        List<String[]> rows = new ArrayList<>();
        rows.add(columnArray);

        while (resultSet.next()) {
            String[] rowData = new String[columnCount];
            for (int i = 0; i < rowData.length; i++) {
                rowData[i] = String.valueOf(resultSet.getObject(i + 1));
            }
            rows.add(rowData);
        }
        resultSet.close();
        return rows;
    }

    public String[][] makeDoubleArray(int column,List<String[]> list){
        String[][] array = new String[list.size()][column];
        for (int i = 0; i <list.size(); i++) {
            array[i]=list.get(i);
        }
        return array;
    }
}
