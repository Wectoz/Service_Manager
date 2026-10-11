# DevOps Service Manager

A console application for managing IT services in DevOps environments. Register, display, search, and manage services across development, staging, and production environments.

## Features

- **Service Management**: Add, remove, search, and display services
- **Service Operations**: Start, stop, and deploy new versions
- **Filtering**: View services by environment (Development, Staging, Production)
- **Statistics**: Display service status counts and information
- **User-Friendly Input**: Numbered options with abbreviations, prefix matching, and helpful prompts

## Architecture

### Core Classes

- **Service**: Base class with common fields (name, version, environment, status) and methods (start, stop, getInfo)
- **WebService**: Web service with port field
- **DatabaseService**: Database service with databaseType field
- **WorkerService**: Background service with jobType field
- **ServiceManager**: Business logic for service operations
- **ConsoleUI**: Interactive console menu

### Interface

- **Deployable**: Implemented by Service for version deployment (inherited by all service types)

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

## User Input Features

- **Numbered Options**: All selections use numbered menus (1, 2, 3, etc.)
- **Abbreviations**: Shortcuts available (e.g., "dev" for DEVELOPMENT, "run" for RUNNING)
- **Prefix Matching**: Type partial inputs (e.g., "d" for DEVELOPMENT, "p" for PostgreSQL)
- **Service Lists**: Existing services displayed before remove/start/stop/deploy operations
- **Format Hints**: Version and port format shown before input prompts
- **Section Headers**: Clear visual separation for each input section

## Error Handling

- Invalid port numbers throw `IllegalArgumentException`
- Missing services throw `ServiceNotFoundException`
- Invalid menu input is handled gracefully with retry
- Invalid service types, database types, job types, environments, and statuses are validated with helpful error messages
