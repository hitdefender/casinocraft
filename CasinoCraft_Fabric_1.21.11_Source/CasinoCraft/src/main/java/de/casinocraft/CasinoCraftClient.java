package de.casinocraft;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class CasinoCraftClient implements ClientModInitializer {
    private static KeyBinding openKey;
    @Override public void onInitializeClient() {
        openKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.casinocraft.open", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_K,
            KeyBinding.Category.MISC
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openKey.wasPressed()) {
                if (client.currentScreen instanceof CasinoScreen) client.setScreen(null);
                else if (client.currentScreen == null) client.setScreen(new CasinoScreen());
            }
        });
    }
}
