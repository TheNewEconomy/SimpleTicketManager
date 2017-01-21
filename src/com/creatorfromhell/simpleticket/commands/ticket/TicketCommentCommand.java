package com.creatorfromhell.simpleticket.commands.ticket;

import com.creatorfromhell.simpleticket.SimpleTicketManager;
import com.creatorfromhell.simpleticket.core.ticket.Ticket;
import com.creatorfromhell.simpleticket.core.ticket.TicketComment;
import com.github.tnerevival.commands.TNECommand;
import com.github.tnerevival.core.Message;
import com.github.tnerevival.user.IDFinder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/**
 * Created by creatorfromhell on 1/7/2017.
 **/
public class TicketCommentCommand extends TNECommand {
  public TicketCommentCommand(SimpleTicketManager plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "comment";
  }

  @Override
  public String[] getAliases() {
    return new String[] {
        "c+"
    };
  }

  @Override
  public String getNode() {
    return "simpletickets.ticket.comment";
  }

  @Override
  public boolean console() {
    return false;
  }

  @Override
  public String[] getHelpLines() {
    return new String[] {
        "/ticket comment <id> <comment>",
        "- Adds a comment to a ticket."
    };
  }

  @Override
  public boolean execute(CommandSender sender, String command, String[] arguments) {
    if(arguments.length < 2) {
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

    UUID player = IDFinder.getID(getPlayer(sender));
    if(t.getPlayer() != player && !t.isAssigned(player) && !sender.hasPermission("simpletickets.override.comment")) {
      sender.sendMessage(ChatColor.RED + "I'm sorry, but you're not allowed to post a comment on this ticket.");
      return false;
    }

    String comment = SimpleTicketManager.join(Arrays.copyOfRange(arguments, 1, arguments.length), " ");
    TicketComment ticketComment = new TicketComment(id, SimpleTicketManager.instance.manager.freeCommentID(t.getId()), player);
    ticketComment.setCreated(new Date().getTime());
    ticketComment.setComment(comment);
    t.comments.put(ticketComment.getId(), ticketComment);
    SimpleTicketManager.instance.manager.tickets.put(id, t);

    Message commented = new Message("Messages.Command.Comment");
    commented.addVariable("$id", id + "");
    commented.translate(SimpleTicketManager.instance.defaultWorld, sender);
    return true;
  }
}