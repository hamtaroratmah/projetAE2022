package be.vinci.pae.dal;

public interface Db {

  String getHashedPassword(String username);
}
