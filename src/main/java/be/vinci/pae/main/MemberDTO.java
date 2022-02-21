package be.vinci.pae.main;

public class MemberDTO {

  private int id_member;
  private String password, username, last_name, first_name, call_number, reason_for_conn_refusal, state;
  private boolean isAdmin;
  private int count_object_not_collected, count_object_given, count_object_got = 0;


  public MemberDTO() {

  }


  public MemberDTO(int id_member, String password, String username, String last_name,
      String first_name, String call_number, String reason_for_conn_refusal, String state,
      boolean isAdmin) {
    this.id_member = id_member;
    this.password = password;
    this.username = username;
    this.last_name = last_name;
    this.first_name = first_name;
    this.call_number = call_number;
    this.reason_for_conn_refusal = reason_for_conn_refusal;
    if (!state.equals("pending") || !state.equals("valid") || !state.equals("denied"))
      throw new IllegalArgumentException();

    this.state = state;
    this.isAdmin = isAdmin;

  }

  public int getId_member() {
    return id_member;
  }

  public void setId_member(int id_member) {
    this.id_member = id_member;
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

  public String getLast_name() {
    return last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  public String getFirst_name() {
    return first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public String getCall_number() {
    return call_number;
  }

  public void setCall_number(String call_number) {
    this.call_number = call_number;
  }

  public String getReason_for_conn_refusal() {
    return reason_for_conn_refusal;
  }

  public void setReason_for_conn_refusal(String reason_for_conn_refusal) {
    this.reason_for_conn_refusal = reason_for_conn_refusal;
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

  public int getCount_object_not_collected() {
    return count_object_not_collected;
  }

  public void setCount_object_not_collected(int count_object_not_collected) {
    this.count_object_not_collected = count_object_not_collected;
  }

  public int getCount_object_given() {
    return count_object_given;
  }

  public void setCount_object_given(int count_object_given) {
    this.count_object_given = count_object_given;
  }

  public int getCount_object_got() {
    return count_object_got;
  }

  public void setCount_object_got(int count_object_got) {
    this.count_object_got = count_object_got;
  }

  @Override
  public String toString() {
    return "Member{" +
        "id_member=" + id_member +
        ", username='" + username + '\'' +
        ", last_name='" + last_name + '\'' +
        ", first_name='" + first_name + '\'' +
        '}';
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }


}
