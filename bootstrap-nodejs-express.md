# bootstrap-nodejs-express
The project is npm archetype to generate a nodejs and express project with Jest


# Install dependances

Install nodeJs version 8+ https://nodejs.org/en/download/

# Check Version installed
Open windows/mac command line and tap:  

```sh
node --version  
npm --version   
```

# Create project

```sh
$ mkdir bootstrap-nodejs-express

$ cd bootstrap-nodejs-express

$ npm init 

name: (bootstrap-nodejs-express)
version: (0.0.0)
description: (Simple bootstrap nodejs express)
entry point: app.js
test command: jest
git repository: (your-git-repository-url)
keywords:
author: (Your name)
license: (ISC)
```

# Install express dependances
```sh
npm install express  
```

# Create NodeJS server using express

```sh
echo "
const express = require('express')
const app = express()

app.get('/', function (req, res) {
  res.send('Hello World!')
})

app.listen(3000, function () {
  console.log('Example app listening on port 3000!')
})" > app.js
```


# Add script start in package.json script for start the server

```sh
echo '
"scripts": {
    "start": "node app.js",
    "test": "jest"
  },' > package.json
```


# Install tests dependances

```sh
npm install --save-dev jest  
```

# Create tests folder

```sh
$ cd bootstrap-nodejs-express

$ mkdir tests

$ cd tests

$ touch index.js

echo "
describe('Sample Test', () => {
    it('should test that true === true', () => {
      expect(true).toBe(true)
    })
  })" > index.js
```

# Configure tests dependances

```sh
$ cd bootstrap-nodejs-express

$ touch jest.config.js

echo "
//jest.config.js
module.exports = {
    verbose: true,
    testMatch: ['**/tests/*.js?(x)'],
};" > jest.config.js
```

# Create file .gitignore
.gitignore file include file or folder wich must not be published in git repository

```sh
$ cd bootstrap-nodejs-express

$ touch .gitignore

echo "
node_modules
build
npm-debug.log
package-lock.json" > .gitignore
```

# Start express app 
```sh
npm run start  
```

# Run tests
```sh
npm run test  
```




