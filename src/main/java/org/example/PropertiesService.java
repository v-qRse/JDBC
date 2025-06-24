package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesService {
   private static final Properties props = new Properties();

   public PropertiesService(String s) {
      try (InputStream input = new FileInputStream(s)) {
         props.load(input);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public String getJDBCstring() {
      return props.getProperty("JDBCstring");
   }

   public String getName() {
      return props.getProperty("name");
   }

   public String getPassword() {
      return props.getProperty("password");
   }
}
