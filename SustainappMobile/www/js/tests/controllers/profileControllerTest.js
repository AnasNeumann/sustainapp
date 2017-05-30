/**
 * test sur le controller du profile personnel
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/05/2017
 * @version 1.0
 */
describe('profileControllerTest', function($stateParams, $filter, $ionicModal, $cordovaFile, $cordovaFileTransfer, $cordovaDevice) {

	/**
	 * Les Mocks
	 */
    var controller
    	sessionServiceMock,
    	profileServiceMock,
    	fileServiceMock, 
    	displayServiceMock,
    	$scopeMock;

    /**
     * @BEFOR
     * Chargement de l'application
     */
    beforeEach(module('app')); 

    /**
     * @BEFOR
     * Injection des Mocks et du controller
     */
    beforeEach(inject(function($controller, $q) {  
        deferredResult = $q.defer();
        profileServiceMock = {
        		update: jasmine.createSpy('login spy')
                          .and.returnValue(deferredResult.promise)           
        };
      
        sessionServiceMock = jasmine.createSpyObj('$state spy', ['get']);
    	fileServiceMock = jasmine.createSpyObj('$state spy'); 
    	displayServiceMock = jasmine.createSpyObj('$state spy');
    	$scopeMock = jasmine.createSpyObj('$state spy');
        
        controller = $controller('profileController', { 
        	            '$scope' : $scopeMock, 
        	            '$stateParams' : $stateParams, 
        	            '$filter' : $filter, 
        	            '$ionicModal' : $ionicModal, 
        	            '$cordovaFile' : $cordovaFile, 
        	            '$cordovaFileTransfer' : $cordovaFileTransfer, 
        	            '$cordovaDevice' : $cordovaDevice,
        	            'sessionService' : sessionServiceMock,
        	        	'profileService' : profileServiceMock,
        	        	'fileService' : fileServiceMock, 
        	        	'displayService' :  displayServiceMock
        	        	});
    }));

    /**
     * @TEST
     * Test de la méthode update profil informations
     */
    describe('#updateProfile', function() {

        beforeEach(inject(function(_$rootScope_) {  
	        $rootScope = _$rootScope_;
	        controller.username = 'test1';
	        controller.password = 'password1';
	        controller.doLogin();
	    }));
        it('should update profile informations', function() {
        	var data = new FormData();
			data.append("firstName", "");
			data.append("lastName", "");
			data.append("bornDate", "");
			data.append("sessionId", sessionServiceMock.get('id'));
			data.append("sessionToken", sessionServiceMock.get('token'));
            expect(profileServiceMock.update).toHaveBeenCalledWith(data); 
        });
        describe('When the update is executed', function() {
            it('succes expected', function() {

                // TODO: Mock the login response from DinnerService

                expect($scope.profileModel.modeRead).toHaveBeenCalledWith(true);
            });

            it('error expected', function() {

                // TODO: Mock the login response from DinnerService

                expect(ionicPopupMock.alert).toHaveBeenCalled();
            });
        });
    })
});