package be.vinci.pae.business.domain;

import be.vinci.pae.dal.MemberDaoImpl;
import be.vinci.pae.dal.interfaces.MemberDao;

public class BizFactory {

  public BizFactory() {

  }

  public MemberDao getUserDAO() {
    return new MemberDaoImpl();
  }

}
