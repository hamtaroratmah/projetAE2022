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
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
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
    if (login.isBlank()) {
      throw new WebApplicationException("Veuillez entrer un nom d'utilisateur");
    }

    String password = json.get("password").asText();
    MemberDTO publicUser = memberUCC.login(login, password);

    return createToken(publicUser.getIdMember());
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
            "firstName")
            || !json.hasNonNull("lastName") || !json.hasNonNull("street") || !json.hasNonNull(
            "buildingNumber")
            || !json.hasNonNull("unitNumber") || !json.hasNonNull("postcode")
            || !json.hasNonNull("city")) {
      throw new WebApplicationException("Lack of informations", Response.Status.BAD_REQUEST);
    }
    if (json.get("username").asText().isBlank()) {
      throw new WebApplicationException("Le pseudo ne peut être vide", Response.Status.BAD_REQUEST);
    }
    if (json.get("password").asText().isBlank()) {
      throw new WebApplicationException("Le mot de passe ne peut être vide",
          Response.Status.BAD_REQUEST);
    }
    if (json.get("firstName").asText().isBlank()) {
      throw new WebApplicationException("Le prénom ne peut être vide", Response.Status.BAD_REQUEST);
    }
    if (json.get("lastName").asText().isBlank()) {
      throw new WebApplicationException("Le nom ne peut être vide", Response.Status.BAD_REQUEST);
    }
    if (json.get("street").asText().isBlank()) {
      throw new WebApplicationException("La rue ne peut être vide", Response.Status.BAD_REQUEST);
    }
    if (json.get("buildingNumber").asText().isBlank()) {
      throw new WebApplicationException("Le numéro de maison ne peut être vide",
          Response.Status.BAD_REQUEST);
    }
    if (json.get("unitNumber").asText().isBlank()) {
      throw new WebApplicationException("Le numéro de maison ne peut être vide",
              Response.Status.BAD_REQUEST);
    }
    if (json.get("postcode").asText().isBlank()) {
      throw new WebApplicationException("Le code postale ne peut être vide",
          Response.Status.BAD_REQUEST);
    }
    if (json.get("city").asText().isBlank()) {
      throw new WebApplicationException("La ville ne peut être vide", Response.Status.BAD_REQUEST);
    }

    // create the Address object of the member
    AddressDTO address = domainFactory.getAddress();
    address.setCity(json.get("city").asText());
    address.setStreet(json.get("street").asText());
    address.setBuildingNumber(json.get("buildingNumber").asInt());
    address.setUnitNumber(json.get("unitNumber").asInt());
    address.setPostcode(json.get("postcode").asInt());
    AddressImpl addressImpl = (AddressImpl) address;
    // create the member
    MemberDTO member = domainFactory.getMember();
    member.setAddress(addressImpl);
    member.setUsername(json.get("username").asText().toLowerCase().replace(" ", ""));
    member.setPassword(json.get("password").asText());
    member.setFirstName(json.get("firstName").asText());
    member.setLastName(json.get("lastName").asText());
    System.out.println(member.getCallNumber());
    Member newMember = (Member) member;
    // create token
    MemberDTO publicUser = memberUCC.register(newMember);
    System.out.print(publicUser);
    return createToken(publicUser.getIdMember());
  }

  private String createToken(int id) {
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


