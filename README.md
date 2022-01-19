# Proxy

## Descrição

Servidor HTTP escrito em vueJS utilizado como proxy para acessar o GeoServer <br />  
da Digicade sem que haja a necessidade de expor as credenciais de acesso <br />  
no frontend da aplicação. A autenticação neste proxy é feita via JWT e a <br />  
autenticação no GeoServer é feita via Basic authentication, dessa forma, <br />
o fluxo de requisições fica desta forma:

1. Aplicação frontend faz uma requisição http ao proxy.
2. Proxy recebe a requisição, efetua a autenticacao usando JWT.
3. Proxy faz requisição http ao Geoserver, usando o Basic Authentication.
4. Proxy repassa o resposta do Geoserver para a aplicação frontend.

### Pré-requisitos

```
Node.js 

* [Windows](http://blog.teamtreehouse.com/install-node-js-npm-windows)
* [Ubuntu](https://nodejs.org/en/download/package-manager
					/#debian-and-ubuntu-based-linux-distributions)
```

### Instalando

```
* Faça download das dependências usando o comando:


	$ npm install vue
	$ npm install quasar
	$ npm install --save @quasar/extras
	$ npm install --save @vue/composition-api
	$ npm add --dev dotenv
	$ npm install ol
```

### Criar projeto vueJS + quasar

```bash
$ vue create mapa-demo
$ cd mapa-demo
$ vue add quasar 
```
### Configurando

```bash
Criar diretorio config na raiz do projeto 
	Criar arquivo '.env.dev' com as seguintes variaveis:
    API: http://localhost:8083/api
    PROXY: http://localhost:8083/proxy
```
    
### Configurar quasar.conf.js 

```
build: {
      env: require('./config/.env.dev')
      ... 
```

### Como usar

Atualmente, ao utilizar o OpenLayers para renderização do mapa, será necessário <br /> 
passar como parâmetro a URL do proxy  como configuração para o objeto que é o **Map** .

Dessa forma:

```
const URL = process.env.PROXY+'/mub/estatico';

this.mapa = new Map({
      layers: [
        new TileLayer({
          source: new WMS({
            url: URL,
            serverType: 'geoserver',
            crossOrigin: false,
            params: {
              VERSION: '1.1.1',
              FORMAT: 'image/png',
              TILED: true,
              LAYERS: 'OIBDMUB:BASE_MAP',
              WIDTH: 256,
              HEIGHT: 256
            }
          })
        })
      ],
      target: 'map',
      pixelRatio: 1,
      view: new View({
        projection: 'EPSG:4326',
        center: [0, 0],
        zoom: 2
      })
    })
    Vue.prototype.$mapa = this.mapa
  }
```
### Proxy

````
	Para acesso ao serviço de mapa é necessário recuperar o token de 
	segurança passando como parametro o client_id e o client_secret 
	usando o serviço requestToken.
	
	*** Exemplo de implementação***
	
	import feign.Body;
	import feign.Headers;
	import feign.Param;
	import feign.RequestLine;
	
	interface IntegracaoClient {
		
		...
		
		@Headers("Content-Type: application/x-www-form-urlencoded")
		@RequestLine("POST {url}")
		@Body(value = "client_id={clientKey}&client_secret={clientSecret}")
		TokenResponseDTO requestToken (
					@Param("url") String url, 
					@Param("clientKey") String 	clientKey, 
					@Param("clientSecret") String clientSecret
				);
		
		...
		
	}
	
	class TokenFooDTO getAcessToken() throws FeignException, UnauthorizedException {
		return getAuthClient().requestToken(acessTokenUrl, clientId, clientSecret);
	}
	
	RETORNO: 
	
	String access_token;
	Long expires_in;
	String refresh_token;
	String scope;
	
	***AS CHAVES PARA SOLICITAR O TOKEN DEVERÁ SER SOLICITADO A OI***
											
	client_id: CHAVE_CLIENT_ID_GERADA_PELA_OI
	client_secret: CHAVE_CLIENT_SECRET_GERADA_PELA_OI

```
### Executando

```
	Incluir no package.json em Scripts  o valor:
	"start": "quasar dev",
```
### Executar projeto java

**- Requisitos**
 * Java 11
 * Spring 2.5.2

```
$ cd c:\diretorio_projeto\proxy
$ mvn spring-boot:run
```
### Executar projeto Quasar + VueJS

```
$ npm install
$ quasar dev
```

### Autores

* **Guilherme Antônio** 
* **Flávio Candioto**




