package be.vinci.pae.ihm.api;

import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.ucc.ItemUCC;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/item")
public class ItemResource {

  @Inject
  ItemUCC itemUcc;

  /**
   * Get offered items from databased sorted by date_offer or type.
   */
  @GET
  @Path("/getLastOfferedItems")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public List<ItemDTO> getLastOfferedItems() {
    return itemUcc.getLastOfferedItems();
  }

  /**
   * Get a specified item according to its id.
   *
   * @param idItem item's id that we want more details
   */
  @POST
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ItemDTO getItem(@PathParam("id") int idItem) {
    return itemUcc.getItem(idItem);
  }

  /**
   * Get list of given items.
   */
  @GET
  @Path("/getGivenItems")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public List<ItemDTO> getListOfGivenItems() {
    return itemUcc.getGivenItems();
  }

}
