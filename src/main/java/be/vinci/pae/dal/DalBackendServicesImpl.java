package be.vinci.pae.dal;

import be.vinci.pae.utils.Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DalBackendServicesImpl implements DalBackendServices {

  Connection conn = null;

  /**
   * Constructor : open the database's connexion.
   */
  public DalBackendServicesImpl() {
    String dbUsername = Config.getProperty("dbUsername");
    String dbPassword = Config.getProperty("dbPassword");
    String url = Config.getProperty("dbUrl");
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println("Driver PostgreSQL manquant !");
      System.exit(1);
    }
    try {
      conn = DriverManager.getConnection(url, dbUsername, dbPassword);
    } catch (SQLException e) {
      System.out.println("Impossible de joindre le server !");
      System.exit(1);
    }
  }

  /**
   * Return a preparedStatement that can be executed after setting the username if the query is
   * executed, it'll give a member he's username match with your parameter.
   */
  @Override
  public PreparedStatement getPreparedStatement(String query) {
    PreparedStatement statement = null;
    try {
      statement = conn.prepareStatement(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return statement;
  }

}
