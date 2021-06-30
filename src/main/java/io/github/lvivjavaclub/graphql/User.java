package io.github.lvivjavaclub.graphql;

public class User {
  public String name;
  public Integer id;

  @Override
  public String toString() {
    return "User{" +
           "name='" + name + '\'' +
           ", id=" + id +
           '}';
  }
}
