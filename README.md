# DevOps Service Manager

## Project Idea
Management of IT services in a DevOps/operations environment. The program should be able to register, display, search for, and manage different types of services running in environments such as development, staging, and production.
The system will simulate common service-management tasks such as starting and stopping services, deploying new versions, filtering services by environment, and displaying service statistics.

## Superclass
- Name: `Service`
- Common fields:
    - `name`
    - `version`
    - `environment`
    - `status`
- Common methods:
    - `start()`
    - `stop()`
    - `getInfo()`

The `Service` class contains the fields and behavior shared by all service types.

## Subclasses

### WebService
Represents a web service, such as an API or web application.

Additional field:
- `port`

Overridden methods:
- `start()`
- `getInfo()`

### DatabaseService
Represents a database service.

Additional field:
- `databaseType`

Overridden methods:
- `start()`
- `getInfo()`

### WorkerService
Represents a background service that performs tasks such as sending emails or running scheduled jobs.

Additional field:
- `jobType`

Overridden methods:
- `start()`
- `getInfo()`

All three subclasses inherit from `Service` and provide their own implementation of the overridden methods.

## Interface

- Name: `Deployable`
- Method:
    - `deploy(String newVersion)`
- Implemented by:
    - `WebService`
    - `WorkerService`

The interface is used so that different service types can be handled polymorphically when a new version is deployed.

## Collections and Polymorphism
The program will store all services in a collection of `Service` objects.

The collection will be used to:

- Search for services by name
- Filter services by environment
- Display all registered services
- Count services based on status
- Handle different service subclasses polymorphically

## Menu
The application will include an interactive console menu.

Available actions:

1. Add a new service
2. Remove a service
3. Search for a service by name
4. Display all registered services
5. Start a service
6. Stop a service
7. Deploy a new version of a service
8. Display services from a specific environment
9. Display service statistics
0. Exit the program

The menu will continue running until the user chooses to exit.

## Service Manager
A separate `ServiceManager` class will contain the main business logic of the application.

Its responsibilities will include:

- Adding services
- Removing services
- Searching for services
- Starting and stopping services
- Deploying new versions
- Filtering services
- Calculating service statistics

The user interface will mainly handle input and output, while the `ServiceManager` handles the application logic.

## Enums
Enums will be used for values with a fixed set of valid options.

Planned enums:

- `ServiceStatus`
    - `RUNNING`
    - `STOPPED`
    - `FAILED`

- `Environment`
    - `DEVELOPMENT`
    - `STAGING`
    - `PRODUCTION`

## Error Scenarios

### Invalid Port
The user tries to create a `WebService` with an invalid port number.

The program should handle this with an `IllegalArgumentException` and display a clear error message.

### Service Not Found
The user tries to search for, remove, start, stop, or deploy a service that does not exist.

The program should handle this safely, using a custom `ServiceNotFoundException`.

### Invalid Menu Input
The user enters invalid input such as text, an empty value, or a menu option that does not exist.

The program should handle the input without crashing and allow the user to try again.

## Planned Structure

- `Service`
- `WebService`
- `DatabaseService`
- `WorkerService`
- `Deployable`
- `ServiceManager`
- `ConsoleUI`
- `ServiceStatus`
- `Environment`
- `ServiceNotFoundException`

## Motivation and Design Reflection
This section will be completed later in the project.
