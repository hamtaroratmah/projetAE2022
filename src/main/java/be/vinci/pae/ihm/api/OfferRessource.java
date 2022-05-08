package be.vinci.pae.ihm.api;


import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.InterestDTO;
import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.business.domain.interfacesdto.OfferDTO;
import be.vinci.pae.business.domain.interfacesdto.TypeDTO;
import be.vinci.pae.business.ucc.ItemUCC;
import be.vinci.pae.business.ucc.OfferUCC;
import be.vinci.pae.ihm.api.filters.Authorize;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/offers")
public class OfferRessource {

  @Inject
  OfferUCC offerUCC;
  @Inject
  ItemUCC itemUcc;
  @Inject
  private DomainFactory domainFactory;

  /**
   * Get a specified item according to its id.
   *
   * @param json item's id that we want more details
   * @return the itemDTO
   */
  @POST
  @Path("/createOffer")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public OfferDTO createOffer(JsonNode json) throws SQLException {

    if (!json.hasNonNull("type")
        || !json.hasNonNull("description")
        || !json.hasNonNull(
        "availabilities")
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
    int idType = itemUcc.typeExisting(type.getType());
    //si le type n existe pas , le creer
    if (idType == -1) {
      idType = itemUcc.createType(json.get("type").asText());
    }
    ItemDTO item = domainFactory.getItem();
    item.setType(type);
    type.setIdType(idType);
    String description = json.get("description").asText();
    description = description.replaceAll("[;&amp;|`]*", "");
    item.setDescription(description);
    item.setAvailabilities(json.get("availabilities").asText());
    item.setOfferingMember(offeringMember);

    return offerUCC.createOffer(item);
  }

  /**
   * Get a specified item according to its id.
   *
   * @params idItem, idMember item's id that we want more details
   */
  @POST
  @Path("/isLiked")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public boolean isLiked(JsonNode json) {
    int idItem = json.get("idItem").asInt();
    int idMember = json.get("idMember").asInt();

    if (idItem < 1 || idMember < 1) {
      throw new WebApplicationException("L'id ne peut être négatif");
    }
    return offerUCC.isLiked(idItem, idMember);
  }

  /**
   * Get all of the interests from an item according to its id.
   *
   * @params idItem, idMember item's id that we want more details
   */
  @POST
  @Path("/interests")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public ArrayList<MemberDTO> interests(JsonNode json) {
    int idItem = json.get("idItem").asInt();
    if (idItem < 1) {
      throw new WebApplicationException("L'id ne peut être négatif");
    }
    return offerUCC.interests(idItem);
  }

  /**
   * cancel an offer.
   *
   * @params idItem, item that we want to cancel
   */
  @POST
  @Path("/cancel")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public boolean cancel(JsonNode json) {
    int idItem = json.get("idItem").asInt();

    if (idItem < 1) {
      throw new WebApplicationException("L'id ne peut être négatif");
    }
    return offerUCC.cancel(idItem);
  }

  /**
   * modify an offer.
   *
   * @params idItem, item that we want to modify
   */
  @POST
  @Path("/modify")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ItemDTO modify(JsonNode json) {
    TypeDTO type = domainFactory.getType();
    int idOffer = json.get("idOffer").asInt();
    String typeText = json.get("type").asText();

    type.setType(typeText);
    int idType = itemUcc.typeExisting(type.getType());
    //si le type n existe pas , le creer
    if (idType == -1) {

      idType = itemUcc.createType(json.get("type").asText());
    }

    String photo = json.get("photo").asText();
    String description = json.get("description").asText();
    String avalaibilities = json.get("availabilities").asText();
    if (idOffer < 1) {
      throw new WebApplicationException("L'id ne peut être négatif");
    }
    return offerUCC.modify(idOffer, idType, photo, description, avalaibilities);
  }

  /**
   * Choose a member to give the item.
   *
   * @params idItem, idMember that we want to offer
   */
  @POST
  @Path("/offer")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public boolean offer(JsonNode json) {
    int idOffer = json.get("idOffer").asInt();
    int idMember = json.get("idMember").asInt();

    if (idOffer < 1 || idMember < 1) {
      throw new WebApplicationException("L'id ne peut être négatif");
    }
    return offerUCC.offer(idOffer, idMember);
  }
}
