package be.vinci.pae.business.domain.interfacesdto;


import be.vinci.pae.business.domain.dtos.RatingImpl;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = RatingImpl.class)

public interface RatingDTO {

  Integer getRatingId();

  Integer getRating();

  String getComment();

  MemberDTO getMember();

  void setRatingId(Integer ratingId);

  void setRating(Integer rating);

  void setComment(String comment);

  void setMember(MemberDTO member);


}
