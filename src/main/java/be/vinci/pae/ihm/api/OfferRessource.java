package be.vinci.pae.ihm.api;


import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.business.domain.interfacesdto.OfferDTO;
import be.vinci.pae.business.domain.interfacesdto.TypeDTO;
import be.vinci.pae.business.ucc.ItemUCC;
import be.vinci.pae.business.ucc.OfferUCC;
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
import java.sql.SQLException;

@Path("/offer")
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
    String description = json.get("description").asText();
    System.out.println(description);

    description = description.replaceAll("[;&amp;|`]*","");
    System.out.println(description);

    item.setDescription(description);

    item.setAvailabilities(json.get("availabilities").asText());
    item.setOfferingMember(offeringMember);

    return offerUCC.createOffer(item);
  }


  /**
   * Get a specified item according to its id.
   */

  public int typeExisting(String type) {
    return itemUcc.typeExisting(type);
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



    if (idItem < 1||idMember<1) {
      throw new WebApplicationException("L'id ne peut être négatif");
    }
    return offerUCC.isLiked(idItem,idMember);
  }


}
