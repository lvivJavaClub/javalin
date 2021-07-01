package io.github.lvivjavaclub;

import io.github.lvivjavaclub.graphql.MyQueryGraphql;
import io.github.lvivjavaclub.graphql.User;
import io.javalin.Javalin;
import io.javalin.plugin.graphql.GraphQLOptions;
import io.javalin.plugin.graphql.GraphQLPlugin;

public class App {

  public static void main(String[] args) {
    Javalin app = Javalin.create(config -> {

      GraphQLOptions graphQLOption = new GraphQLOptions("/graphql", new Object())
          .addPackage("io.github.lvivjavaclub.graphql")
          .register(new MyQueryGraphql());

      config.registerPlugin(new GraphQLPlugin(graphQLOption));

      config.addStaticFiles("web");
      config.enableDevLogging();
    }).start(7000);

    app.get("/hi", context -> context.result("Hello World"));

    app.before("/hi/*", context -> System.out.println("before " + context.splat(0)));
    app.get("/hi/:name", context -> context.result("Hello " + context.pathParam("name")));
    app.after("/hi/*", context -> System.out.println("after " + context.splat(0)));

    app.exception(RuntimeException.class, (e, context) -> {
      context.result(e.getMessage());
      context.status(500);
    });

    app.error(500, context -> System.out.println("on 500"));

    app.get("/num",
        context -> context.result("Hello " + context.queryParam("int", Integer.class).check(val -> val > 100).get()));


    app.post("/users", context -> {
      User user = context.bodyAsClass(User.class);
//      throw new RuntimeException("ops");
      context.json(user);
    });
    
    app.delete("/users", context -> {
      throw new RuntimeException("ops");
    });

    app.ws("/ws-hi", ws -> {
      ws.onConnect(context -> System.out.println("onConnect " + context.getSessionId()));
      ws.onError(context -> System.out.println("onError " + context.getSessionId()));
      ws.onMessage(context -> {
        System.out.println("onMessage " + context.getSessionId() + " mes: " + context.message());
        context.send("Hi JavaClub");
      });
      ws.onClose(context -> System.out.println("onClose " + context.getSessionId()));
    });

    app.sse("/sse", client -> {
      client.sendEvent("connected", "Hello, SSE");
      client.onClose(() -> System.out.println("sse onClose"));
    });

    app.events(event -> {
      event.serverStopping(() -> System.out.println("serverStopping"));
      event.serverStopped(() -> System.out.println("serverStopped"));
    });
  }
}
