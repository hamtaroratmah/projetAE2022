package be.vinci.pae.business.domain.interfacesdto;

public interface InterestDTO {

  Integer getId_interest();

  ItemDTO getItem();

  MemberDTO getMember();

  boolean isRecipient();

  String getDate_delivery();

  boolean isCame();

  void setId_interest(Integer id_interest);

  void setItem(ItemDTO item);

  void setMember(MemberDTO member);

  void setRecipient(boolean recipient);

  void setDate_delivery(String date_delivery);

  void setCame(boolean came);
}
