package be.vinci.pae.exceptions;

public class TokenDecodingException extends RuntimeException {

  public TokenDecodingException() {
    super();
  }

  /**
   * Exceptions thrown if token is invalid.
   *
   * @param message message
   */
  public TokenDecodingException(String message) {
    super(message);
  }

}
