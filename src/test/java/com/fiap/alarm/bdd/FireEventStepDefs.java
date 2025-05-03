package com.fiap.alarm.bdd;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class FireEventStepDefs {
    private Response response;
    private String sensorId;

    @Given("criei um sensor com location {string}")
    public void crieiSensor(String location) {
        try {
            JSONObject json = new JSONObject();
            json.put("location", location);
            Response res = RestAssured.given().contentType("application/json")
                    .body(json.toString()).post("http://localhost:8081/fire/sensors");
            Assertions.assertEquals(200, res.statusCode());
            try {
                sensorId = res.jsonPath().getString("id");
            } catch (Exception e) {
                throw new AssertionError("Não recebeu id do sensor no cadastro! Body: " + res.asString(), e);
            }
        } catch (JSONException e) {
            throw new AssertionError("JSON inválido ao criar request do sensor.", e);
        }
    }

    @When("envio um evento de incêndio para esse sensor com description {string}")
    public void enviaAlerta(String desc) {
        try {
            JSONObject json = new JSONObject();
            json.put("sensorId", sensorId);
            json.put("description", desc);
            response = RestAssured.given().contentType("application/json")
                    .body(json.toString()).post("http://localhost:8081/fire/events");
        } catch (JSONException e) {
            throw new AssertionError("JSON inválido ao criar request de evento.", e);
        }
    }

    @When("envio uma requisição GET para /fire/events")
    public void listEvents() {
        response = RestAssured.get("http://localhost:8081/fire/events");
    }

    @When("envio um evento de incêndio para sensor com id {string} e description {string}")
    public void eventoFake(String fakeId, String desc) {
        try {
            JSONObject json = new JSONObject();
            json.put("sensorId", fakeId);
            json.put("description", desc);
            response = RestAssured.given().contentType("application/json")
                    .body(json.toString()).post("http://localhost:8081/fire/events");
        } catch (JSONException e) {
            throw new AssertionError("JSON inválido ao criar request de evento fake.", e);
        }
    }

    @Then("o status da resposta deve ser {int}")
    public void statusDeveSer(int status) {
        Assertions.assertEquals(status, response.getStatusCode(), "Status HTTP inesperado. Body: " + response.asString());
    }

    @And("o JSON da resposta deve conter {string} igual a {string}")
    public void contains(String campo, String valor) {
        try {
            String actual = response.jsonPath().getString(campo);
            Assertions.assertEquals(valor, actual, "O campo retornado não corresponde ao esperado. Body: " + response.asString());
        } catch (Exception e) {
            throw new AssertionError("Erro ao acessar campo '" + campo + "' do corpo da resposta. Body: " + response.asString(), e);
        }
    }

    @And("o JSON da resposta deve obedecer ao contrato {string}")
    public void schemaCheck(String schema) {
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath("schema/" + schema));
    }

    @And("o array de resposta deve ser uma lista de eventos")
    public void isArray() {
        try {
            java.util.List<?> eventos = response.jsonPath().getList("$");
            Assertions.assertNotNull(eventos, "A resposta é nula ou não é um array. Body: " + response.asString());
        } catch (Exception e) {
            throw new AssertionError("A resposta não é um array de eventos. Body: " + response.asString(), e);
        }
    }
}