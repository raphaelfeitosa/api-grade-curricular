# microservice-grade-curricular
Micro-service responsável pela manutenção curricular

## :speech_balloon: O que há neste documento

- [Executando todos os testes](https://github.com/raphaelfeitosa/microservice-grade-curricular#executando-todos-os-testes)
- [Executando o projeto com Docker](https://github.com/raphaelfeitosa/microservice-grade-curricular#whaleexecutando-o-projeto-com-docker)
- [Executando o projeto localmente](https://github.com/raphaelfeitosa/microservice-grade-curricular#computerexecutando-o-projeto-localmente)
- [Tecnologias](https://github.com/raphaelfeitosa/microservice-grade-curricular#mag_righttecnologias)
- [Endpoints disponíveis](https://github.com/raphaelfeitosa/microservice-grade-curricular#mag_rightendpoints-disponíveis)
- [Swagger](https://github.com/raphaelfeitosa/microservice-grade-curricular#mag_rightendpoints-disponíveis)
- [Autor](https://github.com/raphaelfeitosa/microservice-grade-curricular#mag_rightautor)


## Executando todos os testes

No terminal, navegue até a pasta raiz do projeto e execute

```shell
./mvnw test
```
no Windows

```shell
mvnw.cmd test
```

## :whale:Executando o projeto com Docker

No terminal, navegue até a pasta raiz do projeto e execute

```shell
docker-compose up --build
```

## :computer:Executando o projeto localmente

Para que não seja necessário instalar nada em sua máquina, a aplicação esta configurada para salvar os dados no banco de dados em mémoria.

No terminal, navegue até a pasta raiz do projeto e execute

```shell
./mvnw clean install 
```

no Windows

```shell
mvnw.cmd clean install 
```

Após a conclusão, execute

```shell
./mvnw spring-boot:run
```

no Windows

```shell
mvnw.cmd spring-boot:run
```

## :🛠: Tecnologias

As seguintes tecnologias foram usadas na construção do projeto:


- **[Spring Boot](https://spring.io/projects/spring-boot)**
- **[DB H2](https://www.h2database.com)**
- **[PostgreSQL](https://www.postgresql.org)**
- **[Swagger](https://swagger.io)**
- **[Junit 5](https://junit.org/junit5)**
- **[HATEOAS](https://spring.io/projects/spring-hateoas)**
- **[Modelmapper](http://modelmapper.org/)**

## :mag_right: Endpoint disponíveis (v1)

### MATERIAS `http://localhost:8081/v1`
**POST:** `/materias` com *body*:

```json
{
  "nome": "PROGRAMAÇÃO ORIENTADA A OBJETOS",
  "codigo": "POO",
  "horas": 65,
  "frequencia": 1
}
```

**Response**

```json
{
  "statusCode": 200,
  "data": true,
  "timeStamp": 1628736630946,
  "links": [
    {
      "rel": "self",
      "href": "http://localhost:8081/v1/materias"
    },
    {
      "rel": "UPDATE",
      "href": "http://localhost:8081/v1/materias"
    },
    {
      "rel": "GET_ALL",
      "href": "http://localhost:8081/v1/materias"
    },
    {
      "rel": "DELETE",
      "href": "http://localhost:8081/v1/materias/1"
    }
  ]
}
```
### Obter lista de todas as materias

**GET** `/materias` vai retornar:

```json
{
  "statusCode": 200,
  "data": [
    {
      "id": 1,
      "nome": "PROGRAMAÇÃO ORIENTADA A OBJETOS",
      "horas": 65,
      "codigo": "POO",
      "frequencia": 1,
      "links": [
        {
          "rel": "self",
          "href": "http://localhost:8081/v1/materias/1"
        },
        {
          "rel": "UPDATE",
          "href": "http://localhost:8081/v1/materias"
        },
        {
          "rel": "DELETE",
          "href": "http://localhost:8081/v1/materias/1"
        }
      ]
    }
  ],
  "timeStamp": 1628735814026,
  "links": [
    {
      "rel": "self",
      "href": "http://localhost:8081/v1/materias"
    }
  ]
}
```

### Obter lista de todas as materias por horario minimo

**GET** `/materias/horario-minimo/{horas}` vai retornar:

```json
{
  "statusCode": 200,
  "data": [
    {
      "id": 1,
      "nome": "PROGRAMAÇÃO ORIENTADA A OBJETOS",
      "horas": 65,
      "codigo": "POO",
      "frequencia": 1,
      "links": [
        {
          "rel": "self",
          "href": "http://localhost:8081/v1/materias/1"
        },
        {
          "rel": "UPDATE",
          "href": "http://localhost:8081/v1/materias"
        },
        {
          "rel": "DELETE",
          "href": "http://localhost:8081/v1/materias/1"
        }
      ]
    }
  ],
  "timeStamp": 1628735814026,
  "links": [
    {
      "rel": "self",
      "href": "http://localhost:8081/v1/materias"
    }
  ]
}
```

### Obter lista de todas as materias por frequência

**GET** `/materias/{frequencia}` vai retornar:

```json
{
  "statusCode": 200,
  "data": [
    {
      "id": 1,
      "nome": "PROGRAMAÇÃO ORIENTADA A OBJETOS",
      "horas": 65,
      "codigo": "POO",
      "frequencia": 1,
      "links": [
        {
          "rel": "self",
          "href": "http://localhost:8081/v1/materias/1"
        },
        {
          "rel": "UPDATE",
          "href": "http://localhost:8081/v1/materias"
        },
        {
          "rel": "DELETE",
          "href": "http://localhost:8081/v1/materias/1"
        }
      ]
    }
  ],
  "timeStamp": 1628735814026,
  "links": [
    {
      "rel": "self",
      "href": "http://localhost:8081/v1/materias"
    }
  ]
}
```

### Consultar máteria por id

**GET** `/materias/{id}` vai retornar:

```json
{
  "statusCode": 200,
  "data": {
    "id": 1,
    "nome": "PROGRAMAÇÃO ORIENTADA A OBJETOS",
    "horas": 65,
    "codigo": "POO",
    "frequencia": 1
  },
  "timeStamp": 1628736338954,
  "links": [
    {
      "rel": "self",
      "href": "http://localhost:8081/v1/materias/1"
    },
    {
      "rel": "GET_ALL",
      "href": "http://localhost:8081/v1/materias"
    },
    {
      "rel": "UPDATE",
      "href": "http://localhost:8081/v1/materias"
    },
    {
      "rel": "DELETE",
      "href": "http://localhost:8081/v1/materias/1"
    }
  ]
}
```

### Alterar materia por id

**PUT** `/materias` com *body*:

```json
{
  "id": 1,
  "nome": "PROGRAMAÇÃO ORIENTADA A OBJETOS",
  "horas": 70,
  "codigo": "POO",
  "frequencia": 2
}
```
**Response**

```json
{
  "statusCode": 200,
  "data": true,
  "timeStamp": 1628736630946,
  "links": [
    {
      "rel": "self",
      "href": "http://localhost:8081/v1/materias"
    },
    {
      "rel": "UPDATE",
      "href": "http://localhost:8081/v1/materias"
    },
    {
      "rel": "GET_ALL",
      "href": "http://localhost:8081/v1/materias"
    },
    {
      "rel": "DELETE",
      "href": "http://localhost:8081/v1/materias/1"
    }
  ]
}
```

### excluir materia

**DELETE** `/materias/{id}` sem *body*:

**Response**

```json
{
  "statusCode": 200,
  "data": true,
  "timeStamp": 1628736887404,
  "links": [
    {
      "rel": "self",
      "href": "http://localhost:8081/v1/materias/1"
    },
    {
      "rel": "GET_ALL",
      "href": "http://localhost:8081/v1/materias"
    }
  ]
}
```

## Documentação da API com Swagger:

`http://localhost:8081/v1/swagger-ui.html`

## 🦸 Autor

<a href="https://github.com/raphaelfeitosa">
 <img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/raphaelfeitosa" width="100px;" alt=""/>

<sub><b>Raphael Feitosa</b></sub></a> <a href="https://github.com/raphaelfeitosa/" title="Rocketseat">🚀</a>
<br />

[<img src="https://img.shields.io/badge/linkedin-%230077B5.svg?&style=for-the-badge&logo=linkedin&logoColor=white" />](https://www.linkedin.com/in/raphael-feitosa/) <a href="mailto:raphaelcs2@gmail.com"><img src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white"/></a>
<br />