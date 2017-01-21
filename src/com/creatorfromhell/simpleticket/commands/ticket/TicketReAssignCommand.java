package com.creatorfromhell.simpleticket.commands.ticket;

import com.creatorfromhell.simpleticket.SimpleTicketManager;
import com.creatorfromhell.simpleticket.core.TicketManager;
import com.creatorfromhell.simpleticket.core.ticket.Assignment;
import com.creatorfromhell.simpleticket.core.ticket.Ticket;
import com.github.tnerevival.commands.TNECommand;
import com.github.tnerevival.core.Message;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by creatorfromhell on 1/7/2017.
 **/
public class TicketReAssignCommand extends TNECommand {
  public TicketReAssignCommand(SimpleTicketManager plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "reassign";
  }

  @Override
  public String[] getAliases() {
    return new String[] {
        "ra"
    };
  }

  @Override
  public String getNode() {
    return "simpletickets.ticket.reassign";
  }

  @Override
  public boolean console() {
    return false;
  }

  @Override
  public String[] getHelpLines() {
    return new String[] {
        "/ticket reassign <id> [username]",
        "- Reassigns a ticket to a staff member. This will override all existing assignees.",
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


    Ticket t = SimpleTicketManager.instance.manager.getTicket(id);
    t.setAssigned(new ArrayList<Assignment>());
    List<String> assignedList = t.assign(getPlayer(sender).getName(), assignee);
    SimpleTicketManager.instance.manager.tickets.put(id, t);

    Message reassigned = new Message("Messages.Command.ReAssigned");
    reassigned.addVariable("$id", id + "");
    reassigned.addVariable("$assignee", TicketManager.formatNames(assignedList));
    reassigned.translate(SimpleTicketManager.instance.defaultWorld, sender);
    return true;
  }
}