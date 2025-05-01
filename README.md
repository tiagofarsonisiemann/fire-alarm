Fire Service – Monitoramento Inteligente de Incêndios

*O que é isso?*

Este é um sistema web que serve como exemplo de aplicação para cidades inteligentes: você poderá cadastrar sensores de incêndio, registrar alertas de incêndio, consultar tudo por interface gráfica, com o banco de dados na nuvem (MongoDB Atlas).

PARTE 1 – README

Pré-requisitos:
Java 21
Maven
Conta gratuita no MongoDB Atlas

(Opcional) Git para baixar o projeto, mas você pode copiar/colar sem git!

1. Criando seu MongoDB Atlas
   Entre em https://www.mongodb.com/cloud/atlas/register e faça cadastro.
   Clique em “Build a Database”, escolha M0 (Free).
   Siga os passos para criar um usuário e senha do banco (por exemplo: user: admin, senha: admin123).
   Clique em "Network Access" > "Add IP Address" > "Allow Access From Anywhere".
   Clique em "Database" > "Connect" > "Drivers" > escolha "Java" e copie a string semelhante a:

mongodb+srv://admin:admin123@seudominio.mongodb.net/?retryWrites=true&w=majority
IMPORTANTE: copie a conexão completa, troque o nome do usuário e senha pelos que você escolheu!

2. Baixando e preparando o projeto
   Escolha:

Clone via Git:

git clone https://github.com/tiagofarsonisiemann/fire-alarm.git

cd fire-alarm

Ou crie uma pasta fire-alarm, copie e cole os arquivos abaixo com seus respectivos nomes e conteúdo!

3. Ajustando a configuração para Atlas
   Abra src/main/resources/application.yml e coloque assim (ajustando usuário/senha/host):

YAML

server:
port: 8081

spring:
data:
mongodb:
uri: mongodb+srv://admin:admin123@seudominio.mongodb.net/fire_db?retryWrites=true&w=majority

4. Rodando o projeto

   No terminal, na pasta do projeto, rode:

mvn clean package

Depois:

java -jar target/fire-service-0.0.1-SNAPSHOT.jar
Se tudo der certo, a aplicação estará de pé em http://localhost:8081.

5. Testando a API
   
Acesse: http://localhost:8081/swagger-ui.html
   Você verá uma tela para testar os métodos da API sem precisar saber programar!
   Exemplo de uso:

Criar um sensor: Clique em “POST /fire/sensors” > “Try it out” > preenchA:
JSON

{
"location": "Centro"
}
Criar evento de incêndio: Clique em “POST /fire/events” > “Try it out” > preencha:
JSON

{
"sensorId": "xxxxxx",
"description": "Sensor detectou fumaça"
}

Veja os dados nos outros endpoints.

Dúvidas comuns:

Se não conectou ao MongoDB Atlas, revise usuário, senha, host, e libere o seu IP em Network Access do site Atlas.
Se não abriu o swagger: veja se a aplicação não deu erro ao iniciar.

