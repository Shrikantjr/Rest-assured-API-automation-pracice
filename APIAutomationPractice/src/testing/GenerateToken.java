package testing;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
/*import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
*/

public class GenerateToken {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
	/*
	 * 2 ways of doing, When automating REST API requests with a x-www-form-urlencoded body
	 * 1st one is below
	 * using  .contentType("application/x-www-form-urlencoded; charset=utf-8") & then pass the key value in fromParam 
	 */
RestAssured.baseURI="https://qaapix.vodacom.co.za";
		
		String response1= given().log().all()
				.auth().basic("xx", "yy")
				.header("Authorization", "Basic zz==")
				.header("Content-Type", "application/x-www-form-urlencoded")
				.contentType("application/x-www-form-urlencoded; charset=utf-8")
				.formParam("grant_type", "client_credentials")
				.when().post("aa")
				.then().assertThat().statusCode(200)
				
				.body("token_type", equalTo("BearerToken"))
				.extract().response().asString();
		 		System.out.println(response1);
		 		
		
		/*
		 * 2nd way is below
		 */
		//config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs("\"x-www-form-urlencoded\"", ContentType.URLENC)))
				//contentType(ContentType.URLENC.withCharset("UTF-8"))
		//System.out.println(response);
		

		/*
		 * Supporse we are using https not http, in that case we do not have proper cert to authenticate
		 * in that case use one method after given() i.e. relaxedHTTPSValidation()
		 */
		 		
		 /*
		  * what is O auth
		  * 
		  */
	}

}
