/**
 * test sur le controller de la validation d'un quizz
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/06/2017
 * @version 1.0
 */
describe('quizzControllerTest', function() {

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
    beforeEach(inject(function($rootScope, $controller, $httpBackend, config, $ionicModal, $state, sessionService, quizService, displayService, listService) {  
    	scopeMock = $rootScope.$new();
    	scopeMock.quizModel = {
    		"allErrors" : [],
    		"loaded" : true, 
    		"questions" : [{
    			"answers" : [
    				{
    					"data" : "data"
    				}
    			]
    		}],
    		"responses"  : [],
    		"topic" : 1,
    	};
    	scopeMock.currentPosition = 0;
    	scopeMock.currentQuestion = {};							
    	scopeMock.btnTxt = "quiz.next";
		scopeMock.random = 0.25;
		scopeMock.reponseModel = {};
		scopeMock.reponseModel.loaded = false;
		scopeMock.reponseModel.allTrue = false;
		scopeMock.reponseModel.eachQuestions = [];
		stateParamsMock = {};
		stateParamsMock.id = 1;
        sessionServiceMock = {
        		get: jasmine.createSpy('get spy')
                          .and.returnValue(1)
        };
        configMock = config;
        httpBackend = $httpBackend;
        httpBackend.whenGET(/\.html$/).respond(function(){ return [200,""]});
        httpBackend.whenGET(/\.json$/).respond(function(){ return [200,""]});
        controller = $controller('quizController', { 
					         $scope : scopeMock,
					       	 $stateParams : stateParamsMock,
					       	 $ionicModal : $ionicModal,
					       	 $state : $state,
					       	 'sessionService' : sessionServiceMock,
					         'quizService' : quizService,
					         'listService' : listService,
					       	 'displayService' : displayService
        	        	});
    }));

    /**
     * @TEST
     * Test de la méthode de validation d'une question
     */
    it('VALIDATE QUESTION SUCCESS', function() {
    	result = {
             	"code" : 1,
                "errors" : [],
                "allTrue" : true,
                "eachQuestions" : [true]
         };
    	httpBackend.whenPOST(configMock.remoteServer+"/quiz").respond(200, result);
    	
        scopeMock.validateAnswer(scopeMock.quizModel.questions[0]);
        httpBackend.flush();
        
        expect(scopeMock).toBeDefined();
        expect(scopeMock.reponseModel.allTrue).toEqual(true);
        expect(scopeMock.reponseModel.eachQuestions.length).toEqual(0);
        expect(scopeMock.quizModel.responses[0]).toEqual("data/");
    });

});