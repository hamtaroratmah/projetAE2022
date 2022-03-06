package be.vinci.pae.business.domain.dtos;

import be.vinci.pae.business.domain.interfaces.ItemDTO;

public class ItemDTOImpl implements ItemDTO {

  private int idItem;
  private int idType;
  private String photo;
  private String description;
  private String availabilities;
  private String itemCondition;
  private int rating;
  private int idOfferingMember;

  /**
   * Class constructor.
   */
  public ItemDTOImpl(int idItem, int idType, String photo, String description,
      String availabilities, String itemCondition, int rating, int idOfferingMember) {
    this.idItem = idItem;
    this.idType = idType;
    this.photo = photo;
    this.description = description;
    this.availabilities = availabilities;
    if (!itemCondition.equals("published") || !itemCondition.equals("interest shown")
        || !itemCondition.equals("assigned") || !itemCondition.equals("given")
        || !itemCondition.equals("cancelled")) {
      throw new IllegalArgumentException();
    }
    this.itemCondition = itemCondition;
    this.rating = rating;
    this.idOfferingMember = idOfferingMember;
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
  public int getIdType() {
    return idType;
  }

  @Override
  public void setIdType(int idType) {
    this.idType = idType;
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
    this.description = description;
  }

  @Override
  public String getAvailabilities() {
    return availabilities;
  }

  @Override
  public void setAvailabilities(String availabilities) {
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
  public int getIdOfferingMember() {
    return idOfferingMember;
  }

  @Override
  public void setIdOfferingMember(int idOfferingMember) {
    this.idOfferingMember = idOfferingMember;
  }
}
