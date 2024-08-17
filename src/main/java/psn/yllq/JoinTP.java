package psn.yllq;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;

public class JoinTP extends JavaPlugin implements Listener {

    //创建全局变量
    static JoinTP main;

    @Override
    public void onEnable() {
        // 注册事件监听器
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginCommand("jointp").setExecutor(new MainCommands());

        //生成配置文件
        saveDefaultConfig();

        //设置全局变量
        main = this;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // 指定坐标和世界
        double x = JoinTP.main.getConfig().getDouble("x"); // X坐标
        double y = JoinTP.main.getConfig().getDouble("y");  // Y坐标
        double z = JoinTP.main.getConfig().getDouble("z"); // Z坐标
        String worldName = JoinTP.main.getConfig().getString("worldname"); // 世界名称

        // 延迟执行传送操作
        Bukkit.getScheduler().runTaskLater(this, () -> {
            // 获取目标世界
            org.bukkit.World world = Bukkit.getWorld(worldName);
            if (world != null) {
                // 创建目标位置
                Location location = new Location(world, x, y, z);

                // 传送玩家
                player.teleport(location);
            }
        }, 1); // 延迟 1 个游戏刻度（20 毫秒）执行传送操作
    }
}