package be.vinci.pae.business.domain.interfacesdto;

import be.vinci.pae.business.domain.dtos.ItemImpl;
import be.vinci.pae.business.domain.dtos.MemberImpl;

public interface InterestDTO {

  Integer getInterestId();

  ItemImpl getItem();

  MemberImpl getMember();

  boolean isRecipient();

  String getDateDelivery();

  boolean isCame();

  void setInterestId(Integer interestId);

  void setItem(ItemImpl item);

  void setMember(MemberImpl member);

  void setRecipient(boolean recipient);

  void setDateDelivery(String dateDelivery);

  void setCame(boolean came);
}
