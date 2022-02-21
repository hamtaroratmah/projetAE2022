package be.vinci.pae.main;

public class TypeDTO {

  private int idType;
  private String type;

  public TypeDTO(int idType, String type) {
    this.idType = idType;
    this.type = type;
  }

  public int getIdType() {
    return idType;
  }

  public void setIdType(int idType) {
    this.idType = idType;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
