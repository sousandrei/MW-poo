var express = require('express')
var http = require('http')
var bodyParser = require('body-parser')

var app = express()
var httpServer = http.createServer(app)


app.use(bodyParser.json())
app.use(bodyParser.urlencoded({
    extended: true
}))

app.post('/echo', function (req, res) {
    res.json(req.body)
})

app.post('*', function (req, res) {
    res.sendStatus(200)
})

app.get('*', function (req, res) {
    res.sendStatus(200)
})


httpServer.listen(80)

console.log('Server running at port 80')
