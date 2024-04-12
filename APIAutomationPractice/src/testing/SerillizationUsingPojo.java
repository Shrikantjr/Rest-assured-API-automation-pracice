package testing;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AddPlaceForSerilization;
import pojo.LocationForSerilization;

import static io.restassured.RestAssured.*;


import java.util.ArrayList;
import java.util.List;

public class SerillizationUsingPojo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//first create object of pojo class
		
		AddPlaceForSerilization ad = new AddPlaceForSerilization(); //object of main/parent pojo class
		ad.setAccuracy(50);
		ad.setAddress("1212, wakad, pune");
		ad.setLanguage("en-US");
		ad.setPhone_number("(+91) 911 922 9123");
		ad.setWebsite("https://rahulshettyacademy.com");
		ad.setName("shrikant");
		
		List<String> myList = new ArrayList<String>(); //creating arrayList class object
		myList.add("shoes");
		myList.add("clothes");
		ad.setTypes(myList); // bcoz it is an arraylist class so creating arrayList class then set values
		
		LocationForSerilization l = new LocationForSerilization(); //location class object
		l.setLat(-38.383494);
		l.setLng(33.427362);
		ad.setLocation(l); // it expected another class object, so we need to create location class object first then set values
		//till here we created java object & pass it in body i.e. serialization
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		Response response = given().queryParam("key", "qaclick123")
		.body(ad)
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response();
		
		String responseString = response.asString();
		System.out.println(responseString);
	}

}
