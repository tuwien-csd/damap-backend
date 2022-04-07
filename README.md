# DAMAP

## Introduction

DAMAP is a tool that is currently being developed by TU Wien and TU Graz as part of the 
[FAIR Data Austria](https://forschungsdaten.at/fda/) project. 
It is based on the idea of machine actionable data management plans (maDMPs) and aims to facilitate the 
creation of data management plans (DMPs) for researchers. 
The tool aims to be closely integrated into the institutional environment, collecting information from 
various established systems, in order to perceive project information, research data and personnel data 
from existing systems.
This saves DMP authors from having to enter the same data several times. 
Finally DAMAP delivers both a DMP that can be read and edited as a Word document, and an maDMP whose 
information can be used at machine level. The current content of DAMAP is based on 
[Science Europe’s Practical Guide to the International Alignment of Research Data Management](https://www.tuwien.at/fileadmin/Assets/forschung/Zentrum_Forschungsdatenmanagement/pdf-Sammlung/se_rdm_practical_guide_extended_final_2021.pdf)
and is compatible with the [RDA recommendation on machine actionable DMPs](https://zenodo.org/record/4036060#.Yk20vjWxVaR).

For a showcase of some of the tools functions see the [demo video](https://youtu.be/IxQzqy26ZO4).

## Run with docker-compose

In order to set up the whole system consisting of multiple components a
`docker-compose` file has been prepared. With this file it should be
straight forward to get a sample system up and running.

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
