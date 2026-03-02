package nl.gjorgdy.bars_and_ladders;

import me.fzzyhmstrs.fzzy_config.annotations.Comment;
import me.fzzyhmstrs.fzzy_config.annotations.IgnoreVisibility;
import me.fzzyhmstrs.fzzy_config.api.ConfigApi;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedNumber;
import net.minecraft.resources.Identifier;

@IgnoreVisibility
public class FzzyConfig extends Config {

    static {
        ConfigApi.event().onSyncServer((a, b) -> FzzyConfig.load());
        ConfigApi.event().onSyncClient((a, b) -> FzzyConfig.load());
    }

    public static void load() {
        var config = ConfigApiJava.registerAndLoadConfig(FzzyConfig::new);
        BarsAndLadders.targetSpeed = config.targetSpeed.get();
        BarsAndLadders.dragModifier = config.dragModifier.get();
        BarsAndLadders.ladderReach = config.ladderReach.get();
    }

    private FzzyConfig() {
        super(Identifier.fromNamespaceAndPath(BarsAndLadders.MOD_ID, "config"));
    }

    @Comment("The speed a player will be slowed to when sliding down a bar.")
    private ValidatedDouble targetSpeed = new ValidatedDouble(BarsAndLadders.targetSpeed, 0, -10, ValidatedDouble.WidgetType.TEXTBOX_WITH_BUTTONS);

    @Comment("The value by which the player's speed will be multiplied per tick while sliding down a bar.")
    private ValidatedDouble dragModifier = new ValidatedDouble(BarsAndLadders.dragModifier, 1, 0, ValidatedDouble.WidgetType.TEXTBOX_WITH_BUTTONS);

    @Comment("How long a ladder can be to still be extendable.")
    private ValidatedInt ladderReach = new ValidatedInt(BarsAndLadders.ladderReach, 384, 0, ValidatedDouble.WidgetType.TEXTBOX_WITH_BUTTONS);

}
