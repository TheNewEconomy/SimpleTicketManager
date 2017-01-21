package com.creatorfromhell.simpleticket.commands.ticket;

import com.creatorfromhell.simpleticket.SimpleTicketManager;
import com.github.tnerevival.commands.TNECommand;
import com.github.tnerevival.core.Message;
import com.github.tnerevival.user.IDFinder;
import org.bukkit.command.CommandSender;

import java.util.UUID;

/**
 * Created by creatorfromhell on 1/7/2017.
 **/
public class TicketUnbanCommand extends TNECommand {
  public TicketUnbanCommand(SimpleTicketManager plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "unban";
  }

  @Override
  public String[] getAliases() {
    return new String[] {
        "ub"
    };
  }

  @Override
  public String getNode() {
    return "simpletickets.ticket.unban";
  }

  @Override
  public boolean console() {
    return false;
  }

  @Override
  public String[] getHelpLines() {
    return new String[] {
        "/ticket unban <username>",
        "- Removes a player's ban from the ticket system."
    };
  }

  @Override
  public boolean execute(CommandSender sender, String command, String[] arguments) {
    if(arguments.length < 1) {
      help(sender);
      return false;
    }

    String player = arguments[0];
    UUID id = IDFinder.getID(player);

    if(id == null) {
      Message noPlayer = new Message("Messages.Command.NoPlayer");
      noPlayer.addVariable("$username", player);
      noPlayer.translate(SimpleTicketManager.instance.defaultWorld, sender);
      return false;
    }

    SimpleTicketManager.instance.manager.unban(id);
    Message unbanned = new Message("Messages.Command.UnBanned");
    unbanned.addVariable("$username", player);
    unbanned.translate(SimpleTicketManager.instance.defaultWorld, sender);
    return true;
  }
}