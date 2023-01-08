# API Teste Técnico Attornatus

### Requisitos

Usando Spring boot, crie uma API simples para gerenciar Pessoas. Esta API deve permitir:

- Criar uma pessoa
- Editar uma pessoa
- Consultar uma pessoa
- Listar pessoas
- Criar endereço para pessoa
- Listar endereços da pessoa
- Poder informar qual endereço é o principal da pessoa

Uma Pessoa deve ter os seguintes campos:
- Nome
- Data de nascimento
- Endereço
  - Logradouro
  - CEP
  - Número
  - Cidade

Todas as respostas da API devem ser JSON;  
Banco de dados H2;  
Testes;  
Clean Code;

### Tecnologias
- Java 18
- Spring Boot 3
- Banco de dados H2
- Swagger

### Endpoints
Os endpoints estão declarados em [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) (após a inicialização da aplicação) e também podem ser encontrados em **Collection Teste Técnico.json**.