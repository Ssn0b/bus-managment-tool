# Bus Management Tool

The Bus Management Tool is a web-based application designed to streamline and simplify the management of buses, routes, schedules, and drivers for a transportation business.

## Features

1. **Administrator Dashboard**:
   - Add new buses to the register, providing details about them.
   - Edit bus information, including brand, number of seats, and status (active or for repair).
   - Mark buses as "written off" when they are no longer in service.
   - Manage bus fleet efficiently.

2. **Route Management**:
   - Create new routes by specifying stops and distances.
   - Edit existing routes to accommodate changes.
   - Delete routes that are no longer needed.

3. **Schedule Management**:
   - Add schedules for buses, indicating routes and timings.
   - Delete schedules when necessary.
   - Modify bus schedules to adapt to changing requirements.

4. **Driver Management**:
   - Administer drivers' information, including personal details and work experience.
   - Assign work schedules to drivers.
   - Easily add, update, or remove drivers based on operational needs.

5. **User Registration and Authorization**:
   - Allow users to register and create accounts.
   - Implement secure authentication and authorization mechanisms.
   - Grant different levels of access based on user roles (Administrator, Manager, Driver, etc.).

## Technologies Used

- Spring Boot: Backend application framework.
- Spring Security: Authentication and authorization.
- PostgreSQL: Relational database managed through Docker.
- MongoDB: NoSQL database for data storage and management.
- Angular: Frontend user interface development.
- JSON Web Tokens (JWT): Secure user authentication.
