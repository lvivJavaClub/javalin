package io.github.lvivjavaclub.graphql;

import io.javalin.plugin.graphql.graphql.QueryGraphql;

import com.expediagroup.graphql.annotations.GraphQLDescription;

/**
 * TODO: Add the following code to the create.
 *
 * GraphQLOptions graphQLOption = new GraphQLOptions("/graphql", new Object())
 * .addPackage("io.github.lvivjavaclub.graphql")
 * .register(new MyQueryGraphql());
 *
 * config.registerPlugin(new GraphQLPlugin(graphQLOption));
 */
@GraphQLDescription("Query Example")
public class MyQueryGraphql implements QueryGraphql {

  public String hello() {
    return "Hello world";
  }

  public User user(@GraphQLDescription("awesome input") User user) {
    return user;
  }
}
