package com.creatorfromhell.simpleticket.core.ticket;

import java.util.UUID;

/**
 * Created by creatorfromhell on 1/2/2017.
 **/
public class TicketComment {
  private int ticket;
  private int id;
  private long created;
  private UUID player;
  private String comment;

  public TicketComment(int ticket, int id, UUID player) {
    this.ticket = ticket;
    this.id = id;
    this.player = player;
  }

  public int getTicket() {
    return ticket;
  }

  public void setTicket(int ticket) {
    this.ticket = ticket;
  }

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

  public UUID getPlayer() {
    return player;
  }

  public void setPlayer(UUID player) {
    this.player = player;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}