package be.vinci.pae.ihm.api;

import be.vinci.pae.business.domain.interfacesdto.AddressDTO;
import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.business.ucc.MemberUCC;
import be.vinci.pae.ihm.api.filters.Authorize;
import be.vinci.pae.utils.Json;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/members")
public class MemberResource {

  private final Json<MemberDTO> jsonDB = new Json<>(MemberDTO.class);
  @Inject
  private MemberUCC memberUCC;
  @Inject
  private DomainFactory domainFactory;

  /**
   * Get a member according to his token by his id.
   */
  @GET
  @Path("/")
  @Authorize
  @Produces(MediaType.APPLICATION_JSON)
  public MemberDTO getMember(@Context ContainerRequestContext requestContext) {
    MemberDTO member = (MemberDTO) requestContext.getProperty("user");
    if (member == null) {
      throw new WebApplicationException("token required", Response.Status.BAD_REQUEST);
    }
    return jsonDB.filterPublicJsonView(member);
  }

  /**
   * Update member's information.
   */
  @PUT
  @Path("updateMember")
  @Authorize
  @Produces(MediaType.APPLICATION_JSON)
  public MemberDTO updateMember(@Context ContainerRequestContext requestContext,
      JsonNode json) {
    MemberDTO oldMember = (MemberDTO) requestContext.getProperty("user");
    String confirmPassword = json.get("confirmPassword").asText();
    if (!checkNullOrBlank(json)) {
      throw new BadRequestException("Il manque certains champs");
    }
    return memberUCC.updateMember(oldMember, createMember(json), confirmPassword);
  }

  private MemberDTO createMember(JsonNode json) {
    MemberDTO member = domainFactory.getMember();
    member.setIdMember(json.get("idMember").asInt());
    member.setUsername(json.get("username").asText());
    member.setPassword(json.get("password").asText());
    member.setLastName(json.get("lastName").asText());
    member.setFirstName(json.get("firstName").asText());
    member.setCallNumber(json.get("callNumber").asText());
    member.setState(json.get("state").asText());
    AddressDTO address = domainFactory.getAddress();
    address.setIdAddress(json.get("idAddress").asInt());
    address.setStreet(json.get("street").asText());
    address.setBuildingNumber(json.get("buildingNumber").asInt());
    address.setPostcode(json.get("postcode").asInt());
    address.setCity(json.get("city").asText());
    address.setUnitNumber(json.get("unitNumber").asText());
    member.setAddress(address);
    return member;
  }

  private boolean checkNullOrBlank(JsonNode json) {
    return json.hasNonNull("idMember")
        && json.hasNonNull("username")
        && json.hasNonNull("lastName")
        && json.hasNonNull("firstName")
        && json.hasNonNull("callNumber")
        && json.hasNonNull("idAddress")
        && json.hasNonNull("street")
        && json.hasNonNull("buildingNumber")
        && json.hasNonNull("postcode")
        && json.hasNonNull("city")
        && json.hasNonNull("unitNumber")
        && !json.get("idMember").asText().isBlank()
        && !json.get("password").asText().isBlank()
        && !json.get("username").asText().isBlank()
        && !json.get("lastName").asText().isBlank()
        && !json.get("firstName").asText().isBlank()
        && !json.get("idAddress").asText().isBlank()
        && !json.get("street").asText().isBlank()
        && !json.get("buildingNumber").asText().isBlank()
        && !json.get("postcode").asText().isBlank()
        && !json.get("city").asText().isBlank();
  }

  /**
   * Get the state of the member.
   *
   * @return the member state.
   */
  @POST
  @Path("state")
  @Authorize
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String getState(@Context ContainerRequestContext requestContext) {
    MemberDTO member = (MemberDTO) requestContext.getProperty("user");
    if (member == null) {
      throw new WebApplicationException("token required", Response.Status.BAD_REQUEST);
    }
    return member.getState();
  }

  /**
   * confirm a registration.
   *
   * @param json the
   * @return the member confirmed.
   */
  @PUT
  @Path("confirm")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public MemberDTO confirmRegistration(JsonNode json) {
    String username = json.get("username").asText().toLowerCase();
    if (username.isBlank()) {
      throw new WebApplicationException("Veuillez entrer un nom d'utilisateur");
    }
    boolean isAdmin = json.get("isAdmin").asBoolean();
    return jsonDB.filterPublicJsonView(memberUCC.confirmRegistration(username, isAdmin));
  }

  /**
   * deny a registration.
   *
   * @param json the json
   * @return the member denyes.
   */
  @PUT
  @Path("deny")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public MemberDTO denyRegistration(JsonNode json) {
    String username = json.get("username").asText().toLowerCase();
    String reasonForConnRefusal = json.get("reasonForConnRefusal").asText().toLowerCase();
    if (username.isBlank()) {
      throw new WebApplicationException("Veuillez entrer un nom d'utilisateur");
    }
    return jsonDB.filterPublicJsonView(memberUCC.denyRegistration(username,reasonForConnRefusal));
  }

  /**
   * get list of pending members.
   *
   * @return the list
   */
  @GET
  @Path("listPending")
  @Produces(MediaType.APPLICATION_JSON)
  public ArrayList<MemberDTO> listPendingUsers() {
    return (ArrayList<MemberDTO>) jsonDB.filterPublicJsonViewAsList(memberUCC.listPendingUsers());
  }

  /**
   * get the list of denied members.
   *
   * @return the list
   */
  @GET
  @Path("listDenied")
  @Produces(MediaType.APPLICATION_JSON)
  public ArrayList<MemberDTO> listDeniedUsers() {
    return (ArrayList<MemberDTO>) jsonDB.filterPublicJsonViewAsList(memberUCC.listDeniedUsers());
  }
}