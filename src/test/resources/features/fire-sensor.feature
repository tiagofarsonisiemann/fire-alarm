Feature: Gerenciamento de Sensores

  Scenario: Cadastrar sensor com sucesso
    When envio uma requisição POST em /fire/sensors com location "Sala 505"
    Then o status da resposta do sensor deve ser 200
    And o JSON de sensor deve conter "location" igual a "Sala 505"
    And o JSON do sensor deve obedecer ao contrato "fire-sensor-schema.json"

  Scenario: Buscar sensor cadastrado
    Given criei um sensor com location "Hall Entrada"
    When envio uma requisição GET para /fire/sensors/criado
    Then o status da resposta do sensor deve ser 200
    And o JSON de sensor deve conter "location" igual a "Hall Entrada"
    And o JSON do sensor deve obedecer ao contrato "fire-sensor-schema.json"

  Scenario: Retornar 404 para sensor inexistente
    When envio uma requisição GET para /fire/sensors/FAKE-ID
    Then o status da resposta do sensor deve ser 500