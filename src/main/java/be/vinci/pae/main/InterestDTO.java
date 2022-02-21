package be.vinci.pae.main;

public class InterestDTO {

  private int idInterest;
  private int idItem;
  private int idMember;

  public InterestDTO(int idInterest, int idItem, int idMember) {
    this.idInterest = idInterest;
    this.idItem = idItem;
    this.idMember = idMember;
  }

  public int getIdInterest() {
    return idInterest;
  }

  public void setIdInterest(int idInterest) {
    this.idInterest = idInterest;
  }

  public int getIdItem() {
    return idItem;
  }

  public void setIdItem(int idItem) {
    this.idItem = idItem;
  }

  public int getIdMember() {
    return idMember;
  }

  public void setIdMember(int idMember) {
    this.idMember = idMember;
  }
}
