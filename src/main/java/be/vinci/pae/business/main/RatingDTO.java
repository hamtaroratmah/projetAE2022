package be.vinci.pae.main;

public class RatingDTO {

  private int idRating;
  private int rating;


  //rating constructor
  public RatingDTO(int idRating, int rating) {
    this.idRating = idRating;
    this.rating = rating;
  }

  public int getIdRating() {
    return idRating;
  }


  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }


}
