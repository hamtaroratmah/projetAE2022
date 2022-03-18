package be.vinci.pae.ihm.api;

import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.ucc.ItemUCC;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/item")
public class ItemResource {

  @Inject
  ItemUCC itemUcc;

  /**
   * Get last offered items from databased.
   *
   * @return
   */
  @GET
  @Path("")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public List<ItemDTO> getLastOfferedItems() {
    return itemUcc.getLastOfferedItems();
  }

}
