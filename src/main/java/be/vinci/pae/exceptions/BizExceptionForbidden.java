package be.vinci.pae.exceptions;

public class BizExceptionForbidden extends RuntimeException {

  /**
   * Exception.
   */
  public BizExceptionForbidden() {
    super();
  }

  /**
   * Exception.
   *
   * @param message message
   */
  public BizExceptionForbidden(String message) {
    super(message);
  }

}
