package be.vinci.pae.dal;

import be.vinci.pae.business.utils.Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbImpl implements Db {

  private PreparedStatement psGetHashedPassword;

  public DbImpl() {
    Config config = new Config();
    String dbUsername = Config.getProperty("dbUsername");
    String dbPassword = Config.getProperty("dbPassword");
    String url = Config.getProperty("dbUrl");
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println("Driver PostgreSQL manquant !");
      System.exit(1);
    }

    Connection conn = null;
    try {
      conn = DriverManager.getConnection(url, dbUsername, dbPassword);
    } catch (SQLException e) {
      System.out.println("Impossible de joindre le server !");
      System.exit(1);
    }
    try {
      psGetHashedPassword = conn.prepareStatement("SELECT me.password"
          + " FROM pae.members me "
          + "WHERE me.username = ?");
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @Override
  public String getHashedPassword(String username) {
    try {
      psGetHashedPassword.setString(1, username);
      ResultSet password = psGetHashedPassword.executeQuery();
      if (password == null) {
        return "Username not found";
      }
      return password.getString(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return "";
  }

}