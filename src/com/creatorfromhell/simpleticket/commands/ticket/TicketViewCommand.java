package com.creatorfromhell.simpleticket.commands.ticket;

import com.creatorfromhell.simpleticket.SimpleTicketManager;
import com.creatorfromhell.simpleticket.core.ticket.Ticket;
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
public class TicketViewCommand extends TNECommand {
  public TicketViewCommand(SimpleTicketManager plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "view";
  }

  @Override
  public String[] getAliases() {
    return new String[] {
        "v"
    };
  }

  @Override
  public String getNode() {
    return "simpletickets.ticket.view";
  }

  @Override
  public boolean console() {
    return false;
  }

  @Override
  public String[] getHelpLines() {
    return new String[] {
        "/ticket view [server:name,all] [status:open,closed,assigned,all] [assigned:username,all,self,no one)] [page:#]",
        "- Views a list of tickets based on the options.",
        "- All options will default to all."
    };
  }

  @Override
  public boolean execute(CommandSender sender, String command, String[] arguments) {

    String server = "all";
    String status = "all";
    String assigned = "all";
    Integer page = 1;

    for (int i = 0; i < arguments.length; i++) {
      if(arguments[i].contains(":")) {
        String[] split = arguments[i].toLowerCase().split(":");
        switch(split[0]) {
          case "server":
            if(SimpleTicketManager.instance.api.getBoolean("Core.Server.Specific") && !split[1].equalsIgnoreCase("all") && !split[1].equalsIgnoreCase(SimpleTicketManager.instance.getServer().getName())) {
              server = "all";
              break;
            }
            server = split[1];
            break;
          case "status":
            if(split[1].equalsIgnoreCase("open") || split[1].equalsIgnoreCase("closed") || split[1].equalsIgnoreCase("assigned") || split[1].equalsIgnoreCase("all")) {
              status = split[1];
            }
            break;
          case "assigned":
            if(!split[1].equalsIgnoreCase("all") && !split[1].equalsIgnoreCase("self") && !split[1].equalsIgnoreCase("no one") && IDFinder.getID(split[1]) == null) {
              Message noPlayer = new Message("Messages.Command.NoPlayer");
              noPlayer.addVariable("$username", split[1]);
              noPlayer.translate(SimpleTicketManager.instance.defaultWorld, sender);
              return false;
            }

            if(split[1].equalsIgnoreCase("self")) {
              assigned = getPlayer(sender).getName();
              break;
            }
            assigned = split[1];
            break;
          case "page":
            try {
              page = Integer.parseInt(split[1]);
            } catch(NumberFormatException e) {
              Message invalid = new Message("Messages.General.InvalidInteger");
              invalid.addVariable("$value", split[1]);
              invalid.translate(SimpleTicketManager.instance.defaultWorld, sender);
              return false;
            }
            break;
          default:
            break;
        }
      }
    }
    Paginator paginator = new Paginator(Arrays.asList(new LinkedHashSet<Object>(SimpleTicketManager.instance.manager.sort(server, status, assigned)).toArray()), SimpleTicketManager.configurations.getInt("Core.Tickets.MaxTickets"));

    if(page > paginator.getMaxPages()) page = paginator.getMaxPages();

    System.out.println(paginator.getMaxPages());

    Page p = paginator.getPage(page);
    Message header = new Message("Messages.General.TicketHeader");
    header.addVariable("$page", page + "");
    header.addVariable("$max", paginator.getMaxPages() + "");
    header.translate(SimpleTicketManager.instance.defaultWorld, sender);

    for(Object o : p.getElements()) {
      Ticket t = (Ticket)o;

      Message info = new Message("Messages.General.TicketShort");
      info.addVariable("$id", t.getId() + "");
      info.addVariable("$description", t.getDescription());
      info.translate(SimpleTicketManager.instance.defaultWorld, sender);
    }
    return true;
  }
}
