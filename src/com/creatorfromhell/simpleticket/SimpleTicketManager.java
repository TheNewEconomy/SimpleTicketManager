package com.creatorfromhell.simpleticket;

import com.creatorfromhell.simpleticket.commands.ticket.TicketCommand;
import com.creatorfromhell.simpleticket.core.TicketManager;
import com.creatorfromhell.simpleticket.core.configurations.MainConfiguration;
import com.creatorfromhell.simpleticket.core.configurations.MessageConfigurations;
import com.creatorfromhell.simpleticket.listeners.ConnectionListener;
import com.github.tnerevival.TNELib;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by creatorfromhell on 1/2/2017.
 **/
public class SimpleTicketManager extends TNELib {

  //Configurations
  public File messages;
  public FileConfiguration messageConfigurations;

  public static SimpleTicketManager instance;
  public TicketManager manager;
  public SimpleSQLManager sqlManager;

  public void onEnable() {
    instance = this;


    //Configurations
    initializeConfigurations();
    loadConfigurations();

    super.onEnable();

    configurations.loadAll();
    configurations.add(new MainConfiguration(), "main");
    configurations.add(new MessageConfigurations(), "messages");
    configurations.updateLoad();

    //Commands
    super.registerCommand(new String[] { "ticket", "t"}, new TicketCommand(this));

    cache = api.getBoolean("Core.MySQL.Transactions.Cache");
    saveFormat = "mysql";
    update = api.getInteger("Core.MySQL.Transactions.Update");

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
    messages = new File(getDataFolder(), "messages.yml");
    messageConfigurations = YamlConfiguration.loadConfiguration(messages);
    try {
      setConfigurationDefaults();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

  public void loadConfigurations() {
    getConfig().options().copyDefaults(true);
    messageConfigurations.options().copyDefaults(true);
    super.loadConfigurations();
    saveConfigurations(false);
  }

  public void saveConfigurations(boolean check) {
    if(!check || !new File(getDataFolder(), "config.yml").exists() || configurations.changed.contains("config.yml")) {
      saveConfig();
    }
    try {
      if (!check || !messages.exists() || configurations.changed.contains(messageConfigurations.getName())) {
        messageConfigurations.save(messages);
      }
    } catch(Exception e) {

    }
  }

  public void setConfigurationDefaults() throws UnsupportedEncodingException {
    Reader messagesStream = new InputStreamReader(this.getResource("messages.yml"), "UTF8");
    if (messagesStream != null) {
      YamlConfiguration config = YamlConfiguration.loadConfiguration(messagesStream);
      messageConfigurations.setDefaults(config);
    }
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