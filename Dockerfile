FROM openjdk:11
EXPOSE 8080

RUN apt-get update && \
 apt-get install -y netcat;


COPY /target/gestao-vendas-0.0.1-SNAPSHOT.jar /app/gestao_venda.jar
COPY /wait-for-mysql.sh /app/wait-for-mysql.sh

##RUN sed -i -e 's/\r$//' /app/wait-for-mysql.sh
WORKDIR /app
#ENTRYPOINT [ "java", "-jar", "/app/gestao_venda.jar" ]
