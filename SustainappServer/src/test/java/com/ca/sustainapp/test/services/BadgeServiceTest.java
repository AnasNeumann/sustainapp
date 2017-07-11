package com.ca.sustainapp.test.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.criteria.ProfilBadgeCriteria;
import com.ca.sustainapp.dao.BadgeServiceDAO;
import com.ca.sustainapp.dao.ParticipationServiceDAO;
import com.ca.sustainapp.dao.ProfilBadgeServiceDAO;
import com.ca.sustainapp.dao.TeamServiceDAO;
import com.ca.sustainapp.entities.BadgeEntity;
import com.ca.sustainapp.entities.NotificationEntity;
import com.ca.sustainapp.entities.ProfilBadgeEntity;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.entities.UserAccountEntity;
import com.ca.sustainapp.pojo.SustainappList;
import com.ca.sustainapp.services.BadgeService;
import com.ca.sustainapp.services.NotificationService;
import com.ca.sustainapp.test.boot.AbstractTest;

/**
 * Classe de test pour le service d'ajout de badge
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 29/05/2017
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class )
@ContextConfiguration
public class BadgeServiceTest extends AbstractTest {

	/**
	 * Mocks
	 */
	@Mock
	private ProfilBadgeServiceDAO linkServiceMock;
	@Mock
	private TeamServiceDAO teamServiceMock;
	@Mock
	private ParticipationServiceDAO participationServiceMock;
	@Mock
	protected NotificationService notificationServiceMock;
	@Mock
	private BadgeServiceDAO badgeServiceMock;

	/**
	 * le service a tester
	 */
	@InjectMocks
	private BadgeService service;
	
	/**
	 * JDD
	 */
	List<BadgeEntity> badges;
	
	/**
	 * Initiation du jeu de données
	 */
	@Before
	public final void init() {
		MockitoAnnotations.initMocks(this);
		MockMvcBuilders.standaloneSetup(service).build();
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
		
		badges = new SustainappList<BadgeEntity>().put(new BadgeEntity()
				.setId(GENERIC_ID)
				.setName(SustainappConstantes.BADGE_STAR)
				.setAbout(SustainappConstantes.BADGE_STAR)
				.setIcon(SustainappConstantes.BADGE_STAR)
				.setTimestamps(GregorianCalendar.getInstance()));	
	}
	
	/**
	 * Test d'ajout d'un badge
	 */
	@Test
	public void ajoutBadgeTest(){
		when(getServiceMock.cascadeGet(any(ProfilBadgeCriteria.class))).thenReturn(new SustainappList<ProfilBadgeEntity>());
		when(badgeServiceMock.getAll()).thenReturn(badges);
		when(linkServiceMock.createOrUpdate(any(ProfilBadgeEntity.class))).thenReturn(GENERIC_ID);
		when(profilServiceMock.createOrUpdate(any(ProfileEntity.class))).thenReturn(GENERIC_ID);
		when(notificationServiceMock.create(any(String.class), any(Long.class), any(Long.class), any(Long.class))).thenReturn(new NotificationEntity());

		Boolean result = service.star(connectedUser.getProfile());
		
		verify(linkServiceMock, times(1)).createOrUpdate(any(ProfilBadgeEntity.class));
		verify(profilServiceMock, times(1)).createOrUpdate(any(ProfileEntity.class));
		verify(notificationServiceMock, times(1)).create(any(String.class), any(Long.class), any(Long.class), any(Long.class));
		assertEquals(true, result);
	}
	
	
}