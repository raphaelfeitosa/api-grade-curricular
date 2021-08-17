<h1 id="grade-curricular" align="rigth">
			API Restfull responsável pela manutenção curricular
</h1>


## O que há neste documento

- [Executando todos os testes](https://github.com/raphaelfeitosa/microservice-grade-curricular#executando-todos-os-testes)
- [Executando o projeto com Docker](https://github.com/raphaelfeitosa/microservice-grade-curricular#executando-o-projeto-com-docker)
- [Executando o projeto localmente](https://github.com/raphaelfeitosa/microservice-grade-curricular#executando-o-projeto-localmente)
- [Tecnologias](https://github.com/raphaelfeitosa/microservice-grade-curricular#tecnologias)
- [Recursos](https://github.com/raphaelfeitosa/microservice-grade-curricular#recursos-da-api-v1)
- [Swagger](https://github.com/raphaelfeitosa/microservice-grade-curricular#swagger)
- [Contribuir](https://github.com/raphaelfeitosa/microservice-grade-curricular#como-contribuir-para-o-projeto)
- [Autor](https://github.com/raphaelfeitosa/microservice-grade-curricular#autor)


## Executando todos os testes

No terminal, navegue até a pasta raiz do projeto e execute

```shell
./mvnw test
```
no Windows

```shell
mvnw.cmd test
```

## Executando o projeto com Docker

No terminal, navegue até a pasta raiz do projeto e execute

```shell
docker-compose up --build
```

## Executando o projeto localmente

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

## Tecnologias

As seguintes tecnologias foram usadas na construção do projeto:


- **[Spring Boot](https://spring.io/projects/spring-boot)**
- **[DB H2](https://www.h2database.com)**
- **[PostgreSQL](https://www.postgresql.org)**
- **[Swagger](https://swagger.io)**
- **[Junit 5](https://junit.org/junit5)**
- **[HATEOAS](https://spring.io/projects/spring-hateoas)**
- **[Modelmapper](http://modelmapper.org)**
- **[Docker](https://www.docker.com)**

## Recursos da API v1
Recursos disponíveis para acesso via api: `http://localhost:8081/api/v1`

- ### Rescurso [/materias], Methods: GET, POST, PUT, DELETE
  ```
  GET: /materias: para listar todas as materias  
  GET: /materias/frequencia/{id}: para listar todas as materias pela frequencia 
  GET: /materias/horario-minimo/{horas}: para listar todas as materias pelo horario minimo
  GET: /materias/{id}: para listar a materia pelo id
  POST: /materias: para cadastrar uma materia
  PUT: /materias: para alterar uma materia
  DELETE: /materias/{id}: para deletar uma materia 
  ```

- ### Rescurso [/cursos], Methods: GET, POST, PUT, DELETE
  ```
  GET: /cursos: para listar todos os cursos  
  GET: /cursos/{id}: para listar o curso pelo id
  GET: /cursos/codigo-curso?codigo={codigo}: para listar o curso pelo codigo
  POST: /cursos: para cadastrar um curso
  PUT: /cursos: para alterar um curso
  DELETE: /cursos/{id}: para deletar um curso
  ```

- ### Autorização para acessar todos os recursos:
  ```
  Authorization: Basic Auth [Username: root, Password: root]
  ```

- ### Responses
    | Código | Descrição |
    |---|---|
    | `200` | Requisição executada com sucesso (Success).|
    | `201` | Requisição cadastrada com sucesso (Success).|
    | `400` | Erros de validação ou cadastro existente (Bad Request).|
    | `401` | Dados de acesso inválidos (Unauthorized).|
    | `404` | Registro pesquisado não encontrado (Not Found).|

### MATERIAS
**POST:** `/materias` com *body*:

- Request (application/json)
    ```json
    {
      "nome": "PROGRAMAÇÃO ORIENTADA A OBJETOS",
      "codigo": "POO",
      "horas": 65,
      "frequencia": 1
    }
    ```

- Response 201 (Created)
    ```json
    {
      "statusCode": 200,
      "data": true,
      "timeStamp": 1628736630946,
      "links": [
        {
          "rel": "self",
          "href": "http://localhost:8081/api/v1/materias"
        },
        {
          "rel": "UPDATE",
          "href": "http://localhost:8081/api/v1/materias"
        },
        {
          "rel": "GET_ALL",
          "href": "http://localhost:8081/api/v1/materias"
        }
      ]
    }
    ```
- Response 400 (Bad Request) - Cadastro existente
    ```json
    {
       "statusCode": 400,
       "data": "Matéria já possui cadastro.",
       "timeStamp": 1629227727801,
       "links": []
    }
    ```

- Response 400 (Bad Request) - Erros na validação
    ```json
      {
        "statusCode": 400,
        "data": {
            "horas": "Permitido o mínimo de 34 horas por matéria.",
            "codigo": "Informe o codigo da matéria",
            "frequencia": "Permitido o máximo de 2 vezes ao ano.",
            "nome": "Informe o nome da matéria"
        },
        "timeStamp": 1629224072902,
        "links": []
      }
    ```
- Response 401 (Unauthorized)

### Obter lista de todas as materias

**GET** `/materias` vai retornar:

- Response 200 (application/json)
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
              "href": "http://localhost:8081/api/v1/materias/1"
            },
            {
              "rel": "UPDATE",
              "href": "http://localhost:8081/api/v1/materias"
            },
            {
              "rel": "DELETE",
              "href": "http://localhost:8081/api/v1/materias/1"
            }
          ]
        },
        {
          "id": 2,
          "nome": "INTRODUÇÃO A LOGICA DE PROGRAMAÇÃO",
          "horas": 50,
          "codigo": "ILP",
          "frequencia": 1,
          "links": [
            {
              "rel": "self",
              "href": "http://localhost:8081/api/v1/materias/2"
            },
            {
              "rel": "UPDATE",
              "href": "http://localhost:8081/api/v1/materias"
            },
            {
              "rel": "DELETE",
              "href": "http://localhost:8081/api/v1/materias/2"
            }
          ]
        }
      ],
      "timeStamp": 1629220947193,
      "links": [
        {
          "rel": "self",
          "href": "http://localhost:8081/api/v1/materias"
        }
      ]
    }
    ```

- Response 401 (Unauthorized)

### Obter lista de todas as materias por horario minimo

**GET** `/materias/horario-minimo/{horas}` vai retornar:

- Response 200 (application/json)
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
              "href": "http://localhost:8081/api/v1/materias/1"
            },
            {
              "rel": "UPDATE",
              "href": "http://localhost:8081/api/v1/materias"
            },
            {
              "rel": "DELETE",
              "href": "http://localhost:8081/api/v1/materias/1"
            }
          ]
        }
      ],
      "timeStamp": 1628735814026,
      "links": [
        {
          "rel": "self",
          "href": "http://localhost:8081/api/v1/materias"
        }
      ]
    }
    ```

- Response 401 (Unauthorized)

### Obter lista de todas as materias por frequência

**GET** `/materias/{frequencia}` vai retornar:

- Response 200 (application/json)
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
              "href": "http://localhost:8081/api/v1/materias/1"
            },
            {
              "rel": "UPDATE",
              "href": "http://localhost:8081/api/v1/materias"
            },
            {
              "rel": "DELETE",
              "href": "http://localhost:8081/api/v1/materias/1"
            }
          ]
        }
      ],
      "timeStamp": 1628735814026,
      "links": [
        {
          "rel": "self",
          "href": "http://localhost:8081/api/v1/materias"
        }
      ]
    }
    ```

- Response 401 (Unauthorized)

### Consultar máteria por id

**GET** `/materias/{id}` vai retornar:

- Response 200 (application/json)
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
          "href": "http://localhost:8081/api/v1/materias/1"
        },
        {
          "rel": "GET_ALL",
          "href": "http://localhost:8081/api/v1/materias"
        },
        {
          "rel": "UPDATE",
          "href": "http://localhost:8081/api/v1/materias"
        },
        {
          "rel": "DELETE",
          "href": "http://localhost:8081/api/v1/materias/1"
        }
      ]
    }
    ```

- Response 404 (Not Found) - cadastro inexistente
    ```json
    {
       "statusCode": 404,
       "data": "Matéria não encontrada.",
       "timeStamp": 1629228521823,
       "links": []
    }
    ```

- Response 401 (Unauthorized)

### Alterar materia

**PUT** `/materias` com *body*:

- Request (application/json)
    ```json
    {
      "id": 1,
      "nome": "PROGRAMAÇÃO ORIENTADA A OBJETOS",
      "horas": 70,
      "codigo": "POO",
      "frequencia": 2
    }
    ```
  
- Response 200 (application/json)

    ```json
    {
      "statusCode": 200,
      "data": true,
      "timeStamp": 1628736630946,
      "links": [
        {
          "rel": "self",
          "href": "http://localhost:8081/api/v1/materias"
        },
        {
          "rel": "UPDATE",
          "href": "http://localhost:8081/api/v1/materias"
        },
        {
          "rel": "GET_ALL",
          "href": "http://localhost:8081/api/v1/materias"
        },
        {
          "rel": "DELETE",
          "href": "http://localhost:8081/api/v1/materias/1"
        }
      ]
    }
    ```

- Response 400 (Bad Request) - Erros na validação
    ```json
    {
    "statusCode": 400,
    "data": {
        "data": "Informe o ID para alterar o cadastro",
        "horas": "Permitido o mínimo de 34 horas por matéria.",
        "codigo": "Informe o codigo da matéria",
        "nome": "Informe o nome da matéria"
    },
    "timeStamp": 1629227304659,
    "links": []
    }
    ```

- Response 401 (Unauthorized)

### Excluir materia por id

**DELETE** `/materias/{id}` sem *body*:

- Response 200 (application/json)
    ```json
    {
      "statusCode": 200,
      "data": true,
      "timeStamp": 1628736887404,
      "links": [
        {
          "rel": "self",
          "href": "http://localhost:8081/api/v1/materias/1"
        },
        {
          "rel": "GET_ALL",
          "href": "http://localhost:8081/api/v1/materias"
        }
      ]
    }
    ```

- Response 404 (Not Found) - cadastro inexistente
    ```json
    {
       "statusCode": 404,
       "data": "Matéria não encontrada.",
       "timeStamp": 1629228521823,
       "links": []
    }
    ```

- Response 401 (Unauthorized)

### CURSOS `http://localhost:8081/api/api/v1`
**POST:** `/cursos` com *body*:

- Request (application/json)
    ```json
    {
      "nome": "BACHAREL EM SISTEMAS DE INFORMAÇÃO",
      "codigo": "BSI",
      "materias": [1]
    }
    ```

- Response 201 (Created)

    ```json
    {
      "statusCode": 201,
      "data": true,
      "timeStamp": 1629220404672,
      "links": [
        {
          "rel": "self",
          "href": "http://localhost:8081/api/v1/cursos"
        },
        {
          "rel": "UPDATE",
          "href": "http://localhost:8081/api/v1/cursos"
        },
        {
          "rel": "GET_ALL",
          "href": "http://localhost:8081/api/v1/cursos"
        }
      ]
    }
    ```

- Response 400 (Bad Request) - Cadastro existente
    ```json
    {
       "statusCode": 400,
       "data": "curso já possui cadastro.",
       "timeStamp": 1629227727801,
       "links": []
    }
    ```

- Response 400 (Bad Request) - Erros na validação
    ```json
    {
      "statusCode": 400,
      "data": {
          "codigo": "tamanho deve ser entre 2 e 6",
          "nome": "Informe o nome do curso"
      },
      "timeStamp": 1629228929281,
      "links": []
    }
    ```

- Response 401 (Unauthorized)

### Obter lista de todos os cursos
**GET** `/cursos` vai retornar:

- Response 200 (application/json)
    ```json
    {
      "statusCode": 200,
      "data": [
        {
          "id": 1,
          "nome": "BACHAREL EM SISTEMAS DE INFORMAÇÃO",
          "codigo": "BSI",
          "materias": [
            {
              "id": 1,
              "nome": "PROGRAMAÇÃO ORIENTADA A OBJETOS",
              "horas": 65,
              "codigo": "POO",
              "frequencia": 1
            }
          ],
          "links": [
            {
              "rel": "self",
              "href": "http://localhost:8081/api/v1/cursos/1"
            },
            {
              "rel": "UPDATE",
              "href": "http://localhost:8081/api/v1/cursos"
            },
            {
              "rel": "DELETE",
              "href": "http://localhost:8081/api/v1/cursos/1"
            }
          ]
        }
      ],
      "timeStamp": 1629220661293,
      "links": [
        {
          "rel": "self",
          "href": "http://localhost:8081/api/v1/cursos"
        }
      ]
    }
    ```
  
- Response 401 (Unauthorized)

### Consultar curso por id
**GET** `/cursos/{id}` vai retornar:

- Response 200 (application/json)
    ```json
    {
      "statusCode": 200,
      "data": {
        "id": 1,
        "nome": "BACHAREL EM SISTEMAS DE INFORMAÇÃO",
        "codigo": "BSI",
        "materias": [
          {
            "id": 1,
            "nome": "PROGRAMAÇÃO ORIENTADA A OBJETOS",
            "horas": 65,
            "codigo": "POO",
            "frequencia": 1
          }
        ],
        "links": []
      },
      "timeStamp": 1629220693487,
      "links": [
        {
          "rel": "self",
          "href": "http://localhost:8081/api/v1/cursos/1"
        },
        {
          "rel": "UPDATE",
          "href": "http://localhost:8081/api/v1/cursos"
        },
        {
          "rel": "DELETE",
          "href": "http://localhost:8081/api/v1/cursos/1"
        }
      ]
    }
    ```

- Response 404 (Not Found)
    ```json
    {
       "statusCode": 404,
       "data": "Curso não encontrado.",
       "timeStamp": 1629228521823,
       "links": []
    }
    ```

- Response 401 (Unauthorized)

### Consultar curso por codigo

**GET** `/cursos/codigo-curso?codigo={codigo}` vai retornar:

- Response 200 (application/json)
    ```json
    {
      "statusCode": 200,
      "data": {
        "id": 1,
        "nome": "BACHAREL EM SISTEMAS DE INFORMAÇÃO",
        "codigo": "BSI",
        "materias": [
          {
            "id": 1,
            "nome": "PROGRAMAÇÃO ORIENTADA A OBJETOS",
            "horas": 65,
            "codigo": "POO",
            "frequencia": 1
          }
        ],
        "links": []
      },
      "timeStamp": 1629220693487,
      "links": [
        {
          "rel": "self",
          "href": "http://localhost:8081/api/v1/cursos/1"
        },
        {
          "rel": "UPDATE",
          "href": "http://localhost:8081/api/v1/cursos"
        },
        {
          "rel": "DELETE",
          "href": "http://localhost:8081/api/v1/cursos/1"
        }
      ]
    }
    ```

- Response 404 (Not Found)
    ```json
    {
       "statusCode": 404,
       "data": "Curso não encontrado.",
       "timeStamp": 1629228521823,
       "links": []
    }
    ```

- Response 401 (Unauthorized)

### Alterar curso
**PUT** `/cursos` com *body*:

- Request (application/json)
    ```json
    {
      "id": 1,
      "nome": "BACHAREL EM SISTEMAS DE INFORMAÇÃO",
      "codigo": "BSI",
      "materias": [1,2]
    }
    ```
  
- Response 200 (application/json)
    ```json
    {
      "statusCode": 200,
      "data": true,
      "timeStamp": 1629221204249,
      "links": [
        {
          "rel": "self",
          "href": "http://localhost:8081/api/v1/cursos"
        },
        {
          "rel": "GET_ALL",
          "href": "http://localhost:8081/api/v1/cursos"
        },
        {
          "rel": "DELETE",
          "href": "http://localhost:8081/api/v1/cursos/1"
        }
      ]
    }
    ```

- Response 400 (Bad Request) - Erros na validação
    ```json
    {
    "statusCode": 400,
    "data": {
        "data": "Informe o ID para alterar o cadastro",
        "horas": "Permitido o mínimo de 34 horas por matéria.",
        "codigo": "Informe o codigo da matéria",
        "nome": "Informe o nome da matéria"
    },
    "timeStamp": 1629227304659,
    "links": []
    }
    ```

- Response 404 (Not Found)
    ```json
    {
       "statusCode": 404,
       "data": "Curso não encontrado.",
       "timeStamp": 1629228521823,
       "links": []
    }
    ```

- Response 401 (Unauthorized)

### Excluir curso

**DELETE** `/cursos/{id}` sem *body*:

- Response 200 (application/json)
  ```json
  {
    "statusCode": 200,
    "data": true,
    "timeStamp": 1629221293353,
    "links": [
      {
        "rel": "self",
        "href": "http://localhost:8081/api/v1/cursos/1"
      },
      {
        "rel": "GET_ALL",
        "href": "http://localhost:8081/api/v1/cursos"
      }
    ]
  }
  ```

- Response 404 (Not Found)
    ```json
    {
       "statusCode": 404,
       "data": "Curso não encontrado.",
       "timeStamp": 1629228521823,
       "links": []
    }
    ```

- Response 401 (Unauthorized)

## Swagger

`http://localhost:8081/api/swagger-ui.html`

### Materia

<p align="center" style="display: flex; align-items: flex-start; justify-content: center;">
  <img alt="Vendas" title="#Home" src="https://github.com/raphaelfeitosa/microservice-grade-curricular/blob/main/assets/swagger-materia.png?raw=true"/>
</p>

### Curso

<p align="center" style="display: flex; align-items: flex-start; justify-content: center;">
  <img alt="Vendas" title="#Home" src="https://github.com/raphaelfeitosa/microservice-grade-curricular/blob/main/assets/swagger-curso.png?raw=true"/>
</p>

## Como contribuir para o projeto

1. Faça um **fork** do projeto.
2. Crie uma nova branch com as suas alterações: `git checkout -b my-awesome-feature`
3. Salve as alterações e crie uma mensagem de commit contando o que você fez: `git commit -m "feature: My awesome feature"`
4. Envie as suas alterações: `git push origin my-awesome-feature`
   > Caso tenha alguma dúvida confira este [guia de como contribuir no GitHub](https://github.com/firstcontributions/first-contributions)

## Autor

<a href="https://github.com/raphaelfeitosa">
 <img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/raphaelfeitosa" width="100px;" alt=""/>

<sub><b>Raphael Feitosa</b></sub></a> <a href="https://github.com/raphaelfeitosa/" title="Rocketseat">🚀</a>
<br />

[<img src="https://img.shields.io/badge/linkedin-%230077B5.svg?&style=for-the-badge&logo=linkedin&logoColor=white" />](https://www.linkedin.com/in/raphael-feitosa/) <a href="mailto:raphaelcs2@gmail.com"><img src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white"/></a>
<br />

[⬆ Voltar ao topo](#grade-curricular)<br>