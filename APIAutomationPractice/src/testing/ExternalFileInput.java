package testing;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.RestAssured;
import static org.hamcrest.Matchers.*;

public class ExternalFileInput {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Sending payload via external file into body
		//Body accept string 
		//then 1. convert content of file Byte (use Files class in that use readAllBytes) 
		//then use paths class in that use get and then provide path
		
		//2. Byte data to String (create new String object inside the body)
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		try {
			String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
					.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\devks002\\eclipse-workspace\\VB ticket push notif payload\\VB ticket push notification payload.json")))) 
					////then 1. convert content of file Byte
					//(now if we run & see the console the body displays in byte bcoz we have not yet conveted into string)
					//why we used paths.get bcoz method description says paths class then we have to used get method and provide path
					// to converted into string create new object inside body
					.when().post("Library/Addbook.php")
					.then().assertThat().statusCode(200).body("Msg", equalTo("successfully added"))
					.extract().response().asString();
			System.out.println(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * sending an attachemnt using mulit part method in rest assured
	 * ex., before when() we need to used multiPart("this will be key", new File ("file path"))
	 * & in header ("content-type", "multipart/form-data")
	 */

}
