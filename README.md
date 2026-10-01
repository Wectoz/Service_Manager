# DevOps Service Manager

A console application for managing IT services in DevOps environments. Register, display, search, and manage services across development, staging, and production environments.

## Features

- **Service Management**: Add, remove, search, and display services
- **Service Operations**: Start, stop, and deploy new versions
- **Filtering**: View services by environment (Development, Staging, Production)
- **Statistics**: Display service status counts and information

## Architecture

### Core Classes

- **Service**: Base class with common fields (name, version, environment, status) and methods (start, stop, getInfo)
- **WebService**: Web service with port field
- **DatabaseService**: Database service with databaseType field
- **WorkerService**: Background service with jobType field
- **ServiceManager**: Business logic for service operations
- **ConsoleUI**: Interactive console menu

### Interface

- **Deployable**: Implemented by WebService and WorkerService for version deployment

### Enums

- **ServiceStatus**: RUNNING, STOPPED, FAILED
- **Environment**: DEVELOPMENT, STAGING, PRODUCTION

## Menu Options

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

## Error Handling

- Invalid port numbers throw `IllegalArgumentException`
- Missing services throw `ServiceNotFoundException`
- Invalid menu input is handled gracefully with retry
