/**
 * test sur le controller de la gestion d'une équipe
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/06/2017
 * @version 1.0
 */
describe('teamControllerTest', function() {
	
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
    beforeEach(inject(function($rootScope, $controller, $httpBackend, config, $q, $stateParams, $state, $ionicModal, teamRole, sessionService, teamService, fileService, displayService) {  
    	scopeMock = $rootScope.$new();
    	scopeMock.teamModel = {
    		"allErrors" : [],
    		"edit" : true,
    		"team"	: {
    			"name" : "before"
    		},
			"name" : "new",
			"action" : "action.apply.accept"
    	};
        sessionServiceMock = {
        		get: jasmine.createSpy('get spy')
                          .and.returnValue(1)           
        };
        configMock = config;
        httpBackend = $httpBackend;
        httpBackend.whenGET(/\.html$/).respond(function(){ return [200,""]});
        httpBackend.whenGET(/\.json$/).respond(function(){ return [200,""]});
        controller = $controller('teamController', { 
        	             $scope : scopeMock,
        	        	 $stateParams : $stateParams,
        	        	 $ionicModal : $ionicModal,
        	        	 $state : $state,
        	        	 'sessionService' : sessionServiceMock,
         	        	 'teamService' : teamService,
         	        	 'teamRole' : teamRole,
        	        	 'fileService' : fileService,
        	        	 'displayService' : displayService
        	        	});
    }));
    
    /**
     * @TEST
     * Test de la méthode update d'une team
     */
    it('UPDATE TEAM SUCCESS', function() {
    	result = {
         	  "code" : 1,
           	  "errors" : []
        };
    	httpBackend.whenPOST(configMock.remoteServer+"/team/update").respond(200, result);
    	
        scopeMock.updateTeam();
        httpBackend.flush();
        
        expect(scopeMock).toBeDefined();
        expect(scopeMock.teamModel.allErrors.length).toEqual(0);
        expect(scopeMock.teamModel.team.name).toEqual(scopeMock.teamModel.name);
    });
    
    /**
     * @TEST
     * Test de modification de droit selon le type de user
     */
    it('UPDATE TEAM ROLE SUCCESS', function() {
    	result = {
           	  "code" : 1,
              "errors" : []
        };
    	httpBackend.whenPOST(configMock.remoteServer+"/team/role").respond(200, result);

    	scopeMock.handleTeam("" , "action.apply.accept", "me");
        httpBackend.flush();
        
        expect(scopeMock).toBeDefined();
        expect(scopeMock.teamModel.allErrors.length).toEqual(0);
        expect(scopeMock.teamModel.displayAction).toEqual("action.cancel");
        expect(scopeMock.teamModel.action).toEqual("action.leave.cancel");
        expect(scopeMock.teamModel.role).toEqual("request");
        
    });
	
});