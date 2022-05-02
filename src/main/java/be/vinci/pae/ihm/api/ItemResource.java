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
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Path("/items")
public class ItemResource {

  @Inject
  ItemUCC itemUcc;
  @Inject
  DomainFactory domainFactory;

  /**
   * Get offered items from databased sorted by date_offer or type.
   */
  @GET
  @Path("/getItemSortedBy")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<ItemDTO> getItemSortedBy(
      @DefaultValue("date_offer")
      @QueryParam("sortingParam") String sortingParam,
      @DefaultValue("ASC")
      @QueryParam("order") String order) {
    return itemUcc.getItemSortedBy(sortingParam, order);
  }

  /**
   * Get offered items from database for non-connected users.x
   */
  @GET
  @Path("/getLastOfferedItemsNonConnected")
  @Produces(MediaType.APPLICATION_JSON)
  public List<ItemDTO> getLastOfferedItemsNonConnected() {
    List<ItemDTO> list = itemUcc.getItemSortedBy("date_offer", "DESC");
    if (list.size() >= 4) {
      return list.subList(0, 2);
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
    if (json.get("idOfferingMember").asInt() < 1) {
      throw new WebApplicationException("L'id ne peut être négatif");
    }
    MemberDTO offeringMember = domainFactory.getMember();
    offeringMember.setIdMember(json.get("idOfferingMember").asInt());
    TypeDTO type = domainFactory.getType();
    String typeText = json.get("type").asText();
    type.setType(typeText);
    int idType = itemUcc.typeExisting(type.getType());
    //si le type n'existe pas, le créer
    if (idType == -1) {
      idType = itemUcc.createType(json.get("type").asText());
    }
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
    int idItem;
    int idMember;
    idMember = json.get("idMember").asInt();
    idItem = json.get("idItem").asInt();
    return itemUcc.likeAnItem(idItem, idMember);
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
  public int cancelAnOffer(JsonNode json) throws IOException {
    int itemId;
    itemId = json.get("itemId").asInt();
    return itemUcc.cancelAnOffer(itemId);
  }

  @POST
  @Path("/upload")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public Response uploadFile(@FormDataParam("file") InputStream file,
                             @FormDataParam("file") FormDataContentDisposition fileDisposition) {
    String fileName = UUID.randomUUID().toString();
   // Files.copy(file, Paths.get(fileName));
    return Response.ok().build();
  }




}

