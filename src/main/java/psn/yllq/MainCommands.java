package psn.yllq;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MainCommands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1 && strings[0].equals("reload")) {
            JoinTP.main.reloadConfig();
            commandSender.sendMessage("&9[JoinTP] &aConfig reloaded!");
            return true;
        }
        return false;
    }
}
