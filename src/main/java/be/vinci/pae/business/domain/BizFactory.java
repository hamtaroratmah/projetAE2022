package be.vinci.pae.business.domain;

import be.vinci.pae.dal.AddressDaoImpl;
import be.vinci.pae.dal.MemberDaoImpl;

public class BizFactory {

  public BizFactory() {

  }

  public MemberDaoImpl getUserDAO() {
    return new MemberDaoImpl();
  }
  public AddressDaoImpl getAddressDao(){ return new AddressDaoImpl();}

}
