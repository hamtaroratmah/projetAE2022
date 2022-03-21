package be.vinci.pae.business.domain.interfacesdto;

import be.vinci.pae.business.domain.dtos.TypeDTO;

public interface ItemDTO {

  int getIdItem();

  void setIdItem(int idItem);

  TypeDTO getType();

  void setType(TypeDTO type);

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

  MemberDTO getOfferingMember();

  void setOfferingMember(MemberDTO offeringMember);
}
