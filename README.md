# Proxy

### Pré-requisitos

```
* [IDE Spring Tools](https://spring.io/tools/)
* [MAVEN] (https://maven.apache.org/download.cgi)
* [JDK 11] https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html
```
### Instalar o MAVEN e Congirurar variáveis de ambiente

```
https://dicasdejava.com.br/como-instalar-o-maven-no-windows/
```

### Configuração na aplicação application.yml

````
	Necessário alterar o arquivo 
		- src/main/resources/application.yml
		
		adicionando os valores das variaveis client_id e client_secret,  
		valores esse fornecido pelo ponto foco do geocoder na OI
	
	* **AS CHAVES PARA SOLICITAR O TOKEN DEVERÁ SER SOLICITADO A OI**
											
	client_id: CHAVE_CLIENT_ID_GERADA_PELA_OI
	client_secret: CHAVE_CLIENT_SECRET_GERADA_PELA_OI

```

### Executar projeto java

```
* VIA PROMPT DE COMANDO WINDOWS

$ cd c:\diretorio_projeto\proxy
$ mvn spring-boot:run

* **Obs.: O projeto pode ser executar pela IDE SpringToolsuite**
```


### Autores

* **Guilherme Antônio** 
* **Flávio Candioto**




