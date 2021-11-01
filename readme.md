# Atividade Serviços - Primeira nota
## Objetivo
Criar um servlet de consulta para que seja possível visualizar os dados 
armazenados no banco de dados

## Problema
Realizar uma consulta por cidade e apresentar todos os clientes da cidade consultada
Seguindo a seguinte informação: consultar por nome da cidade e retornar:

```json
{
"cidade": "Desterro",
"uf": "SC",
"clientes": [
    {"id": 1, "nome": "Rodrigo"},
    {"id": 2, "nome": "João"}
  ]
}
```

## Prazo
A Atividade deverá ser desenvolvida em casa e será entregue 
Prazo 03/11/2021. 

### Informações adicionais
* Utilizar o projeto atual como base, pode ser modificado para atender as necessidades.

### Como testar
1. Subir o docker com o postgres e o pgadmin
```bash
docker-compose up -d
```

2.  Subir a aplicação
```
Intellij + tomcat para subir a aplicação...
```

3. Consultar a aplicação em prod no seu navegador
```
http://<app>/cliente?endereco=Desterro
```
