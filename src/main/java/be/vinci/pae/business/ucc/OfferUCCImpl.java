package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesdto.ItemDTO;
import be.vinci.pae.business.domain.interfacesdto.OfferDTO;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.ItemDao;
import be.vinci.pae.dal.interfaces.OfferDao;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.exceptions.ForbiddenException;
import jakarta.inject.Inject;

public class OfferUCCImpl implements OfferUCC {

  @Inject
  private OfferDao offerDao;
  @Inject
  private ItemDao itemDao;


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
      OfferDTO offer = offerDao.getOffer(idOffer);
      dalServices.commitTransaction();
      if (idOffer < 1) {
        throw new ForbiddenException("un id ne peut être inférieur à 0");
      }
      return offer;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
    }
    return null;
  }

  @Override
  public OfferDTO createOffer(ItemDTO item) {
    try {
      dalServices.startTransaction();
      ItemDTO newItem = itemDao.createItem(item);
      System.out.println("id : " + newItem.getIdItem());

      OfferDTO offerDTO = offerDao.createOffer(newItem);
      dalServices.commitTransaction();
      return offerDTO;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public boolean isLiked(int idItem, int idMember) {
    try {
      dalServices.startTransaction();
//      if (idItem < 1) {
//        throw new BizExceptionForbidden("L'id de l'objet doit être supérieur à 0.");
//      }
      boolean isLiked = offerDao.isLiked(idItem, idMember);
      dalServices.commitTransaction();
      return isLiked;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw new FatalException(e.getMessage());
    }

  }
}
