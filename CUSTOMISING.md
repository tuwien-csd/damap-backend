# Customisation

## Customizing DAMAP backend

Implement your own institutional backend by creating a new project with [quarkus](https://quarkus.io/).

Download the damap-backend project and then build it using this command:

```shell
mvn clean install -Dquarkus-profile=dev
```

After compiling the public backend we will have a jar file in the target folder with a filename like this:
**damap-backend-1.2.0.jar**.

Add the following dependency to the pom.xml of your project in order to include the public damap-backend JAR as a dependency,
as well as all of its functionalities:

```xml
<dependency>
  <groupId>at.ac.tuwien</groupId>
  <artifactId>damap-backend</artifactId>
  <type>jar</type>
  <version>1.2.0-SNAPSHOT</version>
</dependency>
```

In order to be available for the build this jar file needs to be placed in the institutional backend project
(this step is not required when it is done locally, instead maven will find it by reload all maven projects).
Copy it manually or make it available on your institutional nexus.

After adding the other components to the project you will be able to run it using:

```shell
   mvn compile quarkus:dev
```


### Overriding config values

In order to connect the required components with our project, we need to provide values to the custom config
variables in the [config file of the project](src/main/resources/application.yaml).

In order to do this add the following to your pom.xml,
so your institutional project will also use the .yaml format for its config file:

```xml
<dependency>
  <groupId>io.quarkus</groupId>
  <artifactId>quarkus-config-yaml</artifactId>
</dependency>
```

Copy and paste the custom config settings variables from the backends
[application.yaml](src/main/resources/application.yaml) into your custom projects application.yaml.
Here you can edit all the relevant config values you need to adapt for the project to run
and they will take precedence over the ones in the dependency.

Alternatively these variables can also be overridden by using your systems environment variables
(see [quarkus configuration properties priority](https://quarkus.io/guides/config-reference)),
which also allows overriding them at runtime.

The value DAMAP_ORIGIN defines your domains, which will be permitted to communicate with the backend.
Include here the domain of your frontend.

```yaml
damap:
   origins: https://your.frontend.com,https://*.yourdomain.com
```


### Configuring Authentication service

Provide the following config values in order to be able to integrate your authentication service:

```yaml
damap:
   auth:
      url: https://your.authentication.server
      client:
         backend: your-backend-authentication-client-id
         frontend: your-frontend-authentication-client-id
      scope: your-authentication-scope
      user: your-authentication-claim-holding-the-user-id
```

Note the value DAMAP_AUTH_USER, which will contain the variable name of your institutional person-ID.
This ID will then be used throughout the project to identify people and users within damap.

Any person that is authenticated through your service will be considered as a user.
If you wish to have users with the administrator role, you need to provide them with the role **Damap Admin**
within your authentication service.

You can view our [sample Keycloak realm export](docker/sample-damap-realm-export.json) for better visualization.


### Configuring Database

Provide the following config values in order to be able to integrate your relational database of choice:

```yaml
damap:
   datasource:
      url: jdbc:your-datasource-language:your-datasource.address
      username: your-datasource-username
      password: your-datasource-password
```

### Configuring Project and Person API
Provide your CRIS system and person database API addresses in the config:

```yaml
damap:
   projects-url: http://your.project.management.system.com
   persons-url: http://your.personnel.management.system.com
```

In your institutional project, write custom API services for retrieving project and person information from your
services, as well as mappers, to map their information to damaps classes.
To this end you need to extend the dummy API services
[ProjectServiceImpl.class](src/main/java/at/ac/tuwien/damap/rest/projects/ProjectServiceImpl.java) and
[PersonServiceImpl.class](src/main/java/at/ac/tuwien/damap/rest/persons/PersonServiceImpl.java),
in order to feed this information to the project.


### Providing a FITS service

Integrate and run a [FITS](https://projects.iq.harvard.edu/fits) service.
You can then provide a link to your service in the config, so that damap might make use of it:

```yaml
damap:
   fits-url: http://your.fits.service:1234
```

## Deploying the damap frontend

For running and adapting the frontend refer to the **[damap frontend project]()**.

## Further customisation

### Recommended repository

If you wish to promote a specific repository from the list of repositories sourced from r3data
then you can provide a list of r3data-IDs in the config file:

```yaml
damap:
   repositories:
      recommendation: [ your-recommened-repositories ]
```

### Storage options

In order to provide the user with a series of storage options, these must be added to the databse beforehand.
After running the project once, liquibase will have added the entire databse structure.

At this point you can add your own storage options by utilizing SQL statements like in the following example:

```sql
insert into damap.internal_storage (id, version, url, storage_location, backup_location)
values (NEXTVAL('damap.hibernate_sequence'), 0, 'your-storage-url', 'AUT', 'AUT');

insert into damap.inter_storage_translation (id, version, internal_storage_id, language_code, title, description)
values (NEXTVAL('damap.hibernate_sequence'),
    0,
    (select id from damap.internal_storage insto where insto.url = 'your-storage-url'),
    'eng',
    'Storage-name',
    'Loquacious storage description.'
);

insert into damap.inter_storage_translation (id, version, internal_storage_id, language_code, title, description)
    values (NEXTVAL('damap.hibernate_sequence'),
    0,
    (select id from damap.internal_storage insto where insto.url = 'your-storage-url'),
    'deu',
    'Storage-name',
    'Loquacious storage description.'
);  
```

### Export customisation

[Export word template](src/main/resources/template/scienceEuropeTemplate.docx) and
[its resource file](src/main/resources/template/scienceEuropeTemplate.resource)
can be replaced in the institutional project
by adding a replacement file in the resources there and then writing a class to extend
[TemplateFileBrokerServiceImpl](src/main/java/at/ac/tuwien/damap/conversion/TemplateFileBrokerServiceImpl.java)
and have it override the methods which retrieve those files.
