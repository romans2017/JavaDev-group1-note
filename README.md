# \~Notes Management System~
### _GoIT final project of Group 1_
### RUNNING PROFILES
There are three profiles for running:
| profile | description |
| ------ | ------ |
|dev|default profile use in-memory DB H2|
|prod|profile use DB PostgreSQL deployed on amazonaws.com|
|mysql|profile use local DB MySQL|
### INSTRUCTIONS FOR RUNNING application locally:
- Customize application.yaml according to chosen DB configuration.
Environment variables: URL, USER_NAME, DB_PASSWORD
- Use http://localhost:9999/ to reach the application.