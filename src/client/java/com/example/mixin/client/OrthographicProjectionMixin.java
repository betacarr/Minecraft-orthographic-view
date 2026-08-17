package com.example.mixin.client;

import com.example.ExampleModClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.Window;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public abstract class OrthographicProjectionMixin {

	@Shadow @Final private MinecraftClient client;

	/**
	 * 替换基础投影矩阵，使其在开启时使用正交投影。
	 */
	@Inject(method = "getBasicProjectionMatrix", at = @At("HEAD"), cancellable = true)
	private void useOrthographicProjection(double fov, CallbackInfoReturnable<Matrix4f> cir) {
		if (!ExampleModClient.orthographicEnabled) {
			return;
		}

		Window window = this.client.getWindow();
		float width = (float) window.getFramebufferWidth();
		float height = (float) window.getFramebufferHeight();
		if (width <= 0 || height <= 0) {
			return;
		}

		float aspect = width / height;
		float scale = ExampleModClient.orthographicScale;

		float left = -aspect * scale;
		float right = aspect * scale;
		float bottom = -scale;
		float top = scale;
		float near = 0.05F;
		float far = 1000.0F;

		Matrix4f matrix = new Matrix4f().setOrtho(left, right, bottom, top, near, far);
		cir.setReturnValue(matrix);
	}
}


