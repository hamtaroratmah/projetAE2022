package be.vinci.pae.main;

public class RatingsDTO {

  private int id_rating;
  private int rating;

  public RatingsDTO(int id_rating, int rating) {
    this.id_rating = id_rating;
    this.rating = rating;
  }

  public int getId_rating() {
    return id_rating;
  }


  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }
}
