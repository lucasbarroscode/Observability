# Projeto de Observabilidade
Feito para estudar conceitos de observabilidade.

# Como Executar
1) verificar se o docker do mysql e o Jaegger esta de pe com docker-compose ps
2) Se nao estiver subir o projeto com docker-compose up
3) Executar a aplicacao.

# Links Importante
## Swagger
http://localhost:8080/swagger-ui.html
## OpenTracing Jaeger
http://localhost:16686/search
## Actuator Health
http://localhost:8080/actuator/health/
http://localhost:8080/actuator/

## Prometheus
http://localhost:9090

## Grafana
http://localhost:3000/?orgId=1
user: admin
password: admin

## Destaque de alguns conceitos utilizados no projeto

@Transactional = Precisa colocar o transactional pq como estamos "atualizando" duas tabelas diferentes (itens e vendas)
para se der alguma execao der rollback nas operações de banco de dados feitas antes 

## TODO
- Inserir curl dos request aqui tbm


## Subindo a aplicação
mvn clean install
Caso não tenha subido a aplicação ainda executar o camando docker-compose up --build
Caso fez alguma alteração e quer subir as novas alterações executar docker-compose down 