package be.vinci.pae.exceptions;

public class LoginException extends RuntimeException {

  /**
   * Exception.
   */
  public LoginException() {
    super();
  }

  /**
   * Exception thrown if an exception is raised during the login.
   *
   * @param message message
   */
  public LoginException(String message) {
    super(message);
  }

}
