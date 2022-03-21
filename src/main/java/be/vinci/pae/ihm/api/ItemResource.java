package be.vinci.pae.ihm.api;

import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.ucc.ItemUCC;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
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

}
