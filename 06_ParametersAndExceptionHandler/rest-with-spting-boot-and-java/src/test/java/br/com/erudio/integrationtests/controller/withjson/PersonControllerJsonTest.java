package br.com.erudio.integrationtests.controller.withjson;

import br.com.erudio.configs.TestConfigs;
import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.erudio.integrationtests.vo.PersonVO;
import br.com.erudio.integrationtests.vo.security.AccountCredentialsVO;
import br.com.erudio.integrationtests.vo.security.TokenVO;
import br.com.restwithsptingbootandjava.Startup;
import br.com.restwithsptingbootandjava.model.Person;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.annotate.JsonIgnore;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = Startup.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper mapper;

    private static PersonVO personVO;

    @BeforeAll
    public static void setup() {
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        personVO = new PersonVO();
    }

    @Test
    @Order(1)
    public void authorization() throws JsonProcessingException {
        AccountCredentialsVO user = new AccountCredentialsVO("ramon", "admin123");
        var response = given()
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(user)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        String accessToken = mapper.readTree(response).path("body").path("accessToken").asText();


        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
                .setBasePath("/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();


    }

    @Test
    @Order(1)
    void testCreate() throws JsonProcessingException {
        mockPerson();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL_HOST)
                .body(personVO)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body().asString();

        PersonVO createdPerson = mapper.readValue(content, PersonVO.class);

        personVO = createdPerson;
        Assertions.assertNotNull(personVO.getId());
        Assertions.assertNotNull(personVO.getLastName());
        Assertions.assertNotNull(personVO.getFirstName());
        Assertions.assertNotNull(personVO.getGender());
        Assertions.assertNotNull(personVO.getAddress());

        Assertions.assertTrue(createdPerson.getId() > 0);

        Assertions.assertEquals("Stallman", createdPerson.getLastName());
        Assertions.assertEquals("Richard", createdPerson.getFirstName());
        Assertions.assertEquals("Male", createdPerson.getGender());
        Assertions.assertEquals("New York City, New York, US", createdPerson.getAddress());
    }

    @Test
    @Order(2)
    void testCreatedWithWrongOrigin() throws JsonProcessingException {
        mockPerson();


        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_WRONG_LOCAL_HOST)
                .body(personVO)
                .when()
                    .post()
                .then()
                    .statusCode(403)
                        .extract()
                            .body().asString();

        Assertions.assertNotNull(content);
        Assertions.assertEquals("Invalid CORS request", content);
    }

    @Test
    @Order(3)
    void testFindById() throws JsonProcessingException {
        mockPerson();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL_HOST)
                .pathParams("id", personVO.getId())
                    .when()
                    .get("{id}")
                .then()
                    .statusCode(200)
                        .extract()
                            .body().asString();

        PersonVO persistedPerson = mapper.readValue(content, PersonVO.class);

        personVO = persistedPerson;
        Assertions.assertNotNull(personVO.getId());
        Assertions.assertNotNull(personVO.getLastName());
        Assertions.assertNotNull(personVO.getFirstName());
        Assertions.assertNotNull(personVO.getGender());
        Assertions.assertNotNull(personVO.getAddress());

        Assertions.assertTrue(persistedPerson.getId() > 0);

        Assertions.assertEquals("Stallman", persistedPerson.getLastName());
        Assertions.assertEquals("Richard", persistedPerson.getFirstName());
        Assertions.assertEquals("Male", persistedPerson.getGender());
        Assertions.assertEquals("New York City, New York, US", persistedPerson.getAddress());
    }

    @Test
    @Order(3)
    void testFindByIdWithWrongOrigin() throws JsonProcessingException {
        mockPerson();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_WRONG_LOCAL_HOST)
                .pathParams("id", personVO.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body().asString();

        Assertions.assertNotNull(content);
        Assertions.assertEquals("Invalid CORS request", content);
    }

    private void mockPerson() {
        personVO.setFirstName("Richard");
        personVO.setLastName("Stallman");
        personVO.setAddress("New York City, New York, US");
        personVO.setGender("Male");
    }
}
