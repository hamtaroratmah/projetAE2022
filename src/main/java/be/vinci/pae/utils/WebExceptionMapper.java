package be.vinci.pae.utils;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Level;

@Provider
public class WebExceptionMapper implements ExceptionMapper<Throwable> {

  @Override
  public Response toResponse(Throwable exception) {
    Log myLog = null;
    try {
      myLog = new Log("log.txt");
    } catch (IOException e) {

      e.printStackTrace();
    }
    myLog.logger.setLevel(Level.WARNING);

    exception.printStackTrace();
    if (exception instanceof WebApplicationException) {
      return ((WebApplicationException) exception).getResponse(); //the response is already prepared
    }
    myLog.logger.warning(exception.getMessage());

    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity(exception.getMessage())
        .build();
  }
}
