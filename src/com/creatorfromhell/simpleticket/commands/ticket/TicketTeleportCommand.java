package com.creatorfromhell.simpleticket.commands.ticket;

import com.creatorfromhell.simpleticket.SimpleTicketManager;
import com.creatorfromhell.simpleticket.core.ticket.Ticket;
import com.github.tnerevival.commands.TNECommand;
import com.github.tnerevival.core.Message;
import org.bukkit.command.CommandSender;

/**
 * Created by creatorfromhell on 1/7/2017.
 **/
public class TicketTeleportCommand extends TNECommand {
  public TicketTeleportCommand(SimpleTicketManager plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "teleport";
  }

  @Override
  public String[] getAliases() {
    return new String[] {
        "tp"
    };
  }

  @Override
  public String getNode() {
    return "simpletickets.ticket.teleport";
  }

  @Override
  public boolean console() {
    return false;
  }

  @Override
  public String[] getHelpLines() {
    return new String[] {
        "/ticket teleport <id> [username]",
        "- Teleport yourself, or another player to a ticket's creation location."
    };
  }

  @Override
  public boolean execute(CommandSender sender, String command, String[] arguments) {
    if(arguments.length < 1) {
      help(sender);
      return false;
    }
    Integer id = Integer.valueOf(arguments[0]);

    if(!SimpleTicketManager.instance.manager.exists(id)) {
      Message none = new Message("Messages.Command.None");
      none.addVariable("$id", id + "");
      none.translate(SimpleTicketManager.instance.defaultWorld, sender);
      return false;
    }


    Ticket t = SimpleTicketManager.instance.manager.getTicket(id);
    getPlayer(sender).teleport(t.getLocation());

    Message teleported = new Message("Messages.Command.Teleported");
    teleported.addVariable("$id", id + "");
    teleported.translate(SimpleTicketManager.instance.defaultWorld, sender);
    return true;
  }
}