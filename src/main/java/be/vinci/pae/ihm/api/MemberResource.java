package be.vinci.pae.ihm.api;

import be.vinci.pae.business.ucc.MemberUCC;
import be.vinci.pae.business.ucc.MemberUCCImpl;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.ws.rs.*;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Locale;

@Path("/member")
public class MemberResource {
    private MemberUCC memberUCC = new MemberUCCImpl();

    @POST
    @Path("confirm")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean confirmInscription(JsonNode json){
       String login = json.get("username").asText().toLowerCase();
       String state = memberUCC.getState(login);
       if(state=="confirmed"){
           throw new WebApplicationException("this user is already confirmed", Response.Status.BAD_REQUEST);

       }

        return false;
    }


    @GET
    @Path("state")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getState(JsonNode json) {
        String username = json.get("username").asText().toLowerCase();
        return memberUCC.getState(username);
    }



  /**
   * API login.
   *
   * @param json jsonNode created by the request and contains information given by the client
   */
  @GET
  @Path("")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public MemberDTO getMember(JsonNode json) {
    if (!json.hasNonNull("token")) {
      throw new WebApplicationException("token required", Response.Status.BAD_REQUEST);
    }
    String token = json.get("token").asText();
    return null;
  }


}