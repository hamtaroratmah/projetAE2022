package be.vinci.pae.domain;

public class MemberDTO {

  private int idMember;
  private String password;
  private String username;
  private String lastName;
  private String firstName;
  private String callNumber;
  private String reasonForConnRefusal;
  private String state;
  private boolean isAdmin;
  private int countObjectNotCollected;
  private int countObjectGiven;
  private int countObjectGot = 0;

/**
 * Empty constructor
 */
  public MemberDTO() {

  }


  public MemberDTO(int idMember , String password , String username , String lastName ,
      String firstName , String callNumber , String reasonForConnRefusal , String state ,
      boolean isAdmin) {
    this.idMember = idMember;
    this.password = password;
    this.username = username;
    this.lastName = lastName;
    this.firstName = firstName;
    this.callNumber = callNumber;
    this.reasonForConnRefusal = reasonForConnRefusal;
    this.state = state;
    this.isAdmin = isAdmin;

  }

  public int getIdMember() {
    return idMember;
  }

  public void setIdMember(int idMember) {
    this.idMember = idMember;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getCallNumber() {
    return callNumber;
  }

  public void setCallNumber(String callNumber) {
    this.callNumber = callNumber;
  }

  public String getReasonForConnRefusal() {
    return reasonForConnRefusal;
  }

  public void setReasonForConnRefusal(String reasonForConnRefusal) {
    this.reasonForConnRefusal = reasonForConnRefusal;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }

  public int getCountObjectNotCollected() {
    return countObjectNotCollected;
  }

  public void setCountObjectNotCollected(int countObjectNotCollected) {
    this.countObjectNotCollected = countObjectNotCollected;
  }

  public int getCountObjectGiven() {
    return countObjectGiven;
  }

  public void setCountObjectGiven(int countObjectGiven) {
    this.countObjectGiven = countObjectGiven;
  }

  public int getCountObjectGot() {
    return countObjectGot;
  }

  public void setCountObjectGot(int countObjectGot) {
    this.countObjectGot = countObjectGot;
  }

  @Override
  public String toString() {
    return "Member{"
            + "id_member="
            + idMember
            +", username='"
            + username
            + '\''
            +", last_name='"
            + lastName
            + '\''
            +", first_name='"
            + firstName
            + '\''
            +'}';
  }

  public int hashCode() {
    return super.hashCode();
  }

  public boolean equals(Object obj) {
    return super.equals(obj);
  }

 
}
