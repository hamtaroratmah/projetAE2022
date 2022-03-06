package be.vinci.pae.business.domain.dtos;

import be.vinci.pae.business.domain.interfaces.TypeDTO;

public class TypeDTOImpl implements TypeDTO {

  private int idType;
  private String type;

  /**
   * Class constructor.
   */
  public TypeDTOImpl(int idType, String type) {
    this.idType = idType;
    this.type = type;
  }

  @Override
  public int getIdType() {
    return idType;
  }

  @Override
  public void setIdType(int idType) {
    this.idType = idType;
  }

  @Override
  public String getType() {
    return type;
  }

  @Override
  public void setType(String type) {
    this.type = type;
  }
}
