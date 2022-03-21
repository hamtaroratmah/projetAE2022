package be.vinci.pae.dal.interfaces;

import be.vinci.pae.business.domain.interfacesdto.AddressDTO;

public interface AddressDao {

    AddressDTO getAddress(int id);
}
