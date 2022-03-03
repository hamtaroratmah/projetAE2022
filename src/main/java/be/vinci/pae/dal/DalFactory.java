package be.vinci.pae.dal;

public class DalFactory {

  public DalFactory() {

  }

  public Db getDb() {
    return new DbImpl();
  }

}
