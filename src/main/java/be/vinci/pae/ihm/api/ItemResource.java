package be.vinci.pae.ihm.api;

import be.vinci.pae.business.domain.interfacesbusiness.Item;
import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.business.domain.interfacesdto.TypeDTO;
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
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/item")
public class ItemResource {

  @Inject
  ItemUCC itemUcc;
  @Inject
  private DomainFactory domainFactory;


  /**
   * Get offered items from databased sorted by date_offer or type.
   */
  @GET
  @Path("/")
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


  /**
   * Get a specified item according to its id.
   *
   * @param json item's id that we want more details
   */
  @POST
  @Path("/createItem")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ItemDTO createItem(JsonNode json) {
    if (!json.hasNonNull("type") || !json.hasNonNull("description") || !json.hasNonNull(
        "availabilities")
        || !json.hasNonNull("item_condition") || !json.hasNonNull("id_offering_member")) {
      throw new WebApplicationException("Lack of informations", Response.Status.BAD_REQUEST);
    }
    MemberDTO offeringMember = domainFactory.getMember();
    offeringMember.setIdMember(json.get("id_offering_member").asInt());

    ItemDTO item = domainFactory.getItem();
    TypeDTO type = domainFactory.getType();
    type.setType(json.get("type").asText());
    item.setType(type);
    item.setDescription(json.get("description").asText());
    item.setAvailabilities(json.get("availabilities").asText());
    item.setItemCondition(json.get("item_condition").asText());
    item.setOfferingMember(offeringMember);
    Item newItem = (Item) item;
    return itemUcc.createItem(newItem);
  }

  /**
   * like an item
   *
   * @return number of interests
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
   * get the number of interests for an offer
   *
   * @return number of interests
   * @return 1 if ok -1 if ko
   * @POST
   * @Path("interests")
   * @Consumes(MediaType.APPLICATION_JSON)
   * @Produces(MediaType.APPLICATION_JSON) public int interestsOfAnOffer(JsonNode json) { int
   * offerId; offerId = json.get("offerId").asInt(); return itemUcc.interestsOfAnOffer(offerId); }
   * <p>
   * /** cancel an offer
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


}