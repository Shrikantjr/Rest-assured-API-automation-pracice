package testing;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class sumValidation {
	
	@Test
	public void sumOfCourse() {
		
		int sum = 0;
		JsonPath js = new JsonPath(payload.coursePrice());
		int count = js.getInt("courses.size()");
		
		for(int i =0; i<count; i++) {
			
			int price = js.get("courses["+i+"].price");
			int copie = js.get("courses["+i+"].copies");
			int amount = price * copie;
			System.out.println(amount);
			sum = sum + amount;
			
	}
		System.out.println(sum);
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(sum, purchaseAmount);
	}

}
