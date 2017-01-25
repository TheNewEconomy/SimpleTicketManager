package com.creatorfromhell.simpleticket.commands.ticket;

import com.creatorfromhell.simpleticket.SimpleTicketManager;
import com.creatorfromhell.simpleticket.core.ticket.Ticket;
import com.creatorfromhell.simpleticket.core.ticket.TicketComment;
import com.creatorfromhell.simpleticket.listeners.CommentListener;
import com.github.tnerevival.commands.TNECommand;
import com.github.tnerevival.core.Message;
import com.github.tnerevival.core.collection.paginate.Page;
import com.github.tnerevival.core.collection.paginate.Paginator;
import com.github.tnerevival.user.IDFinder;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * Created by creatorfromhell on 1/7/2017.
 **/
public class TicketCommentsCommand extends TNECommand {
  public TicketCommentsCommand(SimpleTicketManager plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "comments";
  }

  @Override
  public String[] getAliases() {
    return new String[] {
        "c"
    };
  }

  @Override
  public String getNode() {
    return "simpletickets.ticket.comments";
  }

  @Override
  public boolean console() {
    return false;
  }

  @Override
  public String[] getHelpLines() {
    return new String[] {
        "/ticket comments <id> [page]",
        "- View the comments of a ticket.",
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
    Paginator paginator = new Paginator(Arrays.asList(new LinkedHashSet<Object>(((CommentListener)t.comments.getListener()).values(id)).toArray()), SimpleTicketManager.instance.configurations.getInt("Core.Tickets.MaxComments"));

    int page = 1;
    if(arguments.length >= 2) {
      page = Integer.valueOf(arguments[1]);
    }

    if(page > paginator.getMaxPages()) page = paginator.getMaxPages();

    Page p = paginator.getPage(page);
    Message header = new Message("Messages.General.CommentsHeader");
    header.addVariable("$id", t.getId() + "");
    header.addVariable("$page", page + "");
    header.addVariable("$max", paginator.getMaxPages() + "");
    header.translate(SimpleTicketManager.instance.defaultWorld, sender);

    for(Object o : p.getElements()) {
      TicketComment comment = (TicketComment)o;

      Message commentMessage = new Message("Messages.General.Comment");
      commentMessage.addVariable("$id", comment.getId() + "");
      commentMessage.addVariable("$author", IDFinder.getUsername(comment.getPlayer().toString()));
      commentMessage.addVariable("$created", SimpleTicketManager.formatTime(comment.getCreated()));
      commentMessage.addVariable("$comment", comment.getComment());
      commentMessage.translate(SimpleTicketManager.instance.defaultWorld, sender);
    }
    return true;
  }
}