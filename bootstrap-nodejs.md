# bootstrap-nodejs
The project is npm archetype to generate a nodejs project with Jest


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
$ mkdir bootstrap-nodejs

$ cd bootstrap-nodejs

$ npm init 

name: (bootstrap-nodejs)
version: (0.0.0)
description: (Simple bootstrap nodejs)
entry point: server.js
test command: jest
git repository: (your-git-repository-url)
keywords:
author: (Your name)
license: (ISC)
```


# Create NodeJS server

```sh
echo "
const http = require('http');
const hostname = '127.0.0.1';
const port = 3000;

const server = http.createServer((req, res) => {
  res.statusCode = 200;
  res.setHeader('Content-Type', 'text/plain');
  res.end('Hello World\n');
});

server.listen(port, hostname, () => {
  console.log(`Server running at http://${hostname}:${port}/`);
});" > server.js
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
$ cd bootstrap-nodejs

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
$ cd bootstrap-nodejs

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
$ cd bootstrap-nodejs

$ touch .gitignore

echo "
node_modules
build
npm-debug.log
package-lock.json" > .gitignore
```

# Start server 
```sh
npm run start  
```

# Run tests
```sh
npm run test  
```




