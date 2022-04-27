package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesdto.RatingDTO;

public interface RatingUcc {

  RatingDTO rateAnItem(int itemId, int memberId, int stars, String comment);
}



