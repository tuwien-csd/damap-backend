# Contributing to DAMAP project

The code for the OpenSource project DAMAP is hosted at [GitHub](https://github.com/tuwien-csd/damap-backend),
which allows contributing issues which will be picked up by the lead developers and directly contributing code 
as PullRequests.

## Issues

Whenever something in the application is not working as expected, describe and report the problem in an issue. 
This allows to discuss the issue with a developer, who would then create a PullRequest which solves the issue.

When describing issues, a reporter should describe the issue in a way that a developer can reproduce it. To
help to create good issues, all the points in the issue template should be filled out. 

## PullRequests

To request the lead developers to pull in code change proposals, fork the repository, create a branch and push the code 
to your own fork. Create a PullRequest with `tuwien-csd/damap-backend` as base and write a meaningful description
of your changes.

If you are working with the main repository, the naming convention for branches is `<contributor initials>/<short-description-separated-with-hyphens>` e.g.:
```
xy/file-upload
```
Once the PR is merged, delete the source branch.

Generally PullRequest should cover only a specific topic in form of one or a few commits. The commit message should 
follow common good practices and should be composed of a meaningful subject line with a short description of the 
changes and an optional body to explain the changes and the reasons behind them. A blank line splits the subject line
from the body message.

## Testing

Before requesting code changes, a contributor should run the test capabilities offered by the project by running
the following command in the source repository:

```shell
mvn verify
```

This allows to discover code defects earlier and ensures the application behaves as it should while code changes.

## Documentation

[Javadoc](https://www.baeldung.com/javadoc) is used (or at least planned) to document the source code. Whenever
a contributor changes code, documentation should be updated too.
