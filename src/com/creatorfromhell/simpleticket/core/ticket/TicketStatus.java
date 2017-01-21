package com.creatorfromhell.simpleticket.core.ticket;

/**
 * Created by creatorfromhell on 1/2/2017.
 **/
public enum TicketStatus {

  OPEN(0, "Open"),
  ASSIGNED(1, "Assigned"),
  CLOSED(2, "Closed");

  private Integer id;
  private String name;

  TicketStatus(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public Integer getID() {
    return id;
  }

  public String getName() {
    return name;
  }

  public static TicketStatus fromID(int id) {
    for(TicketStatus status : values()) {
      if(status.getID() == id) return status;
    }
    return TicketStatus.OPEN;
  }
}