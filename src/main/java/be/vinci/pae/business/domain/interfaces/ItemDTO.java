package be.vinci.pae.business.domain.interfaces;

public interface ItemDTO {

  int getIdItem();

  void setIdItem(int idItem);

  int getIdType();

  void setIdType(int idType);

  String getPhoto();

  void setPhoto(String photo);

  String getDescription();

  void setDescription(String description);

  String getAvailabilities();

  void setAvailabilities(String availabilities);

  String getItemCondition();

  void setItemCondition(String itemCondition);

  int getRating();

  void setRating(int rating);

  int getIdOfferingMember();

  void setIdOfferingMember(int idOfferingMember);
}
