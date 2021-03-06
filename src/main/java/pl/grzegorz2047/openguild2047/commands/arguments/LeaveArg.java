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

import com.github.grzegorz2047.openguild.event.MessageBroadcastEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.grzegorz2047.openguild2047.Data;
import pl.grzegorz2047.openguild2047.GenConf;
import pl.grzegorz2047.openguild2047.SimplePlayerGuild;
import pl.grzegorz2047.openguild2047.api.Guild;
import pl.grzegorz2047.openguild2047.api.Guilds;
import pl.grzegorz2047.openguild2047.database.SQLHandler;
import pl.grzegorz2047.openguild2047.managers.MsgManager;
import pl.grzegorz2047.openguild2047.managers.TagManager;

/**
 *
 * @author Grzegorz
 */
public class LeaveArg {

    public static boolean execute(CommandSender sender) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(GenConf.prefix + MsgManager.cmdonlyforplayer);
            return true;
        }
        Player p = (Player) sender;
        if(Data.getInstance().isPlayerInGuild(p.getUniqueId())) {
            if(Data.getInstance().getPlayersGuild(p.getUniqueId()).getLeader().equals(p.getUniqueId())) {
                p.sendMessage(GenConf.prefix + "Jezeli chcesz to zrobic wpisz /gildia rozwiaz!");
                return true;
            }
            saveDb(Guilds.getGuild(p), p);
            TagManager.removeTag(p.getUniqueId());
            final SimplePlayerGuild sg = Data.getInstance().guildsplayers.get(p.getUniqueId());
            Data.getInstance().getPlayersGuild(p.getUniqueId()).removeMember(p.getUniqueId());
            Data.getInstance().guildsplayers.remove(p.getUniqueId());
            p.sendMessage(MsgManager.leaveguildsuccess);
            
            // Event
            MessageBroadcastEvent event = new MessageBroadcastEvent(MessageBroadcastEvent.Message.LEAVE);
            Bukkit.getPluginManager().callEvent(event);
            if(!event.isCancelled()) {
                Bukkit.broadcastMessage(event.getMessage().replace("{TAG}", sg.getClanTag().toUpperCase()).replace("{PLAYER}", sender.getName()));
            }
        } else {
            p.sendMessage(MsgManager.notinguild);
        }
        return true;
    }

    private static void saveDb(Guild guild, Player player) {
        // TODO Tu trzeba zrobic pobieranie gildii, String -> ArrayList, potem usuwanie gracza i ArrayList<String>
        //Nie za bardzo wiem co z TODO, ale na razie tyle wystarczy
        SQLHandler.update(player.getUniqueId(), SQLHandler.PType.GUILD, "");
    }

}
