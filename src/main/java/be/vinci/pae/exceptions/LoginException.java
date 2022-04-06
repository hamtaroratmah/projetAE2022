package be.vinci.pae.exceptions;

public class LoginException extends RuntimeException {

  /**
   * Exception.
   */
  public LoginException() {
    super();
  }

  /**
   * Exception.
   *
   * @param message message
   */
  public LoginException(String message) {
    super(message);
  }

}
