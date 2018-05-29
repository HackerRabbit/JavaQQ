
package common.model.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

//请求对象
public class Request implements Serializable {
	private static final long serialVersionUID = -1237018286305074249L;

	private ResponseType type;

	private String action;

	private Map<String, Object> attributesMap;
	
	public Request(){
		this.attributesMap = new HashMap<String, Object>();
	}

	public ResponseType getType() {
		return type;
	}

	public void setType(ResponseType type) {
		this.type = type;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Map<String, Object> getAttributesMap() {
		return attributesMap;
	}

	public Object getAttribute(String name){
		return this.attributesMap.get(name);
	}
	
	public void setAttribute(String name, Object value){
		this.attributesMap.put(name, value);
	}
	
	public void removeAttribute(String name){
		this.attributesMap.remove(name);
	}
	
	public void clearAttribute(){
		this.attributesMap.clear();
	}
}
