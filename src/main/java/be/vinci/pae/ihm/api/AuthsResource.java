package be.vinci.pae.ihm.api;

import be.vinci.pae.business.domain.dtos.AddressImpl;
import be.vinci.pae.business.domain.interfacesbusiness.Member;
import be.vinci.pae.business.domain.interfacesdto.AddressDTO;
import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.business.ucc.MemberUCC;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auths")
public class AuthsResource {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();
  @Inject
  private MemberUCC memberUCC;
  @Inject
  private DomainFactory domainFactory;

  /**
   * API login.
   *
   * @param json jsonNode created by the request and contains information given by the client
   */
  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String login(JsonNode json) {
    // Get and check credentials
    if (!json.hasNonNull("username") || !json.hasNonNull("password")) {
      throw new WebApplicationException("login or password required", Response.Status.BAD_REQUEST);
    }
    String login = json.get("username").asText().toLowerCase();
    login = login.replace(" ", "");
    String password = json.get("password").asText();
    MemberDTO publicUser = memberUCC.login(login, password);

    String token = createToken(publicUser.getIdMember());
    return token;
  }

  /**
   * API register.
   *
   * @param json jsonNode created by the request and contains information given by the client.
   */
  @POST
  @Path("register")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String register(JsonNode json) {
    if (!json.hasNonNull("username") || !json.hasNonNull("password") || !json.hasNonNull(
            "firstname")
            || !json.hasNonNull("lastname") || !json.hasNonNull("street") || !json.hasNonNull(
            "building_number")
            || !json.hasNonNull("unit_number") || !json.hasNonNull("postcode") || !json.hasNonNull(
            "commune")
            || !json.hasNonNull("city")) {
      throw new WebApplicationException("Lack of informations", Response.Status.BAD_REQUEST);
    }
    // create the Address object of the member
    AddressDTO address = domainFactory.getAddress();
    address.setCity(json.get("city").asText());
    address.setStreet(json.get("street").asText());
    address.setBuildingNumber(json.get("building_number").asInt());
    address.setUnitNumber(json.get("unit_number").asInt());
    address.setPostcode(json.get("postcode").asInt());
    address.setCommune(json.get("commune").asText());
    AddressImpl addressImpl = (AddressImpl) address;
    // create the member
    MemberDTO member = domainFactory.getMember();
    member.setAddress(addressImpl);
    member.setUsername(json.get("username").asText());
    member.setPassword(json.get("password").asText());
    member.setFirstName(json.get("firstname").asText());
    member.setLastName(json.get("lastname").asText());
    Member newMember = (Member) member;
    // create token
    MemberDTO publicUser = memberUCC.register(newMember);
    String token = createToken(publicUser.getIdMember());
    return token;
  }

  private String createToken(int id) { //TODO

    String token;
    try {
      token = JWT.create().withIssuer("auth0")
              .withClaim("id_member", id).sign(this.jwtAlgorithm);
    } catch (Exception e) {
      System.out.println("Unable to create token");
      return null;
    }
    return jsonMapper.createObjectNode()
            .put("token", token)
            .put("id", id).toPrettyString();
  }


}


