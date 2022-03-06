package be.vinci.pae.business.domain.dtos;

import be.vinci.pae.business.domain.interfaces.OfferDTO;
import java.time.LocalDateTime;

public class OfferDTOImpl implements OfferDTO {

  private int idOffer;
  private LocalDateTime dateOffer;
  private int idItem;
  private int idRecipientMember;

  /**
   * Class constructor.
   */
  public OfferDTOImpl(int idOffer, LocalDateTime dateOffer, int idItem, int idRecipientMember) {
    this.idOffer = idOffer;
    this.dateOffer = dateOffer;
    this.idItem = idItem;
    this.idRecipientMember = idRecipientMember;
  }

  @Override
  public int getIdOffer() {
    return idOffer;
  }

  @Override
  public void setIdOffer(int idOffer) {
    this.idOffer = idOffer;
  }

  @Override
  public LocalDateTime getDateOffer() {
    return dateOffer;
  }

  @Override
  public void setDateOffer(LocalDateTime date0ffer) {
    this.dateOffer = dateOffer;
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
  public int getIdRecipientMember() {
    return idRecipientMember;
  }

  @Override
  public void setIdRecipientMember(int idRecipientMember) {
    this.idRecipientMember = idRecipientMember;
  }
}
