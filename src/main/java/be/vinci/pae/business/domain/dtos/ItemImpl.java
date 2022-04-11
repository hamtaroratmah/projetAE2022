package be.vinci.pae.business.domain.dtos;

import be.vinci.pae.business.domain.interfacesbusiness.Item;
import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.business.domain.interfacesdto.OfferDTO;
import be.vinci.pae.business.domain.interfacesdto.TypeDTO;

public class ItemImpl implements ItemDTO, Item {

  private int idItem;
  private TypeDTO type;
  private String photo;
  private String description;
  private String availabilities;
  private String itemCondition;
  private int rating;
  private MemberDTO offeringMember;
  private OfferDTO offer;

  /**
   * Class constructor.
   */
  public ItemImpl() {
  }

  @Override
  public int getIdItem() {
    return idItem;
  }

  @Override
  public void setIdItem(int idItem) {
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
  public int getRating() {
    return rating;
  }

  @Override
  public void setRating(int rating) {
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

  public void tempMethod() {

  }

  public OfferDTO getOffer() {
    return offer;
  }

  public void setOffer(OfferDTO offer) {
    this.offer = offer;
  }
}
