package be.vinci.pae.exceptions;

public class FatalException extends RuntimeException {

  /**
   * Exception.
   */
  public FatalException() {
    super();
  }

  /**
   * Exception.
   *
   * @param message message
   */
  public FatalException(String message) {
    super(message);
  }
}