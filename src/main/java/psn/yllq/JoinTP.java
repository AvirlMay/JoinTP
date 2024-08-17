package psn.yllq;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class JoinTP extends JavaPlugin implements Listener, CommandExecutor {

    private Location targetLocation;

    @Override
    public void onEnable() {
        // 保存默认配置（如果配置文件不存在，将会生成默认配置）
        this.saveDefaultConfig();

        // 从配置文件中加载传送目标位置
        loadLocationFromConfig();

        // 注册事件监听器
        if (targetLocation != null) {
            Bukkit.getPluginManager().registerEvents(this, this);
        } else {
            getLogger().severe("请设置JoinTP的目标坐标点！");
            // 尝试停止服务器
            Bukkit.getScheduler().runTask(this, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop"));
        }

        // 注册命令
        PluginCommand command = this.getCommand("jointp");
        if (command != null) {
            command.setExecutor(this);
        } else {
            getLogger().severe("未能注册命令 '/jointp'，请检查 plugin.yml 文件配置！");
        }

        // 打印插件加载成功的消息
        getLogger().info("JoinTP 插件已启用！");
    }

    @Override
    public void onDisable() {
        // 打印插件卸载的消息
        getLogger().info("JoinTP 插件已禁用！");
    }

    private void loadLocationFromConfig() {
        FileConfiguration config = this.getConfig();

        String worldName = config.getString("teleport.world");
        if (worldName == null || !config.contains("teleport.x") || !config.contains("teleport.y") || !config.contains("teleport.z")) {
            targetLocation = null;
        } else {
            double x = config.getDouble("teleport.x");
            double y = config.getDouble("teleport.y");
            double z = config.getDouble("teleport.z");
            float yaw = (float) config.getDouble("teleport.yaw", 0.0);
            float pitch = (float) config.getDouble("teleport.pitch", 0.0);

            targetLocation = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (targetLocation != null) {
            Player player = event.getPlayer();

            // 延迟传送玩家，确保玩家已完全加载
            Bukkit.getScheduler().runTaskLater(this, () -> player.teleport(targetLocation), 20L); // 延迟20 tick（1秒）
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("jointp")) {
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                // 重新加载配置文件
                this.reloadConfig();
                loadLocationFromConfig();
                if (targetLocation == null) {
                    sender.sendMessage("JoinTP 配置缺少必要的传送坐标，请设置后重试！");
                } else {
                    sender.sendMessage("JoinTP 配置已重新加载！");
                }
                return true;
            }
        }
        return false;
    }
}
