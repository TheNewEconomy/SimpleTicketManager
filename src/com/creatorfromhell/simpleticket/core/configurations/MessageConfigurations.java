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
package com.creatorfromhell.simpleticket.core.configurations;

import com.creatorfromhell.simpleticket.SimpleTicketManager;
import com.github.tnerevival.core.configurations.Configuration;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by creatorfromhell on 1/17/2017.
 **/
public class MessageConfigurations extends Configuration {
  @Override
  public FileConfiguration getConfiguration() {
    return SimpleTicketManager.instance.messageConfigurations;
  }

  @Override
  public String node() {
    return "Messages";
  }

  @Override
  public void load(FileConfiguration configurationFile) {
    configurations.put("Messages.General.Statistics", "<white>====== [<green>SimpleTicketManager<white>] ======<newline><white>There has been a total of <green>$total<white> tickets created.<newline><white>Of these, <green>$open<white> are open, <green>$assigned<white> have been assigned, and <green>$closed<white> have been closed.");
    configurations.put("Messages.General.Ticket", "<white>Ticket <green>$id<newline>Created by <green>$author<white>on<green>$created<white>.<newline>Status is <green>$status<white> with <green>$comments<white> $comments_string.<newline>Currently assigned <green>$assignee<white>.<newline>Players Online <green>$player_count<newline>$description");
    configurations.put("Messages.General.Comment", "<white>Comment <green>#$id<white> posted by <green>$author<white> on <green>$created<newline>$comment");
    configurations.put("Messages.General.CommentsHeader", "<white>Comments for Ticket <green>#$id<white>. Page <green>$page<white>/$max.");
    configurations.put("Messages.General.TicketHeader", "<white>====== [<green>Simple Tickets<white>] ====== Page <green>$page<white>/$max.");
    configurations.put("Messages.General.TicketShort", "<white>Ticket <green>$id<newline>$description");
    configurations.put("Messages.General.New", "<white>A new ticket has been opened. Type /ticket info $id to view it.");
    configurations.put("Messages.General.Close", "<white>Ticket <green>#$id<white> has been closed by <green>$username<white>.");
    configurations.put("Messages.General.CantClose", "<red>You must be assigned to this ticket in order to close it.");
    configurations.put("Messages.General.InvalidInteger", "<red>An integer was expected, but instead $value was received.");
    configurations.put("Messages.General.MaxAssigned", "<red>I'm sorry, but $username has been assigned to the max number of tickets.");
    configurations.put("Messages.Command.NoPlayer", "<red>I'm sorry, but there is no such player \"$username\".");
    configurations.put("Messages.Command.None", "<red>I'm sorry, but there is no ticket with the id \"$id\".");
    configurations.put("Messages.Command.Assigned", "<white>You have successfully assigned $assignee to ticket <green>#$id<white>.");
    configurations.put("Messages.Command.UnAssigned", "<white>You have successfully unassigned $assignee from ticket <green>#$id<white>.");
    configurations.put("Messages.Command.ReAssigned", "<white>You have successfully reassigned ticked <green>#$id<white> to $assignee.");
    configurations.put("Messages.Command.Banned", "<white>Successfully banned player \"$username\" from the ticket system.");
    configurations.put("Messages.Command.UnBanned", "<white>Successfully unbanned player \"$username\" from the ticket system.");
    configurations.put("Messages.Command.Closed", "<white>Ticket <green>#$id<white> has been closed by $username.");
    configurations.put("Messages.Command.Opened", "<white>Ticket <green>#$id<white> has been reopened by $username.");
    configurations.put("Messages.Command.Teleported", "<white>You have been teleported to the location where ticket <green>#$id<white> was created.");
    configurations.put("Messages.Command.Created", "<white>Your ticket has been created! Your ticket id is <green>$id<white>. You may view future ticket updates using /ticket info $id.<newline><white>You may also view your ticket's comments using /ticket comments $id.");
    configurations.put("Messages.Command.Comment", "<white>Your comment has been posted for ticket <green>#$id<white>.");

    super.load(configurationFile);
  }
}