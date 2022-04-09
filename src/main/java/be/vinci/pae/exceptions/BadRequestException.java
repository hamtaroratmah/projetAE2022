package be.vinci.pae.exceptions;

public class BadRequestException extends RuntimeException {

  /**
   * Exception.
   */
  public BadRequestException() {
    super();
  }

  /**
   * Exception.
   *
   * @param message message
   */
  public BadRequestException(String message) {
    super(message);
  }

}
