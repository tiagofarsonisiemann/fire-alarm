package com.fiap.alarm.bdd;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class FireSensorStepDefs {
    private Response response;
    private String sensorId;

    @When("envio uma requisição POST em \\/fire\\/sensors com location {string}")
    public void postSensor(String location) {
        try {
            JSONObject json = new JSONObject();
            json.put("location", location);
            response = RestAssured.given().contentType("application/json")
                    .body(json.toString()).post("http://localhost:8081/fire/sensors");
            if (response.getStatusCode() == 200) {
                try {
                    sensorId = response.jsonPath().getString("id");
                    String respLocation = response.jsonPath().getString("location");
                    Assertions.assertEquals(location, respLocation,
                            "O campo location retornado não bate com enviado.");
                    boolean activeVal = response.jsonPath().getBoolean("active");
                    Assertions.assertTrue(activeVal,
                            "O campo active do sensor recém-cadastrado não está true.");
                } catch (Exception e) {
                    throw new AssertionError(
                            "Erro ao obter campos obrigatórios do sensor criado. Body: " + response.asString(), e);
                }
            }
        } catch (JSONException e) {
            throw new AssertionError("JSON inválido ao criar request do sensor.", e);
        }
    }

    @Given("criei um sensor com location {string}")
    public void crieiSensorSensor(String location) {
        postSensor(location);
    }

    @When("envio uma requisição GET para /fire/sensors/criado")
    public void getSensorBySavedId() {
        response = RestAssured.get("http://localhost:8081/fire/sensors/" + sensorId);
    }

    @When("envio uma requisição GET para \\/fire\\/sensors\\/FAKE-ID")
    public void getSensorFake() {
        response = RestAssured.get("http://localhost:8081/fire/sensors/FAKE-ID");
    }

    @Then("o status da resposta do sensor deve ser {int}")
    public void statusSensorDeveSer(int status) {
        Assertions.assertEquals(status, response.getStatusCode(),
                "Status HTTP inesperado. Body: " + response.asString());
    }

    @And("o JSON de sensor deve conter {string} igual a {string}")
    public void sensorContains(String campo, String valor) {
        try {
            String actual = response.jsonPath().getString(campo);
            Assertions.assertEquals(valor, actual,
                    "O campo '" + campo + "' retornado não corresponde ao esperado. Body: " + response.asString());
        } catch (Exception e) {
            throw new AssertionError(
                    "Erro ao acessar campo '" + campo + "' do corpo da resposta. Body: " + response.asString(), e);
        }
    }

    @And("o JSON do sensor deve obedecer ao contrato {string}")
    public void sensorSchemaCheck(String schema) {
        Assertions.assertNotNull(schema, "O nome do schema não pode ser nulo!");
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath("schema/" + schema));
    }
}