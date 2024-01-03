package com.alihaine.bulpearl.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Reflections {
    private static Class<?> CRAFTPLAYERCLASS;
    private static Class<?> PACKET_PLAYER_CHAT_CLASS;
    private static Class<?> ICHATCOMP;
    private static Class<?> CHATMESSAGE;
    private static Class<?> PACKET_CLASS;
    private static Constructor<?> PACKET_PLAYER_CHAT_CONSTRUCTOR;
    private static Constructor<?> CHATMESSAGE_CONSTRUCTOR;
    private static final String SERVER_VERSION;

    static {
        String name = Bukkit.getServer().getClass().getName();
        name = name.substring(name.indexOf("craftbukkit.") + "craftbukkit.".length());
        SERVER_VERSION = name.substring(0, name.indexOf("."));
        if (SERVER_VERSION.contains("1_8") || SERVER_VERSION.contains("1_9")) {
            try {
                Reflections.CRAFTPLAYERCLASS = Class.forName("org.bukkit.craftbukkit." + Reflections.SERVER_VERSION + ".entity.CraftPlayer");
                Reflections.PACKET_PLAYER_CHAT_CLASS = Class.forName("net.minecraft.server." + Reflections.SERVER_VERSION + ".PacketPlayOutChat");
                Reflections.PACKET_CLASS = Class.forName("net.minecraft.server." + Reflections.SERVER_VERSION + ".Packet");
                Reflections.ICHATCOMP = Class.forName("net.minecraft.server." + Reflections.SERVER_VERSION + ".IChatBaseComponent");
                Reflections.PACKET_PLAYER_CHAT_CONSTRUCTOR = Reflections.PACKET_PLAYER_CHAT_CLASS.getConstructor(Reflections.ICHATCOMP, Byte.TYPE);
                Reflections.CHATMESSAGE = Class.forName("net.minecraft.server." + Reflections.SERVER_VERSION + ".ChatMessage");
                Reflections.CHATMESSAGE_CONSTRUCTOR = Reflections.CHATMESSAGE.getDeclaredConstructor(String.class, Object[].class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendActionBarMessage(final Player player, String message) {

        if (message == null || message.isEmpty())
            return;
        message = message.replace('&', '§');
        if (!is1_8Version() && !is1_9Version()) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
            return ;
        }
        try {
            final Object icb = Reflections.CHATMESSAGE_CONSTRUCTOR.newInstance(message, new Object[0]);
            final Object packet = Reflections.PACKET_PLAYER_CHAT_CONSTRUCTOR.newInstance(icb, (byte)2);
            final Object craftplayerInst = Reflections.CRAFTPLAYERCLASS.cast(player);
            final Method methodHandle = Reflections.CRAFTPLAYERCLASS.getMethod("getHandle", (Class<?>[])new Class[0]);
            final Object methodhHandle = methodHandle.invoke(craftplayerInst, new Object[0]);
            final Object playerConnection = methodhHandle.getClass().getField("playerConnection").get(methodhHandle);
            playerConnection.getClass().getMethod("sendPacket", Reflections.PACKET_CLASS).invoke(playerConnection, packet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean is1_8Version() {
        return SERVER_VERSION.contains("1_8");
    }

    public static boolean is1_9Version() {
        return SERVER_VERSION.contains("1_9");
    }
}
