const fs = require('fs');

function fullSetup()
{
    if(!checkIfExists())
    {
        fs.mkdir('./daraStorage', () => {});
    }
}

function checkIfExists()
{
    return fs.existsSync('./daraStorage', () => {});
}

module.exports = {
    fullSetup: fullSetup,
    checkIfExists: checkIfExists
};