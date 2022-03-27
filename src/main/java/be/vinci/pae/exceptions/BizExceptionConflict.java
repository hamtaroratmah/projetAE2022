package be.vinci.pae.exceptions;

public class BizExceptionConflict extends RuntimeException {

  /**
   * Exception.
   */
  public BizExceptionConflict() {
    super();
  }

  /**
   * Exception.
   *
   * @param message message
   */
  public BizExceptionConflict(String message) {
    super(message);
  }
}