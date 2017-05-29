package com.ca.sustainapp.test.boot;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ca.sustainapp.controllers.GenericController;
import com.ca.sustainapp.dao.CityServiceDAO;
import com.ca.sustainapp.dao.ProfileServiceDAO;
import com.ca.sustainapp.dao.UserAccountServiceDAO;
import com.ca.sustainapp.entities.CityEntity;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.entities.UserAccountEntity;
import com.ca.sustainapp.services.BadgeService;
import com.ca.sustainapp.services.CascadeDeleteService;
import com.ca.sustainapp.services.CascadeGetService;


/**
 * Classe générique de test sur les controllers
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 24/05/2017
 * @version 1.0
 */
public class AbstractTest {
	
	/**
	 * Mock de simulation d'une request
	 */
	@Mock
	protected HttpServletRequest requestMock;
	
	/**
	 * Mock pour les services utilisé partout
	 */
	@Mock
	protected ProfileServiceDAO profilServiceMock;
	@Mock
	protected CityServiceDAO cityServiceMock;
	@Mock
	protected UserAccountServiceDAO userAccountServiceMock;
	
	/**
	 * Mock pour les services business
	 */
	@Mock
	protected CascadeGetService getServiceMock;
	@Mock
	protected CascadeDeleteService deleteServiceMock;
	@Mock
	protected BadgeService badgeServiceMock;
	
	/**
	 * JDD generic utile pour tout les tests
	 */
	protected UserAccountEntity connectedUser;
	protected CityEntity city;
	
	protected final Long GENERIC_ID = 1L;
	protected final Long GENERIC_NEW_ID = 2L;
	protected final Long GENERIC_UNKNOW_ID = 3L;
	
	protected final String GENERIC_TOKEN = "x";
	protected final String GENERIC_NEW_TOKEN = "y";
	protected final String GENERIC_UNKNOW_TOKEN = "z";
	
	protected final String FIRST_NAME ="anas";
	protected final String LAST_NAME="neumann";
	protected final String GENERIC_NAME_OR_TITLE = "name";

	/**
	 * Initialisation du jeu de données
	 * @param controller
	 */
	protected void init(GenericController controller){
		MockitoAnnotations.initMocks(this);
		MockMvcBuilders.standaloneSetup(controller).build();
		connectedUser = new UserAccountEntity()
				.setId(GENERIC_ID)
				.setMail("test@mail.com")
				.setIsAdmin(false)
				.setPassword("xxxxxxxxxx")
				.setTimestamps(GregorianCalendar.getInstance())
				.setToken(GENERIC_TOKEN)
				.setType(1)
				.setProfile(new ProfileEntity()
						.setFirstName(FIRST_NAME)
						.setLastName(LAST_NAME)
						.setId(GENERIC_ID)
						.setUserId(GENERIC_ID)
						.setLevel(0)
						.setVisibility(1)
						.setTimestamps(GregorianCalendar.getInstance()));
		city = new CityEntity()
				.setId(GENERIC_ID)
				.setUserId(connectedUser.getId())
				.setName(GENERIC_NAME_OR_TITLE)
				.setTimestamps(GregorianCalendar.getInstance());
		
		when(requestMock.getParameter("sessionId")).thenReturn(GENERIC_ID.toString());
		when(requestMock.getParameter("sessionToken")).thenReturn(GENERIC_TOKEN);
		when(userAccountServiceMock.getByToken(any(Long.class), any(String.class))).thenReturn(connectedUser);
	}
	
}
