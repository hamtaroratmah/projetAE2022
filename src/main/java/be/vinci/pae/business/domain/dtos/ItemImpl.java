package be.vinci.pae.business.domain.dtos;

import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.business.domain.interfacesdto.OfferDTO;
import be.vinci.pae.business.domain.interfacesdto.TypeDTO;
import be.vinci.pae.utils.Views;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

@JsonInclude(Include.NON_NULL)
public class ItemImpl implements ItemDTO {

  @JsonView(Views.Public.class)
  private Integer idItem;
  @JsonView(Views.Public.class)
  private TypeDTO type;
  @JsonView(Views.Public.class)
  private String photo;
  @JsonView(Views.Public.class)
  private String description;
  @JsonView(Views.Public.class)
  private String availabilities;
  @JsonView(Views.Public.class)
  private String itemCondition;
  @JsonView(Views.Public.class)
  private Integer rating;
  @JsonView(Views.Public.class)
  private MemberDTO offeringMember;
  @JsonView(Views.Public.class)
  private OfferDTO offer;

  /**
   * Class constructor.
   */
  public ItemImpl() {
  }

  @Override
  public Integer getIdItem() {
    return idItem;
  }

  @Override
  public void setIdItem(Integer idItem) {
    this.idItem = idItem;
  }

  @Override
  public TypeDTO getType() {
    return type;
  }

  @Override
  public void setType(TypeDTO type) {
    if (type == null) {
      throw new IllegalArgumentException();
    }
    this.type = type;
  }

  @Override
  public String getPhoto() {
    return photo;
  }

  @Override
  public void setPhoto(String photo) {
    this.photo = photo;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public void setDescription(String description) {
    if (description == null || description.isBlank()) {
      throw new IllegalArgumentException();
    }
    this.description = description;
  }

  @Override
  public String getAvailabilities() {
    return availabilities;
  }

  @Override
  public void setAvailabilities(String availabilities) {
    if (availabilities == null || availabilities.isBlank()) {
      throw new IllegalArgumentException();
    }
    this.availabilities = availabilities;
  }

  @Override
  public String getItemCondition() {
    return itemCondition;
  }

  @Override
  public void setItemCondition(String itemCondition) {
    this.itemCondition = itemCondition;
  }

  @Override
  public Integer getRating() {
    return rating;
  }

  @Override
  public void setRating(Integer rating) {
    this.rating = rating;
  }

  @Override
  public MemberDTO getOfferingMember() {
    return offeringMember;
  }

  @Override
  public void setOfferingMember(MemberDTO offeringMember) {
    this.offeringMember = offeringMember;
  }

  public OfferDTO getOffer() {
    return offer;
  }

  public void setOffer(OfferDTO offer) {
    this.offer = offer;
  }
}
