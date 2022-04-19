package be.vinci.pae.business.domain.interfacesdto;

import be.vinci.pae.business.domain.dtos.TypeImpl;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = TypeImpl.class)

public interface TypeDTO {

  Integer getIdType();

  void setIdType(Integer idType);

  String getType();

  void setType(String type);
}
