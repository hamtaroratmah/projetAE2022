package be.vinci.pae.ihm.api;

import be.vinci.pae.business.ucc.MemberUCC;
import be.vinci.pae.business.ucc.MemberUCCImpl;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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


}

// vérifier si le token est toujours valide ou pas

/* TODO
* éviter le json coté business
* renomé le code :
*   dataservice-> UCC ou service
*   deux interface pour une classe DTO
* remember me
* close preparedStatement, resultSet
* Injection de dépendance
* */