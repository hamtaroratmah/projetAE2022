package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesdto.OfferDTO;
import be.vinci.pae.dal.OfferDao;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.exceptions.BizExceptionForbidden;
import jakarta.inject.Inject;

public class OfferUCCImpl implements OfferUCC {

  @Inject
  private OfferDao offerDao;

  @Inject
  private DalServices dalServices;

  public OfferUCCImpl() {

  }

  /**
   * get an offer from database according to its idOffer.
   */
  @Override
  public OfferDTO getOffer(int idOffer) {
    try {
      dalServices.startTransaction();
      if (idOffer < 1) {
        throw new BizExceptionForbidden("un id ne peut être inférieur à 0");
      }
      return offerDao.getOffer(idOffer);
    } catch (Exception e) {
      dalServices.rollbackTransaction();
    } finally {
      dalServices.commitTransaction();
    }
    return null;
  }

}
