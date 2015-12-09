package net.jose.di;

public class HiWorldMessageProvier implements MessageProvider {

  @Override
  public String getMessage() {
    return "Hi World";
  }

}
