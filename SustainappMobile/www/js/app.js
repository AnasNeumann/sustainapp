/**
 * Fichier principal de routing de l'application
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 01/02/2017
 * @version 1.0
 */
angular.module('sustainapp', ['ionic', 'sustainapp.controllers', 'sustainapp.services', 'sustainapp.constantes', 'ngCordova', 'pascalprecht.translate', 'ngSanitize', 'ionic.rating', 'ngDraggable', 'ngStomp'])

/**
 * DEMARAGE DE SUSTAINAPP
 */
.run(function($ionicPlatform, $rootScope, $timeout) {
  $ionicPlatform.ready(function() {
    if (window.cordova && window.cordova.plugins && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
      cordova.plugins.Keyboard.disableScroll(true);
    }
    if (window.StatusBar) {
      StatusBar.styleDefault();
    }
    if(null != window.plugin){ // uniquement sur mobile => Gestion des notifications locales
	    window.plugin.notification.local.onadd = function (id, state, json) {
	        var notification = {
	            id: id,
	            state: state,
	            json: json
	        };
	        $timeout(function() {
	            $rootScope.$broadcast("$cordovaLocalNotification:added", notification);
	        });
	    };
    }
  });
})

/**
 * ROUTING DE SUSTAINAPP
 */
.config(function($stateProvider, $urlRouterProvider, $translateProvider, $sceDelegateProvider, $cordovaInAppBrowserProvider) {
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
    .state('tab.cours-one', {
	  url: '/cours/:id',
	  views: {
	    'tab-certificates': {
	      templateUrl: 'templates/certificates/cours-one.html',
	      controller: 'coursController'
	    }
	  }
    })
    .state('tab.topic-one', {
	  url: '/topic/:id',
	  views: {
	    'tab-certificates': {
	      templateUrl: 'templates/certificates/topic-one.html',
	      controller: 'topicController'
	    }
	  }
    })
    .state('tab.questions', {
	  url: '/questions/:id',
	  views: {
	    'tab-certificates': {
	      templateUrl: 'templates/certificates/questions-main.html',
	      controller: 'questionsController'
	    }
	  }
    })
    .state('tab.question', {
	  url: '/question/:id',
	  views: {
	    'tab-certificates': {
	      templateUrl: 'templates/certificates/question-one.html',
	      controller: 'questionController'
	    }
	  }
    })
    .state('tab.quiz', {
	  url: '/quiz/:id',
	  views: {
	    'tab-certificates': {
	      templateUrl: 'templates/quiz/quiz-one.html',
	      controller: 'quizController'
	    }
	  }
    })
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
	 * Autoriser les urls provenant de ces sites web
	 */
	$sceDelegateProvider.resourceUrlWhitelist([
		'self',
		'https://www.youtube.com/embed/**'
	]);

	/**
	 * Blacklister les urls provenants de ces sites web
	 */
	$sceDelegateProvider.resourceUrlBlacklist([
	]);
	
	/**
	 * Permettre l'ouverture de liens externe
	 */
	 var defaultOptions = {
		    location: 'no',
		    clearcache: 'no',
		    toolbar: 'no'
		  };
	 document.addEventListener("deviceready", function () {
		    $cordovaInAppBrowserProvider.setDefaultOptions(options)	
		  }, false);
	 
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

