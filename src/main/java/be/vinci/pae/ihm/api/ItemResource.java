package be.vinci.pae.ihm.api;

import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.ucc.ItemUCC;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/item")
public class ItemResource {

  @Inject
  ItemUCC itemUcc;

  /**
   * Get offered items from databased sorted by date_offer or type.
   *
   * @param json contains the value of typeOrder. If it doesn't contains the value typeOrder' values
   *             will be ASC by default
   */
  @POST
  @Path("")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public List<ItemDTO> getLastOfferedItems(JsonNode json) {
    String typeOrder;
    if (json == null || json.get("typeOrder") == null) {
      typeOrder = "ASC";
    } else {
      typeOrder = json.get("typeOrder").asText();
    }
    return itemUcc.getLastOfferedItems(typeOrder);
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
    if (idItem < 1) {
      throw new WebApplicationException("l'id de l'objet ne peux être négatif");
    }
    ItemDTO item = itemUcc.getItem(idItem);
    if (item == null) {
      throw new WebApplicationException("L'objet désiré n'existe pas");
    }
    return item;
  }

  /**
   * Get list of given items.
   */
  @GET
  @Path("/getGivenItems")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public List<ItemDTO> getListOfGivenItems() {
    List<ItemDTO> items = itemUcc.getGivenItems();
    if (items.isEmpty()) {
      throw new WebApplicationException("Il n'y a aucun objet déjà offert");
    }
    return items;
  }

}
