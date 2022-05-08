package be.vinci.pae.business.domain;

import be.vinci.pae.business.domain.dtos.InterestImpl;
import be.vinci.pae.dal.AddressDaoImpl;
import be.vinci.pae.dal.ItemDaoImpl;
import be.vinci.pae.dal.MemberDaoImpl;
import be.vinci.pae.dal.OfferDaoImpl;

public class BizFactory {

  public BizFactory() {

  }

  public MemberDaoImpl getUserDAO() {
    return new MemberDaoImpl();
  }

  public OfferDaoImpl getOfferDAO() {
    return new OfferDaoImpl();
  }

  public ItemDaoImpl getItemDAO() {
    return new ItemDaoImpl();
  }

  public AddressDaoImpl getAddressDao() {
    return new AddressDaoImpl();
  }

}

