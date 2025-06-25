package com.alihaine.bulpearl.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CoolDown {

    private final HashMap<UUID, Long> coolDownList = new HashMap<>();

    public boolean isPlayerOnCoolDown (UUID playerId) {
        return coolDownList.containsKey(playerId);
    }

    public void addPlayerCoolDown(UUID playerId, int cd) {
        coolDownList.put(playerId, System.currentTimeMillis() + (cd * 1000L));
    }

    public void removePlayerCoolDown(UUID playerId) {
        coolDownList.remove(playerId);
    }

    public long getCoolDownTimeLeft(UUID playerId) {
        return (coolDownList.get(playerId) - System.currentTimeMillis()) / 1000;
    }

    public int getCoolDownTime(Player player) {
        for (int i = 1; i <= 600; i++) {
            if (player.hasPermission("bulpearl.cd." + i))
                return i;
        }
        return Config.getConfigInt("cooldown");
    }
}
