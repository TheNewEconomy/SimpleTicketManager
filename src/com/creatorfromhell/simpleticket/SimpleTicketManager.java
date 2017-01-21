package com.creatorfromhell.simpleticket;

import com.creatorfromhell.simpleticket.commands.ticket.TicketCommand;
import com.creatorfromhell.simpleticket.core.TicketManager;
import com.creatorfromhell.simpleticket.core.configurations.MainConfiguration;
import com.creatorfromhell.simpleticket.listeners.ConnectionListener;
import com.github.tnerevival.TNELib;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by creatorfromhell on 1/2/2017.
 **/
public class SimpleTicketManager extends TNELib {


  public static SimpleTicketManager instance;
  public TicketManager manager;
  public SimpleSQLManager sqlManager;

  public void onEnable() {
    instance = this;


    //Configurations
    initializeConfigurations();
    loadConfigurations();

    super.onEnable();

    //Commands
    super.registerCommand(new String[] { "ticket", "t"}, new TicketCommand(this));

    configurations.loadAll();
    configurations.add(new MainConfiguration(), "main");
    configurations.updateLoad();

    cache = api.getBoolean("Core.Transactions.Cache");
    saveFormat = "mysql";
    update = api.getInteger("Core.Transactions.Update");

    manager = new TicketManager();
    sqlManager = new SimpleSQLManager(api.getString("Core.MySQL.Host"), api.getInteger("Core.MySQL.Port"),
                                api.getString("Core.MySQL.Database"), api.getString("Core.MySQL.User"),
                                api.getString("Core.MySQL.Password"), api.getString("Core.MySQL.Prefix"),
                                "", "", ""
    );

    //Listeners
    getServer().getPluginManager().registerEvents(new ConnectionListener(this), this);
  }

  private void initializeConfigurations() {
  }

  public void loadConfigurations() {
    super.loadConfigurations();
  }

  public void saveConfigurations(boolean check) {
  }

  public void setConfigurationDefaults() {
  }

  public static String join(String[] array, String separator) {
    StringBuilder builder = new StringBuilder();

    for(int i = 0; i < array.length; i++) {
      if(i > 0) builder.append(separator);
      builder.append(array[i]);
    }
    return builder.toString();
  }

  public static String formatTime(long time) {
    final Date date = new Date(time);
    final SimpleDateFormat dateFormat = new SimpleDateFormat(configurations.getString("Core.Time.Format", "main"));
    dateFormat.setTimeZone(TimeZone.getTimeZone(configurations.getString("Core.Time.Timezone", "main")));

    return dateFormat.format(date);
  }
}