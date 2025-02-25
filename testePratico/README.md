# Documentação do Projeto

## Arquitetura do Projeto

O projeto segue uma arquitetura em camadas, um padrão de desenvolvimento que organiza o código em diferentes camadas, separando responsabilidades e tornando o sistema mais modular e fácil de manter. Essa abordagem melhora a legibilidade do código, facilita a realização de testes e permite a escalabilidade do projeto.

Por se tratar de um projeto pequeno, não foi necessário adotar uma arquitetura mais complexa, como a hexagonal.

As camadas estão organizadas da seguinte forma:

- **config**: Configurações gerais do projeto, integração com outros serviços.
- **controllers**: Contém os endpoints da API, responsáveis por receber as requisições e retornar as respostas.
- **dto (Data Transfer Object)**: Objetos de transferência de dados.
    - **request**: Representa os dados recebidos na requisição.
    - **response**: Representa os dados retornados na resposta.
- **entities**: Representa os modelos de dados utilizados no banco de dados.
- **exceptions**: Tratamento de exceções e personalização de erros.
- **repositories**: Camada de persistência, onde estão os repositórios que interagem com o banco de dados.
- **services**: Contém a lógica de negócio da aplicação.

## Documentação da API

A API é documentada utilizando o Swagger, permitindo que todas as rotas disponíveis sejam consumidas de forma intuitiva.

Além disso, as rotas de `getAll` utilizam paginação para limitar a quantidade de dados retornados por página, melhorando a performance e evitando sobrecarga de informações.

## Testes

O projeto conta com uma cobertura de testes unitários, garantindo maior confiabilidade e estabilidade no desenvolvimento.

## Como Rodar o Projeto

### Rodando o projeto standalone
```sh
cd .\testePratico\
mvn clean package
```

### Rodando o projeto com Docker
```sh
docker compose up --build
```

### Rodando apenas o banco de dados
```sh
docker compose up db
```

## Conexão com o Banco de Dados

| Parâmetro         | Valor        |
|--------------------|-------------|
| POSTGRES_DB       | giro        |
| POSTGRES_USER     | postgres    |
| POSTGRES_PASSWORD | admin000    |
| Porta            | 5432        |

## Acessando o Swagger

Para acessar a documentação da API via Swagger, abra o navegador e acesse:

```
http://localhost:8080/swagger-ui/index.html#/
```

