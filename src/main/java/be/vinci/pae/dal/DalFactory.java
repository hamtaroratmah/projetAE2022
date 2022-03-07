package be.vinci.pae.dal;

public class DalFactory {

  public DalFactory() {

  }

  public DalServices getDalServices() {
    return new DalServicesImpl();
  }

}
