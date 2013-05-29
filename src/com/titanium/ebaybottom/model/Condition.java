
package com.titanium.ebaybottom.model;

import java.util.List;

public class Condition{
   	private List<String> conditionDisplayName;
   	private List<String> conditionId;

 	public List<String> getConditionDisplayName(){
		return this.conditionDisplayName;
	}
	public void setConditionDisplayName(List<String> conditionDisplayName){
		this.conditionDisplayName = conditionDisplayName;
	}
 	public List<String> getConditionId(){
		return this.conditionId;
	}
	public void setConditionId(List<String> conditionId){
		this.conditionId = conditionId;
	}
}
