# Projeto de Microserviços: Accounts, Cards e Loans

Este repositório contém a implementação de um sistema baseado em microserviços para gestão de contas, cartões e empréstimos. O projeto utiliza Docker, Kubernetes e Apache Kafka para gerenciar conteinerização, orquestração e comunicação assíncrona entre os serviços.

## Estrutura do Projeto

### Microserviços

1. **Accounts**
   - Gerencia as informações de contas de usuários.
   - Exemplo de API REST:
     - `GET /accounts/{id}`: Recupera detalhes da conta.
     - `POST /accounts`: Cria uma nova conta.

2. **Cards**
   - Gerencia informações sobre cartões de crédito.
   - Exemplo de API REST:
     - `GET /cards/{id}`: Recupera detalhes do cartão.
     - `POST /cards`: Emite um novo cartão.

3. **Loans**
   - Gerencia empréstimos solicitados pelos usuários.
   - Exemplo de API REST:
     - `GET /loans/{id}`: Recupera detalhes do empréstimo.
     - `POST /loans`: Solicita um novo empréstimo.

## Tecnologias Utilizadas

### Docker
Cada microserviço possui uma **Docker image** para facilitar a conteinerização e portabilidade.

- **Comandos importantes:**
  - Construção da imagem:
    ```bash
    docker build -t <nome_servico>:<versao> .
    ```
  - Execução do container:
    ```bash
    docker run -d -p <porta_host>:<porta_container> <nome_servico>:<versao>
    ```
  
- Exemplo de Dockerfile para um microserviço:
  ```dockerfile
  FROM openjdk:17-jdk-alpine
  WORKDIR /app
  COPY target/<nome_jar>.jar app.jar
  ENTRYPOINT ["java", "-jar", "app.jar"]
  ```

### Kubernetes
A orquestração dos microserviços é feita através de um cluster Kubernetes.

- **Componentes principais:**
  - **Deployments**: Garantem a disponibilidade e atualização das aplicações.
  - **Services**: Exponhem os microserviços dentro ou fora do cluster.
  - **ConfigMaps e Secrets**: Gerenciam configurações e informações sensíveis.

- **Arquivos YAML de exemplo:**

  - Deployment:
    ```yaml
    apiVersion: apps/v1
    kind: Deployment
    metadata:
      name: accounts-service
    spec:
      replicas: 3
      selector:
        matchLabels:
          app: accounts
      template:
        metadata:
          labels:
            app: accounts
        spec:
          containers:
          - name: accounts
            image: <nome_servico>:<versao>
            ports:
            - containerPort: 8080
    ```

  - Service:
    ```yaml
    apiVersion: v1
    kind: Service
    metadata:
      name: accounts-service
    spec:
      selector:
        app: accounts
      ports:
      - protocol: TCP
        port: 80
        targetPort: 8080
      type: LoadBalancer
    ```

### Mensageria com Apache Kafka

O Apache Kafka é utilizado para implementar comunicação assíncrona entre os microserviços.

- **Configurações principais:**
  - Tópicos configurados:
    - `accounts-events`
    - `cards-events`
    - `loans-events`
  
- **Comandos úteis para Kafka:**
  - Criar um tópico:
    ```bash
    kafka-topics.sh --create --topic accounts-events --bootstrap-server localhost:9092
    ```
  - Consumir mensagens:
    ```bash
    kafka-console-consumer.sh --topic accounts-events --from-beginning --bootstrap-server localhost:9092
    ```

- **Exemplo de produção de mensagem:**
  ```java
  kafkaTemplate.send("accounts-events", "Nova conta criada: ID 1234");
  ```

- **Exemplo de consumo de mensagem:**
  ```java
  @KafkaListener(topics = "accounts-events", groupId = "group_id")
  public void consume(String message) {
      System.out.println("Mensagem recebida: " + message);
  }
  ```

## Como Executar o Projeto

### Usando Docker Compose
Crie um arquivo `docker-compose.yml` para iniciar os serviços simultaneamente.

```yaml
version: '3.8'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:latest
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
  kafka:
    image: confluentinc/cp-kafka:latest
    environment:
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
  accounts:
    build: ./accounts
    ports:
      - "8081:8080"
  cards:
    build: ./cards
    ports:
      - "8082:8080"
  loans:
    build: ./loans
    ports:
      - "8083:8080"
```

### Usando Kubernetes
- Aplique os manifests YAML:
  ```bash
  kubectl apply -f k8s/
  ```

## Contribuições
Contribuições são bem-vindas! Fique à vontade para abrir issues ou enviar pull requests.
