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
package com.creatorfromhell.simpleticket.listeners;

import com.creatorfromhell.simpleticket.SimpleTicketManager;
import com.creatorfromhell.simpleticket.core.ticket.TicketComment;
import com.github.tnerevival.core.collection.MapListener;

import java.util.*;

/**
 * Created by creatorfromhell on 1/16/2017.
 **/
public class CommentListener implements MapListener {
  Map<Integer, TicketComment> changed = new HashMap<>();

  @Override
  public void update() {

    for(TicketComment comment : changed.values()) {
      SimpleTicketManager.instance.sqlManager.saveComment(comment);
    }
  }

  @Override
  public Map changed() {
    return changed;
  }

  @Override
  public void clearChanged() {
    changed.clear();
  }

  @Override
  public void put(Object key, Object value) {
    SimpleTicketManager.instance.sqlManager.saveComment((TicketComment)value);
  }

  @Override
  public Object get(Object key) {
    return null;
  }

  @Override
  public Collection values() {
    return SimpleTicketManager.instance.sqlManager.loadComments();
  }

  public Collection values(int ticket) {
    return SimpleTicketManager.instance.sqlManager.loadComments(ticket);
  }

  @Override
  public int size() {
    return values().size();
  }

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public boolean containsKey(Object key) {
    return get(key) != null;
  }

  @Override
  public boolean containsValue(Object key) {
    return false;
  }

  @Override
  public void preRemove(Object key, Object value) {

  }

  @Override
  public Set keySet() {
    return null;
  }

  @Override
  public Set<Map.Entry> entrySet() {
    return null;
  }

  @Override
  public void remove(Object key) {

  }
}