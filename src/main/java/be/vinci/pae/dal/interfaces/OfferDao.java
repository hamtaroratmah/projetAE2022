package be.vinci.pae.dal.interfaces;

import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.OfferDTO;

public interface OfferDao {

  OfferDTO getOffer(int idOffer);

  OfferDTO createOffer(ItemDTO newItem);

}
