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
 * Created by creatorfromhell on 1/3/2017.
 **/
public class TicketCreateCommand extends TNECommand {
  public TicketCreateCommand(SimpleTicketManager plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "create";
  }

  @Override
  public String[] getAliases() {
    return new String[] {
      "+"
    };
  }

  @Override
  public String getNode() {
    return "simpletickets.ticket.create";
  }

  @Override
  public boolean console() {
    return false;
  }

  @Override
  public String[] getHelpLines() {
    return new String[] {
        "/ticket create <description>",
        "- Creates a new ticket."
    };
  }

  @Override
  public boolean execute(CommandSender sender, String command, String[] arguments) {
    if(arguments.length < 2) {
      help(sender);
      return false;
    }
    Integer id = SimpleTicketManager.instance.manager.freeID();
    String description = SimpleTicketManager.join(Arrays.copyOfRange(arguments, 1, arguments.length), " ");

    Ticket t = new Ticket(id, IDFinder.getID(getPlayer(sender)), TicketStatus.OPEN);
    t.setCreated(new Date().getTime());
    t.setServer(SimpleTicketManager.instance.getServer().getServerName());
    t.setLocation(getPlayer(sender).getLocation());
    t.setPlayers(SimpleTicketManager.instance.getServer().getOnlinePlayers().size());
    t.setDescription(description);

    SimpleTicketManager.instance.manager.tickets.put(id, t);

    Message created = new Message("Messages.General.Created");
    created.addVariable("$id", id + "");
    created.translate(SimpleTicketManager.instance.defaultWorld, sender);

    if(SimpleTicketManager.instance.api.getBoolean("Core.Tickets.Broadcast.New")) {
      for(Player p : plugin.getServer().getOnlinePlayers()) {
        if(p.hasPermission("simpletickets.staff.new")) {
          Message statistics = new Message("Messages.General.New");
          statistics.addVariable("$id", t.getId() + "");
          statistics.translate(SimpleTicketManager.instance.defaultWorld, p);
        }
      }
    }
    return true;
  }
}
