# Spring Mighty Config

A case study for a mighty and flexible config system on top of the spring config capabilities.
The addition is supposed to override the config default (established via Sprong Boot's) while the override values
can come from different locations following this hierarchy:

    application.yaml
         ↓ 
    environment variables
         ↓
    DB table entries
         ↓
    HTTP request
