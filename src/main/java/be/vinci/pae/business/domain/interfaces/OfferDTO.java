package be.vinci.pae.business.domain.interfaces;

import java.time.LocalDateTime;

public interface OfferDTO {

  int getIdOffer();

  void setIdOffer(int idOffer);

  LocalDateTime getDateOffer();

  void setDateOffer(LocalDateTime date0ffer);

  int getIdItem();

  void setIdItem(int idItem);

  int getIdRecipientMember();

  void setIdRecipientMember(int idRecipientMember);
}
