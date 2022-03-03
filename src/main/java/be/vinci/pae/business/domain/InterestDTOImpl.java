package be.vinci.pae.business.domain;

import be.vinci.pae.business.domain.interfaces.InterestDTO;

public class InterestDTOImpl implements InterestDTO {

  private int idInterest;
  private int idItem;
  private int idMember;

  /**
   * Class constructor.
   */
  public InterestDTOImpl(int idInterest, int idItem, int idMember) {
    this.idInterest = idInterest;
    this.idItem = idItem;
    this.idMember = idMember;
  }

  @Override
  public int getIdInterest() {
    return idInterest;
  }

  @Override
  public void setIdInterest(int idInterest) {
    this.idInterest = idInterest;
  }

  @Override
  public int getIdItem() {
    return idItem;
  }

  @Override
  public void setIdItem(int idItem) {
    this.idItem = idItem;
  }

  @Override
  public int getIdMember() {
    return idMember;
  }

  @Override
  public void setIdMember(int idMember) {
    this.idMember = idMember;
  }
}
