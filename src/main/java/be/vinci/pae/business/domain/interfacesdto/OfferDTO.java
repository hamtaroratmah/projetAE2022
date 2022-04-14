package be.vinci.pae.business.domain.interfacesdto;

import java.time.LocalDate;

public interface OfferDTO {

  Integer getIdOffer();

  void setIdOffer(Integer idOffer);

  LocalDate getDateOffer();

  void setDateOffer(LocalDate dateOffer);

  Integer getIdItem();

  void setIdItem(Integer idItem);

}
