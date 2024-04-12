package testing;

import files.payload;
import io.restassured.path.json.JsonPath;

public class complexJsonParse {

	//creating dummy response as per swagger while developer creating it and once develped update with correct 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath js = new JsonPath(payload.coursePrice());
		//print no of size or count or coruse
		
		int count = js.getInt("courses.size()");
		System.out.println(count);
		
		int totalamount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalamount);

		String titleOfFirstCourse = js.get("courses[0].title");
		System.out.println(titleOfFirstCourse);
		
		//get the copies of title rpa
		for(int i =0; i<count; i++) {
		String coursesTitle =js.get("courses["+i+"].title");
			if(coursesTitle.equalsIgnoreCase("RPA")) {
				int copies = js.get("courses[2].copies");
				System.out.println(copies);
				break;
			}
		}
		
		//get all course price*copies = total amount
		

}
}