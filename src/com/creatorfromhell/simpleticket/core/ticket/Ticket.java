package com.creatorfromhell.simpleticket.core.ticket;

import com.creatorfromhell.simpleticket.SimpleTicketManager;
import com.creatorfromhell.simpleticket.core.TicketManager;
import com.github.tnerevival.core.collection.EventMap;
import com.github.tnerevival.user.IDFinder;
import org.bukkit.Location;

import java.util.*;

/**
 * Created by creatorfromhell on 1/2/2017.
 **/
public class Ticket {
  public EventMap<Integer, TicketComment> comments = new EventMap<>();

  private List<Assignment> assigned = new ArrayList<>();

  private int id;
  private long created;
  private long closed;
  private UUID player;
  private String server;
  private Location location;
  private int players;

  private UUID closedBy;
  private String closeReason;
  private String description;
  private TicketStatus status;

  public Ticket(int id, UUID player, TicketStatus status) {
    this.id = id;
    this.player = player;
    this.status = status;
    comments.setListener(SimpleTicketManager.instance.manager.commentListener);
  }

  public int freeCommentID() {
    return SimpleTicketManager.instance.sqlManager.loadComments(id).size() + 1;
  }

  public String assigneeString() {
    Assignment[] assignments = assigned.toArray(new Assignment[assigned.size()]);
    StringBuilder builder = new StringBuilder();

    for(int i = 0; i < assignments.length; i++) {
      if(i > 0) {
        String addition = (i < assignments.length - 1)? ", " : " and ";
        builder.append(addition);
      }
      builder.append(IDFinder.getPlayer(assignments[i].getAssignee()).getName());
    }
    return builder.toString();
  }

  public List<String> assign(String assigner, String names) {
    List<String> assignedList = new ArrayList<>();
    String[] nameArray = TicketManager.namesToArray(names);

    for(String s : nameArray) {
      UUID id = IDFinder.getID(s);
      if(id != null && !isAssigned(id)) {
        assignedList.add(s);
        assigned.add(new Assignment(id, IDFinder.getID(assigner), new Date().getTime()));
      }
    }
    return assignedList;
  }

  public List<String> unassign(String assigner, String names) {
    List<String> unassignedList = new ArrayList<>();
    String[] nameArray = TicketManager.namesToArray(names);

    for(String s : nameArray) {
      UUID id = IDFinder.getID(s);
      if(id != null && isAssigned(id)) {
        unassignedList.add(s);
        removeAssignee(id);
      }
    }
    return unassignedList;
  }

  /*
   * Getters and Setters
   */

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public long getCreated() {
    return created;
  }

  public void setCreated(long created) {
    this.created = created;
  }

  public long getClosed() {
    return closed;
  }

  public void setClosed(long closed) {
    this.closed = closed;
  }

  public UUID getPlayer() {
    return player;
  }

  public void setPlayer(UUID player) {
    this.player = player;
  }

  public String getServer() {
    return server;
  }

  public void setServer(String server) {
    this.server = server;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public int getPlayers() {
    return players;
  }

  public void setPlayers(int players) {
    this.players = players;
  }

  public boolean isAssigned(UUID id) {
    for(Assignment a : assigned) {
      if(a.getAssignee().equals(id)) {
        return true;
      }
    }
    return false;
  }

  public void removeAssignee(UUID id) {
    Iterator<Assignment> i = assigned.iterator();

    while(i.hasNext()) {
      Assignment assignment = i.next();
      if(assignment.getAssignee().equals(id)) {
        i.remove();
      }
    }
  }

  public String getAssignmentString() {
    StringBuilder builder = new StringBuilder();

    for(Assignment assignment : assigned) {
      if(builder.length() > 0) builder.append(";");
      builder.append(assignment.toString());
    }
    return builder.toString();
  }

  public void assignmentFromString(String data) {
    String[] assignments = data.split(";");

    for(String s : assignments) {
      assigned.add(Assignment.fromString(s));
    }
  }

  public List<Assignment> getAssigned() {
    return assigned;
  }

  public void setAssigned(List<Assignment> assigned) {
    this.assigned = assigned;
  }

  public UUID getClosedBy() {
    return closedBy;
  }

  public void setClosedBy(UUID closedBy) {
    this.closedBy = closedBy;
  }

  public String getCloseReason() {
    return closeReason;
  }

  public void setCloseReason(String closeReason) {
    this.closeReason = closeReason;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setComments(Collection<TicketComment> comments1) {
    for(TicketComment comment : comments1) {
      comments.put(comment.getId(), comment, true);
    }
  }

  public TicketStatus getStatus() {
    return status;
  }

  public void setStatus(TicketStatus status) {
    this.status = status;
  }
}