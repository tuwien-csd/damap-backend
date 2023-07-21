# Installation

## Run with docker-compose

In order to set up the whole system consisting of multiple components a
`docker-compose` file has been prepared. With this file it should be
straight forward to get a sample system up and running.

The full system will be comprised of 
* damap-backend,
* damap-frontend,
* dummy Keycloak with login name "user" and password "user"
* dummy postgres database
* and dummy APIs providing person and project data

To start up the cluster of components just issue the following command:

```shell
cd docker
docker-compose up -d
```

See the documented sections in the [docker/docker-compose.yaml]() to make further
configurations.

### Keycloak

Keycloak can be accessed through http://localhost:8087 and you can login
to keycloak as admin with

```shell
username: admin
password: admin
```

### Update Realm config

If you update a running Keycloak instance, by adding users, changing properties
a.s.o., you can export the current configuration to a Json file.
Save this Json file to [keycloak export file](docker/sample-damap-realm-export.json)
to integrate it within the docker-compose "cluster". Be sure to rebuild keycloak
by issuing:

```shell
# rebuild
docker-compose build keycloak

# restart keycloak
docker-compose up -d keycloak
```

### Postgres

You can access the Postgres CLI directly with the container with:

```shell
cd docker
docker-compose exec damap-db psql -U damap damap
```

## Custom Deployment

In order to adapt the project and deploy it in your own institutional environment
follow the [deployment instructions](INSTALLATION.md).
