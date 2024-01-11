## Sobre o projeto

* Esse projeto tem como objetivo realizar um fluxo simples de criação e autenticação de usuários utilizando principalmente para essas finalidades o Spring boot, Spring Security e JWT.
* Foi realizado deploy do projeto e integrado com Postgres. Entretanto, para fins de testes, ele está disponível apenas localmente utilizando o H2 database(banco em memória).
* Ao explorar o código será possível observar que existe uma implementação com um serviço de e-mail, ele realiza o envio de e-mail a partir do momento que um usuário é criado.
  Porém, se desejar utilizar, será necessário inicializar um serviço de e-mail em algum provider e implementar no código, uma vez que esse e-mail deve estar vinculado ao provider
  do serviço de e-mail e no código onde solicita essa informação (foi utilizado o AWS SES para essa finalidade).
    * Dica: Se desejar utilizar um provider diferente, basta implementar a interface EmailSenderGateway na classe especifica do serviço de e-mail que deseja utilizar e criar outra classe
    para as configs, como as credenciais desse provider, e.g.

## Instalação e execução

* Antes de executar será necessário clonar o projeto.
  ```
  git clone https://github.com/RaulMafra/auth-service.git
  ```
### 1ª opção para execução:
* A primeira opção consiste em criar o pacote e executá-lo a partir dos passos abaixo:
   * Acesse o diretório do projeto e execute o comando abaixo para criar o pacote *.jar. 
      ```
      mvn package
      ```
    * Por fim, dentro do diretório do projeto, inicialize a aplicação a partir do *.jar criado.
      ```
      java -jar ./target/auth-service-0.0.1.jar
      ```
### 2ª opção para execução:
* A segunda opção é abrir o projeto em alguma IDE de sua escolha e inicializá-la a partir dela como qualquer outro projeto.


## Especificações

* A documentação está no formato OpenAPI 3.0, onde poderá ser acessada através do endereço http://localhost:8080/swagger-ui/index.html após a inicialização.
* Através da documentação será possível conhecer os endpoints, funcionalidade de cada um deles e realizar testes. Acredito que não cabe explicá-los aqui, uma vez que são auto-explicativos
  e na documentação existe a descrição de cada um deles.
* A collection de usuários para teste está disponível em [Users Collection](https://github.com/RaulMafra/auth-service/blob/main/UsersCollection/Users.json). Vale ressaltar
  que essa collection possui apenas usuários com a role de usuários. Existe apenas um usuário com a role de administrator, sendo esse inserido no banco assim que a aplicação
  inicializa, já que será executado em ambiente de desenvolvimento. Essa abordagem impede que qualquer usuário se cadastre e autentica como administrador.
  
## License

See the [LICENSE](https://github.com/RaulMafra/auth-service/blob/main/LICENSE) file for license rights and limitations (MIT).
