'use strict';

const http = require('http');
const cluster = require('cluster');
const os = require('os');
const setup = require('./scripts/setup');

cluster.schedulingPolicy = cluster.SCHED_RR;

const PORT = 3000;
  
const user = { name: 'jura', age: 22 };
const pid = process.pid;
 
const routing = {
    '/': '<h1>GAY WEBSITE!<h1>',
    '/user': user,
    '/user/': user,
    '/user/name': () => user.name,
    '/user/name/': () => user.name,
    '/user/age': () => user.age,
    '/user/age/': () => user.age
  };

const types = {
    object: JSON.stringify,
    string: s => s,
    number: n => n.toString(),
    undefined: () => 'not found',
    function: (fn, par, client) => JSON.stringify(fn(client, par)),
};
  
if (cluster.isMaster) {
    const count = os.cpus().length;
    console.log(`Master pid: ${pid}`);
    console.log(`Starting ${count} forks`);
    for (let i = 0; i < count; i++) cluster.fork();
} else {
    const id = cluster.worker.id;
    console.log(`Worker: ${id}, pid: ${pid}, port: ${PORT}`);
    http.createServer((req, res) => {
        console.log('worker:' + cluster.worker.id + " going to send response ");
        const data = routing[req.url];
        const type = typeof data;
        const serializer = types[type];
        res.setHeader('Process-Id', pid);
        res.setHeader('Worker-Id', id);
        res.end(serializer(data, req, res));
    }).listen(PORT);
}

// const server = https.createServer((req, res) => {
//     console.log('Server request');

//     res.setHeader('Content-Type', 'text/html');

//     res.write('<h1>GAY WEBSITE!<h1>');
    
//     res.end();
// });

// setup.fullSetup()

// server.listen(PORT, HOST, (error) => {
//     if(error != null) {
//         console.log(error);
//     }else{
//         console.log(`listening on port ${PORT}`);
//     }
// });