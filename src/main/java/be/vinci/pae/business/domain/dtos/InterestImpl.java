package be.vinci.pae.business.domain.dtos;

import be.vinci.pae.business.domain.interfacesdto.InterestDTO;
import be.vinci.pae.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;

public class InterestImpl implements InterestDTO {

  @JsonView(Views.Public.class)
  private Integer id_interest;
  @JsonView(Views.Public.class)
  private ItemImpl item;
  @JsonView(Views.Public.class)
  private MemberImpl member;
  @JsonView(Views.Public.class)
  private boolean isRecipient;
  @JsonView(Views.Public.class)
  private String date_delivery;
  @JsonView(Views.Public.class)
  private boolean came;


  /**
   * Class constructor.
   */
  public InterestImpl() {
  }

  @Override
  public Integer getId_interest() {
    return id_interest;
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
  public String getDate_delivery() {
    return date_delivery;
  }

  @Override
  public boolean isCame() {
    return came;
  }

  @Override
  public void setId_interest(Integer id_interest) {
    this.id_interest = id_interest;
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
  public void setDate_delivery(String date_delivery) {
    this.date_delivery = date_delivery;
  }

  @Override
  public void setCame(boolean came) {
    this.came = came;
  }
}
