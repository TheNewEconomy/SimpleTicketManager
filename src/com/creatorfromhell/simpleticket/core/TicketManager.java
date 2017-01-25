package com.creatorfromhell.simpleticket.core;

import com.creatorfromhell.simpleticket.SimpleTicketManager;
import com.creatorfromhell.simpleticket.core.ticket.Ticket;
import com.creatorfromhell.simpleticket.core.ticket.TicketStatus;
import com.creatorfromhell.simpleticket.listeners.CommentListener;
import com.creatorfromhell.simpleticket.listeners.TicketListener;
import com.github.tnerevival.core.collection.EventMap;
import com.github.tnerevival.user.IDFinder;

import java.util.*;

/**
 * Created by creatorfromhell on 1/2/2017.
 **/
public class TicketManager {
  public EventMap<Integer, Ticket> tickets = new EventMap<>();

  public TicketListener ticketListener = new TicketListener();
  public CommentListener commentListener = new CommentListener();

  public TicketManager() {
    tickets.setListener(ticketListener);
  }

  public Collection<Ticket> sort(String server, String status, String assigned) {
    TreeMap<Integer, Ticket> sorted = new TreeMap<>();

    Collection<Ticket> values = tickets.values();

    for(Ticket t : values) {
      if(!server.equalsIgnoreCase("all") && !server.equalsIgnoreCase(t.getServer())) continue;
      if(!status.equalsIgnoreCase("all") && !status.equalsIgnoreCase(t.getStatus().getName())) continue;
      if(!assigned.equalsIgnoreCase("all") && !assigned.equalsIgnoreCase("no one") && !t.isAssigned(IDFinder.getID(assigned)) || assigned.equalsIgnoreCase("no one") && t.getAssigned().size() != 0) continue;
      sorted.put(t.getId(), t);
    }
    return sorted.values();
  }

  public int getAssignedCount(UUID player) {
    int assigned = 0;

    for(Ticket ticket : tickets.values()) {
      if(ticket.isAssigned(player)) {
        assigned++;
      }
    }
    return assigned;
  }

  public int freeID() {
    return tickets.size() + 1;
  }

  public int freeCommentID(int ticket) {
    return tickets.get(ticket).freeCommentID();
  }

  public boolean exists(Integer id) {
    return tickets.containsKey(id);
  }

  public Ticket getTicket(Integer id) {
    return tickets.get(id);
  }

  public static String[] namesToArray(String names) {
    return (names.contains(","))? names.split(",") : new String[] { names };
  }

  public static String formatNames(Collection<String> names) {
    if(names.size() == 0) return "no one";
    String[] namesArray = names.toArray(new String[names.size()]);

    StringBuilder builder = new StringBuilder();

    for(int i = 0; i < namesArray.length; i++) {
      if(i > 0) {
        String addition = (i < namesArray.length - 1)? ", " : " and ";
        builder.append(addition);
      }
      builder.append(namesArray[i]);
    }
    return builder.toString();
  }

  public static String formatNames(String name) {
    String result = name;

    if(result.contains(",")) {
      int index = result.lastIndexOf(",");

      //Safety check
      if(index >= 0) {
        result = result.substring(0, index) + " and " + result.substring(index + 1);
      }
    }
    return result;
  }

  public void ban(UUID id) {
    String table = SimpleTicketManager.instance.sqlManager.getPrefix() + "_BANNED";
    SimpleTicketManager.instance.sqlManager.mysql().executePreparedUpdate("INSERT INTO " + table + " (banned_player) VALUES (?)", new Object[] {
      id.toString()
    });
    SimpleTicketManager.instance.sqlManager.mysql().close();
  }

  public void unban(UUID id) {
    String table = SimpleTicketManager.instance.sqlManager.getPrefix() + "_BANNED";
    SimpleTicketManager.instance.sqlManager.mysql().executePreparedUpdate("DELETE FROM " + table + " WHERE banned_player = ?", new Object[] {
      id.toString()
    });
    SimpleTicketManager.instance.sqlManager.mysql().close();
  }

  public int getOpened() {
    int open = 0;

    for(Ticket ticket : tickets.values()) {
      if(ticket.getStatus().equals(TicketStatus.OPEN)) {
        open++;
      }
    }
    return open;
  }

  public int getAssigned() {
    int assigned = 0;

    for(Ticket ticket : tickets.values()) {
      if(ticket.getStatus().equals(TicketStatus.ASSIGNED)) {
        assigned++;
      }
    }
    return assigned;
  }

  public int getClosed() {
    int closed = 0;

    for(Ticket ticket : tickets.values()) {
      if(ticket.getStatus().equals(TicketStatus.CLOSED)) {
        closed++;
      }
    }
    return closed;
  }
}