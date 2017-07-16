package com.ca.sustainapp.test.controllers;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ca.sustainapp.advice.SustainappSecurityException;
import com.ca.sustainapp.controllers.GenericController;
import com.ca.sustainapp.entities.UserAccountEntity;
import com.ca.sustainapp.test.boot.AbstractTest;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Classe de test pour le controller Generic
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 26/05/2017
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class )
@ContextConfiguration
public class GenericControllerTest extends AbstractTest {

	/**
	 * le controller a tester
	 */
	@InjectMocks
	private GenericController controller;

	/**
	 * Initiation du jeu de données
	 */
	@Before
	public final void init() {
		super.init(controller);
	}

	/**
	 * Récuperation du user avec success
	 */
	@Test
	public void getConnectedUserSuccessTest(){
		when(requestMock.getParameter("sessionId")).thenReturn(super.GENERIC_ID.toString());
		when(requestMock.getParameter("sessionToken")).thenReturn(super.GENERIC_TOKEN);
		when(super.userAccountServiceMock.getByToken(any(Long.class), any(String.class))).thenReturn(super.connectedUser);
		UserAccountEntity user = controller.getConnectedUser(requestMock);
		verify(super.userAccountServiceMock, times(1)).getByToken(any(Long.class), any(String.class));
		assertNotNull(user);
	}

	/**
	 * Erreur de récupération du user
	 */
	@Test
	public void getConnectedUserRequestErrorTest(){
		when(requestMock.getParameter("sessionId")).thenReturn(null);
		when(requestMock.getParameter("sessionToken")).thenReturn(null);
		UserAccountEntity user = null;
		try{
			user = controller.getConnectedUser(requestMock);
		}catch(SustainappSecurityException e){
			
		}
		assertEquals(null, user);
		verify(super.userAccountServiceMock, times(0)).getByToken(any(Long.class), any(String.class));
	}

	/**
	 * Vérification qu'un use n'a pas les droits admin
	 */
	@Test
	public void isNotAdminErrorTest(){
		when(requestMock.getParameter("sessionId")).thenReturn(super.GENERIC_ID.toString());
		when(requestMock.getParameter("sessionToken")).thenReturn(super.GENERIC_TOKEN);
		when(super.userAccountServiceMock.getByToken(any(Long.class), any(String.class))).thenReturn(super.connectedUser);
		Boolean isAdmin = controller.isAdmin(requestMock);
		assertEquals(false, isAdmin);
	}

}