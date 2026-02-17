package me.nova.lifesteal;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerListener implements Listener {

    private final double HEART_VALUE = 2.0; // 2 = 1 corazón
    private final double MAX_HEALTH = 40.0; // 20 corazones

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        // Quitar corazón al que murió
        AttributeInstance victimAttribute = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (victimAttribute == null) {
            return;
        }

        double victimHealth = victimAttribute.getBaseValue();
        victimHealth -= HEART_VALUE;

        if (victimHealth <= 0) {
            Bukkit.getBanList(BanList.Type.NAME).addBan(
                    victim.getName(),
                    "Has perdido todos tus corazones.",
                    null,
                    "LifestealSMP"
            );
            victim.kickPlayer("☠ Has perdido todos tus corazones.");
            return;
        }

        victimAttribute.setBaseValue(victimHealth);

        // Dar corazón al killer
        if (killer != null) {
            AttributeInstance killerAttribute = killer.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if (killerAttribute == null) {
                return;
            }

            double killerHealth = killerAttribute.getBaseValue();

            if (killerHealth < MAX_HEALTH) {
                killerHealth += HEART_VALUE;
                if (killerHealth > MAX_HEALTH) {
                    killerHealth = MAX_HEALTH;
                }
                killerAttribute.setBaseValue(killerHealth);
            }
        }
    }
}