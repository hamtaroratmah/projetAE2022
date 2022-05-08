package be.vinci.pae.business.domain.interfacesdto;

public interface InterestDTO {

  Integer getInterestId();

  ItemDTO getItem();

  MemberDTO getMember();

  boolean isRecipient();

  String getDateDelivery();

  boolean isCame();

  void setInterestId(Integer interestId);

  void setItem(ItemDTO item);

  void setMember(MemberDTO member);

  void setRecipient(boolean recipient);

  void setDateDelivery(String dateDelivery);

  void setCame(boolean came);
}
