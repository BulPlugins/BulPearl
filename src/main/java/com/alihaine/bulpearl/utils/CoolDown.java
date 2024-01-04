package com.alihaine.bulpearl.utils;

import java.util.HashMap;
import java.util.UUID;

public class CoolDown {

    private final HashMap<UUID, Long> coolDownList = new HashMap<>();

    public boolean isPlayerOnCoolDown (UUID playerId) {
        return coolDownList.containsKey(playerId);
    }

    public void addPlayerCoolDown(UUID playerId) {
        int coolDown = Config.getConfigInt("cooldown");

        coolDownList.put(playerId, System.currentTimeMillis() + (coolDown * 1000L));
    }

    public void removePlayerCoolDown(UUID playerId) {
        coolDownList.remove(playerId);
    }

    public long getCoolDownTimeLeft(UUID playerId) {
        return (coolDownList.get(playerId) - System.currentTimeMillis()) / 1000;
    }
}
