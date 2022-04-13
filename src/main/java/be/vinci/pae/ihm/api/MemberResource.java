package be.vinci.pae.ihm.api;

import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.business.ucc.MemberUCC;
import be.vinci.pae.ihm.api.filters.Authorize;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
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


  @Inject
  private MemberUCC memberUCC;

  /**
   * Get a member according to his token by his id.
   */
  @GET
  @Path("/")
  @Authorize
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public MemberDTO getMember(@Context ContainerRequestContext requestContext) {
    MemberDTO member = (MemberDTO) requestContext.getProperty("user");
    if (member == null) {
      throw new WebApplicationException("token required", Response.Status.BAD_REQUEST);
    }
    return member;
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
   * @param json the json
   * @return the member confirmed.
   */
  @POST
  @Path("confirm")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public MemberDTO confirmRegistration(JsonNode json) {
    String username = json.get("username").asText().toLowerCase();
    if (username.isBlank()) {
      throw new WebApplicationException("Veuillez entrer un nom d'utilisateur");
    }
    System.out.println(username);
    System.out.println("test de confirm");
    boolean isAdmin = json.get("isAdmin").asBoolean();
    return memberUCC.confirmRegistration(username, isAdmin);
  }

  /**
   * deny a registration.
   *
   * @param json the json
   * @return the member denyes.
   */
  @POST
  @Path("deny")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public MemberDTO denyRegistration(JsonNode json) {
    String username = json.get("username").asText().toLowerCase();
    if (username.isBlank()) {
      throw new WebApplicationException("Veuillez entrer un nom d'utilisateur");
    }

    System.out.println(username);
    System.out.println("test de deny");
    return memberUCC.denyRegistration(username);
  }

  /**
   * get list of pending members.
   *
   * @return the list
   */
  @GET
  @Path("list")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ArrayList<MemberDTO> listUsersByState(JsonNode json) {
    System.out.println("lister les utilisateur donc l inscription est en attente");
    String state = json.get("state").asText();
    return memberUCC.listUsersByState(state);
  }

  /**
   * get the list of denied members.
   *
   * @return the list
   */
  @GET
  @Path("listDenied")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ArrayList<MemberDTO> listDeniedUsers() {
    System.out.println("lister les utilisateur donc l inscription est refusee");
    return memberUCC.listDeniedUsers();
  }

  /**
   * get the list of pending members.
   *
   * @return the list
   */
  @GET
  @Path("listPending")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ArrayList<MemberDTO> listPendingUsers() {
    System.out.println("lister les utilisateur dont l inscription est en attente");
    return memberUCC.listPendingUsers();
  }

}