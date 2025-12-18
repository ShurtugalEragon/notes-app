# notes-app

This is a real-time collaborative notes app that allows multiple
users to edit the same document simultaneously.


How to build:


First, git clone https://github.com/ShurtugalEragon/notes-app.git

    Local Development:

        1. In client directory, run 'npm install' and 'npm run dev'
        2. Set up a local postgresql database named notes
        3. Set environment variables: SPRING_DATASOURCE_URL: db url,
                                      SPRING_DATASOURCE_USERNAME: db user,
                                      SPRING_DATASOURCE_PASSWORD: db password
        2. In server directory, run './mvnw spring-boot:run'
        3. Go to localhost:5173

    Production:

        1. Install docker on your machine
        2. In notes-app directory, run 'docker compose up'
        3. Go to localhost:5173
