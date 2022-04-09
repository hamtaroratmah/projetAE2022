package be.vinci.pae.exceptions;

public class ForbiddenException extends RuntimeException {

  /**
   * Exception.
   */
  public ForbiddenException() {
    super();
  }

  /**
   * Exception.
   *
   * @param message message
   */
  public ForbiddenException(String message) {
    super(message);
  }

}
