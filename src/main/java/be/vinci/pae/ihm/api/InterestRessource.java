package be.vinci.pae.ihm.api;


import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.InterestDTO;
import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.ucc.InterestUcc;
import be.vinci.pae.dal.interfaces.InterestDao;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;

@Path("/interests")
public class InterestRessource {
  @Inject
  InterestUcc interestUcc;



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

}
