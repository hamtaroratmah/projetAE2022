package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.OfferDTO;

public interface OfferUCC {

  OfferDTO getOffer(int idOffer);

  OfferDTO createOffer(ItemDTO item);

  boolean isLiked(int idItem, int idMember);
}
