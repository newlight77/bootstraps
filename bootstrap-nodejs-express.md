# bootstrap-nodejs-express
The project is npm archetype to generate a nodejs and express project with Jest


# Install dependances

Install nodeJs version 8+ https://nodejs.org/en/download/

# Check Version installed
Open windows/mac command line and tap:  
node --version  
npm --version   

# Create project

1. Create folder (example named **bootstrap-nodejs**)

2. Change directory using command line : **cd bootstrap-nodejs**  with windows/mac or visual studio code

3. Run command ligne **npm init** and follow these step below

<code><pre>
name: (bootstrap-nodejs)
version: (0.0.0)
description: (Simple bootstrap nodejs)
entry point: app.js
test command: jest
git repository: (your-git-repository-url)
keywords:
author: (Your name)
license: (ISC)
</code></pre>


# Install express dependances

Run command line  **npm install express**

# Create NodeJS server using express

Copy this line in server.js file  

<pre><code>
const express = require('express')
const app = express()

app.get('/', function (req, res) {
  res.send('Hello World!')
})

app.listen(3000, function () {
  console.log('Example app listening on port 3000!')
})
</code></pre>


# Add script start in package.json script for start the server
<pre><code>
"scripts": {
    "start": "node app.js",
    "test": "jest"
  },
</code></pre>


# Install tests dependances

Run command line  **npm install --save-dev jest**


# Create tests folder

Create tests folder named tests at the root of the project

Change directory to go to the project using command line **cd tests**

Create file index.js and add these line into the file

<code><pre>
describe('Sample Test', () => {
    it('should test that true === true', () => {
      expect(true).toBe(true)
    })
  })
</code></pre>


# Configure tests dependances

Create a file named **jest.config.js** at the root of the project and update the file with these line :  

<code><pre>
// jest.config.js
module.exports = {
    verbose: true,
    testMatch: ['**/tests/*.js?(x)'],
};
</code></pre>

# Create file .gitignore
Create a file named .gitignore at the root of the project  

.gitignore file include file or folder wich must not be published in git repository


Add these line in .gitignore:  

<code><pre>
node_modules
build
npm-debug.log
package-lock.json
</code></pre>


# Start express app 
Start server with command line  **npm run start**

# Run tests
Run tests with command line  **npm run test**





