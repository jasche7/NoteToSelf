# Note to Self (Web Page)

Spring Boot web app that stores Notes that can be read and edited by authenticated users with an Okta account.

Not currently deployed anywhere.

# Milestones

Current:
Fully functional in dev environment.
Basic unit and integration testing suite.
Uses in-memory SQL database.

Todo:
Deploy to Heroku with PostgreSQL database.
Improve UI.

## Project Screenshot(s)

<img width="1280" alt="image" src="https://user-images.githubusercontent.com/25709225/125215840-5e86b380-e271-11eb-895e-bb48934b2579.png">

## Usage

In root directory, run:
./mvnw spring-boot:run -e -Pprod


## Reflection

This project was imagined as a tool for me to easily copy and paste text between devices in a persistent yet secure manner. Email drafts provide a similar function but end up cluttering, which is not ideal.

I prioritized learning good development practices, such as unit testing, clean code, and documentation. There is also the goal of familiarizing with practical aspects of web development, such as database management and security.
