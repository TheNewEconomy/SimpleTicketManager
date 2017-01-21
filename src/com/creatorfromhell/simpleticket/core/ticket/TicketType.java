package com.creatorfromhell.simpleticket.core.ticket;

/**
 * Created by creatorfromhell on 1/2/2017.
 **/
public enum TicketType {

  GENERAL("Inquiry"),
  BUG("Bug"),
  REPORT("Player Report");

  private String name;

  TicketType(String name) {
    this.name = name;
  }
}