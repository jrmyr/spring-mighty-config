# Spring Mighty Config

A case study for a mighty and flexible config system on top of the Spring config capabilities.
The addition is supposed to override the config default (established via Spring Boot) while the override values
can come from different locations following this hierarchy:

    application.yaml
         ↓ 
    environment variables
         ↓
    DB table entries
         ↓
    HTTP request

***Note**: not all layers are curretly supported by the implementation. There is more to come...*