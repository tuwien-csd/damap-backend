# Contributing to DAMAP project

The code for the OpenSource project DAMAP is hosted at [GitHub](https://github.com/tuwien-csd/damap-backend),
which allows contributing issues which will be picked up by the lead developers and directly contributing code
as Pull Requests.

## Issues

Whenever something in the application is not working as expected, describe and report the problem in an issue.
This allows to discuss the issue with a developer, who would then create a Pull Request which solves the issue.

When describing issues, a reporter should describe the issue in a way that a developer can reproduce it. To
help to create good issues, all the points in the issue template should be filled out.

## Java Docs
When publishing the package, java docs will be generated. In order to keep everything documented, adding java docs is really helpful.
Times to run the java docs command:
- Add, remove or update function
- Add or remove class
- Add, remove or update annotator/decorator
- Add, remove or update test

Creating java docs can be done via a command. We only care about:
- parameters
- return value
- linking classes
- throws info

In order to run the command for the code in the src folder, run `mvn javadoc:fix -DfixTags=param,return,link,throws`
In order to run the command for the code in the test folder, run `mvn javadoc:test-fix -DfixTags=param,return,link,throws`

After that, also make sure to format the code ([see Code Formatting](#code-formatting)).

## Code Formatting

This project uses a configured opinionated code formatter [spotless](https://github.com/diffplug/spotless). Letting a specific tool take care of the formatting lets developers
and reviewers focus on the implementation part.

This maven plugin provides CLI commands for the project. Useful (and used) commands are:

- check: Check the code for formatting issues and report them (`mvn spotless:check`)
- apply: Check the code and apply required code changes (`mvn spotless:apply`)

The check command will be run during the `verify` phase of maven as well.

## Testing

Before requesting code changes, a contributor should run the test capabilities offered by the project by running
the following command in the source repository:

```shell
mvn verify
```

This allows to discover code defects earlier and ensures the application behaves as it should while code changes.

## Pull Requests

Before creating a pull request, the changes should be tested locally as described in the [Testing](#testing) section.

To request the lead developers to pull in code change proposals, fork the repository, create a branch and push the code
to your own fork. Create a Pull Request with `tuwien-csd/damap-backend` as base and write a meaningful description
of your changes.

If you are working with the main repository, the naming convention for branches is `<contributor initials>/<short-description-separated-with-hyphens>` e.g.:

```
xy/file-upload
```

Once the PR is merged, delete the source branch.

Generally Pull Request should cover only a specific topic in form of one or a few commits. The commit message should
follow common good practices and should be composed of a meaningful subject line with a short description of the
changes and an optional body to explain the changes and the reasons behind them. A blank line splits the subject line
from the body message.

## Documentation

[Javadoc](https://www.baeldung.com/javadoc) is used (or at least planned) to document the source code. Whenever
a contributor changes code, documentation should be updated too.
