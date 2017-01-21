package com.creatorfromhell.simpleticket.commands.ticket;

import com.creatorfromhell.simpleticket.SimpleTicketManager;
import com.creatorfromhell.simpleticket.core.ticket.Ticket;
import com.creatorfromhell.simpleticket.core.ticket.TicketStatus;
import com.github.tnerevival.commands.TNECommand;
import com.github.tnerevival.core.Message;
import org.bukkit.command.CommandSender;

/**
 * Created by creatorfromhell on 1/7/2017.
 **/
public class TicketOpenCommand extends TNECommand {
  public TicketOpenCommand(SimpleTicketManager plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "open";
  }

  @Override
  public String[] getAliases() {
    return new String[] {
        "o"
    };
  }

  @Override
  public String getNode() {
    return "simpletickets.ticket.open";
  }

  @Override
  public boolean console() {
    return false;
  }

  @Override
  public String[] getHelpLines() {
    return new String[] {
        "/ticket open <id>",
        "- Reopens a closed ticket."
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
    t.setStatus(TicketStatus.OPEN);
    SimpleTicketManager.instance.manager.tickets.put(id, t);

    Message opened = new Message("Messages.Command.Opened");
    opened.addVariable("$id", id + "");
    opened.addVariable("$username", getPlayer(sender).getName());
    opened.translate(SimpleTicketManager.instance.defaultWorld, sender);
    return true;
  }
}