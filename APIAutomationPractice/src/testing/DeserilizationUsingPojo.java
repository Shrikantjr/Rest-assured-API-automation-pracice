package testing;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetSampleData;
import pojo.WebAutomation;

public class DeserilizationUsingPojo {

	public static void main(String[] args) {
		// This is sample testing for serilization & deserilization using pojo class
		// sample json taken from google for learning

		String[] courseTitles = {"Selenium Webdriver Java","Cypress","Protractor"};
		//expected list of courses title store in string of array
		
		
		
		String response = 	given()
			.formParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
			.formParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
			.formParams("grant_type","client_credentials")
			.formParams("scope","trust")
			.when().log().all()
			.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asPrettyString();
		
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String accessToken = js.getString("access_token");
		
		System.out.println(accessToken);
		System.out.println("-------------------------");
		
		//Simply getting the response 
		String javaObjectRes = given()
				.queryParam("access_token", accessToken)
				.when().log().all()
				.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").asPrettyString();
				System.out.println("Simply getting the response: " +javaObjectRes);
				
				JsonPath js1 = new JsonPath(javaObjectRes);
				String withoutUsingPojoConect = js1.getString("courses.mobile[0].courseTitle");
				System.out.println("simple way of getting value from response: " + withoutUsingPojoConect);
				
				//dynamic way of getting price of the title
				int count = js1.getInt("courses.webAutomation.size()");
				for (int i=0; i<count ; i++) {
					String webCouseName = js1.get("courses.webAutomation["+i+"].courseTitle");
					if(webCouseName.equalsIgnoreCase("Selenium Webdriver Java")) {
						int price = js1.getInt("courses.webAutomation[1].price");
						System.out.println("price of Selenium Webdriver Java using jsonpath class is : "+price);
					}
				}
		/*
		 * ---------------------------------------------------------------------------------		
		 */
		
		//Deserialization by converting response body back to java Object
				GetSampleData javaObjectResUsingPojo = given()
						.queryParam("access_token", accessToken)
						.when().log().all()
						.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetSampleData.class);
						System.out.println("response getting using pojo concept " +javaObjectResUsingPojo);
		/*
		 * till now we get any response in String format but now for deserilization we cannot get as String 
		 * bocz deserilization means convert request/response body Json into Java object
		 * so we need to used one class that is supportive class i.e. main pojo class
		 * ilke "as(GetSampleData.class);" & change the return type of String to class/object 
		 */
		
		System.out.println(javaObjectResUsingPojo.getLinkedIn());
		System.out.println(javaObjectResUsingPojo.getCourses().getApi().get(1).getCourseTitle());
		//getting course title of soup ui
		
		/*
		 * suppose the position of index in course we don't know
		 * then we have to iterate it and get the price of that course once match
		 */
		
		
		//using dynamic to get values
		List<Api> apiCourseName = javaObjectResUsingPojo.getCourses().getApi();
		//first we storing list of course in one variable
		
		for(int i=0; i<apiCourseName.size(); i++) {
			//1st we are getting the size of courses
			if(apiCourseName.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				//here in tat list of courses we are getting by index & matching it
				String apiCoursesPrice= apiCourseName.get(i).getPrice(); 
				System.out.println("course price of api of course tile of SoapUI Webservices testing using pojo is: " +apiCoursesPrice);
				
				//then we are getting price of that index
				//means if we get the course title match at index 2 then will get the price of that index
			}
		}

		
		List<WebAutomation> webAutomationCoursename = javaObjectResUsingPojo.getCourses().getWebAutomation();
			for(int i=0; i<webAutomationCoursename.size(); i++) {
				if(webAutomationCoursename.get(i).getCourseTitle().equalsIgnoreCase("Protractor")) {
					String webAutomationCoursePrice = webAutomationCoursename.get(i).getPrice();
					System.out.println("course price of webautom of course tile of Protractor using pojo is: " +webAutomationCoursePrice);
				}
			}
			
			//now we can't say how many of titles will be there for that using arraylist & storing it in a variable
			ArrayList<String> actualListOfCourses = new ArrayList<String>();
			for(int i=0; i<webAutomationCoursename.size(); i++) {
				//getting list of all course & then checking with assertion
				actualListOfCourses.add(webAutomationCoursename.get(i).getCourseTitle()); // we are storing actual courses in arraylist
				//String actualListOfCoursesTitle = webAutomationCoursename.get(i).getCourseTitle();// by doing this will get all courses
				//System.out.println(actualListOfCoursesTitle);
			}
			
			//now we have to compare but our expected strings are in array & actaul in arraylist
			//so first convert array into arraylist so that we can easily compare using equals method of arraylist
			
			
			List<String> expectedListOfCourses = Arrays.asList(courseTitles);
			
			Assert.assertTrue(actualListOfCourses.equals(expectedListOfCourses), "actual & expected arraylist of courses should be same");
		
	/*
	 * sample response body for deserialization
	 * {
    "instructor": "RahulShetty",
    "url": "rahulshettycademy.com",
    "services": "projectSupport",
    "expertise": "Automation",
    "courses": {
        "webAutomation": [
            {
                "courseTitle": "Selenium Webdriver Java",
                "price": "50"
            },
            {
                "courseTitle": "Cypress",
                "price": "40"
            },
            {
                "courseTitle": "Protractor",
                "price": "40"
            }
        ],
        "api": [
            {
                "courseTitle": "Rest Assured Automation using Java",
                "price": "50"
            },
            {
                "courseTitle": "SoapUI Webservices testing",
                "price": "40"
            }
        ],
        "mobile": [
            {
                "courseTitle": "Appium-Mobile Automation using Java",
                "price": "50"
            }
        ]
    },
    "linkedIn": "https://www.linkedin.com/in/rahul-shetty-trainer/"
}

	 */
	}

}
