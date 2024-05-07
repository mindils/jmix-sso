## экспорт realms

```shell
docker exec -it  [container-id] /opt/keycloak/bin/kc.sh export --realm=[realm-name] --file=/tmp/myrealm-export.json 
docker cp [container-id]:/tmp/myrealm-export.json ./myrealm-export.json
```

