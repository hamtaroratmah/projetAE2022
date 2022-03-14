package be.vinci.pae.business.domain.dtos;

import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;

public class ItemImpl implements ItemDTO {

  private int idItem;
  private TypeDTO type;
  private String photo;
  private String description;
  private String availabilities;
  private String itemCondition;
  private int rating;
  private MemberDTO offeringMember;

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
    if (photo.isBlank()) {
      throw new IllegalArgumentException();
    }
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
    if (itemCondition == null || itemCondition.isBlank()) {
      throw new IllegalArgumentException();
    }
    this.itemCondition = itemCondition;
  }

  @Override
  public int getRating() {
    return rating;
  }

  @Override
  public void setRating(int rating) {
    if (rating <= 0) {
      throw new IllegalArgumentException();
    }
    this.rating = rating;
  }

  @Override
  public MemberDTO getOfferingMember() {
    return offeringMember;
  }

  @Override
  public void setOfferingMember(MemberDTO offeringMember) {
    if (offeringMember == null) {
      throw new IllegalArgumentException();
    }
    this.offeringMember = offeringMember;
  }

  public void tempMethod() {

  }
}
