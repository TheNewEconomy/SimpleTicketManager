package com.creatorfromhell.simpleticket.commands.ticket;

import com.creatorfromhell.simpleticket.SimpleTicketManager;
import com.creatorfromhell.simpleticket.core.TicketManager;
import com.creatorfromhell.simpleticket.core.ticket.Ticket;
import com.github.tnerevival.commands.TNECommand;
import com.github.tnerevival.core.Message;
import com.github.tnerevival.user.IDFinder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by creatorfromhell on 1/7/2017.
 **/
public class TicketAssignCommand extends TNECommand {
  public TicketAssignCommand(SimpleTicketManager plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "assign";
  }

  @Override
  public String[] getAliases() {
    return new String[] {
        "a"
    };
  }

  @Override
  public String getNode() {
    return "simpletickets.ticket.assign";
  }

  @Override
  public boolean console() {
    return false;
  }

  @Override
  public String[] getHelpLines() {
    return new String[] {
        "/ticket assign <id> [username]",
        "- Assign a ticket to a staff member.",
        "- Defaults to your own username.",
        "- Assign multiple players by separating username with a \"`\" character."
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

    String assignee = (arguments.length == 2)? arguments[1] : getPlayer(sender).getName();

    if(!assignee.contains(",") && getPlayer(sender, assignee) == null) {
      Message noPlayer = new Message("Messages.Command.NoPlayer");
      noPlayer.addVariable("$username", assignee);
      noPlayer.translate(SimpleTicketManager.instance.defaultWorld, sender);
      return false;
    }

    if(!assignee.contains(",")) {
      Player p = IDFinder.getPlayer(assignee);

      if(!p.hasPermission("simpletickets.staff.nomax") && SimpleTicketManager.instance.manager.getAssignedCount(p.getUniqueId()) >= SimpleTicketManager.instance.api.getInteger("Core.Tickets.Max")) {
        Message maxassigned = new Message("Messages.Command.MaxAssigned");
        maxassigned.addVariable("$username", assignee);
        maxassigned.translate(SimpleTicketManager.instance.defaultWorld, sender);
        return false;
      }
    }


    Ticket t = SimpleTicketManager.instance.manager.getTicket(id);
    List<String> assignedList = t.assign(getPlayer(sender).getName(), assignee);
    SimpleTicketManager.instance.manager.tickets.put(id, t);

    Message assigned = new Message("Messages.Command.Assigned");
    assigned.addVariable("$id", id + "");
    assigned.addVariable("$assignee", TicketManager.formatNames(assignedList));
    assigned.translate(SimpleTicketManager.instance.defaultWorld, sender);
    return true;
  }
}