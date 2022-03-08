package be.vinci.pae.business.domain;

import be.vinci.pae.dal.MemberDaoImpl;

public class BizFactory {

  public BizFactory() {

  }

  public MemberDaoImpl getUserDAO() {
    return new MemberDaoImpl();
  }

}
