package be.vinci.pae.business.domain.interfacesdto;

import be.vinci.pae.business.domain.dtos.TypeDTO;

public interface DomainFactory {

  MemberDTO getMember();

  ItemDTO getItem();

  TypeDTO getType();
}
