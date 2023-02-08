package JuiceShop;

import io.gatling.core.body.StringBody;
import io.gatling.javaapi.core.Simulation;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.util.concurrent.ThreadLocalRandom;

public class RegistrationSimulation extends Simulation {

    ChainBuilder register =
            exec(http("Search")
                    .post("/api/Users")
                    .body(StringBody("{\"email\":\"test@test.com\",\"password\":\"12345\",\"passwordRepeat\":\"12345\",\"securityQuestion\":{\"id\":2,\"question\":\"Mother's maiden name?\",\"createdAt\":\"2023-01-23T15:57:40.777Z\",\"updatedAt\":\"2023-01-23T15:57:40.777Z\"},\"securityAnswer\":\"1\"}"))
                    .check(
                            status().is(201)
                    )
            );
    ScenarioBuilder users = scenario("Users").exec(register);
    HttpProtocolBuilder httpProtocolBuilder = http.baseUrl("http://localhost:3000")
            .acceptHeader("application/json");

    {
        setUp(
                users.injectOpen(rampUsers(20).during(10))
        ).protocols(httpProtocolBuilder);
    }
}
