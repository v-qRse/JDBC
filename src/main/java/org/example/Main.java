package org.example;

public class Main {
   private static final String PROPERTIES_PATH = "src/main/resources/config.properties";
   private static final String QUIT_REQUEST = "QUIT";

   public static void main(String[] args) {
      IOservice ioService = new IOservice();
      PropertiesService properties = new PropertiesService(PROPERTIES_PATH);
      JDBCservice jdbсService = new JDBCservice(properties.getJDBCstring(),
              properties.getName(), properties.getPassword());

      ioService.sayHello();
      String request = ioService.read();
      String resultOutput = "";
      while (!request.equals(QUIT_REQUEST)) {
         try {
            boolean printResult = jdbсService.executeStatement(request);
            resultOutput = printResult ? jdbсService.getResultString() : "SQL выражение успешно выполнилось";
         } catch (Exception e) {
            resultOutput = e.toString();
         } finally {
            ioService.print(resultOutput);
            ioService.print("Введите SQL выражение:");
            request = ioService.read();
         }
      }

      try {
         jdbсService.closeConnection();
      } catch (Exception e) {
         ioService.print(e.toString());
      }
   }
}