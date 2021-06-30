package io.github.lvivjavaclub;

import io.javalin.Javalin;

public class App {

  public static void main(String[] args) {
    Javalin app = Javalin.create().start(7000);
    app.get("/hi", context -> context.result("Hello World"));
  }
}
