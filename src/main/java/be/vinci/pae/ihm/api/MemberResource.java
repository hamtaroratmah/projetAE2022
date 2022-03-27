package be.vinci.pae.ihm.api;

import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.business.ucc.MemberUCC;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/member")
public class MemberResource {


  @Inject
  private MemberUCC memberUCC;
  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final JWTVerifier jwtVerifier = JWT.require(this.jwtAlgorithm).withIssuer("auth0")
      .build();

  /**
   * Get a member according to his token by his id.
   *
   * @param jsonNode jsonNode created by the request and contains information given by the client
   */
  @POST
  @Path("getMember")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String getMember(JsonNode jsonNode) {
    if (jsonNode.get("token") == null) {
      throw new WebApplicationException("token required", Response.Status.BAD_REQUEST);
    }
    String token = jsonNode.get("token").asText();
    String[] tokenSplit = token.split("\"");
    DecodedJWT decodedToken = this.jwtVerifier.verify(tokenSplit[3]);
    return memberUCC.getOne(JWT.decode(decodedToken.getToken()).getClaim("id_member").asInt())
        .getUsername();
  }

  /**
   * get a memmber state
   *
   * @param json
   * @return the member state
   */
  @POST
  @Path("state")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String getState(JsonNode json) {
    String username = json.get("username").asText().toLowerCase();
    return memberUCC.getState(username);
  }

  /**
   * confirm a registration
   *
   * @param json
   * @return the member confirmed
   */
  @POST
  @Path("confirm")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public MemberDTO confirmRegistration(JsonNode json) {
    String username = json.get("username").asText().toLowerCase();
    boolean isAdmin = json.get("isAdmin").asBoolean();
    System.out.println(username);
    System.out.println("test de confirm");
    return memberUCC.confirmRegistration(username, isAdmin);
  }

  /**
   * deny a registration
   *
   * @param json
   * @return the member denyes
   */
  @POST
  @Path("deny")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public MemberDTO denyRegistration(JsonNode json) {
    String username = json.get("username").asText().toLowerCase();
    System.out.println(username);
    System.out.println("test de deny");
    return memberUCC.denyRegistration(username);
  }

  /**
   * get list of pending members
   *
   * @return the list
   */
  @GET
  @Path("pending")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ArrayList<MemberDTO> listPendingUsers() {
    System.out.println("lister les utilisateur donc l inscription est en attente");
    return memberUCC.listPendingUsers();
  }

  /**
   * get the list of denied members
   *
   * @return the list
   */
  @GET
  @Path("denied")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ArrayList<MemberDTO> listDeniedUsers() {
    System.out.println("lister les utilisateur donc l inscription est refusee");
    return memberUCC.listDeniedUsers();
  }


}