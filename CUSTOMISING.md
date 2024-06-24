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
  <groupId>org.damap</groupId>
  <artifactId>base</artifactId>
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
The variable name should be an additional property provided by your authentication service,
and contains the users ID as per your project/researcher management system.
This ID will then be used throughout the project to identify people and users within damap.
This allows you to make people recognizeable through your various systems, without having to map or track new IDs.

Any person that is authenticated through your service will be considered as a user, 
as long as they have the provided person-ID attribute (renamed to your preference).
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
      db-kind: postgresql # your database type
```
### Custom Database Changes

If you want to customize [liquibase](https://www.liquibase.org/get-started) for your own institutional backend:
- Create a new namespace for your institutions name - like e.g. at/ac/tugraz/damap
- Create your own root changelog and change the liquibase path in application.yaml to point to your new root changelog
```yaml
liquibase:
  migrate-at-start: true
  change-log: at/ac/tugraz/damap/db/newChangeLog-root.yaml
```
- Your new root changelog should look something like this:
```yaml
databaseChangeLog:
  - include:
      file: org/damap/base/db/changeLog-root.yaml
```
- This include statement makes sure, that all damap changesets are automatically included in your seperate backend
- After that, you can create your own changelogs in your custom institutional folder and include them in your root
- Damap uses sequential integers as ids - to avoid conflicts, you should adpopt your own id system, that does not clash
  with damap. Liquibase allows any type of id, as long as it is unique - e.g. tuwien_1, tuwien_2...

If you want to completely do your own thing, keep everything above the same, but instead of including the changeLog-root.yaml
file, include every changeset you want to keep separately. Beware - this approach requires a lot of maintenance, since
every new damap version has to be checked for new changesets you might want to include.
Also before version 3, all changesets where kept in db/changeLog.yaml. If you might want to remove certain changesets in
this file, you would have to create your own version in your institutional folder. 

### Configuring Project and Person API
Provide your CRIS system and person database API addresses in the config:

In your institutional project, write custom API services for retrieving project and person information from your
services, as well as mappers, to map their information to damaps classes.
To this end you need to implement the API services
[ProjectService.class](src/main/java/org/damap/base/rest/projects/ProjectService.java) and
[PersonService.class](src/main/java/org/damap/base/rest/persons/PersonService.java).

You can then integrate the project service by overriding the mock implementation 
through the annotation @Priority(1). 

For the persons data service instead, add it to the list of person services (see below) 
and remove the mock implementation.
This will allow you to integrate several person services in parallel. The frontend will then 
communicate with the service the user chooses. This component will provide the list of options to the frontend, 
so no further changes are necessary there.

```yaml
  person-services:
    - display-text: 'University'
      query-value: 'UNIVERSITY'
      class-name: 'org.damap.base.rest.persons.MockUniversityPersonServiceImpl'
    - display-text: 'ORCID'
      query-value: 'ORCID'
      class-name: 'org.damap.base.rest.persons.orcid.ORCIDPersonServiceImpl'
```

### Providing a FITS service

Integrate and run a [FITS](https://projects.iq.harvard.edu/fits) service.
You can then provide a link to your service in the config, so that damap might make use of it:

```yaml
damap:
   fits-url: http://your.fits.service:1234
```

### Defining Production vs. Development systems

Change the **DEV** property to **PROD** in order to enable production environment specific behavior in the frontend,
such as removing the test-system notification banner.

```yaml
damap:
  env: DEV
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

[Export word template](src/main/resources/org/damap/base/template/scienceEuropeTemplate.docx) and
[its resource file](src/main/resources/org/damap/base/template/scienceEuropeTemplate.resource)
can be replaced in the institutional project
by adding a replacement file in the resources folder of your customisation project.
Then write a class to extend
[TemplateFileBrokerServiceImpl](src/main/java/org/damap/base/conversion/TemplateFileBrokerServiceImpl.java)
and have it override the methods which retrieve those files.

The new filepath will look into the local resource folder and take the file placed there.
This .docx file needs to contain the placeholder keywords found in the sample template provided in the generic project, 
in order to replace the text at those specific places.
By replacing the resource file instead, the default texts used to compose the document can be adapted.

The template is selected automatically when exporting, if the project has a funder. You may want to override this functionality, 
if you have custom templates for example. To override this, write a class that extends [TemplateSelectorServiceImpl](src/main/java/org/damap/base/conversion/TemplateSelectorServiceImpl.java)
and have it override the methods that determine the template.
