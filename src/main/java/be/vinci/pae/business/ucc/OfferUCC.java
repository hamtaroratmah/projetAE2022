package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesdto.OfferDTO;
import be.vinci.pae.dal.OfferDao;
import jakarta.inject.Inject;

public class OfferUCC {

  public OfferUCC() {

  }

  @Inject
  private OfferDao offerDao;

  /**
   * get an offer from database according to its idOffer.
   */
  public OfferDTO getOffer(int idOffer) {
    if (idOffer < 1) {
      throw new IllegalArgumentException("un id ne peut être inférieur à 0");
    }
    return offerDao.getOffer(idOffer);
  }

}
