# kata-bootstrap

## Pre-requisites

- [Nodejs 8+ and Npm 6+] (https://nodejs.org/en/download/)
- [Angular CLI] (https://github.com/angular/angular-cli)

  ```sh
  npm install -g @angular/cli
  ```

  ```sh
  ng version

  # shows :
  # Angular CLI: 7.3.8
  # Node: 11.15.0
  ```

## Generate an Angular app

```sh
ng new bootstrap-angular
```

```sh
? Would you like to add Angular routing? Yes
? Which stylesheet format would you like to use? SCSS   [ http://sass-lang.com   ]
CREATE bootstrap-angular/README.md (1043 bytes)
CREATE bootstrap-angular/.editorconfig (246 bytes)
CREATE bootstrap-angular/.gitignore (629 bytes)
CREATE bootstrap-angular/angular.json (4111 bytes)
CREATE bootstrap-angular/package.json (1329 bytes)
CREATE bootstrap-angular/tsconfig.json (435 bytes)
CREATE bootstrap-angular/tslint.json (1621 bytes)
CREATE bootstrap-angular/src/favicon.ico (5430 bytes)
CREATE bootstrap-angular/src/index.html (313 bytes)
CREATE bootstrap-angular/src/main.ts (372 bytes)
CREATE bootstrap-angular/src/polyfills.ts (2841 bytes)
CREATE bootstrap-angular/src/styles.scss (80 bytes)
CREATE bootstrap-angular/src/test.ts (642 bytes)
CREATE bootstrap-angular/src/browserslist (388 bytes)
CREATE bootstrap-angular/src/karma.conf.js (1043 bytes)
CREATE bootstrap-angular/src/tsconfig.app.json (166 bytes)
CREATE bootstrap-angular/src/tsconfig.spec.json (256 bytes)
CREATE bootstrap-angular/src/tslint.json (244 bytes)
CREATE bootstrap-angular/src/assets/.gitkeep (0 bytes)
CREATE bootstrap-angular/src/environments/environment.prod.ts (51 bytes)
CREATE bootstrap-angular/src/environments/environment.ts (662 bytes)
CREATE bootstrap-angular/src/app/app-routing.module.ts (245 bytes)
CREATE bootstrap-angular/src/app/app.module.ts (393 bytes)
CREATE bootstrap-angular/src/app/app.component.scss (0 bytes)
CREATE bootstrap-angular/src/app/app.component.html (1152 bytes)
CREATE bootstrap-angular/src/app/app.component.spec.ts (1167 bytes)
CREATE bootstrap-angular/src/app/app.component.ts (235 bytes)
CREATE bootstrap-angular/e2e/protractor.conf.js (752 bytes)
CREATE bootstrap-angular/e2e/tsconfig.e2e.json (213 bytes)
CREATE bootstrap-angular/e2e/src/app.e2e-spec.ts (659 bytes)
CREATE bootstrap-angular/e2e/src/app.po.ts (251 bytes)
```

```sh
cd bootstrap-angular
ng serve --open
```

```sh
ng test --watch=false

# shows : TOTAL: 3 SUCCESS
```

When starting a new Angular application, the Angular CLI sets up everything you need for End-to-end testing using Protractor and Jasmine.

## Adding dependencies

```sh
npm install --save @types/{chai,cucumber} chai cucumber protractor-cucumber-framewor
```

### Chai

Cucumber is a testing framework which doesn't come with an assertion library like Jasmine does, so we will be using `chai`.

### Protractor Cucumber Framework

The `protractor-cucumber-framework` package is a plugin that does the glue between Protractor and Cucumber. It's what makes possible running Cucumber tests using Protractor.

### Type Definitions for Chai and Cucumber

The `@types/chai` and `@types/cucumber` Type definitions allow TypeScript to perform the necessary type checking.

## Setup Cucumber

### Type Defintions

Edit __e2e/tsconfig.e2e.json__ :

- Replace

```json
"types": [
        "jasmine", "jasminewd2", "node"
    ]
```

By

```json
"types": [
        "chai", "cucumber", "node"
    ]
```

### Protractor Configuration

Edit __e2e/protractor.conf.js__ :

- Replace

```js
specs: [
    './src/**/*.e2e-spec.ts'
  ]
```

By

```js
specs: [
    './src/features/**/*.feature'
  ]
```

Our feature files will reside under `e2e/src/features`.

- Replace

```js
framework: 'jasmine'
```

By

```js
framework: 'custom',
frameworkPath: require.resolve('protractor-cucumber-framework')
```

- Remove Jasmine related code

```js
jasmineNodeOpts: {
    showColors: true,
    defaultTimeoutInterval: 30000,
    print: function() {}
  }
```

```js
jasmine.getEnv().addReporter(new SpecReporter({ spec: { displayStacktrace: true } }));
```

```js
const { SpecReporter } = require('jasmine-spec-reporter');
```

- Add Cucumber Options

```js
cucumberOpts: {
   require: ['./src/steps/**/*.steps.ts'],
 }
```

`cucumberOpts` defines the command line options passed to Cucumber. Our step definitions will reside under `e2e/src/steps`.

