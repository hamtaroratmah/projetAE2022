package be.vinci.pae.dal.interfaces;

import java.sql.PreparedStatement;

public interface DalServices {
  
  void startTransaction();

  void commitTransaction();

  void rollbackTransaction();

  PreparedStatement getPreparedStatement(String query);

}
