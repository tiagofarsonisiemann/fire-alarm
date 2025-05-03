Feature: Gerenciamento de Eventos de Incêndio

  Scenario: Criar evento de incêndio para um sensor ativo
    Given criei um evento com location "Sala TI"
    When envio um evento de incêndio para esse sensor com description "Fumaça detectada"
    Then o status da resposta do evento deve ser 200
    And o JSON de evento deve conter "status" igual a "ALERT"
    And o JSON do evento deve obedecer ao contrato "fire-event-schema.json"

  Scenario: Listar todos os eventos
    When envio uma requisição GET para /fire/events
    Then o status da resposta do evento deve ser 200
    And o array de resposta deve ser uma lista de eventos

  Scenario: Tentar criar evento para sensor inexistente
    When envio um evento de incêndio para sensor com id "FAKE-ID" e description "Teste"
    Then o status da resposta do evento deve ser 200  # ou 500 se a API retornar 500