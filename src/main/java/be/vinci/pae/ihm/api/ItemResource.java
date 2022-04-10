package be.vinci.pae.ihm.api;

import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.ucc.ItemUCC;
import be.vinci.pae.ihm.api.filters.Authorize;
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
   */
  @GET
  @Path("/getLastOfferedItems")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<ItemDTO> getLastOfferedItems() {
    return itemUcc.getLastOfferedItems();
  }

  /**
   * Get offered items from database for non-connected users.
   */
  @GET
  @Path("/getLastOfferedItemsNonConnected")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public List<ItemDTO> getLastOfferedItemsNonConnected() {
    List<ItemDTO> list = itemUcc.getLastOfferedItems();
    if (list.size() >= 5) {
      return list.subList(0, 5);
    }
    return list;
  }

  /**
   * Get a specified item according to its id.
   *
   * @param idItem item's id that we want more details
   */
  @GET
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ItemDTO getItem(@PathParam("id") int idItem) {
    if (idItem < 1) {
      throw new WebApplicationException("L'id ne peut être négatif");
    }
    return itemUcc.getItem(idItem);
  }

  /**
   * Get list of given items.
   *
   * @return the list
   */
  @GET
  @Path("/getGivenItems")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public List<ItemDTO> getListOfGivenItems() {
    return itemUcc.getGivenItems();
  }


  /**
   * like an item.
   *
   * @param json the json
   * @return number of interests.
   */
  @POST
  @Path("like")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public int likeAnItem(JsonNode json) {
    int offerId;
    int memberId;
    memberId = json.get("memberId").asInt();
    offerId = json.get("offerId").asInt();
    return itemUcc.likeAnItem(offerId, memberId);
  }


  /**
   * cancel an offer.
   *
   * @param json the json
   * @return 1 if ok, -1 if ko.
   */
  @POST
  @Path("cancelOffer")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public int cancelAnOffer(JsonNode json) {
    int itemId;
    itemId = json.get("itemId").asInt();
    return itemUcc.cancelAnOffer(itemId);
  }


  /**
   * Get a specified item according to its id.
   */

  public int typeExisting(String type) {
    return itemUcc.typeExisting(type);
  }


}

