package testing;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import pojo.CreateOrder;
import pojo.CreateOrderDetails;
import pojo.GetCustomerOrderResponse;
import pojo.LoginRequest;
import pojo.LoginResponsePayload;
import pojo.data;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

public class EcommerceAPITest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//building e2e 	
		/*
		 * note: we can use multipart while giving input data as file or attachement or img
		 * multiPart("img", new File ("path of file"));
		 */
		
		String expectedMessage = "Orders fetched for customer Successfully";
		//login
		RequestSpecification req =new RequestSpecBuilder().setBaseUri("https://www.rahulshettyacademy.com")
		.setContentType(ContentType.JSON).build();
		
		LoginRequest lq = new LoginRequest(); //creating object of pojo class for serilization/ for request payload
		lq.setUserEmail("Shrikant.devkamble.1212@gmail.com");
		lq.setUserPassword("Learning@12");
		
		
		RequestSpecification reqLogin = given().log().all().spec(req).body(lq); //holding in one object
		
		LoginResponsePayload loginResponse =reqLogin.when().post("/api/ecom/auth/login")
		.then().extract().response().as(LoginResponsePayload.class);

		String accessToken = loginResponse.getToken();
		System.out.println("user access token: " +accessToken);
		String userId = loginResponse.getUserId();
		System.out.println("User id is: " +userId);
		System.out.println(loginResponse.getMessage());
		
		/*
		//createorder
		RequestSpecification createOrderBaseSpec =new RequestSpecBuilder().setBaseUri("https://www.rahulshettyacademy.com")
				.addHeader("authorization", accessToken)
				.setContentType(ContentType.JSON).build();
		
		
		CreateOrderDetails createOrderDetails = new CreateOrderDetails();
		createOrderDetails.setCountry("British Indian Ocean Territory");
		createOrderDetails.setProductOrderedId("6581ca399fd99c85e8ee7f45");
		
		CreateOrderDetails createOrderDetails1 = new CreateOrderDetails();
		createOrderDetails1.setCountry("British Indian Ocean Territory");
		createOrderDetails1.setProductOrderedId("6581ca979fd99c85e8ee7faf");
		
		List<CreateOrderDetails> createOrderDetailsList = new ArrayList<CreateOrderDetails>(); //array of json
		createOrderDetailsList.add(createOrderDetails);
		createOrderDetailsList.add(createOrderDetails1);
		
		CreateOrder createOrder = new CreateOrder(); // main json
		createOrder.setOrders(createOrderDetailsList);
		
		RequestSpecification createOrderReq = given().log().all().spec(createOrderBaseSpec).body(createOrder);
		
		String createOrderResponse = createOrderReq.when().post("/api/ecom/order/create-order")
		.then().log().all().extract().response().asPrettyString();
		
		*/
		
		
		
		//get customer orders
		RequestSpecification getCustomerOrdersSpec =new RequestSpecBuilder().setBaseUri("https://www.rahulshettyacademy.com")
				.addHeader("authorization", accessToken)
				.setContentType(ContentType.JSON).build();
		
		RequestSpecification getCustomerOrder = given().log().all()
				.spec(getCustomerOrdersSpec).pathParam("userId", userId);
		
		GetCustomerOrderResponse getCustomerOrderResponse = getCustomerOrder.when()
				.get("/api/ecom/order/get-orders-for-customer/{userId}")
				.then().log().all().extract().response().as(GetCustomerOrderResponse.class);
		
		String actualMessage = getCustomerOrderResponse.getMessage();
		System.out.println("message is: " + actualMessage);
		Assert.assertEquals(actualMessage, expectedMessage, "Message is not matched");
		
		int countofOrders = getCustomerOrderResponse.getCount();
		System.out.println("count of orders placed are: " + countofOrders);
		
		
		//normal way of getting data 
				String productIdBySimpleWay =getCustomerOrderResponse.getData().get(0).getOrderBy();
				System.out.println("getting order by, by simple way: " +productIdBySimpleWay);
				
				//dynamic handleing
				List<data> idOfProduct =getCustomerOrderResponse.getData();
				for(int i=0; i<idOfProduct.size(); i++) {
					if(idOfProduct.get(i).get_id().equalsIgnoreCase("65e2c42fa86f8f74dc8da532")) {
						String price = idOfProduct.get(i).getOrderPrice();
						System.out.println(" price of product is: "+ price);
					}
				}
				
		String ids = idOfProduct.toString();
		System.out.println("list of product ids: " +ids);
		
		
		System.out.println("-----------------------------------------------------------");
		System.out.println("-----------------------------------------------------------");
		
		String Response = given().log().all()
				.auth().oauth2(accessToken)
				.baseUri("https://www.rahulshettyacademy.com")
				.pathParam("userId", userId)
				.when().get("/api/ecom/order/get-orders-for-customer/{userId}")
				.then().log().all().extract().response().asPrettyString();
		
		
		
		
	}
	

}
