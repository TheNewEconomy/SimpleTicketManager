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
public class TicketBanCommand extends TNECommand {

  public TicketBanCommand(SimpleTicketManager plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "ban";
  }

  @Override
  public String[] getAliases() {
    return new String[] {
        "b"
    };
  }

  @Override
  public String getNode() {
    return "simpletickets.ticket.ban";
  }

  @Override
  public boolean console() {
    return false;
  }

  @Override
  public String[] getHelpLines() {
    return new String[] {
        "/ticket ban <username>",
        "- Bans a player from the ticket system."
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

    SimpleTicketManager.instance.manager.ban(id);
    Message banned = new Message("Messages.Command.Banned");
    banned.addVariable("$username", player);
    banned.translate(SimpleTicketManager.instance.defaultWorld, sender);
    return true;
  }
}