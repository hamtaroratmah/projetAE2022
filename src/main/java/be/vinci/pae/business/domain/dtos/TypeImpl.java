package be.vinci.pae.business.domain.dtos;

public class TypeImpl implements TypeDTO {

  private int idType;
  private String type;

  /**
   * Class constructor.
   */
  public TypeImpl() {
  }

  @Override
  public int getIdType() {
    return idType;
  }

  @Override
  public void setIdType(int idType) {
    if (idType <= 0) {
      throw new IllegalArgumentException();
    }
    this.idType = idType;
  }

  @Override
  public String getType() {
    return type;
  }

  @Override
  public void setType(String type) {
    if (type == null || type.isBlank()) {
      throw new IllegalArgumentException();
    }
    this.type = type;
  }

  public String toString() {
    return this.getType();
  }


}
