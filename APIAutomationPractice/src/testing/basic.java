package testing;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
//import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import files.payload;

public class basic {

	public static void main(String[] args) {
		
		
		
		//generate access token
		RestAssured.baseURI="https://rahulshetty.com";
		//SessionFilter session = new SessionFilter();
		String tokenGeneration= given().log().all()
				.auth().basic("x", "y")
				.header("Authorization", "Basic z==")
				.header("Content-Type", "application/x-www-form-urlencoded")
				.contentType("application/x-www-form-urlencoded; charset=utf-8")
				.formParam("grant_type", "client_credentials")
				//.filter(session)
				.when().post("/oauth-framework/generateaccesstoken")
				.then().assertThat().statusCode(200)
				.body("token_type", equalTo("BearerToken"))
				.extract().response().asString();
		 		System.out.println("QA env token generation " + tokenGeneration);
		 		
		 		JsonPath js1 = new JsonPath(tokenGeneration);
		 		String accessToken = js1.get("access_token");
		 		System.out.println("Access token: "+ accessToken);
		
		// create trouble ticket
		//SessionFilter session = new SessionFilter();
		//can be used for sesssion based class or cookie based auth, can be used instead of jsonpath class
		//like storing in a variable and pass it new api
		//for token we can use jsonpath class but there is 1 shortcut way ie., session filter class of rest assured
        //create object of sessionfilter class
		
		 		
		 	//building request spec builder so , we do need to send again & again same given data
		 	// and then passing the variable name to given("variable name")method
		 	//so by doing this we making framework looks good, requestspecbuilder have to keep in utility file for common use
		 RequestSpecification req =new RequestSpecBuilder().setBaseUri("https://rahulsheety.com")
				.addHeader("Content-Type", "application/json")
				.addHeader("X-Source-TransactionId", "1234").build();
		 
		 //response sepc builder, in this not adding the status code validation boz
		 //1 api expects 200 & other 201 thats why not added
		ResponseSpecification res = new ResponseSpecBuilder()
		 .expectBody("a", equalTo("a"))
		 .expectBody("b", equalTo("b"))
		 .expectBody("c", equalTo("c"))
		 .build();
		 
		String aa  = given().spec(req).log().all()
				.auth().oauth2(""+accessToken+"")
        // .filter(session) //this session will store the response & take it as valid session & pass it to the next api
         .body(payload.aa())  //one way of sending static payload
         .when().post("abc/bcd")
         .then().assertThat().statusCode(200).spec(res).extract().response().asString();
		System.out.println("hello " + aa);
		
		//parsing the Json response body using JosnPath class
		JsonPath js = new JsonPath(aa); //creating object and passing the response as an argument coz it accept string arg
		String id = js.getString("id"); //storing it a variable coz we are going to use this in next api call to pass the value
		
		System.out.println("id" + createdId);
		
		//update trouble ticket
		String updateResponse = given().spec(req).log().all()
				.auth().oauth2(""+accessToken+"")
			
        .body(payload.bb())
      //  .filter(session) // called filter method and pass name of obejct, for session object
       // .filter(session)
        .when().patch("aa"+idgenerated+"")// 1 way sending path param
        //another way is mention pathParam(" name ","value")
        //then mentioned the same name in resource i.e. aa/{name}
        
        .then().assertThat().statusCode(200).spec(res)
        .extract().response().asString();
		System.out.println("bb"  +bb);
		
		JsonPath js2 = new JsonPath(bb);
        String updatedId = js2.getString("id");
        System.out.println("bb" + updatedId);
        
	
        
        
        
	}

}
