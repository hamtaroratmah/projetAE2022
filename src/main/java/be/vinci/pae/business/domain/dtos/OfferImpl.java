package be.vinci.pae.business.domain.dtos;

import be.vinci.pae.business.domain.interfacesdto.OfferDTO;
import be.vinci.pae.utils.Views;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;
import java.time.LocalDate;

@JsonInclude(Include.NON_NULL)
public class OfferImpl implements OfferDTO {

  @JsonView(Views.Public.class)
  private Integer idOffer;
  @JsonView(Views.Public.class)
  private LocalDate dateOffer;
  @JsonView(Views.Public.class)
  private Integer idItem;

  /**
   * Class constructor.
   */
  public OfferImpl() {
  }

  @Override
  public Integer getIdOffer() {
    return idOffer;
  }

  @Override
  public void setIdOffer(Integer idOffer) {
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
  public Integer getIdItem() {
    return idItem;
  }

  @Override
  public void setIdItem(Integer idItem) {
    this.idItem = idItem;
  }

  @Override
  public String toString() {
    return "OfferImpl{" + "idOffer=" + idOffer + ", dateOffer=" + dateOffer.toString() + ", idItem="
        + idItem + '}';
  }
}
