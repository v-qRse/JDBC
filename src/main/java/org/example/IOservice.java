package org.example;

import java.io.*;

public class IOservice {
   private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
   private static final PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

   public void sayHello() {
      print("Подключение установлено, введите SQL выражение");
   }

   public String read() {
      try {
         return in.readLine();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public void print(String s) {
      out.println(s);
      out.flush();
   }
}
