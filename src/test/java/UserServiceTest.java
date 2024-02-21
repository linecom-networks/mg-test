import com.mango.businesslogic.IUserService;
import com.mango.businesslogic.impl.UserServiceImpl;
import com.mango.data.repository.SloganRepository;
import com.mango.data.repository.UserRepository;
import com.mango.model.dto.SloganDTO;
import com.mango.model.dto.UserDTO;
import com.mango.model.entity.SloganEntity;
import com.mango.model.entity.UserEntity;
import com.mango.model.util.UserUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {UserServiceImpl.class, UserUtils.class})
@TestPropertySource(locations = { "classpath:application-test.properties" })
public class UserServiceTest {

	@Autowired
	private IUserService userService;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private SloganRepository sloganRepository;

	@MockBean
	private UserUtils userUtils;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void createUser_OK() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setName("Carles");
		userDTO.setLastName("Mateos");
		userDTO.setCity("Palau");
		userDTO.setEmail("cmateos@linecom.net");
		userDTO.setAddress("AV Cat 129");

		UserEntity entity = UserEntity.builder()
			.id(userDTO.getUserId())
			.name(userDTO.getName())
			.lastName(userDTO.getLastName())
			.address(userDTO.getAddress())
			.city(userDTO.getCity())
			.email(userDTO.getEmail())
			.build();
		Mockito.when(userUtils.mapUserEntity(userDTO)).thenReturn(entity);
		Long id = 34342L;
		entity.setId(id);
		Mockito.when(userRepository.save(Mockito.any())).thenReturn(entity);

		Long userId = userService.createUser(userDTO);

		Assert.assertEquals(id, userId);

	}

	@Test
	public void createUser_EXCEPTION() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setName("Carles");
		userDTO.setLastName("Mateos");
		userDTO.setCity("Palau");
		userDTO.setEmail("cmateos@linecom.net");
		userDTO.setAddress("AV Cat 129");

		thrown.expectMessage("Error saving user");
		userService.createUser(userDTO);

	}

	@Test
	public void updateUser_OK() throws Exception {
		Long userId = 98394L;
		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(userId);
		userDTO.setName("Carles");
		userDTO.setLastName("Mateos");
		userDTO.setCity("Palau");
		userDTO.setEmail("cmateos@linecom.net");
		userDTO.setAddress("AV Cat 129");

		UserEntity entity = UserEntity.builder()
			.id(userDTO.getUserId())
			.name(userDTO.getName())
			.lastName(userDTO.getLastName())
			.address(userDTO.getAddress())
			.city(userDTO.getCity())
			.email(userDTO.getEmail())
			.build();
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(entity));

		Mockito.when(userUtils.mapUserEntity(userDTO)).thenReturn(entity);
		Mockito.when(userRepository.save(Mockito.any())).thenReturn(entity);

		Long userIdResponse = userService.updateUser(userDTO);

		Assert.assertEquals(userId, userIdResponse);

	}

	@Test
	public void updateUser_USER_NOT_EXISTS() throws Exception {
		Long userId = 98394L;
		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(userId);
		userDTO.setName("Carles");
		userDTO.setLastName("Mateos");
		userDTO.setCity("Palau");
		userDTO.setEmail("cmateos@linecom.net");
		userDTO.setAddress("AV Cat 129");

		UserEntity entity = UserEntity.builder()
			.id(userDTO.getUserId())
			.name(userDTO.getName())
			.lastName(userDTO.getLastName())
			.address(userDTO.getAddress())
			.city(userDTO.getCity())
			.email(userDTO.getEmail())
			.build();
		Mockito.when(userRepository.findById(userId)).thenThrow(new NoSuchElementException());

		thrown.expectMessage("User with ID: " + userDTO.getUserId() + " not exists");
		userService.updateUser(userDTO);

	}

	@Test
	public void updateUser_ERROR() throws Exception {
		Long userId = 98394L;
		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(userId);
		userDTO.setName("Carles");
		userDTO.setLastName("Mateos");
		userDTO.setCity("Palau");
		userDTO.setEmail("cmateos@linecom.net");
		userDTO.setAddress("AV Cat 129");

		UserEntity entity = UserEntity.builder()
			.id(userDTO.getUserId())
			.name(userDTO.getName())
			.lastName(userDTO.getLastName())
			.address(userDTO.getAddress())
			.city(userDTO.getCity())
			.email(userDTO.getEmail())
			.build();
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(entity));

		Mockito.when(userUtils.mapUserEntity(userDTO)).thenReturn(entity);
		Mockito.when(userRepository.save(Mockito.any())).thenThrow(new RuntimeException());

		thrown.expectMessage("Error updating user with id: " + userDTO.getUserId());
		userService.updateUser(userDTO);

	}

	@Test
	public void addSloganOK() throws Exception {
		Long userId = 845L;
		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(userId);
		userDTO.setName("Carles");
		userDTO.setLastName("Mateos");
		userDTO.setCity("Palau");
		userDTO.setEmail("cmateos@linecom.net");
		userDTO.setAddress("AV Cat 129");

		UserEntity entity = UserEntity.builder()
			.id(userDTO.getUserId())
			.name(userDTO.getName())
			.lastName(userDTO.getLastName())
			.address(userDTO.getAddress())
			.city(userDTO.getCity())
			.email(userDTO.getEmail())
			.build();

		SloganDTO sloganDTO = new SloganDTO();
		sloganDTO.setUserId(userId);
		sloganDTO.setDescription("Porque tu lo vales");

		List<SloganEntity> userSlogans = new ArrayList<>();

		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(entity));
		Mockito.when(sloganRepository.findByUserId(userId)).thenReturn(userSlogans);
		SloganEntity entityMock = new SloganEntity();
		Mockito.when(userUtils.mapSloganEntity(sloganDTO)).thenReturn(entityMock);
		Mockito.when(sloganRepository.save(entityMock)).thenReturn(entityMock);

		Integer numSlogans = userService.addSlogan(sloganDTO);

		Assert.assertEquals((Integer) 1, numSlogans);

		//Simulate sloganCreation
		userSlogans.add(entityMock);
		numSlogans = userService.addSlogan(sloganDTO);

		Assert.assertEquals((Integer) 2, numSlogans);
	}

	@Test
	public void addSloganERROR_USER_NOT_EXISTS() throws Exception {
		Long userId = 845L;
		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(userId);
		userDTO.setName("Carles");
		userDTO.setLastName("Mateos");
		userDTO.setCity("Palau");
		userDTO.setEmail("cmateos@linecom.net");
		userDTO.setAddress("AV Cat 129");

		UserEntity entity = UserEntity.builder()
			.id(userDTO.getUserId())
			.name(userDTO.getName())
			.lastName(userDTO.getLastName())
			.address(userDTO.getAddress())
			.city(userDTO.getCity())
			.email(userDTO.getEmail())
			.build();

		SloganDTO sloganDTO = new SloganDTO();
		sloganDTO.setUserId(userId);
		sloganDTO.setDescription("Porque tu lo vales");

		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());
		thrown.expectMessage("User with ID: " + userDTO.getUserId() + " not exists");

		userService.addSlogan(sloganDTO);

	}

	@Test
	public void addSloganERROR_MAX_SLOGANS() throws Exception {
		Long userId = 845L;
		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(userId);
		userDTO.setName("Carles");
		userDTO.setLastName("Mateos");
		userDTO.setCity("Palau");
		userDTO.setEmail("cmateos@linecom.net");
		userDTO.setAddress("AV Cat 129");

		UserEntity entity = UserEntity.builder()
			.id(userDTO.getUserId())
			.name(userDTO.getName())
			.lastName(userDTO.getLastName())
			.address(userDTO.getAddress())
			.city(userDTO.getCity())
			.email(userDTO.getEmail())
			.build();

		SloganDTO sloganDTO = new SloganDTO();
		sloganDTO.setUserId(userId);
		sloganDTO.setDescription("Porque tu lo vales");

		List<SloganEntity> userSlogans = new ArrayList<>();

		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(entity));
		Mockito.when(sloganRepository.findByUserId(userId)).thenReturn(userSlogans);
		SloganEntity entityMock = new SloganEntity();
		Mockito.when(userUtils.mapSloganEntity(sloganDTO)).thenReturn(entityMock);
		Mockito.when(sloganRepository.save(entityMock)).thenReturn(entityMock);

		Integer numSlogans = userService.addSlogan(sloganDTO);

		Assert.assertEquals((Integer) 1, numSlogans);

		//Simulate sloganCreation
		userSlogans.add(entityMock);
		numSlogans = userService.addSlogan(sloganDTO);

		Assert.assertEquals((Integer) 2, numSlogans);

		//Simulate sloganCreation
		userSlogans.add(entityMock);

		thrown.expectMessage("Max number of slogans");
		userService.addSlogan(sloganDTO);

	}

	@Test
	public void addSloganERROR_SAVE() throws Exception {
		Long userId = 845L;
		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(userId);
		userDTO.setName("Carles");
		userDTO.setLastName("Mateos");
		userDTO.setCity("Palau");
		userDTO.setEmail("cmateos@linecom.net");
		userDTO.setAddress("AV Cat 129");

		UserEntity entity = UserEntity.builder()
			.id(userDTO.getUserId())
			.name(userDTO.getName())
			.lastName(userDTO.getLastName())
			.address(userDTO.getAddress())
			.city(userDTO.getCity())
			.email(userDTO.getEmail())
			.build();

		SloganDTO sloganDTO = new SloganDTO();
		sloganDTO.setUserId(userId);
		sloganDTO.setDescription("Porque tu lo vales");

		List<SloganEntity> userSlogans = new ArrayList<>();

		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(entity));
		Mockito.when(sloganRepository.findByUserId(userId)).thenReturn(userSlogans);
		SloganEntity entityMock = new SloganEntity();
		Mockito.when(userUtils.mapSloganEntity(sloganDTO)).thenReturn(entityMock);
		Mockito.when(sloganRepository.save(entityMock)).thenThrow(new RuntimeException());

		thrown.expectMessage("Error adding new slogan, user with id: " + sloganDTO.getUserId());

		userService.addSlogan(sloganDTO);

	}

}
