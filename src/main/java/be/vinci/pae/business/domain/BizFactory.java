package be.vinci.pae.business.domain;

import be.vinci.pae.dal.UserDAO;

public class BizFactory {

  public BizFactory() {

  }

  public UserDAO getUserDAO() {
    return new UserDAO();
  }

}
