package com.creatorfromhell.simpleticket.commands.ticket;

import com.creatorfromhell.simpleticket.SimpleTicketManager;
import com.creatorfromhell.simpleticket.core.TicketManager;
import com.creatorfromhell.simpleticket.core.ticket.Ticket;
import com.github.tnerevival.commands.TNECommand;
import com.github.tnerevival.core.Message;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Created by creatorfromhell on 1/7/2017.
 **/
public class TicketUnAssignCommand extends TNECommand {
  public TicketUnAssignCommand(SimpleTicketManager plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "unassign";
  }

  @Override
  public String[] getAliases() {
    return new String[] {
        "ua"
    };
  }

  @Override
  public String getNode() {
    return "simpletickets.ticket.unassign";
  }

  @Override
  public boolean console() {
    return false;
  }

  @Override
  public String[] getHelpLines() {
    return new String[] {
        "/ticket unassign <id> [username, all]",
        "- Unassign a player from a ticket.",
        "- Username is not required, will default to all and remove everyone assigned."
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


    Ticket t = SimpleTicketManager.instance.manager.getTicket(id);
    List<String> assignedList = t.unassign(getPlayer(sender).getName(), assignee);
    SimpleTicketManager.instance.manager.tickets.put(id, t);

    Message assigned = new Message("Messages.Command.UnAssigned");
    assigned.addVariable("$id", id + "");
    assigned.addVariable("$assignee", TicketManager.formatNames(assignedList));
    assigned.translate(SimpleTicketManager.instance.defaultWorld, sender);
    return true;
  }
}
