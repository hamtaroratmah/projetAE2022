package be.vinci.pae.dal;

public interface DalServices {

  void openConnection();

  void startTransaction();

  void commitTransaction();

  void rollbackTransaction();
}
