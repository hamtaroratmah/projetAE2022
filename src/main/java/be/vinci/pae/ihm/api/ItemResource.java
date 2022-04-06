package be.vinci.pae.ihm.api;

import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.business.domain.interfacesdto.TypeDTO;
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
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import org.glassfish.jersey.server.ContainerRequest;

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
  @Path("/getLastOfferedItems")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<ItemDTO> getLastOfferedItems(@Context ContainerRequest request) {
    MemberDTO member = (MemberDTO) request.getProperty("user");

    List<ItemDTO> list = itemUcc.getLastOfferedItems();
    if (member == null && list.size() > 12) {
      return list.subList(0, 9);
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
   * Get a specified item according to its id.
   *
   * @param json item's id that we want more details
   * @return the itemDTO
   */
  @POST
  @Path("/createItem")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ItemDTO createItem(JsonNode json) throws SQLException {

    if (!json.hasNonNull("type")
        || !json.hasNonNull("description")
        || !json.hasNonNull(
        "availabilities")
        || !json.hasNonNull("itemCondition")
        || !json.hasNonNull("idOfferingMember")) {
      throw new WebApplicationException("Lack of informations", Response.Status.BAD_REQUEST);
    }
    MemberDTO offeringMember = domainFactory.getMember();
    if (json.get("idOfferingMember").asInt() < 1) {
      throw new WebApplicationException("L'id ne peut être négatif");
    }
    offeringMember.setIdMember(json.get("idOfferingMember").asInt());
    TypeDTO type = domainFactory.getType();
    String typeText = json.get("type").asText();
    type.setType(typeText);
    int idType = typeExisting(type.getType());
    System.out.print(idType);

    //si le type n existe pas , le creer
    if (idType == -1) {
      System.out.print("ko1");

      idType = itemUcc.createType(json.get("type").asText());
    }
    System.out.print("ok2");
    ItemDTO item = domainFactory.getItem();
    type.setIdType(idType);
    item.setType(type);
    item.setDescription(json.get("description").asText());
    item.setAvailabilities(json.get("availabilities").asText());
    item.setItemCondition(json.get("itemCondition").asText());
    item.setOfferingMember(offeringMember);

    return itemUcc.createItem(item);
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

