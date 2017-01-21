package com.creatorfromhell.simpleticket.commands.ticket;

import com.creatorfromhell.simpleticket.SimpleTicketManager;
import com.github.tnerevival.commands.TNECommand;
import org.bukkit.command.CommandSender;

/**
 * Created by creatorfromhell on 1/7/2017.
 **/
public class TicketReloadCommand extends TNECommand {
  public TicketReloadCommand(SimpleTicketManager plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "reload";
  }

  @Override
  public String[] getAliases() {
    return new String[] {
        "r"
    };
  }

  @Override
  public String getNode() {
    return "simpletickets.ticket.reload";
  }

  @Override
  public boolean console() {
    return false;
  }

  @Override
  public String[] getHelpLines() {
    return new String[] {
        "/ticket reload",
        "- Reloads the configuration files."
    };
  }

  @Override
  public boolean execute(CommandSender sender, String command, String[] arguments) {
    return false;
  }
}