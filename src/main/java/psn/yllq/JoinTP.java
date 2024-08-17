package psn.yllq;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;

import java.util.Objects;

import static org.bukkit.Bukkit.getPluginCommand;

public class JoinTP extends JavaPlugin implements Listener {

    //创建全局变量
    static JoinTP main;

    @Override
    public void onEnable() {
        // 注册事件监听器
        Bukkit.getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getPluginCommand("jointp")).setExecutor(new MainCommands());

        //生成配置文件
        saveDefaultConfig();

        //设置全局变量
        main = this;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // 指定坐标和世界
        double x = 0.0; // X坐标
        double y = 51.0;  // Y坐标
        double z = 0.0; // Z坐标
        String worldName = "world"; // 世界名称
        float yaw = 0.0f;
        float pitch = 0.0f;

        // 延迟执行传送操作
        Bukkit.getScheduler().runTaskLater(this, () -> {
            // 获取目标世界
            org.bukkit.World world = Bukkit.getWorld(worldName);
            if (world != null) {
                // 创建目标位置
                Location location = new Location(world, x, y, z, yaw, pitch);

                // 传送玩家
                player.teleport(location);
            }
        }, 1); // 延迟 1 个游戏刻度（20 毫秒）执行传送操作
    }
}