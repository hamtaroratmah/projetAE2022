package be.vinci.pae.main;

public class ItemDTO {

  private int idItem;
  private int idType;
  private String photo;
  private String description;
  private String availabilities;
  private String itemCondition;
  private int rating;
  private int idOfferingMember;

  /**
   * Constructor
   */
  public ItemDTO(int idItem, int idType, String photo, String description,
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

  public int getIdItem() {
    return idItem;
  }

  public void setIdItem(int idItem) {
    this.idItem = idItem;
  }

  public int getIdType() {
    return idType;
  }

  public void setIdType(int idType) {
    this.idType = idType;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAvailabilities() {
    return availabilities;
  }

  public void setAvailabilities(String availabilities) {
    this.availabilities = availabilities;
  }

  public String getItemCondition() {
    return itemCondition;
  }

  public void setItemCondition(String itemCondition) {
    this.itemCondition = itemCondition;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public int getIdOfferingMember() {
    return idOfferingMember;
  }

  public void setIdOfferingMember(int idOfferingMember) {
    this.idOfferingMember = idOfferingMember;
  }
}
