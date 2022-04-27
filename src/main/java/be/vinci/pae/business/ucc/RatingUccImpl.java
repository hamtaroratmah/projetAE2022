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
    RatingDTO rating = null;
    try {
      System.out.println("ok");

      dalServices.startTransaction();
      rating = ratingDao.rateAnItem(itemId, memberId, stars, comment);
      dalServices.commitTransaction();
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      e.printStackTrace();
    }
    return rating;
  }

}
