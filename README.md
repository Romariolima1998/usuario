# api de cadastro e autenticacao de usuario

api de cadastro de usuario e altenticacao por Bearer token com spring security, jjwt, banco de dados postgres, swagger, openfeign, migracao de DB com flyway e observabilidade com actuator, micrometer, zipkins, prometheus e grafana

***

### como rodar:

`git clone https://github.com/Romariolima1998/usuario.git`

abra a pasta usuario no terminal

cole o comando `docker compose up --build`

***
link da documentacao da api

`http://localhost:8083/swagger-ui/index.html#/`

<img style="display: block;-webkit-user-select: none;margin: auto;cursor: zoom-in;background-color: hsl(0, 0%, 90%);transition: background-color 300ms;" src="https://raw.githubusercontent.com/Romariolima1998/usuario/refs/heads/master/images/Captura%20de%20tela%20de%202026-01-23%2009-58-14.png" width="1365" height="656">

***

link para metricas com grafana

`http://localhost:3000/`

usuario: admin
senha: admin

apos o login altere a senha, va para conections, data sources, add new data souce, selecione prometheus, em prometheus server url cole `http://prometheus:9090` , salva e testa
va para dashboards adicione dashbord, importar, procure o id do dashboard desejado no site do grafana ou cole um desses: `11378`, `14430` selecione prometheus e pronto.

<img style="display: block;-webkit-user-select: none;margin: auto;cursor: zoom-in;background-color: hsl(0, 0%, 90%);transition: background-color 300ms;" src="https://raw.githubusercontent.com/Romariolima1998/usuario/refs/heads/master/images/Captura%20de%20tela%20de%202026-01-23%2009-55-57.png" width="1365" height="656">
