// Karma configuration
// Generated on Mon May 29 2017 15:38:07 GMT-0400 (Atlantique)

module.exports = function(config) {
  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: '',

    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine'],

    // list of files / patterns to load in the browser
    files: [  
      '../../lib/angular/angular.js',
      '../../lib/ionic/js/ionic.bundle.js',
      '../../lib/angular-ui-router/release/angular-ui-router.js',
      '../../lib/angular-mocks/angular-mocks.js',
      '../../lib/angular-animate/angular-animate.js',
      '../../lib/chart.js/dist/Chart.js',
      '../../lib/angular-chart.js/dist/angular-chart.js',
      '../../lib/angular-sanitize/angular-sanitize.js',
      '../../lib/angular-translate/angular-translate.js',
      '../../lib/ionic-rating/ionic-rating.js',
      '../../lib/angular-translate-loader-static-files/angular-translate-loader-static-files.js',
      '../../lib/ngCordova/dist/ng-cordova.js',
      '../../lib/ngCordova/dist/ng-cordova-mocks.js',
      '../../lib/ngDraggable/ngDraggable.js',
      '../../lib/ng-stomp/dist/ng-stomp.min.js',
      '../../lib/sockjs-client/dist/sockjs.js',
      '../app.js',
      '*.js',
      '../*.js',
      '../**/*.js',
      '../**/**/*.js'
    ],

    // list of files to exclude
    exclude: [
    ],

    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
    },

    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['progress'],

    // web server port
    port: 9876,

    // enable / disable colors in the output (reporters and logs)
    colors: true,

    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,

    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,

    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    browsers: ['Chrome'],

    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false,

    // Concurrency level
    // how many browser should be started simultaneous
    concurrency: Infinity
  })
}
