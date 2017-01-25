package com.creatorfromhell.simpleticket.commands.ticket;

import com.creatorfromhell.simpleticket.SimpleTicketManager;
import com.creatorfromhell.simpleticket.core.ticket.Ticket;
import com.creatorfromhell.simpleticket.listeners.CommentListener;
import com.github.tnerevival.commands.TNECommand;
import com.github.tnerevival.core.Message;
import com.github.tnerevival.user.IDFinder;
import org.bukkit.command.CommandSender;

/**
 * Created by creatorfromhell on 1/7/2017.
 **/
public class TicketInfoCommand extends TNECommand {
  public TicketInfoCommand(SimpleTicketManager plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "info";
  }

  @Override
  public String[] getAliases() {
    return new String[] {
        "i"
    };
  }

  @Override
  public String getNode() {
    return "simpletickets.ticket.info";
  }

  @Override
  public boolean console() {
    return false;
  }

  @Override
  public String[] getHelpLines() {
    return new String[] {
        "/ticket info <id>",
        "- View information about a ticket."
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
    String comments = (t.comments.size() == 1)? "comment" : "comments";

    Message info = new Message("Messages.General.Ticket");
    info.addVariable("$id", id + "");
    info.addVariable("$author", IDFinder.getUsername(t.getPlayer().toString()) + "");
    info.addVariable("$created", SimpleTicketManager.formatTime(t.getCreated()));
    info.addVariable("$status", t.getStatus().name());
    info.addVariable("$comments", ((CommentListener)t.comments.getListener()).values(t.getId()).size() + "");
    info.addVariable("$comments_string", comments + "");
    info.addVariable("$assignee", t.assigneeString() + "");
    info.addVariable("$player_count", t.getPlayers() + "");
    info.addVariable("$description", t.getDescription());
    info.translate(SimpleTicketManager.instance.defaultWorld, sender);
    return true;
  }
}
