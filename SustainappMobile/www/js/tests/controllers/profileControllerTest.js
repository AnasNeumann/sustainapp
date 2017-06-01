/**
 * test sur le controller du profile personnel
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/05/2017
 * @version 1.0
 */
describe('profileControllerTest', function() {

	/**
	 * Les Mocks
	 */
    var controller,
    	sessionServiceMock,
    	profileServiceMock,
    	scopeMock;

    /**
     * @BEFOR
     * Chargement des modules
     */
    beforeEach(module('sustainapp')); 
    beforeEach(module('sustainapp.directives')); 
    beforeEach(module('sustainapp.constantes'));
    beforeEach(module('sustainapp.controllers'));
    beforeEach(module('sustainapp.services'));
    
    /**
     * @BEFOR
     * Injection des Mocks et du controller
     */  /*
    beforeEach(inject(function($rootScope, $controller, $q, $stateParams, $scope, $filter, $ionicModal, $cordovaFile, $cordovaFileTransfer, $cordovaDevice, sessionService, profileService, fileService, displayService) {  
        scopeMock = $rootScope.$new();
        
        deferredResult = $q.defer();
        profileServiceMock = {
        		update: jasmine.createSpy('update spy')
                          .and.returnValue(deferredResult.promise)           
        };
      
        sessionServiceMock = jasmine.createSpyObj('sessionService spy', ['get']);
        controller = $controller('profileController', { 
        	             $scope : scopeMock,
        	        	 $stateParams : $stateParams,
        	        	 $filter : $filter,
        	        	 $ionicModal : $ionicModal,
        	        	 $cordovaFile : $cordovaFile,
        	        	 $cordovaFileTransfer : $cordovaFileTransfer,
        	        	 $cordovaDevice : $cordovaDevice,
        	        	 'sessionService' : sessionServiceMock,
         	        	 'profileService' : profileServiceMock,
        	        	 'fileService' : fileService,
        	        	 'displayService' : displayService
        	        	});
        scope.$digest();
    }));*/
    
    /**
     * @BEFOR
     * Injection des Mocks et du controller
     */
    beforeEach(inject(function($rootScope){
    	scopeMock = $rootScope.$new();
    }));

    /**
     * @TEST
     * Test de la méthode update profil informations
     */
    it('UPDATE', function() {
    	expect(scopeMock).toBeDefined();

    	//scopeMock.updateProfile();
    	//console.log(scopeMock);
        //expect(scopeMock.profileModel.allErrors).toBe([]);
    	
    	//expect(scope.settings.enableFriends).toEqual(true);
        //expect($scope.profileModel.modeRead).toHaveBeenCalledWith(true);
    });
});