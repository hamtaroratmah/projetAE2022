package be.vinci.pae.business.domain.interfacesdto;

import be.vinci.pae.business.domain.dtos.ItemImpl;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = ItemImpl.class)
public interface ItemDTO {

  Integer getIdItem();

  void setIdItem(Integer idItem);

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

  Integer getRating();

  void setRating(Integer rating);

  MemberDTO getOfferingMember();

  void setOfferingMember(MemberDTO offeringMember);

  void setOffer(OfferDTO offer);


}
