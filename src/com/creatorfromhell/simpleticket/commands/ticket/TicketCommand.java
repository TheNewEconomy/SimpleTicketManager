package com.creatorfromhell.simpleticket.commands.ticket;

import com.creatorfromhell.simpleticket.SimpleTicketManager;
import com.github.tnerevival.commands.TNECommand;

/**
 * Created by creatorfromhell on 1/3/2017.
 **/
public class TicketCommand extends TNECommand {

  public TicketCommand(SimpleTicketManager plugin) {
    super(plugin);
    subCommands.add(new TicketAssignCommand(plugin));
    subCommands.add(new TicketBanCommand(plugin));
    subCommands.add(new TicketCloseCommand(plugin));
    subCommands.add(new TicketCommentCommand(plugin));
    subCommands.add(new TicketCommentsCommand(plugin));
    subCommands.add(new TicketCreateCommand(plugin));
    subCommands.add(new TicketInfoCommand(plugin));
    subCommands.add(new TicketOpenCommand(plugin));
    subCommands.add(new TicketReAssignCommand(plugin));
    subCommands.add(new TicketReloadCommand(plugin));
    subCommands.add(new TicketStatisticsCommand(plugin));
    subCommands.add(new TicketTeleportCommand(plugin));
    subCommands.add(new TicketUnAssignCommand(plugin));
    subCommands.add(new TicketUnbanCommand(plugin));
    subCommands.add(new TicketViewCommand(plugin));
  }

  @Override
  public String getName() {
    return "ticket";
  }

  @Override
  public String[] getAliases() {
    return new String[0];
  }

  @Override
  public String getNode() {
    return "simpletickets.ticket";
  }

  @Override
  public boolean console() {
    return false;
  }
}
