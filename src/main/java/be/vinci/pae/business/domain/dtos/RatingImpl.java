package be.vinci.pae.business.domain.dtos;

import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.utils.Views;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

@JsonInclude(Include.NON_NULL)
public class RatingImpl {

  @JsonView(Views.Public.class)
  private Integer rating;
  @JsonView(Views.Public.class)
  private String comment;
  @JsonView(Views.Public.class)
  private MemberDTO member;

  /**
   * Empty constructor.
   */
  public RatingImpl() {

  }

  public Integer getRating() {
    return rating;
  }

  public String getComment() {
    return comment;
  }

  public MemberDTO getMember() {
    return member;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public void setMember(MemberDTO member) {
    this.member = member;
  }
}
