package be.vinci.pae.business.domain.dtos;

import be.vinci.pae.business.domain.interfacesdto.InterestDTO;
import be.vinci.pae.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;

public class InterestImpl implements InterestDTO {

  @JsonView(Views.Public.class)
  private Integer interestId;
  @JsonView(Views.Public.class)
  private ItemImpl item;
  @JsonView(Views.Public.class)
  private MemberImpl member;
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
  public Integer getInterestId() {
    return interestId;
  }

  @Override
  public ItemImpl getItem() {
    return item;
  }

  @Override
  public MemberImpl getMember() {
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
  public void setInterestId(Integer interestId) {
    this.interestId = interestId;
  }

  @Override
  public void setItem(ItemImpl item) {
    this.item = item;
  }

  @Override
  public void setMember(MemberImpl member) {
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
