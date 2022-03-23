package be.vinci.pae.business.domain.dtos;

import be.vinci.pae.business.domain.interfacesdto.OfferDTO;
import java.time.LocalDate;

public class OfferImpl implements OfferDTO {

  private int idOffer;
  private LocalDate dateOffer;
  private int idItem;

  /**
   * Class constructor.
   */
  public OfferImpl() {
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
  public LocalDate getDateOffer() {
    return dateOffer;
  }

  @Override
  public void setDateOffer(LocalDate dateOffer) {
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
  public String toString() {
    return "OfferImpl{" + "idOffer=" + idOffer + ", dateOffer=" + dateOffer.toString() + ", idItem="
        + idItem + '}';
  }
}
