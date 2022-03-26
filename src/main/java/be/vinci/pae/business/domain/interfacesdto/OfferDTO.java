package be.vinci.pae.business.domain.interfacesdto;

import java.time.LocalDate;

public interface OfferDTO {

  int getIdOffer();

  void setIdOffer(int idOffer);

  LocalDate getDateOffer();

  void setDateOffer(LocalDate dateOffer);

  int getIdItem();

  void setIdItem(int idItem);

}
