const {reasons} = require('./reasons.js')
const express = require('express')
const app = express()
app.get('/reasons', (req, res) => res.json(reasons))
const port = 3103
app.listen(port, () => console.log(`Cuore server running on ${port}`))
