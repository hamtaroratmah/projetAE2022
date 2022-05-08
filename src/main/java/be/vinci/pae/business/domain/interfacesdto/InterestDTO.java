package be.vinci.pae.business.domain.interfacesdto;

import be.vinci.pae.business.domain.dtos.ItemImpl;
import be.vinci.pae.business.domain.dtos.MemberImpl;

public interface InterestDTO {

  Integer getId_interest();

  ItemImpl getItem();

  MemberImpl getMember();

  boolean isRecipient();

  String getDate_delivery();

  boolean isCame();

  void setId_interest(Integer id_interest);

  void setItem(ItemImpl item);

  void setMember(MemberImpl member);

  void setRecipient(boolean recipient);

  void setDate_delivery(String date_delivery);

  void setCame(boolean came);
}
