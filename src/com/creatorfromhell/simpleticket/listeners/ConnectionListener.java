package com.creatorfromhell.simpleticket.listeners;

import com.creatorfromhell.simpleticket.SimpleTicketManager;
import com.github.tnerevival.core.Message;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by creatorfromhell on 1/3/2017.
 **/
public class ConnectionListener implements Listener {

  private SimpleTicketManager plugin;

  public ConnectionListener(SimpleTicketManager plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    if(SimpleTicketManager.instance.api.getBoolean("Core.Ticket.Broadcast.Join")) {
      Message statistics = new Message("Messages.General.Statistics");
      statistics.addVariable("$total", SimpleTicketManager.instance.manager.tickets.size() + "");
      statistics.addVariable("$open", SimpleTicketManager.instance.manager.getOpened() + "");
      statistics.addVariable("$assigned", SimpleTicketManager.instance.manager.getAssigned() + "");
      statistics.addVariable("$closed", SimpleTicketManager.instance.manager.getClosed() + "");
      statistics.translate(SimpleTicketManager.instance.defaultWorld, event.getPlayer());
    }
  }
}