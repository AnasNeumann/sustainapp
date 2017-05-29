package com.ca.sustainapp.test.controllers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;

import com.ca.sustainapp.controllers.UserAccountController;
import com.ca.sustainapp.criteria.CityCriteria;
import com.ca.sustainapp.criteria.UserAccountCriteria;
import com.ca.sustainapp.entities.CityEntity;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.entities.UserAccountEntity;
import com.ca.sustainapp.pojo.SearchResult;
import com.ca.sustainapp.pojo.SustainappList;
import com.ca.sustainapp.test.boot.AbstractTest;
import com.ca.sustainapp.validators.LoginValidator;
import com.ca.sustainapp.validators.SigninValidator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import static org.mockito.Matchers.anyLong;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Classe de test pour le controller de connexion/inscription
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 24/05/2017
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class )
@ContextConfiguration
public class UserAccountControllerTest extends AbstractTest {

	/**
	 * Les mocks pour simuler les services et validators
	 */
	@Mock
	private SigninValidator signinValidatorMock;
	@Mock
	private LoginValidator loginValidatorMock;

	
	/**
	 * le controller a tester
	 */
	@InjectMocks
	private UserAccountController controller;
	@InjectMocks
	private LoginValidator loginValidator;
	@InjectMocks
	private SigninValidator signinValidator;
	
	/**
	 * Initiation du jeu de données
	 */
	@Before
	public final void init() {
		super.init(controller);
		when(requestMock.getParameter("mail")).thenReturn("test@mail.com");
		when(requestMock.getParameter("password")).thenReturn("xxxxxxxxxx");
		when(requestMock.getParameter("token")).thenReturn(super.GENERIC_TOKEN);
	}
	
	/**
	 * Test de la fonction singin
	 */
	@Test
	public final void singinSuccessTest(){
		when(requestMock.getParameter("firstName")).thenReturn("jean");
		when(requestMock.getParameter("lastName")).thenReturn("dupond");
		when(requestMock.getParameter("type")).thenReturn("1");
		when(requestMock.getParameter("phone")).thenReturn("0021629029653");
		when(requestMock.getParameter("city")).thenReturn("Toronto");
		
		when(profilServiceMock.createOrUpdate(any(ProfileEntity.class))).thenReturn(super.GENERIC_ID);
		when(userAccountServiceMock.createOrUpdate(any(UserAccountEntity.class))).thenReturn(super.GENERIC_ID);
		when(cityServiceMock.createOrUpdate(any(CityEntity.class))).thenReturn(super.GENERIC_ID);
		
		controller.singin(super.requestMock);
		
		verify(profilServiceMock, times(1)).createOrUpdate(any(ProfileEntity.class));
		verify(userAccountServiceMock, atLeast(1)).createOrUpdate(any(UserAccountEntity.class));
		verify(cityServiceMock, times(1)).createOrUpdate(any(CityEntity.class));
	}
	
	/**
	 * Test si le mail existe déjà en base de données
	 */
	@Test
	public final void singinMailExistTest(){
		when(requestMock.getParameter("firstName")).thenReturn("jean");
		when(requestMock.getParameter("lastName")).thenReturn("dupond");
		when(requestMock.getParameter("type")).thenReturn("1");
		when(requestMock.getParameter("phone")).thenReturn("0021629029653");
		when(requestMock.getParameter("city")).thenReturn("Toronto");
		
		when(profilServiceMock.createOrUpdate(any(ProfileEntity.class))).thenReturn(super.GENERIC_ID);
		when(userAccountServiceMock.createOrUpdate(any(UserAccountEntity.class))).thenReturn(super.GENERIC_ID);
		when(cityServiceMock.createOrUpdate(any(CityEntity.class))).thenReturn(super.GENERIC_ID);
		
		SearchResult<UserAccountEntity> research =  new SearchResult<UserAccountEntity>();
		research.setResults(new SustainappList<UserAccountEntity>().put(super.connectedUser));
		when(userAccountServiceMock.searchByCriteres(any(UserAccountCriteria.class), anyLong(), anyLong())).thenReturn(research);
		
		Map<String, String> errors = signinValidator.validate(requestMock);
		assertNotNull(errors);
		assertEquals(1, errors.size());
		
		verify(profilServiceMock, times(0)).createOrUpdate(any(ProfileEntity.class));
		verify(userAccountServiceMock, times(0)).createOrUpdate(any(UserAccountEntity.class));
		verify(cityServiceMock, times(0)).createOrUpdate(any(CityEntity.class));
	}
	
	/**
	 * Test de la méthode login
	 */
	@Test
	public final void loginSuccessTest(){
		when(userAccountServiceMock.connect(any(String.class), any(String.class))).thenReturn(super.connectedUser);
		when(super.getServiceMock.cascadeGetCities(any(CityCriteria.class))).thenReturn(new SustainappList<CityEntity>().put(city));
		controller.login(super.requestMock);
		verify(userAccountServiceMock, times(1)).connect(any(String.class), any(String.class));
		verify(super.getServiceMock, times(1)).cascadeGetCities(any(CityCriteria.class));
	}
	
	/**
	 * Test erreur de connexion le use n'existe pas 
	 */
	@Test
	public final void UserNotExist(){
		when(userAccountServiceMock.connect(any(String.class), any(String.class))).thenReturn(null);
		when(super.getServiceMock.cascadeGetCities(any(CityCriteria.class))).thenReturn(new SustainappList<CityEntity>().put(city));
		controller.login(super.requestMock);
		verify(userAccountServiceMock, times(1)).connect(any(String.class), any(String.class));
		verify(super.getServiceMock, times(0)).cascadeGetCities(any(CityCriteria.class));
	}
	
}