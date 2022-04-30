package be.vinci.pae.dal.interfaces;

import be.vinci.pae.business.domain.interfacesdto.RatingDTO;

public interface RatingDao {

  RatingDTO rateAnItem(int itemId, int memberId, int stars, String comment);


}
