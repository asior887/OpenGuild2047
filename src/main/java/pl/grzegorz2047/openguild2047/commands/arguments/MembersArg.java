/*
 * The MIT License
 *
 * Copyright 2014 Grzegorz.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pl.grzegorz2047.openguild2047.commands.arguments;

import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.grzegorz2047.openguild2047.Data;
import pl.grzegorz2047.openguild2047.GenConf;
import pl.grzegorz2047.openguild2047.SimpleGuild;
import pl.grzegorz2047.openguild2047.managers.MsgManager;

/**
 *
 * @author Grzegorz
 */
public class MembersArg {

    public static boolean execute(CommandSender sender) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(GenConf.prefix + MsgManager.cmdonlyforplayer);
            return true;
        }
        Player p = (Player) sender;
        if(Data.getInstance().isPlayerInGuild(p.getUniqueId())) {
            List<UUID> members = Data.getInstance().getPlayersGuild(p.getUniqueId()).getMembers();
            StringBuilder sb = new StringBuilder();
            if(members.size() != 1) {
                int size = 1;
                for(int i = 0; i < members.size(); i++) {
                    size++;
                    SimpleGuild sg = Data.getInstance().getPlayersGuild(p.getUniqueId());
                    UUID member = sg.getMembers().get(i);
                    OfflinePlayer nick = Bukkit.getOfflinePlayer(member);
                    if(sg.getLeader().equals(member)) {
                        sb.append(ChatColor.GOLD);
                        sb.append("(Lider) ");
                    }
                    if(i % 5 != 0) {
                        if(nick != null && nick.isOnline()) {
                            sb.append(ChatColor.GREEN);
                        } else {
                            sb.append(ChatColor.DARK_GRAY);
                        }
                        sb.append(nick.getName());
                        sb.append(ChatColor.DARK_GRAY);
                        sb.append(", ");
                    } else {
                        sb.append("\n");
                    }
                }
                sender.sendMessage(ChatColor.DARK_GRAY + " ----------------- " + ChatColor.GOLD + MsgManager.getIgnorePref("titlemems") + ChatColor.DARK_GRAY + " (" + size + ") ----------------- ");
                p.sendMessage(sb.toString());
                return true;
            } else {
                p.sendMessage(MsgManager.nomembersinguild);
                return true;
            }
        } else {
            p.sendMessage(MsgManager.notinguild);
            return true;
        }
    }

}
