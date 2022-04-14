package be.vinci.pae.business.domain.dtos;

import be.vinci.pae.business.domain.interfacesdto.TypeDTO;
import be.vinci.pae.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;

public class TypeImpl implements TypeDTO {

  @JsonView(Views.Public.class)
  private Integer idType;
  @JsonView(Views.Public.class)
  private String type;

  /**
   * Class constructor.
   */
  public TypeImpl() {
  }

  @Override
  public Integer getIdType() {
    return idType;
  }

  @Override
  public void setIdType(Integer idType) {
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
