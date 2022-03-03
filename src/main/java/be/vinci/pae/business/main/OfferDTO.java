package be.vinci.pae.main;

import java.time.LocalDateTime;

public class OfferDTO {

  private int idOffer;
  private LocalDateTime dateOffer;
  private int idItem;
  private int idRecipientMember;

  /**
   * Class constructor.
   */
  public OfferDTO(int idOffer, LocalDateTime dateOffer, int idItem, int idRecipientMember) {
    this.idOffer = idOffer;
    this.dateOffer = dateOffer;
    this.idItem = idItem;
    this.idRecipientMember = idRecipientMember;
  }

  public int getIdOffer() {
    return idOffer;
  }

  public void setIdOffer(int idOffer) {
    this.idOffer = idOffer;
  }

  public LocalDateTime getDateOffer() {
    return dateOffer;
  }

  public void setDateOffer(LocalDateTime date0ffer) {
    this.dateOffer = dateOffer;
  }

  public int getIdItem() {
    return idItem;
  }

  public void setIdItem(int idItem) {
    this.idItem = idItem;
  }

  public int getIdRecipientMember() {
    return idRecipientMember;
  }

  public void setIdRecipientMember(int idRecipientMember) {
    this.idRecipientMember = idRecipientMember;
  }
}
