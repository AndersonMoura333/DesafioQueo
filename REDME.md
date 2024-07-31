# Projeto Queo: Microsserviços de Associação e Cadastro

Este repositório contém dois microsserviços separados, `associacaoQueo` e `CadastroQueo`, desenvolvidos em Java utilizando Spring Boot. O `associacaoQueo` lida com a lógica de associação entre veículos e condutores, enquanto o `CadastroQueo` gerencia o cadastro e a consulta de veículos.

## Estrutura do Projeto

- `associacaoQueo`: Microsserviço responsável pela associação de veículos e condutores.
- `cadastroQueo`: Microsserviço responsável pelo cadastro e consulta de veículos e condutores.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- Hibernate
- PostgreSQL
- Feign Client (para comunicação entre microsserviços)
- Docker

## Requisitos

- JDK 17 ou superior
- Maven 3.8 ou superior
- Docker e Docker Compose

## Configuração e Execução

### Passo 1: Clonar o Repositório

```sh
git clone https://github.com/AndersonMoura333/DesafioQueo.git
cd DesafioQueo
```
## Endpoints Disponíveis

### AssociationQueo

- `POST /association/login`: Realiza a associação entre um condutor e um veiculo.
- `PATCH /association/logout`:  Realiza o fim de uma associação entre um condutor e um veiculo.
- `GET  /association/findbyperiod`: Retorna todas as associações do veículo dentro do período especificado.
- `GET  /association/status/{{registration}}`:  Retorna o status de associação atual do condutor.

### CadastroQueo

- `POST /vehicle`: Registra um novo veículo.
- `GET /vehicle/{id}`: Consulta um veículo pelo id.
- `GET /vehicle/find-plate/{plate}`: Consulta um veículo pelo número da placa.
- `PATCH /vehicle/{id}`: Atualiza um veículo pelo número do id.
- `DELETE /vehicle/{id}`: Deleta um veículo pelo número do id.

- `POST /driver`: Registra um novo condutor.
- `GET /driver/{id}`: Consulta um condutor pelo id.
- `GET /registration/{registration}`: Consulta um condutor pelo número da matrícula.
- `PATCH /driver/{id}`: Atualiza um condutor pelo número do id.
- `DELETE /driver/{id}`: Deleta um condutor pelo número do id.

## Comunicação entre Microsserviços

Os microsserviços utilizam o Feign Client para comunicação interna. Abaixo está um exemplo de configuração do Feign Client no `AssociationQueo`:

```java
@FeignClient(name = "cadastroQueo", url = "http://localhost:8081")
public interface CadastrationFeignClient {

    @GetMapping("/vehicle/find-plate/{plate}")
    boolean existsVehicleByPlate(@PathVariable("plate") String plate);
}
