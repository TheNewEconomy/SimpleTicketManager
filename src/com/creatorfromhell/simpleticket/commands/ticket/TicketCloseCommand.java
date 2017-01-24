package com.creatorfromhell.simpleticket.commands.ticket;

import com.creatorfromhell.simpleticket.SimpleTicketManager;
import com.creatorfromhell.simpleticket.core.ticket.Ticket;
import com.creatorfromhell.simpleticket.core.ticket.TicketStatus;
import com.github.tnerevival.commands.TNECommand;
import com.github.tnerevival.core.Message;
import com.github.tnerevival.user.IDFinder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by creatorfromhell on 1/7/2017.
 **/
public class TicketCloseCommand extends TNECommand {
  public TicketCloseCommand(SimpleTicketManager plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "close";
  }

  @Override
  public String[] getAliases() {
    return new String[] {
        "-"
    };
  }

  @Override
  public String getNode() {
    return "simpletickets.ticket.close";
  }

  @Override
  public boolean console() {
    return false;
  }

  @Override
  public String[] getHelpLines() {
    return new String[] {
        "/ticket close <id> [reason]",
        "- Close a ticket with an optional reason.",
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

    String reason = (arguments.length > 1)? SimpleTicketManager.join(Arrays.copyOfRange(arguments, 1, arguments.length), " ") : "";


    Ticket t = SimpleTicketManager.instance.manager.getTicket(id);

    if(SimpleTicketManager.instance.api.getBoolean("Core.Tickets.OthersClose") && !t.isAssigned(IDFinder.getID(getPlayer(sender))) && !sender.hasPermission("simpletickets.staff.override")) {
      Message cant = new Message("Messages.General.CantClose");
      cant.translate(SimpleTicketManager.instance.defaultWorld, sender);
      return false;
    }

    t.setStatus(TicketStatus.CLOSED);
    t.setCloseReason(reason);
    t.setClosedBy(IDFinder.getID(getPlayer(sender)));
    t.setClosed(new Date().getTime());
    SimpleTicketManager.instance.manager.tickets.put(id, t);

    Message closed = new Message("Messages.Command.Closed");
    closed.addVariable("$id", id + "");
    closed.addVariable("$username", getPlayer(sender).getName());
    closed.translate(SimpleTicketManager.instance.defaultWorld, sender);

    if(SimpleTicketManager.instance.api.getBoolean("Core.Tickets.Broadcast.Close")) {
      for(Player p : SimpleTicketManager.instance.getServer().getOnlinePlayers()) {
        if(p.hasPermission("simpletickets.staff.close")) {
          Message statistics = new Message("Messages.General.Close");
          statistics.addVariable("$id", t.getId() + "");
          statistics.addVariable("$username", getPlayer(sender).getName());
          statistics.translate(SimpleTicketManager.instance.defaultWorld, p);
        }
      }
    }
    return true;
  }
}