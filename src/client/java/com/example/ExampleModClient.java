package com.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class ExampleModClient implements ClientModInitializer {

	public static boolean orthographicEnabled = false;
	/**
	 * 正交视角缩放系数，数值越大，视野越大（相当于“拉远镜头”）
	 */
	public static float orthographicScale = 15.0f;

	private static KeyBinding toggleOrthographicKey;
	private static KeyBinding zoomInKey;
	private static KeyBinding zoomOutKey;

	@Override
	public void onInitializeClient() {
		// 注册按键：默认按 O 键 切换正交/透视
		toggleOrthographicKey = KeyBindingHelper.registerKeyBinding(
				new KeyBinding(
						"key.orthographic_view.orthographic_toggle",
						InputUtil.Type.KEYSYM,
						GLFW.GLFW_KEY_O,
						"category.orthographic_view.camera"
				)
		);
		// 注册缩放按键：默认按 [ / ] 调整缩放
		zoomInKey = KeyBindingHelper.registerKeyBinding(
				new KeyBinding(
						"key.orthographic_view.orthographic_zoom_in",
						InputUtil.Type.KEYSYM,
						GLFW.GLFW_KEY_LEFT_BRACKET,
						"category.orthographic_view.camera"
				)
		);
		zoomOutKey = KeyBindingHelper.registerKeyBinding(
				new KeyBinding(
						"key.orthographic_view.orthographic_zoom_out",
						InputUtil.Type.KEYSYM,
						GLFW.GLFW_KEY_RIGHT_BRACKET,
						"category.orthographic_view.camera"
				)
		);

		// 在客户端 tick 中检测按键，切换状态和缩放
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (toggleOrthographicKey.wasPressed()) {
				orthographicEnabled = !orthographicEnabled;

				MinecraftClient mc = client;
				if (mc != null && mc.player != null) {
					mc.player.sendMessage(
							Text.literal("正交视角: " + (orthographicEnabled ? "开启" : "关闭")),
							true
					);
				}
			}

			if (!orthographicEnabled) {
				return;
			}

			boolean changed = false;
			while (zoomInKey.wasPressed()) {
				orthographicScale *= 0.9f; // 放大（看得更近）
				changed = true;
			}
			while (zoomOutKey.wasPressed()) {
				orthographicScale *= 1.1f; // 缩小（看得更远）
				changed = true;
			}

			if (changed) {
				// 限制缩放范围，避免数值过大/过小
				if (orthographicScale < 1.0f) orthographicScale = 1.0f;
				if (orthographicScale > 200.0f) orthographicScale = 200.0f;

				if (client != null && client.player != null) {
					client.player.sendMessage(
							Text.literal("正交缩放: " + String.format("%.1f", orthographicScale)),
							true
					);
				}
			}
		});
	}
}