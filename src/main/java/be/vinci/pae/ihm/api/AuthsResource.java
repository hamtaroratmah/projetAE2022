package be.vinci.pae.ihm.api;

import be.vinci.pae.business.services.MemberDataService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/login")
public class AuthsResource {

  /**
   * API login.
   *
   * @param json jsonNode created by the request and contains information given by the client
   */
  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode login(JsonNode json) {

    System.out.println(json.hasNonNull("username"));
    System.out.println(json.hasNonNull("password"));
    // Get and check credentials
    if (!json.hasNonNull("username") || !json.hasNonNull("password")) {
      throw new WebApplicationException("login or password required", Response.Status.BAD_REQUEST);
    }
    String login = json.get("username").asText().toLowerCase();
    String password = json.get("password").asText();
    //TODO use factory as soon as it's made
    MemberDataService memberDataService = new MemberDataService();
    ObjectNode publicUser = memberDataService.login(login, password);
    if (publicUser == null) {
      throw new WebApplicationException("Password incorrect",
          Response.Status.UNAUTHORIZED);
    }
    return publicUser;
  }
}
