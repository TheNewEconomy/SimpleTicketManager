package com.creatorfromhell.simpleticket.core.ticket;

import java.util.UUID;

/**
 * Created by creatorfromhell on 1/7/2017.
 **/
public class Assignment {

  private UUID assignee;
  private UUID assigner;
  private long date;

  public Assignment(UUID assignee, UUID assigner, long date) {
    this.assignee = assignee;
    this.assigner = assigner;
    this.date = date;
  }

  @Override
  public String toString() {
    return assignee.toString() + ":" + assigner.toString() + ":" + date;
  }

  public static Assignment fromString(String data) {
    String[] parsed = data.split(":");

    return new Assignment(UUID.fromString(parsed[0]), UUID.fromString(parsed[1]), Long.valueOf(parsed[2]));
  }

  public UUID getAssignee() {
    return assignee;
  }

  public void setAssignee(UUID assignee) {
    this.assignee = assignee;
  }

  public UUID getAssigner() {
    return assigner;
  }

  public void setAssigner(UUID assigner) {
    this.assigner = assigner;
  }

  public long getDate() {
    return date;
  }

  public void setDate(long date) {
    this.date = date;
  }
}