/*
 * The New Economy Minecraft Server Plugin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.

 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.creatorfromhell.simpleticket;

import com.creatorfromhell.simpleticket.core.ticket.Ticket;
import com.creatorfromhell.simpleticket.core.ticket.TicketComment;
import com.creatorfromhell.simpleticket.core.ticket.TicketStatus;
import com.github.tnerevival.core.SQLManager;
import com.github.tnerevival.serializable.SerializableLocation;

import java.util.*;

/**
 * Created by creatorfromhell on 1/16/2017.
 **/
public class SimpleSQLManager extends SQLManager {
  public SimpleSQLManager(String mysqlHost, Integer mysqlPort, String mysqlDatabase,
                          String mysqlUser, String mysqlPassword, String prefix,
                          String h2File, String sqliteFile, String flatfile
  ) {
    super(mysqlHost, mysqlPort, mysqlDatabase, mysqlUser, mysqlPassword, prefix, h2File, sqliteFile, flatfile);
  }

  public boolean containsTicket(int id) {
    String table = prefix + "_TICKETS";
    try {
      int ticketIndex = sql().executePreparedQuery("SELECT * FROM " + table + " WHERE ticket_id = ?", new Object[]{
          id
      });
      return sql().results(ticketIndex).next();
    } catch(Exception e) {

    }
    return false;
  }

  public void saveTicket(Ticket ticket) {
    String table = prefix + "_TICKETS";
    sql().executePreparedUpdate("INSERT INTO `" + table + "` (ticket_id, ticket_created, ticket_closed, ticket_author, ticket_server, ticket_location, ticket_players, " +
            "ticket_assigned, ticket_closedby, ticket_description, ticket_closereason, ticket_status) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" +
            " ON DUPLICATE KEY UPDATE ticket_closed = ?, ticket_assigned = ?, ticket_closedby = ?, ticket_closereason = ?, ticket_status = ?",
        new Object[] {
            ticket.getId(),
            ticket.getCreated(),
            ticket.getClosed(),
            ticket.getPlayer().toString(),
            ticket.getServer(),
            new SerializableLocation(ticket.getLocation()).toString(),
            ticket.getPlayers(),
            ticket.getAssignmentString(),
            (ticket.getClosedBy() == null)? "" : ticket.getClosedBy().toString(),
            ticket.getDescription(),
            ticket.getCloseReason(),
            ticket.getStatus().getID(),
            ticket.getClosed(),
            ticket.getAssignmentString(),
            (ticket.getClosedBy() == null)? "" : ticket.getClosedBy().toString(),
            ticket.getCloseReason(),
            ticket.getStatus().getID(),
        }
    );


    for(TicketComment comment : ticket.comments.values()) {
      saveComment(comment);
    }
    sql().close();
  }

  public Collection<Ticket> loadTickets() {
    List<Ticket> ticketList = new ArrayList<>();

    String table = getPrefix() + "_TICKETS";
    try {
      int ticketIndex = sql().executeQuery("SELECT * FROM " + table + " ORDER BY ticket_id ASC;");
      while (sql().results(ticketIndex).next()) {
        Ticket ticket = new Ticket(sql().results(ticketIndex).getInt("ticket_id"), UUID.fromString(sql().results(ticketIndex).getString("ticket_author")), TicketStatus.fromID(sql().results(ticketIndex).getInt("ticket_status")));
        ticket.setCreated(sql().results(ticketIndex).getLong("ticket_created"));
        ticket.setClosed(sql().results(ticketIndex).getLong("ticket_closed"));
        ticket.setServer(sql().results(ticketIndex).getString("ticket_server"));
        ticket.setLocation(SerializableLocation.fromString(sql().results(ticketIndex).getString("ticket_location")).getLocation());
        ticket.setPlayers(sql().results(ticketIndex).getInt("ticket_players"));
        ticket.assignmentFromString(sql().results(ticketIndex).getString("ticket_assigned"));
        String closedBy = sql().results(ticketIndex).getString("ticket_closedby");
        ticket.setClosedBy((closedBy.trim().equals(""))? null : UUID.fromString(closedBy));
        ticket.setDescription(sql().results(ticketIndex).getString("ticket_description"));
        ticket.setCloseReason(sql().results(ticketIndex).getString("ticket_closereason"));
        ticket.setStatus(TicketStatus.fromID(sql().results(ticketIndex).getInt("ticket_status")));                                                                                       ;
        ticketList.add(ticket);
      }
      sql().close();
    } catch(Exception e) {
      e.printStackTrace();
    }
    return ticketList;
  }

  public Ticket loadTicket(int id) {
    String table = prefix + "_TICKETS";
    try {
      int ticketIndex = sql().executePreparedQuery("SELECT * FROM " + table + " WHERE ticket_id = ?", new Object[] {
          id
      });
      if(sql().results(ticketIndex).next()) {
        Ticket ticket = new Ticket(sql().results(ticketIndex).getInt("ticket_id"), UUID.fromString(sql().results(ticketIndex).getString("ticket_author")), TicketStatus.fromID(sql().results(ticketIndex).getInt("ticket_status")));
        ticket.setCreated(sql().results(ticketIndex).getLong("ticket_created"));
        ticket.setClosed(sql().results(ticketIndex).getLong("ticket_closed"));
        ticket.setServer(sql().results(ticketIndex).getString("ticket_server"));
        ticket.setLocation(SerializableLocation.fromString(sql().results(ticketIndex).getString("ticket_location")).getLocation());
        ticket.setPlayers(sql().results(ticketIndex).getInt("ticket_players"));
        ticket.assignmentFromString(sql().results(ticketIndex).getString("ticket_assigned"));
        String closedBy = sql().results(ticketIndex).getString("ticket_closedby");
        ticket.setClosedBy((closedBy.trim().equals(""))? null : UUID.fromString(closedBy));
        ticket.setDescription(sql().results(ticketIndex).getString("ticket_description"));
        ticket.setCloseReason(sql().results(ticketIndex).getString("ticket_closereason"));
        ticket.setStatus(TicketStatus.fromID(sql().results(ticketIndex).getInt("ticket_status")));
        ticket.setComments(loadComments(id));
        sql().close();
        return ticket;
      }
    } catch(Exception e) {
    }
    return null;
  }

  public void saveComment(TicketComment comment) {
    String table = SimpleTicketManager.instance.sqlManager.getPrefix() + "_COMMENTS";
    sql().executePreparedUpdate("INSERT INTO `" + table + "` (comment_id, comment_ticket, comment_created, comment_author, comment_text) " +
            "VALUES(?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE comment_created = ?, comment_author = ?, comment_text = ?",
        new Object[] {
            comment.getId(),
            comment.getTicket(),
            comment.getCreated(),
            comment.getPlayer().toString(),
            comment.getComment(),
            comment.getCreated(),
            comment.getPlayer().toString(),
            comment.getComment(),
        }
    );
    sql().close();
  }

  public Collection<TicketComment> loadComments() {
    TreeMap<Integer, TicketComment> comments = new TreeMap<>();

    String table = SimpleTicketManager.instance.sqlManager.getPrefix() + "_COMMENTS";
    try {
      int commentIndex = sql().executeQuery("SELECT * FROM " + table + " ORDER BY comment_id ASC;");
      while (sql().results(commentIndex).next()) {
        TicketComment comment = new TicketComment(sql().results(commentIndex).getInt("comment_ticket"), sql().results(commentIndex).getInt("comment_id"), UUID.fromString(sql().results(commentIndex).getString("comment_author")));
        comment.setCreated(sql().results(commentIndex).getLong("comment_created"));
        comment.setComment(sql().results(commentIndex).getString("comment_text"));
        comments.put(comment.getId(), comment);
      }
      sql().close();
    } catch(Exception e) {
    }
    return comments.values();
  }

  public Collection<TicketComment> loadComments(int ticket) {
    List<TicketComment> commentList = new ArrayList<>();

    String table = SimpleTicketManager.instance.sqlManager.getPrefix() + "_COMMENTS";
    try {
      int commentIndex = sql().executePreparedQuery("SELECT * FROM " + table + " WHERE comment_ticket = ? ORDER BY comment_id ASC;", new Object[] {
        ticket
      });
      while (sql().results(commentIndex).next()) {
        TicketComment comment = new TicketComment(sql().results(commentIndex).getInt("comment_ticket"), sql().results(commentIndex).getInt("comment_id"), UUID.fromString(sql().results(commentIndex).getString("comment_author")));
        comment.setCreated(sql().results(commentIndex).getLong("comment_created"));
        comment.setComment(sql().results(commentIndex).getString("comment_text"));
        commentList.add(comment);
      }
      sql().close();
    } catch(Exception e) {
    }
    return commentList;
  }

  public TicketComment loadComment(int id, int ticket) {
    String table = SimpleTicketManager.instance.sqlManager.getPrefix() + "_COMMENTS";
    try {
      int commentIndex = sql().executePreparedQuery("SELECT * FROM " + table + " WHERE comment_id = ? AND comment_ticket = ?", new Object[] {
          id,
          ticket
      });
      if(sql().results(commentIndex).next()) {
        TicketComment comment = new TicketComment(ticket, id, UUID.fromString(sql().results(commentIndex).getString("comment_author")));
        comment.setCreated(sql().results(commentIndex).getLong("comment_created"));
        comment.setComment(sql().results(commentIndex).getString("comment_text"));
        sql().close();
        return comment;
      }
    } catch(Exception e) {
    }
    return null;
  }

  public void load() {
    if(SimpleTicketManager.instance.cache) {
      Collection<Ticket> tickets = loadTickets();

      for(Ticket ticket : tickets) {
        SimpleTicketManager.instance.manager.tickets.put(ticket.getId(), ticket, true);
      }
    }
  }

  public void save() {
    if(SimpleTicketManager.instance.cache) {
      for (Ticket ticket : SimpleTicketManager.instance.manager.tickets.values()) {
        saveTicket(ticket);
      }
    }
  }
}
