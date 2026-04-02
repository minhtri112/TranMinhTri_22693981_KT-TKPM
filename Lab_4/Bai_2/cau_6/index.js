const express = require('express')
const app = express()
const port = 5000

// !important! 
// you need to install the following libraries |express|ejs|[dotenv > if required]
// or run this command >> npm i express ejs dotenv 

app.set('view engine', 'engine')



app.get('/' , (req , res)=>{

   res.send('hello from simple server :)')

})


app.listen(port , ()=> console.log('> Server is up and running on port : ' + port))