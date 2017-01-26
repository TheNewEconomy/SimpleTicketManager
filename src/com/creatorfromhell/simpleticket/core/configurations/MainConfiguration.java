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
 * Created by creatorfromhell on 1/7/2017.
 **/
public class MainConfiguration extends Configuration {
  @Override
  public FileConfiguration getConfiguration() {
    return SimpleTicketManager.instance.getConfig();
  }

  @Override
  public String node() {
    return "Core";
  }

  @Override
  public void load(FileConfiguration configurationFile) {
    configurations.put("Core.Time.Format", "M, d y");
    configurations.put("Core.Time.Timezone", "US/Eastern");
    configurations.put("Core.Tickets.Max", 10);
    configurations.put("Core.Tickets.MaxTickets", 5);
    configurations.put("Core.Tickets.MaxComments", 5);
    configurations.put("Core.Tickets.OthersClose", false);
    configurations.put("Core.Tickets.Broadcast.New", true);
    configurations.put("Core.Tickets.Broadcast.Join", true);
    configurations.put("Core.Tickets.Broadcast.Close", true);

    configurations.put("Core.Server.Specific", false);

    configurations.put("Core.SQL.Transactions.Cache", true);
    configurations.put("Core.SQL.Transactions.Update", 600);
    configurations.put("Core.SQL.Format", "mysql");
    configurations.put("Core.SQL.H2File", "Tickets");
    configurations.put("Core.SQL.Prefix", "ST");
    configurations.put("Core.SQL.Host", "localhost");
    configurations.put("Core.SQL.Port", 3306);
    configurations.put("Core.SQL.Database", "Tickets");
    configurations.put("Core.SQL.User", "user");
    configurations.put("Core.SQL.Password", "password");

    super.load(configurationFile);
  }
}