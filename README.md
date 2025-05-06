Fire Service – Monitoramento Inteligente de Incêndios

O que é isso?

Este é um sistema web que serve como exemplo de aplicação para cidades inteligentes: você poderá cadastrar sensores de incêndio, registrar alertas de incêndio, consultar tudo por interface gráfica swaggerUI, com o banco de dados noSQL (MongoDB) rodando na nuvem da Azure. Os testes unitários e pipeline CI/CD já estão implementados com Gherkin e Cocumber via Github Actions para fazer a validação dos métodos e o deploy de forma automática ao enviar um git push para a branch main no repositório do Github.

GUIA PASSO-A-PASSO

Pré-requisitos:

- Java 21
- Maven build tool
- Conta Github
- Conta MongoDB
- Conta Dockerhub
- Conta Azure

1. Criar seu banco MongoDB Atlas e configurar string de conexão:


- Entre em https://www.mongodb.com/cloud/atlas/register e faça cadastro.
- Clique em “Build a Database”, escolha M0 (Free).
- Siga os passos para criar um usuário e senha do banco (por exemplo - user: admin, senha: admin123).
- Clique em "Network Access" > "Add IP Address" > "Allow Access From Anywhere".
- Clique em "Database" > "Connect" > "Drivers" > escolha "Java" e copie a string semelhante a:

- String: mongodb+srv://USUARIO:SENHA@SEU_CLUSTER.mongodb.net/SEU_BANCO?retryWrites=true&w=majority&appName=SEU_CLUSTER
   
- IMPORTANTE: copie a conexão completa, troque o nome do usuário, senha, nome do cluestes e nome do banco pelos que você criou.


2. Criar o repositório no seu Github e prepar o projeto:

- Acesse o repositório original em: https://github.com/tiagofarsonisiemann/fire-alarm
- No canto superior direito, clique no botão “Fork”.
- Escolha sua conta pessoal ou organização para onde deseja copiar o repositório.
- O GitHub irá criar uma cópia exata desse repositório na sua conta.
- Clone o repositório Forkado para sua máquina usando: git clone https://github.com/SEU_USUARIO/fire-alarm.git

4. Compilar o Projeto para primeiro deploy manual:

- No terminal dentro do seu projeto, compile a aplicação usando o código mvn clean package -DskipTests.
- Isso criará um JAR compilado dentro da pasta target/.

5. Criar imagem e enviar para DockerHub para primeiro deploy manual:

- Crie imagem e envie para o Docker Hub
- Faz a build da imagem: docker build -t seu_usuario/fire-alarm:latest .
- Envia a imagem para o DockerHub: docker push seu_usuario/fire-alarm:latest
- Lembre-se: para o Azure fazer o deploy, o DockerHub do seu projeto deve estar como público.
- Você pode verificar as configurações do seu DockerHub online em https://hub.docker.com/repository/docker/SEU_USUARIO/fire-alarm-app/settings

6. Criar Web App na Azure e fazer primeiro deploy manual

- Crie o seu ResourceGroup
- Vá ao Portal > “App Services” > +Criar: Web App.
- Na aba Basics, selecione as opções para Publish: "Container" > OS: "Linux" > Plano: B1 pelo menos (não funciona o deploy automático no free F1).
- Na aba Container, selecione em Image Source: "Other container registries" > Access Type: "Public".
- Em Image and tag coloque o local do seu app no DockerHub, ex: SEU_USUARIO/fire-alarm-app:latest
- Finalize e espere o deploy na aplicação na Azure.
- OBS: somente este primeiro deploy precisa ser manual para ajustar as configurações iniciais, os próximos ja serão feitos de forma automatizada.

7. Configurar variavel de ambiente (string) de conexão ao banco na Azure

- No portal do Azure vá até o App Service criado.
- Menu lateral > Settings > Environment variables.
- Clique em + Add.
- Salve como Nome: SPRING_DATA_MONGODB_URI
- Salve como Valor a string de conexão do banco: mongodb+srv://USUARIO:SENHA@SEU_CLUSTER.mongodb.net/SEU_BANCO?retryWrites=true&w=majority&appName=SEU_CLUSTER
- Salve em "Apply" e reinicialize o app.

8. Configurar integração CI/CD da Azure com Github para deploy automatico

- No portal do Azure vá até o App Service criado.
- Menu lateral > Deployment Center > Settings.
- Source: Github Actions > Logue com sua conta do Github.
- Escolha o Repository: "fire-alarm" > Banch: "main" > Workflow options: "Use existing workflow".
- Em Registry settings > Registry source: "Docker Hub" > Login: coloque seu user > Password: coloque seu token de acesso > Image name: "SEU_USUARIO/fire-alarm-app".
- O gerenciamento das tags será feito pelo próprio Github.

9. Configurar as secrets no Github

- No seu projeto no Github, vá em Settings > Secrets and Variables > Actions > New Repository Secret > crie as seguintes variáveis e salve.
- DOCKERHUB_USERNAME: seu usuario Docker Hub.
- DOCKERHUB_TOKEN: seu token de acesso Docker Hub (precisá já ter gerado anteriormente na sua conta Docker Hub).
- AZUREAPPSERVICE_PUBLISHPROFILE: no seu app na Azure > vá "download publish profile" > copie, cole e salve o codigo do seu publish profile.

10. Testar a automação dos testes e CI/CD

- Faça alguma alteração simples em algum arquivo do codigo.
- No terminal, dê os seguintes comandos.
- git add .
- git commit -m "sua mensagem"
- git push origin main

11. Teste os métodos da aplicação com SwaggerUI

- 