package be.vinci.pae.business.domain;

import be.vinci.pae.dal.MemberDao;
import be.vinci.pae.dal.MemberDaoImpl;

public class BizFactory {

  public BizFactory() {

  }

  public MemberDao getUserDAO() {
    return new MemberDaoImpl();
  }

}
