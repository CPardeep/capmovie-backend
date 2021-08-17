# Agent Backend Service

This service process requests made from the front end services (Agent Registration, and Agent Dashboard) and Client Frontend service. Based of the request made, this service will manipulate the data into mongoDb returning the appropriate response. The key functions this service provides are as follows:

  - Register and agent(Used to add an aganet to the database)

  - Reading an agent's details based of their ARN(Agent Reference Number)

  - Check if an agent exists(Used for Login)

  - Read All Clients (Used to populate agents dashboard with clients associated to the agent.)

  - Remove Client(Used to remove the connection between a client and agent.)

  - Delete Client(Used to delete a client from the database.)

  - Update Business Name(Used to update the business name of the agent in the database.)

  - Update Eamil(Used to update the email for an agent in the database.)

  - Update Contact Number(Used to update the contact number for an agent in the database.)

  - Update Modes of Correspondance(Used to update the correspondence type for an agent in the database.)

  - Update Property Type(Used to update the property type for an agent in the database.)

  - Update Post Code (Used to update the post code for an agent in the database.)

How to Setup/run the service:

  - To run this service you must have the following setup:

  - Have nothing running on ports (9009)

  - Have scala version: 2.12.13

  - Have sbt installed on your computer

This code is open source software licensed under the Apache 2.0 License.
