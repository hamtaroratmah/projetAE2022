package be.vinci.pae.business.domain.interfaces;

public interface MemberDTO {

  int getIdMember();

  void setIdMember(int idMember);

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

  boolean isAdmin();

  void setAdmin(boolean admin);

  int getCountObjectNotCollected();

  void setCountObjectNotCollected(int countObjectNotCollected);

  int getCountObjectGiven();

  void setCountObjectGiven(int countObjectGiven);

  int getCountObjectGot();

  void setCountObjectGot(int countObjectGot);
}
