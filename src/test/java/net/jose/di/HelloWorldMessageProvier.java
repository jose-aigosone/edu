package net.jose.di;

public class HelloWorldMessageProvier implements MessageProvider {

  @Override
  public String getMessage() {
    return "Hello World";
  }

}
