package esf.kl3xy;

import esf.kl3xy.mixin.GameRendererAccessor;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElytraSpeedFov implements ModInitializer {
	public static final String MOD_ID = "elytraspeedfov";

	public static int use_time = 0;

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			ClientPlayerEntity player = client.player;


			if (player == null) return;

			/*
			* Check if player is currently flying.
			* */
			var gameRenderer = MinecraftClient.getInstance().gameRenderer;
			var inst = ((GameRendererAccessor) gameRenderer);

			var finalFovEdit = 1f;

			if (player.isGliding()) {
				var speed = Math.clamp(player.getVelocity().length() - 0.5, 0, 0.35) ;
				finalFovEdit += speed;

				var item = player.getMainHandStack().getItem();
				if (item instanceof FireworkRocketItem) {
					if (client.options.useKey.isPressed()) {
						use_time = 60;
					}
				}

				if (use_time > 0) {
					use_time -= 1;
					finalFovEdit += 0.1f;
				}
				LOGGER.info(String.valueOf(finalFovEdit));
				var get = inst.esf$getFovMultiplier();
				inst.esf$setFovMultiplier(finalFovEdit);
			}

			if (player.isOnGround()) {
				use_time = 0;
			}
		});



		LOGGER.info("Hello Fabric world!");
	}
}