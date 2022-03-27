package be.vinci.pae.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class WebExceptionMapper implements ExceptionMapper<Throwable> {

  /**
   * La méthode construite une réponse pour les exceptions attrapées.
   *
   * @param exception exception
   * @return renvoie une réponse pour le client
   */
  @Override
  public Response toResponse(Throwable exception) {
    exception.printStackTrace();
    if (exception instanceof WebApplicationException) {
      return Response.status(((WebApplicationException) exception).getResponse().getStatus())
          .entity(exception.getMessage())
          .build();
    } else if (exception instanceof BizExceptioinUnauthorized) {
      return Response.status(Status.UNAUTHORIZED)
          .entity(exception.getMessage())
          .build();
    } else if (exception instanceof BizExceptionForbidden) {
      return Response.status(Status.FORBIDDEN)
          .entity(exception.getMessage())
          .build();
    } else if (exception instanceof BizExceptionConflict) {
      return Response.status(Status.FORBIDDEN)
          .entity(exception.getMessage())
          .build();
    } else if (exception instanceof FatalException) {
      return Response.status(Status.INTERNAL_SERVER_ERROR)
          .entity(exception.getMessage())
          .build();
    }
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity(exception.getMessage())
        .build();
  }
}