package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesdto.RatingDTO;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.RatingDao;
import jakarta.inject.Inject;

public class RatingUccImpl implements RatingUcc {

  @Inject
  RatingDao ratingDao;
  @Inject
  private DalServices dalServices;

  @Override
  public RatingDTO rateAnItem(int itemId, int memberId, int stars, String comment) {
    try {
      dalServices.startTransaction();
      RatingDTO rating = ratingDao.rateAnItem(itemId, memberId, stars, comment);
      dalServices.commitTransaction();
      return rating;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      e.printStackTrace();
    }
    return null;
  }

}
