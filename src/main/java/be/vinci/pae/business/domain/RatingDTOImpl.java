package be.vinci.pae.business.domain;

import be.vinci.pae.business.domain.interfaces.RatingDTO;

public class RatingDTOImpl implements RatingDTO {

  private int idRating;
  private int rating;


  //rating constructor
  public RatingDTOImpl(int idRating, int rating) {
    this.idRating = idRating;
    this.rating = rating;
  }

  @Override
  public int getIdRating() {
    return idRating;
  }


  @Override
  public int getRating() {
    return rating;
  }

  @Override
  public void setRating(int rating) {
    this.rating = rating;
  }


}
