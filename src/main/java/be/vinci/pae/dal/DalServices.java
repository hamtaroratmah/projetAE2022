package be.vinci.pae.dal;

import be.vinci.pae.business.utils.Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DalServices {

  Connection conn = null;

  /**
   * TODO
   */
  public DalServices() {
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
   * TODO
   */
  public PreparedStatement getUser(String user) {
    PreparedStatement statement = null;
    try {
      statement = conn.prepareStatement("SELECT id_member, password, username,"
          + " last_name, first_name, call_number, isadmin, reason_for_conn_refusal,"
          + " state, count_object_not_collected, count_object_given, count_object_got"
          + " FROM pae.members "
          + "WHERE username = ?");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return statement;
  }

}
