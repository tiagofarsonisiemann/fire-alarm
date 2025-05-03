Feature: Gerenciamento de Eventos de Incêndio

  Scenario: Criar evento de incêndio para um sensor ativo
    Given criei um sensor com location "Sala TI"
    When envio um evento de incêndio para esse sensor com description "Fumaça detectada"
    Then o status da resposta deve ser 200
    And o JSON da resposta deve conter "status" igual a "ALERT"
    And o JSON da resposta deve obedecer ao contrato "event-schema.json"

  Scenario: Listar todos os eventos
    When envio uma requisição GET para /fire/events
    Then o status da resposta deve ser 200
    And o array de resposta deve ser uma lista de eventos

  Scenario: Tentar criar evento para sensor inexistente
    When envio um evento de incêndio para sensor com id "FAKE-ID" e description "Teste"
    Then o status da resposta deve ser 500