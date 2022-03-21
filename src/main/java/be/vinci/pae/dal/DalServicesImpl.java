package be.vinci.pae.dal;

import be.vinci.pae.utils.Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DalServicesImpl implements DalBackendServices, DalServices {

  Connection conn = null;
  private ThreadLocal<Connection> threadLocalValue;
  private String dbUsername;
  private String dbPassword;
  private String url;

  /**
   * Constructor : open the database's connexion.
   */
  public DalServicesImpl() {
    threadLocalValue = new ThreadLocal<>();
    dbUsername = Config.getProperty("dbUsername");
    dbPassword = Config.getProperty("dbPassword");
    url = Config.getProperty("dbUrl");
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

  @Override
  public void openConnection() {
    try {
      conn = DriverManager.getConnection(url, dbUsername, dbPassword);
      threadLocalValue.set(conn);
    } catch (SQLException e) {
      System.out.println("Impossible de joindre le server !");
      System.exit(1);
    }
  }

  @Override
  public void startTransaction() {
    try {
      openConnection();
      conn = threadLocalValue.get();
      conn.setAutoCommit(false);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void commitTransaction() {
    try {
      conn = threadLocalValue.get();
      conn.commit();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void rollbackTransaction() {
    try {
      conn = threadLocalValue.get();
      conn.rollback();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
