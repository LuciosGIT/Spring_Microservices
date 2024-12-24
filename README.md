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
  

## Como Executar o Projeto

### Usando Docker Compose
Execute o comando ```bash
    docker compose up -d
    ``` nos diretórios que possuem o arquivo `docker-compose.yml` para iniciar os serviços simultaneamente.



### Usando Kubernetes
- Aplique os manifests YAML encontrados no diretório kubernetes:
  ```bash
  kubectl apply -f <nome_do_serviço>.yaml 
  ```

