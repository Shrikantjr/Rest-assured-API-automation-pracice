package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {
	
	//handling dynamic json payload
	
	@Test(dataProvider="BooksData")
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().log().all().header("Content-Type","application/json")
				
				//passing dynamic input, so we do not need to pass manually
				
		.body(payload.AddBook(isbn, aisle)).
		when().post("Library/Addbook.php")
		.then().log().all().statusCode(200).extract().response().asString();
		
		//using reusable method
		JsonPath js = new JsonPath(response);
		String id = js.get("ID");
		System.out.println(id);
	}
	
	//testng data provider annotation for parameterization using this we passing more than 1 input data to payload
	@DataProvider(name="BooksData")
	public Object[][] getData() {
		Object[][] myObj = new Object[][] {{"shri","121"},{"okok","222"},{"okie","212"}};
		System.out.println(myObj);
		return myObj;
		
	}
	
	
}
