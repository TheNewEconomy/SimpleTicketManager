package com.creatorfromhell.simpleticket.commands.ticket;

import com.creatorfromhell.simpleticket.SimpleTicketManager;
import com.github.tnerevival.commands.TNECommand;
import com.github.tnerevival.core.Message;
import org.bukkit.command.CommandSender;

/**
 * Created by creatorfromhell on 1/7/2017.
 **/
public class TicketStatisticsCommand extends TNECommand {
  public TicketStatisticsCommand(SimpleTicketManager plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "statistics";
  }

  @Override
  public String[] getAliases() {
    return new String[] {
        "stats"
    };
  }

  @Override
  public String getNode() {
    return "simpletickets.ticket.statistics";
  }

  @Override
  public boolean console() {
    return false;
  }

  @Override
  public String[] getHelpLines() {
    return new String[] {
        "/ticket statistics",
        "- Shows some statistics for the ticket system.",
        "- Shows how many are open, closed, assigned, and a grand total."
    };
  }

  @Override
  public boolean execute(CommandSender sender, String command, String[] arguments) {
    Message statistics = new Message("Messages.General.Statistics");
    statistics.addVariable("$total", SimpleTicketManager.instance.manager.tickets.size() + "");
    statistics.addVariable("$open", SimpleTicketManager.instance.manager.getOpened() + "");
    statistics.addVariable("$assigned", SimpleTicketManager.instance.manager.getAssigned() + "");
    statistics.addVariable("$closed", SimpleTicketManager.instance.manager.getClosed() + "");
    statistics.translate(SimpleTicketManager.instance.defaultWorld, sender);
    return true;
  }
}