package rezscripts.core.factions.util.bountiful;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import rezscripts.core.factions.P;

public class Bapi extends JavaPlugin implements Listener {
	private static boolean useOldMethods;
	public static String nmsver;

	@Deprecated
	public static void sendTitle(final Player player, final Integer fadeIn, final Integer stay, final Integer fadeOut,
			final String message) {
		sendTitle(player, fadeIn, stay, fadeOut, message, null);
	}

	@Deprecated
	public static void sendSubtitle(final Player player, final Integer fadeIn, final Integer stay,
			final Integer fadeOut, final String message) {
		sendTitle(player, fadeIn, stay, fadeOut, null, message);
	}

	@Deprecated
	public static void sendFullTitle(final Player player, final Integer fadeIn, final Integer stay,
			final Integer fadeOut, final String title, final String subtitle) {
		sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
	}

	@Deprecated
	public static Integer getPlayerProtocol(final Player player) {
		return 47;
	}

	public static void sendPacket(final Player player, final Object packet) {
		try {
			final Object handle = player.getClass().getMethod("getHandle", (Class<?>[]) new Class[0]).invoke(player,
					new Object[0]);
			final Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Class<?> getNMSClass(final String name) {
		final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			return Class.forName("net.minecraft.server." + version + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void sendTitle(final Player player, final Integer fadeIn, final Integer stay, final Integer fadeOut,
			String title, String subtitle) {
		try {
			if (title != null) {
				title = ChatColor.translateAlternateColorCodes('&', title);
				title = title.replaceAll("%player%", player.getDisplayName());
				Object e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
				Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
						.getMethod("a", String.class).invoke(null, "{\"text\":\"" + title + "\"}");
				Constructor subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
						getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
						Integer.TYPE, Integer.TYPE, Integer.TYPE);
				Object titlePacket = subtitleConstructor.newInstance(e, chatTitle, fadeIn, stay, fadeOut);
				sendPacket(player, titlePacket);
				e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
				chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
						.invoke(null, "{\"text\":\"" + title + "\"}");
				subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
						getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"));
				titlePacket = subtitleConstructor.newInstance(e, chatTitle);
				sendPacket(player, titlePacket);
			}
			if (subtitle != null) {
				subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
				subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
				Object e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
				Object chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
						.getMethod("a", String.class).invoke(null, "{\"text\":\"" + title + "\"}");
				Constructor subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
						getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
						Integer.TYPE, Integer.TYPE, Integer.TYPE);
				Object subtitlePacket = subtitleConstructor.newInstance(e, chatSubtitle, fadeIn, stay, fadeOut);
				sendPacket(player, subtitlePacket);
				e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
				chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
						.invoke(null, "{\"text\":\"" + subtitle + "\"}");
				subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
						getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
						Integer.TYPE, Integer.TYPE, Integer.TYPE);
				subtitlePacket = subtitleConstructor.newInstance(e, chatSubtitle, fadeIn, stay, fadeOut);
				sendPacket(player, subtitlePacket);
			}
		} catch (Exception var11) {
			var11.printStackTrace();
		}
	}

	public static void clearTitle(final Player player) {
		sendTitle(player, 0, 0, 0, "", "");
	}

	public static void sendTabTitle(final Player player, String header, String footer) {
		if (header == null) {
			header = "";
		}
		header = ChatColor.translateAlternateColorCodes('&', header);
		if (footer == null) {
			footer = "";
		}
		footer = ChatColor.translateAlternateColorCodes('&', footer);
		header = header.replaceAll("%player%", player.getDisplayName());
		footer = footer.replaceAll("%player%", player.getDisplayName());
		try {
			final Object tabHeader = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
					.getMethod("a", String.class).invoke(null, "{\"text\":\"" + header + "\"}");
			final Object tabFooter = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
					.getMethod("a", String.class).invoke(null, "{\"text\":\"" + footer + "\"}");
			final Constructor<?> titleConstructor = getNMSClass("PacketPlayOutPlayerListHeaderFooter")
					.getConstructor((Class<?>[]) new Class[0]);
			final Object packet = titleConstructor.newInstance(new Object[0]);
			final Field aField = packet.getClass().getDeclaredField("a");
			aField.setAccessible(true);
			aField.set(packet, tabHeader);
			final Field bField = packet.getClass().getDeclaredField("b");
			bField.setAccessible(true);
			bField.set(packet, tabFooter);
			sendPacket(player, packet);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void sendActionBar(final Player player, final String message) {
		if (!player.isOnline()) {
			return;
		} else {
			sendActionBarPre112(player, message);
		}
	}

	private static void sendActionBarPost112(final Player player, final String message) {
		if (!player.isOnline()) {
			return;
		}
		try {
			final Class<?> craftPlayerClass = Class
					.forName("org.bukkit.craftbukkit." + P.p.getServer().getVersion() + ".entity.CraftPlayer");
			final Object craftPlayer = craftPlayerClass.cast(player);
			final Class<?> c4 = Class
					.forName("net.minecraft.server." + P.p.getServer().getVersion() + ".PacketPlayOutChat");
			final Class<?> c5 = Class.forName("net.minecraft.server." + P.p.getServer().getVersion() + ".Packet");
			final Class<?> c6 = Class
					.forName("net.minecraft.server." + P.p.getServer().getVersion() + ".ChatComponentText");
			final Class<?> c7 = Class
					.forName("net.minecraft.server." + P.p.getServer().getVersion() + ".IChatBaseComponent");
			final Class<?> chatMessageTypeClass = Class
					.forName("net.minecraft.server." + P.p.getServer().getVersion() + ".ChatMessageType");
			final Object[] chatMessageTypes = (Object[]) chatMessageTypeClass.getEnumConstants();
			Object chatMessageType = null;
			for (final Object obj : chatMessageTypes) {
				if (obj.toString().equals("GAME_INFO")) {
					chatMessageType = obj;
				}
			}
			final Object o = c6.getConstructor(String.class).newInstance(message);
			final Object ppoc = c4.getConstructor(c7, chatMessageTypeClass).newInstance(o, chatMessageType);
			final Method m1 = craftPlayerClass.getDeclaredMethod("getHandle", (Class<?>[]) new Class[0]);
			final Object h = m1.invoke(craftPlayer, new Object[0]);
			final Field f1 = h.getClass().getDeclaredField("playerConnection");
			final Object pc = f1.get(h);
			final Method m2 = pc.getClass().getDeclaredMethod("sendPacket", c5);
			m2.invoke(pc, ppoc);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void sendActionBarPre112(final Player player, final String message) {
		if (!player.isOnline()) {
			return;
		}
		try {
			final Class<?> craftPlayerClass = Class
					.forName("org.bukkit.craftbukkit." + P.p.getServer().getVersion() + ".entity.CraftPlayer");
			final Object craftPlayer = craftPlayerClass.cast(player);
			final Class<?> c4 = Class
					.forName("net.minecraft.server." + P.p.getServer().getVersion() + ".PacketPlayOutChat");
			final Class<?> c5 = Class.forName("net.minecraft.server." + P.p.getServer().getVersion() + ".Packet");
			Object ppoc;

			final Class<?> c6 = Class
					.forName("net.minecraft.server." + P.p.getServer().getVersion() + ".ChatComponentText");
			final Class<?> c7 = Class
					.forName("net.minecraft.server." + P.p.getServer().getVersion() + ".IChatBaseComponent");
			final Object o = c6.getConstructor(String.class).newInstance(message);
			ppoc = c4.getConstructor(c7, Byte.TYPE).newInstance(o, 2);

			final Method m4 = craftPlayerClass.getDeclaredMethod("getHandle", (Class<?>[]) new Class[0]);
			final Object h = m4.invoke(craftPlayer, new Object[0]);
			final Field f1 = h.getClass().getDeclaredField("playerConnection");
			final Object pc = f1.get(h);
			final Method m5 = pc.getClass().getDeclaredMethod("sendPacket", c5);
			m5.invoke(pc, ppoc);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
