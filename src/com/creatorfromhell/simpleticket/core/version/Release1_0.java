package com.creatorfromhell.simpleticket.core.version;

import com.creatorfromhell.simpleticket.SimpleTicketManager;
import com.github.tnerevival.core.SQLManager;
import com.github.tnerevival.core.version.Version;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;

/**
 * Created by creatorfromhell on 1/29/2017.
 **/
public class Release1_0 extends Version {

  public Release1_0(SQLManager sqlManager) {
    super(sqlManager);
  }

  @Override
  public boolean firstRun() {
    if(SimpleTicketManager.instance.saveFormat.equalsIgnoreCase("mysql") || SimpleTicketManager.instance.saveFormat.equalsIgnoreCase("h2")) {
      if(SimpleTicketManager.instance.saveFormat.equalsIgnoreCase("h2")) {
        File h2DB = new File(getSqlManager().getH2File());
        if (!h2DB.exists()) {
          return true;
        }
      }
      String table = getSqlManager().getPrefix() + "_INFO";
      try {
        ResultSet result = sql().connection().getMetaData().getTables(null, null, table, null);
        boolean first = result.next();
        result.close();
        sql().close();
        return first;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return true;
  }

  @Override
  public double getSaveVersion() {
    String table = getSqlManager().getPrefix() + "_INFO";
    if(SimpleTicketManager.instance.saveFormat.equalsIgnoreCase("mysql") || SimpleTicketManager.instance.saveFormat.equalsIgnoreCase("h2")) {
      try {
        Integer index = sql().executePreparedQuery("SELECT version FROM " + table + " WHERE id = ?", new Object[]{
            1
        });
        if (sql().results(index).first()) {
          return sql().results(index).getDouble("version");
        }
      } catch(Exception e) {

      }
    }
    return 0.0;
  }

  @Override
  public double versionNumber() {
    return 1.0;
  }

  @Override
  public void update(double version, String saveFormat) {
    //We could try to update to a pre 1.0 version, but sadly it doesn't exist.
  }

  @Override
  public void loadFlat(File file) {
    //We prefer to stay away from FlatFile for now
  }

  @Override
  public void saveFlat(File file) {
    //We prefer to stay away from FlatFile for now
  }

  @Override
  public void loadMySQL() {
    SimpleTicketManager.instance.sqlManager.load();
  }

  @Override
  public void saveMySQL() {
    SimpleTicketManager.instance.sqlManager.save();
  }

  @Override
  public void loadSQLite() {
    //We prefer H2 over SQLite
  }

  @Override
  public void saveSQLite() {
    //We prefer H2 over SQLite
  }

  @Override
  public void loadH2() {
    SimpleTicketManager.instance.sqlManager.load();
  }

  @Override
  public void saveH2() {
    SimpleTicketManager.instance.sqlManager.save();
  }

  @Override
  public void createTables(String saveFormat) {
    String table = getSqlManager().getPrefix() + "_INFO";
    if(saveFormat.equalsIgnoreCase("mysql")) {
      mysql().executeUpdate("CREATE TABLE IF NOT EXISTS `" + table + "` (" +
          "`id` INTEGER NOT NULL UNIQUE," +
          "`version` DOUBLE," +
          "`server_name` VARCHAR(250)" +
          ");");

      table = getSqlManager().getPrefix() + "_BANNED";
      mysql().executeUpdate("CREATE TABLE IF NOT EXISTS `" + table + "` (" +
          "`banned_player` VARCHAR(36) UNIQUE" +
          ");");

      table = getSqlManager().getPrefix() + "_TICKETS";
      mysql().executeUpdate("CREATE TABLE IF NOT EXISTS `" + table + "` (" +
          "`ticket_id` INT(60) NOT NULL," +
          "`ticket_created` BIGINT(60) NOT NULL," +
          "`ticket_closed` BIGINT(60)," +
          "`ticket_author` VARCHAR(36)," +
          "`ticket_server` VARCHAR(90)," +
          "`ticket_location` LONGTEXT," +
          "`ticket_players` INT(4)," +
          "`ticket_assigned` LONGTEXT," +
          "`ticket_closedby` VARCHAR(36)," +
          "`ticket_description` LONGTEXT," +
          "`ticket_closereason` LONGTEXT," +
          "`ticket_status` TINYINT(1)," +
          "PRIMARY KEY(ticket_id)" +
          ");");

      table = getSqlManager().getPrefix() + "_COMMENTS";
      mysql().executeUpdate("CREATE TABLE IF NOT EXISTS `" + table + "` (" +
          "`comment_id` INT(60) NOT NULL," +
          "`comment_ticket` INT(60) NOT NULL," +
          "`comment_created` BIGINT(60)," +
          "`comment_author` VARCHAR(36)," +
          "`comment_text` LONGTEXT," +
          "PRIMARY KEY(comment_id, comment_ticket)" +
          ");");
      mysql().close();
    } else {
      File h2DB = new File(getSqlManager().getH2File());
      if(!h2DB.exists()) {
        try {
          h2DB.createNewFile();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      h2().executeUpdate("CREATE TABLE IF NOT EXISTS `" + table + "` (" +
          "`id` INTEGER NOT NULL UNIQUE," +
          "`version` DOUBLE(10)," +
          "`server_name` VARCHAR(250)" +
          ");");

      table = getSqlManager().getPrefix() + "_BANNED";
      h2().executeUpdate("CREATE TABLE IF NOT EXISTS `" + table + "` (" +
          "`banned_player` VARCHAR(36) UNIQUE" +
          ");");

      table = getSqlManager().getPrefix() + "_TICKETS";
      h2().executeUpdate("CREATE TABLE IF NOT EXISTS `" + table + "` (" +
          "`ticket_id` INT(60) NOT NULL," +
          "`ticket_created` BIGINT(60) NOT NULL," +
          "`ticket_closed` BIGINT(60)," +
          "`ticket_author` VARCHAR(36)," +
          "`ticket_server` VARCHAR(90)," +
          "`ticket_location` LONGTEXT," +
          "`ticket_players` INT(4)," +
          "`ticket_assigned` LONGTEXT," +
          "`ticket_closedby` VARCHAR(36)," +
          "`ticket_description` LONGTEXT," +
          "`ticket_closereason` LONGTEXT," +
          "`ticket_status` TINYINT(1)," +
          "PRIMARY KEY(ticket_id)" +
          ");");

      table = getSqlManager().getPrefix() + "_COMMENTS";
      h2().executeUpdate("CREATE TABLE IF NOT EXISTS `" + table + "` (" +
          "`comment_id` INT(60) NOT NULL," +
          "`comment_ticket` INT(60) NOT NULL," +
          "`comment_created` BIGINT(60)," +
          "`comment_author` VARCHAR(36)," +
          "`comment_text` LONGTEXT," +
          "PRIMARY KEY(comment_id, comment_ticket)" +
          ");");
      h2().close();
    }
  }
}