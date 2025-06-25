package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCservice {
   private final Connection connection;
   private ResultSet resultSet;
   private StringBuilder resultRequest;

   public JDBCservice(String jdbc, String name, String password) {
      try {
         connection = DriverManager.getConnection(jdbc, name, password);
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public String getResultString() {
      return resultRequest.toString();
   }

   public void closeConnection() throws Exception {
      try {
         connection.close();
      } catch (SQLException e) {
         throw new Exception("Невозможно разорвать соединение");
      }
   }

   public boolean executeStatement(String sqlQuery) throws Exception {
      try {
         Statement statement = connection.createStatement();
         boolean resultExists = execute(statement, sqlQuery);
         executeResult(resultExists);
         statement.close();
         return resultExists;
      } catch (SQLException e) {
         throw new Exception("Невозможно создать точку выполнения запроса");
      }
   }

   private boolean execute(Statement statement, String sql) throws Exception {
      try {
         boolean resultExecute = statement.execute(sql);
         resultSet = statement.getResultSet();
         return resultExecute;
      } catch (SQLException e) {
         throw new Exception("Невозможно выполнить заданный sql-запрос \n" + e);
      }
   }

   private void executeResult(boolean resultExists) throws Exception {
      if (resultExists) {
         fetchResultSet();
         createResultRequest();
      }
   }

   private void fetchResultSet() throws Exception {
      try {
         resultSet.setFetchSize(11);
      } catch (SQLException e) {
         throw new Exception("Ошибка обрезания данных");
      }
   }

   private void createResultRequest() throws Exception {
      List<String> columnNames = extractColumnNames();
      resultRequest = new StringBuilder();
      int counter = 0;
      while (resultSet.next()) {
         for (String columnName : columnNames) {
            appendNameAndValueColumn(columnName);
         }
         resultRequest.append("\n");
         counter++;

         if (counter >= 10 && resultSet.next()) {
            resultRequest.append("В БД есть еще записи");
            break;
         }
      }
   }

   private List<String> extractColumnNames() throws Exception {
      try {
         ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
         List<String> columnNames = new ArrayList<>();
         for (int index = 1; index <= resultSetMetaData.getColumnCount(); index++) {
            columnNames.add(resultSetMetaData.getColumnName(index));
         }
         return columnNames;
      } catch (RuntimeException e) {
         throw new Exception("Ошибка подключения к базе данных при выполнении запроса");
      }
   }

   private void appendNameAndValueColumn(String currentColumName) throws Exception {
      try {
         resultRequest.append(currentColumName)
                 .append(": ")
                 .append(resultSet.getString(currentColumName))
                 .append("\t");
      } catch (SQLException e) {
         throw new Exception("Ошибка получение значения колонки");
      }
   }
}
