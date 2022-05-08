package be.vinci.pae.business.domain.interfacesdto;

public interface InterestDTO {

  int getInterestId();

  ItemDTO getItem();

  MemberDTO getMember();

  boolean isRecipient();

  String getDateDelivery();

  boolean isCame();

  void setInterestId(int interestId);

  void setItem(ItemDTO item);

  void setMember(MemberDTO member);

  void setRecipient(boolean recipient);

  void setDateDelivery(String dateDelivery);

  void setCame(boolean came);
}
