Microservices Architecture: Accounts, Cards, and Loans

Este projeto consiste em uma arquitetura de microserviços que gerencia serviços financeiros, incluindo contas, cartões e empréstimos. Cada microserviço foi desenvolvido de forma independente, utilizando boas práticas de design, escalabilidade e manutenção.

Estrutura do Projeto

Microserviços Principais

Accounts Microservice:

Gerencia informações de contas bancárias de clientes.

Operações: consulta, criação e atualização de contas.

Cards Microservice:

Gerencia cartões de crédito/débito associados às contas.

Operações: emissão, bloqueio e consulta de limites.

Loans Microservice:

Gerencia empréstimos contratados pelos clientes.

Operações: criação de contratos, simulação de parcelas e consulta de histórico.

Tecnologias Utilizadas

Spring Boot: Framework para criação de APIs RESTful.

Kafka: Sistema de mensageria para comunicação assíncrona entre microserviços.

Docker: Contenerização dos microserviços para maior portabilidade.

Kubernetes: Orquestração e gerenciamento de containers para garantir alta disponibilidade e escalabilidade.

MySQL: Banco de dados relacional para armazenar informações persistentes.

Requisitos

Java 17 ou superior.

Docker e Docker Compose.

Kubernetes (local ou em nuvem).

Kafka configurado no ambiente.

Configurações e Deploy

1. Docker Images

Cada microserviço possui um Dockerfile que define sua imagem. Utilize os comandos abaixo para construir as imagens:

# Accounts Microservice
cd accounts-service
docker build -t accounts-service:latest .

# Cards Microservice
cd ../cards-service
docker build -t cards-service:latest .

# Loans Microservice
cd ../loans-service
docker build -t loans-service:latest .

Para rodar os containers localmente:

docker-compose up

2. Kubernetes Deployment

Os manifestos Kubernetes estão localizados no diretório k8s. Certifique-se de que o cluster Kubernetes está ativo e aplique os manifestos:

kubectl apply -f k8s/accounts-deployment.yaml
kubectl apply -f k8s/cards-deployment.yaml
kubectl apply -f k8s/loans-deployment.yaml
kubectl apply -f k8s/kafka.yaml  # Configurações do Kafka

3. Configuração do Kafka

Certifique-se de que o Kafka está configurado corretamente no cluster:

Utilize o arquivo k8s/kafka.yaml para criar os brokers e os tópicos necessários.

Configure os microserviços para publicar e consumir mensagens:

Tópicos:

accounts-events

cards-events

loans-events

4. Endpoints REST

Os principais endpoints expostos por cada microserviço:

Accounts Service

GET /api/accounts/{id} - Consulta informações de uma conta.

POST /api/accounts - Cria uma nova conta.

PUT /api/accounts/{id} - Atualiza uma conta existente.

Cards Service

GET /api/cards/{id} - Consulta detalhes do cartão.

POST /api/cards - Emite um novo cartão.

PUT /api/cards/{id}/block - Bloqueia um cartão.

Loans Service

GET /api/loans/{id} - Consulta empréstimos de um cliente.

POST /api/loans - Solicita um novo empréstimo.

Arquitetura

Mensageria com Kafka

Os microserviços comunicam-se de forma assíncrona utilizando Kafka:

Accounts Service publica eventos no tópico accounts-events.

Cards Service consome eventos de accounts-events e publica no tópico cards-events.

Loans Service consome eventos de accounts-events e cards-events para atualização de status.

Escalabilidade e Orquestração

Com Kubernetes, os microserviços são distribuidos em pods independentes. Os deployments garantem alta disponibilidade e escalabilidade automática com base na carga do sistema.

Como Contribuir

Faça um fork do repositório.

Crie um branch para sua feature ou correção.

Envie um pull request com uma descrição detalhada.
