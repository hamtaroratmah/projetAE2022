package be.vinci.pae.dal;

public class DalFactoryImpl implements DalFactory {

  public DalFactoryImpl() {

  }

  @Override
  public DalServices getDalServices() {
    return new DalServicesImpl();
  }

}
