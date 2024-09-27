//External imports
const express = require('express');
const mysql = require('mysql2/promise');

//Internal imports
const routes = require('./routes/alarm.Routes');

port = 5000;
const app = express();

// DB connection
const connect = async () => {
    try {
        const connectdb = await mysql.createConnection({
            host: 'localhost',
            user: 'root',
            password: '',
            database: 'AlarmSys', 
        });
        console.log(`DB connected to: ${connectdb.config.host}`);
        app.locals.db = connectdb; 
        global.db = connectdb; 

    } catch (error) {
        console.error(`Error MySQL: ${error}`);
    }
};
connect();

//body parser
app.use(express.json());

//Router
app.use("/route", routes);

//Listner
app.listen(port, () => {
    console.log(`Server is running on port : ${port}`);
});


