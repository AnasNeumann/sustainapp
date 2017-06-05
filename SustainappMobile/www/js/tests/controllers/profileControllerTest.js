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
    	scopeMock,
        result,
        configMock,
        httpBackend;

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
     */
    beforeEach(inject(function($rootScope, $controller, $httpBackend, config, $q, $stateParams, $filter, $ionicModal, $cordovaFile, $cordovaFileTransfer, $cordovaDevice, sessionService, profileService, fileService, displayService) {  
    	scopeMock = $rootScope.$new();
    	scopeMock.profileModel = {
    		"allErrors" : [],
    		"profileTemp"	: {
    			"firstName" : "test",
    			"lastName" : "test",
    			"bornDate" : "01/01/2017"
    		},
    		"profile" : {
    			"firstName" : "befor",
    			"lastName" : "befor",
    			"bornDate" : "befor"
    		}
    	};
        sessionServiceMock = {
        		get: jasmine.createSpy('get spy')
                          .and.returnValue(1)           
        };
        configMock = config;
        httpBackend = $httpBackend;
        httpBackend.whenGET(/\.html$/).respond(function(){ return [200,""]});
        httpBackend.whenGET(/\.json$/).respond(function(){ return [200,""]});
        controller = $controller('profileController', { 
        	             $scope : scopeMock,
        	        	 $stateParams : $stateParams,
        	        	 $filter : $filter,
        	        	 $ionicModal : $ionicModal,
        	        	 $cordovaFile : $cordovaFile,
        	        	 $cordovaFileTransfer : $cordovaFileTransfer,
        	        	 $cordovaDevice : $cordovaDevice,
        	        	 'sessionService' : sessionServiceMock,
         	        	 'profileService' : profileService,
        	        	 'fileService' : fileService,
        	        	 'displayService' : displayService
        	        	});
    }));

    /**
     * @TEST
     * Test de la méthode update profil informations
     */
    it('UPDATE PROFILE SUCCESS', function() {
    	result = { 
         	   "code" : 1,
           	  "errors" : []
         };
    	httpBackend.whenPOST(configMock.remoteServer+"/profile").respond(200, result);
    	
        scopeMock.updateProfile();
        httpBackend.flush();
        
        expect(scopeMock).toBeDefined();
        expect(scopeMock.profileModel.allErrors.length).toEqual(0);
        expect(scopeMock.profileModel.profile.firstName).toEqual(scopeMock.profileModel.profileTemp.firstName);
        expect(scopeMock.profileModel.profile.lastName).toEqual(scopeMock.profileModel.profileTemp.lastName);
        expect(scopeMock.profileModel.profile.bornDate).toEqual(scopeMock.profileModel.profileTemp.bornDate);
    });
    
    /**
     * @TEST
     * Test de la méthode update profil informations avec une erreur detectée côté serveur
     */
    it('UPDATE PROFILE ERROR', function() {
    	result = { 
         	  "code" : 0,
           	  "errors" : {
           		  "name" : "form.field.mandatory"
           	  }
         };
    	httpBackend.whenPOST(configMock.remoteServer+"/profile").respond(200, result);
    	
        scopeMock.updateProfile();
        httpBackend.flush();
        
        expect(scopeMock).toBeDefined();
        expect(scopeMock.profileModel.allErrors.name).toEqual("form.field.mandatory");

    });
    
});