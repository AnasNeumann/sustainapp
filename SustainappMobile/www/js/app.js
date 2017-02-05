/**
 * Fichier principal de routing de l'application
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 01/02/2017
 * @version 1.0
 */
angular.module('sustainapp', ['ionic', 'sustainapp.controllers', 'sustainapp.services', 'ngCordova', 'pascalprecht.translate'])

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
    templateUrl: 'templates/tabs.html'
  })
  .state('tab.dash', {
    url: '/dash',
    views: {
      'tab-dash': {
        templateUrl: 'templates/tab-dash.html',
        controller: 'DashCtrl'
      }
    }
  })
  .state('tab.chats', {
      url: '/chats',
      views: {
        'tab-chats': {
          templateUrl: 'templates/tab-chats.html',
          controller: 'ChatsCtrl'
        }
      }
    })
    .state('tab.chat-detail', {
      url: '/chats/:chatId',
      views: {
        'tab-chats': {
          templateUrl: 'templates/chat-detail.html',
          controller: 'ChatDetailCtrl'
        }
      }
    })
    .state('tab.account', {
	  url: '/account',
	  views: {
	    'tab-account': {
	      templateUrl: 'templates/tab-account.html',
	      controller: 'AccountCtrl'
	    }
	  }
    })
   .state('tab.photo', {
	  url: '/photo',
	  views: {
	    'tab-photo': {
	      templateUrl: 'templates/photo.html',
	      controller: 'PhotosCtrl'
	    }
	  }
  });
  $urlRouterProvider.otherwise('/tab/dash');
  
  /**
   * SYSTEME DE TRADUCTION
   */
  $translateProvider.useSanitizeValueStrategy('escape');
  $translateProvider.useStaticFilesLoader({prefix: 'i18n/', suffix: '.json'});
  $translateProvider.registerAvailableLanguageKeys(['en','fr'], {'en_US': 'en', 'en_UK': 'en', 'fr_FR': 'fr', 'fr_BE': 'fr'})
  .determinePreferredLanguage();
  $translateProvider.use();
});

/**
 * CONTROLLERS
 */
angular.module('sustainapp.controllers', []);

/**
 * SERVICES
 */
angular.module('sustainapp.services', []);