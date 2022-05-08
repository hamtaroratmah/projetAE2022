package be.vinci.pae.ihm.api;

import be.vinci.pae.business.domain.interfacesdto.InterestDTO;
import be.vinci.pae.business.ucc.InterestUCC;
import be.vinci.pae.ihm.api.filters.Authorize;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;

@Path("/interests")
public class InterestRessource {

  @Inject
  InterestUCC interestUcc;
  @Inject
  InterestDTO interestDTO;


  /**
   * Get a specified item according to its id.
   *
   * @param idInterest item's id that we want more details
   */
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public InterestDTO getInterest(@PathParam("id") int idInterest) {
    if (idInterest < 1) {
      throw new WebApplicationException("L'id ne peut être négatif");
    }
    return interestUcc.getInterest(idInterest);
  }

  /**
   * Get all of the interests from an item according to its id.
   *
   * @params idItem
   */
  @POST
  @Path("/listInterests")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public ArrayList<InterestDTO> listInterests(JsonNode json) {
    int idItem = json.get("idItem").asInt();
    if (idItem < 1) {
      throw new WebApplicationException("L'id ne peut être négatif");
    }
    return interestUcc.listInterests(idItem);
  }
}
