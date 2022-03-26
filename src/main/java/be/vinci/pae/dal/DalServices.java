package be.vinci.pae.dal;

import java.sql.PreparedStatement;

public interface DalServices {

  void openConnection();

  void startTransaction();

  void commitTransaction();

  void rollbackTransaction();

  PreparedStatement getPreparedStatement(String query);

}
