package MoseShipsSponge.Causes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FailedCause {
	
	Set<Object> CAUSES;
	
	public FailedCause(List<Object> object){
		CAUSES = new HashSet<Object>(object);
	}
	
	public FailedCause(Object... objects){
		CAUSES = new HashSet<>(Arrays.asList(objects));
	}
	
	public Set<Object> getCauses(){
		return CAUSES;
	}
	
	public static class Builder{
		
		List<Object> CAUSES = new ArrayList<Object>();
		
		public Builder add(Object add){
			CAUSES.add(add);
			return this;
		}
		
		public Builder addMultiple(Object...objects){
			CAUSES.addAll(Arrays.asList(objects));
			return this;
		}
		
		public FailedCause build(){
			return new FailedCause(CAUSES);
		}
	}

}
