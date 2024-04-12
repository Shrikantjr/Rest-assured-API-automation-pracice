package pojo;

public class GetSampleData {
	
	private String url;
	private String services;
	private String expertise;
	private Courses Courses;
	/*
	 * return type of this Courses is not string is class 
	 * ie.. mini json class (Object of Courses variable is ntng but object of Courses class)
	 */
	private String instructor;
	private String linkedIn;
	
	public String getLinkedIn() {
		return linkedIn;
	}
	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) { // parameter
		this.url = url; //this represent current class attribute or variable
		
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public String getExpertise() {
		return expertise;
	}
	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}
	public pojo.Courses getCourses() {
		return Courses;
	}
	public void setCourses(pojo.Courses Courses) {
		this.Courses = Courses;
	}
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	

}
