package pojo;

import java.util.List;

public class Courses {
	// this class is mini json or child or nested json of getSmapleData pojo class
	// and we need to nject this mini json into main json for that go to main json pojo class
	
	private List<WebAutomation> webAutomation;
	//as webautomation nest is a array of json object
	private List<Api> api;
	private List<Mobile> mobile;
	
	public List<WebAutomation> getWebAutomation() {
		return webAutomation;
	}
	public void setWebAutomation(List<WebAutomation> webAutomation) {
		this.webAutomation = webAutomation;
	}
	public List<Api> getApi() {
		return api;
	}
	public void setApi(List<Api> api) {
		this.api = api;
	}
	public List<Mobile> getMobile() {
		return mobile;
	}
	public void setMobile(List<Mobile> mobile) {
		this.mobile = mobile;
	}
	

	
}
