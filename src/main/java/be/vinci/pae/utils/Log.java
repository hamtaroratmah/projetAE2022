package be.vinci.pae.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {

  public Logger logger;
  FileHandler fh;

  /**
   * Create a log file who's contains all error raised by the application.
   */
  public Log(String fileName) throws SecurityException, IOException {
    File f = new File(fileName);
    if (!f.exists()) {
      f.createNewFile();

    }
    fh = new FileHandler(fileName, true);
    logger = Logger.getLogger("test");
    logger.addHandler(fh);
    SimpleFormatter formatter = new SimpleFormatter();
    fh.setFormatter(formatter);

  }

}
