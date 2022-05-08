package be.vinci.pae.business.domain.interfacesdto;

import be.vinci.pae.business.domain.dtos.MemberImpl;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = MemberImpl.class)
public interface MemberDTO {

  Integer getIdMember();

  void setIdMember(Integer idMember);

  String getPassword();

  void setPassword(String password);

  String getUsername();

  void setUsername(String username);

  String getLastName();

  void setLastName(String lastName);

  String getFirstName();

  void setFirstName(String firstName);

  String getCallNumber();

  void setCallNumber(String callNumber);

  String getReasonForConnRefusal();

  void setReasonForConnRefusal(String reasonForConnRefusal);

  String getState();

  void setState(String state);

  Boolean isAdmin();

  void setAdmin(Boolean admin);

  Integer getCountObjectNotCollected();

  void setCountObjectNotCollected(Integer countObjectNotCollected);

  Integer getCountObjectGiven();

  void setCountObjectGiven(Integer countObjectGiven);

  Integer getCountObjectGot();

  void setCountObjectGot(Integer countObjectGot);

  void setAddress(AddressDTO address);

  AddressDTO getAddress();


  Boolean getAdmin();

  Boolean getPrecluded();

  void setPrecluded(Boolean precluded);
}
