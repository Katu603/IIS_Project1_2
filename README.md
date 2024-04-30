# IIS_Project1_2 - Company Phone Book with RDF
### (Mini-Herold - Company Edition)
This project is created in the course of the LVA Integrated Information Systems (exersice 1). This project was created with Intellij in java and the maven libraries of apache-jena (v6 - v6.1). The application is run as a command-line application and provides a small command-line UI where phone book entries, representing companies, can be queried. If either/and legal name, email, business field, postal code, street name and/or street number the query will be executed and the results will displayed as a table. If nothing is entered, all entries of the peron phone book will be listed.

Via the apache-jena API (for RDF/RDFS) a RDF graph is constructed (containing the databases schema and individuals) and saved as a rdfxml file (CompPhoneBook.rdf) for later use. (An API by apache-jena for ontology creation would have been available too.) The actual query (SPARQL) is executed on the RDF graph containing through a query processor provided by the API.

![rdf-grapher_company_phone_book](https://github.com/Katu603/IIS_Project1_2/assets/81974491/a67371a8-0c8b-4b09-906a-a4830eac3695)
The graphic above shows the RDF graph with its schema and one instance.

Project created by group 2 (member: Katharina Blaimschein)
