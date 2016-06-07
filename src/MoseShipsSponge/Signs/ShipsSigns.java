package MoseShipsSponge.Signs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class ShipsSigns {
	
	/**
	 * this colours the sign in the licence sign format
	 * @param sign: the target sign
	 * @return: the same sign
	 */
	public static Sign colourSign(Sign sign){
		ListValue<Text> lines = sign.lines();
		List<String> strings = new ArrayList<>();
		lines.forEach(t -> strings.add(t.toPlain()));
		List<Text> linesTo = Arrays.asList(colour(strings));
		sign.offer(Keys.SIGN_LINES, linesTo);
		return sign;
	}
	
	public static Text[] colour(String... lines){
		return colour(Arrays.asList(lines));
	}
	
	public static Optional<SignTypes> getSignType(Sign sign){
		List<Text> lines = sign.get(Keys.SIGN_LINES).get();
		if(lines.get(0).toPlain().equals("[Ships]")){
			return Optional.of(SignTypes.LICENCE);
		}else{
			for(SignTypes type : SignTypes.values()){
				if(type.LINES.length == lines.size()){
				boolean check = true;
				for(int A = 0; A < type.LINES.length; A++){
					Text line = type.LINES[A];
					if(!line.equals(lines.get(A))){
						check = false;
					}
				}
				if(check){
					return Optional.of(type);
				}
				}
			}
		}
		return Optional.empty();
	}
	
	public static Text[] colour(List<String> lines){
		Text[] lines2 = new Text[lines.size()];
		lines2[0] = Text.builder(lines.get(0)).color(TextColors.YELLOW).build();
		lines2[1] = Text.builder(lines.get(1)).color(TextColors.BLUE).build();
		for(int A = 2; A < lines.size(); A++){
			lines2[A] = Text.builder(lines.get(A)).color(TextColors.GREEN).build();
		}
		return lines2;
	}
	
	public enum SignTypes{
		LICENCE,
		MOVE(Text.builder("[Move]").color(TextColors.YELLOW).build(), Text.builder("{Engine}").color(TextColors.GREEN).build(), Text.builder("Boost").build()),
		WHEEL,
		EOT,
		ALTITUDE;
		
		Text[] LINES;
		
		SignTypes(){
			
		}
		
		SignTypes(Text... text){
			LINES = text;
		}
		
		public Optional<Text[]> getLines(){
			return Optional.ofNullable(LINES);
		}
	}

}
