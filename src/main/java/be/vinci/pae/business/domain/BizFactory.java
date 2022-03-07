package be.vinci.pae.business.domain;

import be.vinci.pae.dal.MemberDao;

public class BizFactory {

  public BizFactory() {

  }

  public MemberDao getUserDAO() {
    return new MemberDao();
  }

}
