package esf.kl3xy.mixin;

import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameRenderer.class)
public interface GameRendererAccessor {
    @Accessor("fovMultiplier")
    float esf$getFovMultiplier();

    @Accessor("fovMultiplier")
    void esf$setFovMultiplier(float value);
}
