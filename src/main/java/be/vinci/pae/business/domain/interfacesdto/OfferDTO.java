package be.vinci.pae.business.domain.interfacesdto;

import be.vinci.pae.business.domain.dtos.OfferImpl;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.LocalDate;

@JsonDeserialize(as = OfferImpl.class)

public interface OfferDTO {

  Integer getIdOffer();

  void setIdOffer(Integer idOffer);

  LocalDate getDateOffer();

  void setDateOffer(LocalDate dateOffer);

  Integer getIdItem();

  void setIdItem(Integer idItem);

}
