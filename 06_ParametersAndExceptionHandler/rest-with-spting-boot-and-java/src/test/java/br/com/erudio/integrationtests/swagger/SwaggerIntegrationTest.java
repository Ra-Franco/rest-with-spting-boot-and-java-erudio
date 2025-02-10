package br.com.erudio.integrationtests.swagger;

import static io.restassured.RestAssured.given;

import br.com.erudio.configs.TestConfigs;
import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.restwithsptingbootandjava.Startup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = Startup.class)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {
    @Test
    void shouldDisplaySwaggerUiPage(){
       var content = given()
                        .basePath("/swagger-ui/index.html")
                        .port(TestConfigs.SERVER_PORT)
                        .when()
                            .get()
                        .then()
                            .statusCode(200)
                        .extract()
                            .body().asString();
        Assertions.assertTrue(content.contains("Swagger UI"));
    }
}
