/**
 * Fichier principal de routing de l'application
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 01/02/2017
 * @version 1.0
 */
angular.module('sustainapp', ['ionic', 'sustainapp.controllers', 'sustainapp.services', 'sustainapp.constantes', 'ngCordova', 'pascalprecht.translate', 'ngSanitize'])

/**
 * DEMARAGE DE SUSTAINAPP
 */
.run(function($ionicPlatform) {
  $ionicPlatform.ready(function() {
    if (window.cordova && window.cordova.plugins && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
      cordova.plugins.Keyboard.disableScroll(true);

    }
    if (window.StatusBar) {
      StatusBar.styleDefault();
    }
  });
})

/**
 * ROUTING DE SUSTAINAPP
 */
.config(function($stateProvider, $urlRouterProvider, $translateProvider) {
  $stateProvider
  .state('tab', {
    url: '/tab',
    abstract: true,
    templateUrl: 'templates/common/tabs.html'
  })
  .state('tab.administration', {
    url: '/administration/menu',
    views: {
      'tab-news': {
        templateUrl: 'templates/administration/menu.html',
        controller: 'administrationController'
      }
    }
  })
  .state('tab.teams', {
    url: '/team/all',
    views: {
      'tab-news': {
        templateUrl: 'templates/teams/teams-main.html',
        controller: 'teamsController'
      }
    }
  })
  .state('tab.team-one', {
    url: '/team/:id',
    views: {
      'tab-news': {
        templateUrl: 'templates/teams/team-one.html',
        controller: 'teamController'
      }
    }
  })
  .state('tab.profile', {
    url: '/profile/:id',
    views: {
      'tab-news': {
        templateUrl: 'templates/profile/profile-main.html',
        controller: 'profileController'
      }
    }
  })
  .state('tab.report', {
    url: '/report',
    views: {
      'tab-news': {
        templateUrl: 'templates/reports/report-main.html',
        controller: 'reportController'
      }
    }
  })
  .state('tab.news', {
    url: '/news',
    views: {
      'tab-news': {
        templateUrl: 'templates/news/news-main.html',
        controller: 'newsController'
      }
    }
  })
  .state('tab.challenges', {
      url: '/challenges',
      views: {
        'tab-challenges': {
          templateUrl: 'templates/challenges/challenges-main.html',
          controller: 'challengesController'
        }
      }
    })
    .state('tab.challenge-one', {
      url: '/challenges/:id',
      views: {
        'tab-challenges': {
          templateUrl: 'templates/challenges/challenge-one.html',
          controller: 'challengeController'
        }
      }
    })
    .state('tab.certificates', {
	  url: '/certificates',
	  views: {
	    'tab-certificates': {
	      templateUrl: 'templates/certificates/certificates-main.html',
	      controller: 'certificatesController'
	    }
	  }
    })
    /*
    .state('tab.cours-one', {
	  url: '/cours/:id',
	  views: {
	    'tab-certificates': {
	      templateUrl: 'templates/certificates/cours-one.html',
	      controller: 'coursController'
	    }
	  }
    })
    */
   .state('tab.notifications', {
	  url: '/notifications',
	  views: {
	    'tab-notifications': {
	      templateUrl: 'templates/notifications/notifications-main.html',
	      controller: 'notificationsController'
	    }
	  }
  });
  $urlRouterProvider.otherwise('/tab/news');
  
  /**
   * SYSTEME DE TRADUCTION
   */
  $translateProvider.useStaticFilesLoader({prefix: 'i18n/', suffix: '.json'});
  $translateProvider.useSanitizeValueStrategy('sanitizeParameters');
  $translateProvider.registerAvailableLanguageKeys(['en','fr'], {'en_US': 'en', 'en_UK': 'en', 'fr_FR': 'fr', 'fr_BE': 'fr'})
  .determinePreferredLanguage();
  $translateProvider.use();
});

/**
 * DIRECTIVES
 */
angular.module('sustainapp.directives', []);

/**
 * CONSTANTES
 */
angular.module('sustainapp.constantes', []);

/**
 * CONTROLLERS
 */
angular.module('sustainapp.controllers', []);

/**
 * SERVICES
 */
angular.module('sustainapp.services', []);

