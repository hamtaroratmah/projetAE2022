package be.vinci.pae.dal.interfaces;

import java.sql.PreparedStatement;

public interface DalServices {

  /**
   * Return a preparedStatement that can be executed after setting the username if the query is
   * executed, it'll give a member he's username match with your parameter.
   */
  PreparedStatement getPreparedStatement(String query);
}
