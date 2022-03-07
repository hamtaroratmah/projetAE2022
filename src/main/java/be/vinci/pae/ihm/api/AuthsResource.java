package be.vinci.pae.ihm.api;

import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.business.ucc.MemberUCC;
import be.vinci.pae.business.ucc.MemberUCCImpl;
import be.vinci.pae.business.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.Date;

@Path("/login")
public class AuthsResource {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();
  @Inject
  private MemberUCC memberUCC = new MemberUCCImpl();

  /**
   * API login.
   *
   * @param json jsonNode created by the request and contains information given by the client
   */
  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public MemberDTO login(JsonNode json) {
    // Get and check credentials
    if (!json.hasNonNull("username") || !json.hasNonNull("password")) {
      throw new WebApplicationException("login or password required", Response.Status.BAD_REQUEST);
    }
    String login = json.get("username").asText().toLowerCase();
    String password = json.get("password").asText();
    MemberDTO publicUser = memberUCC.login(login, password);

    ObjectNode token = createToken(publicUser.getIdMember());
    //TODO
    if (token == null) {
      throw new WebApplicationException("Password incorrect",
          Response.Status.UNAUTHORIZED);
    }
    return publicUser;
  }

  private ObjectNode createToken(int id) { //TODO

    String token;
    Date expirationDate = new Date(LocalDate.now().getDayOfYear() + 30);
    try {
      token = JWT.create().withExpiresAt(expirationDate).withIssuer("auth0")
          .withClaim("member", id).sign(this.jwtAlgorithm);
    } catch (Exception e) {
      System.out.println("Unable to create token");
      return null;
    }
    return jsonMapper.createObjectNode()
        .put("token", token)
        .put("id", id);
  }
}


