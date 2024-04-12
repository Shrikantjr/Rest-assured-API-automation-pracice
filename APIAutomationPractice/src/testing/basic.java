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
		RestAssured.baseURI="https://qaapix.vodacom.co.za";
		//SessionFilter session = new SessionFilter();
		String tokenGeneration= given().log().all()
				.auth().basic("5sGEDsva7gycMv4iII1GXo89TdFRSLYP", "MMQA4mKZ7AutA51z")
				.header("Authorization", "Basic NXNHRURzdmE3Z3ljTXY0aUlJMUdYbzg5VGRGUlNMWVA6TU1RQTRtS1o3QXV0QTUxeg==")
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
		 RequestSpecification req =new RequestSpecBuilder().setBaseUri("https://qaapix.vodacom.co.za")
				.addHeader("Content-Type", "application/json")
				.addHeader("X-Source-TransactionId", "123e4567-e89b-42d3-a456-556642440000").build();
		 
		 //response sepc builder, in this not adding the status code validation boz
		 //1 api expects 200 & other 201 thats why not added
		ResponseSpecification res = new ResponseSpecBuilder()
		 .expectBody("ticketType", equalTo("Incident"))
		 .expectBody("status", equalTo("New"))
		 .expectBody("severity", equalTo("3 - Moderate"))
		 .build();
		 
		String createTroubleTicketVB  = given().spec(req).log().all()
				.auth().oauth2(""+accessToken+"")
        // .filter(session) //this session will store the response & take it as valid session & pass it to the next api
         .body(payload.createTroubleTicket())  //one way of sending static payload
         .when().post("v1/VBfixed/troubleTicket")
         .then().assertThat().statusCode(200).spec(res).extract().response().asString();
		System.out.println("Created VB trouble ticket response " + createTroubleTicketVB);
		
		//parsing the Json response body using JosnPath class
		JsonPath js = new JsonPath(createTroubleTicketVB); //creating object and passing the response as an argument coz it accept string arg
		String createdId = js.getString("id"); //storing it a variable coz we are going to use this in next api call to pass the value
		
		System.out.println("Created VB trouble ticket id: " + createdId);
		
		//update trouble ticket
		String updateResponse = given().spec(req).log().all()
				.auth().oauth2(""+accessToken+"")
			
        .body(payload.updateTroubleTicket())
      //  .filter(session) // called filter method and pass name of obejct, for session object
       // .filter(session)
        .when().patch("v1/VBfixed/troubleTicket/"+createdId+"")// 1 way sending path param
        //another way is mention pathParam(" name ","value")
        //then mentioned the same name in resource i.e. v1/VBfixed/troubleTicket/{name}
        
        .then().assertThat().statusCode(200).spec(res)
        .extract().response().asString();
		System.out.println("Updated VB trouble ticket response "  +updateResponse);
		
		JsonPath js2 = new JsonPath(updateResponse);
        String updatedId = js2.getString("id");
        System.out.println("Updated VB trouble ticket id: " + updatedId);
        
		//push trouble ticket
        
        
        
       // given().log().all().auth().oauth2("9KSOsw7hiLem6EWpHi7UztHtF1mu").header("Content-Type","application/json")
       // .header("X-Source-TransactionId", "123e4567-e89b-42d3-a456-556642440000");
        
        
        
        
	}

}
