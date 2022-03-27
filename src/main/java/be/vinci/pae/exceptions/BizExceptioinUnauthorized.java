package be.vinci.pae.exceptions;

public class BizExceptioinUnauthorized extends RuntimeException {

  /**
   * Exception.
   */
  public BizExceptioinUnauthorized() {
    super();
  }

  /**
   * Exception.
   *
   * @param message message
   */
  public BizExceptioinUnauthorized(String message) {
    super(message);
  }
}