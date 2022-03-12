package be.vinci.pae.ihm.api.filters;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class TokenDecodingException extends WebApplicationException {

  public TokenDecodingException() {
    super(Response.status(Response.Status.UNAUTHORIZED)
        .build());
  }


  /**
   * Filling javadoc to avoid jenkins error.
   *
   * @param message message
   */
  public TokenDecodingException(String message) {
    super(Response.status(Response.Status.UNAUTHORIZED)
        .entity(message)
        .type("text/plain")
        .build());
  }

  /**
   * Filling javadoc to avoid jenkins error.
   *
   * @param cause cause
   */
  public TokenDecodingException(Throwable cause) {
    super(Response.status(Response.Status.UNAUTHORIZED)
        .entity(cause.getMessage())
        .type("text/plain")
        .build());
  }
}
