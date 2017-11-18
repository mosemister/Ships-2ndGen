package MoseShipsSponge.ShipBlock.Signs;

import java.util.Arrays;
import java.util.List;

import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class ShipWheelSign implements ShipSign{

	@Override
	public void onCreation(ChangeSignEvent event, Player player) {
		SignData data = event.getText();
		data.setElement(0, Text.builder("[Wheel]").color(TextColors.YELLOW).build());
		data.setElement(1, Text.builder("\\\\||//").color(TextColors.RED).build());
		data.setElement(2, Text.builder("==||==").color(TextColors.RED).build());
		data.setElement(3, Text.builder("//||\\\\").color(TextColors.RED).build());
	}

	@Override
	public void onShiftRightClick(Player player, Sign sign, LiveShip ship) {
	}

	@Override
	public void onRightClick(Player player, Sign sign, LiveShip ship) {
		SignData data = sign.getSignData();
		if (!data.get(2).isPresent()) {
			apply(sign);
		}
		ship.rotateRight(Cause.source(this).named("direction", "right").build());
	}

	@Override
	public void onLeftClick(Player player, Sign sign, LiveShip ship) {
		SignData data = sign.getSignData();
		if (!data.get(2).isPresent()) {
			apply(sign);
		}
		ship.rotateLeft(Cause.source(this).named("direction", "left").build());
	}

	@Override
	public boolean onRemove(Player player, Sign sign) {
		return true;
	}

	@Override
	public List<String> getFirstLine() {
		return Arrays.asList("[wheel]");
	}

	@Override
	public boolean isSign(Sign sign) {
		SignData data = sign.getSignData();
		if(!data.get(0).isPresent()){
			return false;
		}
		Text text = data.get(0).get();
		if((text.getColor().equals(TextColors.YELLOW)) && (text.toPlain().equals("[Wheel]"))){
			return true;
		}
		return false;
	}

	@Override
	public void apply(Sign sign) {
		SignData data = sign.getSignData();
		data.setElement(0, Text.builder("[Wheel]").color(TextColors.YELLOW).build());
		data.setElement(1, Text.builder("\\\\||//").color(TextColors.RED).build());
		data.setElement(2, Text.builder("==||==").color(TextColors.RED).build());
		data.setElement(3, Text.builder("//||\\\\").color(TextColors.RED).build());
	}

}
