package be.vinci.pae.dal.interfaces;

import be.vinci.pae.business.domain.interfacesdto.AddressDTO;

public interface AddressDao {

  AddressDTO getAddress(int id);

  int insertAddress(AddressDTO address);

  AddressDTO updateAddress(AddressDTO address, AddressDTO address1);
}
