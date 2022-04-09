package be.vinci.pae.exceptions;

public class FatalException extends RuntimeException {

  /**
   * Exception.
   */
  public FatalException() {
    super();
  }

  /**
   * Exception thrown if an exception is raised by the database
   *
   * @param message message
   */
  public FatalException(String message) {
    super(message);
  }
}