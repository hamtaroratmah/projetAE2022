package be.vinci.pae.dal.interfaces;

import java.sql.PreparedStatement;

public interface DalServices {

  void openConnection();

  void startTransaction();

  void commitTransaction();

  void rollbackTransaction();

  PreparedStatement getPreparedStatement(String query);

}
