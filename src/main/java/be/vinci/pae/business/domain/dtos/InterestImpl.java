package be.vinci.pae.business.domain.dtos;

import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;

public class InterestImpl implements be.vinci.pae.business.domain.interfacesdto.InterestDTO {

  @JsonView(Views.Public.class)
  private int interestId;
  @JsonView(Views.Public.class)
  private ItemDTO item;
  @JsonView(Views.Public.class)
  private MemberDTO member;
  @JsonView(Views.Public.class)
  private boolean isRecipient;
  @JsonView(Views.Public.class)
  private String dateDelivery;
  @JsonView(Views.Public.class)
  private boolean came;


  /**
   * Class constructor.
   */
  public InterestImpl() {
  }

  @Override
  public int getInterestId() {
    return interestId;
  }

  @Override
  public ItemDTO getItem() {
    return item;
  }

  @Override
  public MemberDTO getMember() {
    return member;
  }

  @Override
  public boolean isRecipient() {
    return isRecipient;
  }

  @Override
  public String getDateDelivery() {
    return dateDelivery;
  }

  @Override
  public boolean isCame() {
    return came;
  }

  @Override
  public void setInterestId(int interestId) {
    this.interestId = interestId;
  }

  @Override
  public void setItem(ItemDTO item) {
    this.item = item;
  }


  @Override
  public void setMember(MemberDTO member) {
    this.member = member;
  }

  @Override
  public void setRecipient(boolean recipient) {
    isRecipient = recipient;
  }

  @Override
  public void setDateDelivery(String dateDelivery) {
    this.dateDelivery = dateDelivery;
  }

  @Override
  public void setCame(boolean came) {
    this.came = came;
  }
}
