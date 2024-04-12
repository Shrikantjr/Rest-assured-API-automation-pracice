package pojo;

import java.util.List;

public class GetCustomerOrderResponse {
	private int count;
	private String message;
	private List<data> data;
	
	
	
	public List<data> getData() {
		return data;
	}
	public void setData(List<data> data) {
		this.data = data;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
