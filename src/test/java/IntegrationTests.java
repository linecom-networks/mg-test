import com.fasterxml.jackson.databind.ObjectMapper;
import com.mango.businesslogic.IUserService;
import com.mango.customer.Application;
import com.mango.httpcontroller.UserController;
import com.mango.model.dto.ResponseSloganDTO;
import com.mango.model.dto.ResponseUserDTO;
import com.mango.model.dto.SloganDTO;
import com.mango.model.dto.UserDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.MOCK,
	classes = Application.class)
public class IntegrationTests {

	private MockMvc mockMvc;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private IUserService userService;

	@InjectMocks
	private UserController controller;

	@Before
	public void setUp() {
		setMockMvc(MockMvcBuilders.standaloneSetup(controller).build());

		ReflectionTestUtils.setField(controller, "userService", userService);
	}

	@Test
	public void createUserOK() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setName("Carles");
		userDTO.setLastName("Mateos");
		userDTO.setCity("Palau");
		userDTO.setEmail("cmateos@linecom.net");
		userDTO.setAddress("AV Cat 129");

		ResultActions response = invokeCreateUser(userDTO);
		ResponseUserDTO responseDTO = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), ResponseUserDTO.class);

		Assert.assertNotNull(responseDTO.getUserId());
		Assert.assertNotNull(responseDTO.getResult());

		//Verify that user saved in DB is correct
		response = invokeGetUser(responseDTO.getUserId());
		UserDTO saved = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), UserDTO.class);

		Assert.assertEquals(userDTO.getName(), saved.getName());
		Assert.assertEquals(userDTO.getLastName(), saved.getLastName());
		Assert.assertEquals(userDTO.getCity(), saved.getCity());
		Assert.assertEquals(userDTO.getEmail(), saved.getEmail());
		Assert.assertEquals(userDTO.getAddress(), saved.getAddress());

	}

	@Test
	public void createUserBAD_REQUEST() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setName("Carles");
		userDTO.setLastName("Mateos");
		userDTO.setCity("Palau");
		userDTO.setEmail("cmateos@linecom.net");
		//Skipping address

		ResultActions response = invokeCreateUser(userDTO);
		response.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

	}

	@Test
	public void updateUserOK() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setName("Carles");
		userDTO.setLastName("Mateos");
		userDTO.setCity("Palau");
		userDTO.setEmail("cmateos@linecom.net");
		userDTO.setAddress("AV Cat 129");

		ResultActions response = invokeCreateUser(userDTO);
		ResponseUserDTO responseDTO = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), ResponseUserDTO.class);

		Assert.assertNotNull(responseDTO.getUserId());
		Assert.assertNotNull(responseDTO.getResult());

		//Verify that user saved in DB is correct
		response = invokeGetUser(responseDTO.getUserId());
		UserDTO saved = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), UserDTO.class);

		Assert.assertEquals(userDTO.getName(), saved.getName());
		Assert.assertEquals(userDTO.getLastName(), saved.getLastName());
		Assert.assertEquals(userDTO.getCity(), saved.getCity());
		Assert.assertEquals(userDTO.getEmail(), saved.getEmail());
		Assert.assertEquals(userDTO.getAddress(), saved.getAddress());

		//Updating all
		userDTO.setUserId(responseDTO.getUserId());
		userDTO.setName("Eduardo");
		userDTO.setLastName("Cabeza");
		userDTO.setCity("Caldes");
		userDTO.setEmail("edu1045@gmail.com");
		userDTO.setAddress("Carrer Voltor 12");

		response = invokeUpdateUser(userDTO);
		responseDTO = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), ResponseUserDTO.class);

		Assert.assertNotNull(responseDTO.getUserId());
		Assert.assertNotNull(responseDTO.getResult());

		response = invokeGetUser(responseDTO.getUserId());
		saved = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), UserDTO.class);

		Assert.assertEquals(userDTO.getName(), saved.getName());
		Assert.assertEquals(userDTO.getLastName(), saved.getLastName());
		Assert.assertEquals(userDTO.getCity(), saved.getCity());
		Assert.assertEquals(userDTO.getEmail(), saved.getEmail());
		Assert.assertEquals(userDTO.getAddress(), saved.getAddress());

	}

	@Test
	public void updateUserERROR_NOT_EXISTS() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(999L);
		userDTO.setName("Eduardo");
		userDTO.setLastName("Cabeza");
		userDTO.setCity("Caldes");
		userDTO.setEmail("edu1045@gmail.com");
		userDTO.setAddress("Carrer Voltor 12");

		ResultActions response = invokeUpdateUser(userDTO);
		ResponseUserDTO responseDTO = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), ResponseUserDTO.class);

		Assert.assertNull(responseDTO.getUserId());
		Assert.assertNotNull(responseDTO.getResult());

	}

	@Test
	public void addSloganOK() throws Exception {
		//First create user
		UserDTO userDTO = new UserDTO();
		userDTO.setName("Carles");
		userDTO.setLastName("Mateos");
		userDTO.setCity("Palau");
		userDTO.setEmail("cmateos@linecom.net");
		userDTO.setAddress("AV Cat 129");

		ResultActions response = invokeCreateUser(userDTO);
		ResponseUserDTO responseDTO = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), ResponseUserDTO.class);

		Long userId = responseDTO.getUserId();

		//Then create slogan
		SloganDTO sloganDTO = new SloganDTO();
		sloganDTO.setUserId(userId);
		sloganDTO.setDescription("Slogan de prueba");

		response = invokeCreateSlogan(sloganDTO);
		ResponseSloganDTO responseSloganDTO = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), ResponseSloganDTO.class);

		Assert.assertEquals((Integer) 1, responseSloganDTO.getNumSlogansActuales());

	}

	@Test
	public void addSloganBAD_REQUESTK() throws Exception {

		//Then create slogan
		SloganDTO sloganDTO = new SloganDTO();
		sloganDTO.setDescription("Slogan de prueba");

		ResultActions response = invokeCreateSlogan(sloganDTO);
		response.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

	}

	@Test
	public void addSloganMAX_SLOGANS() throws Exception {
		//First create user
		UserDTO userDTO = new UserDTO();
		userDTO.setName("Carles");
		userDTO.setLastName("Mateos");
		userDTO.setCity("Palau");
		userDTO.setEmail("cmateos@linecom.net");
		userDTO.setAddress("AV Cat 129");

		ResultActions response = invokeCreateUser(userDTO);
		ResponseUserDTO responseDTO = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), ResponseUserDTO.class);

		Long userId = responseDTO.getUserId();

		//Then create slogan
		SloganDTO sloganDTO = new SloganDTO();
		sloganDTO.setUserId(userId);
		sloganDTO.setDescription("Slogan de prueba");

		response = invokeCreateSlogan(sloganDTO);
		ResponseSloganDTO responseSloganDTO = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), ResponseSloganDTO.class);

		Assert.assertEquals((Integer) 1, responseSloganDTO.getNumSlogansActuales());

		//Add another
		sloganDTO.setDescription("Parapapapa. I'm loving it");

		response = invokeCreateSlogan(sloganDTO);
		responseSloganDTO = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), ResponseSloganDTO.class);

		Assert.assertEquals((Integer) 2, responseSloganDTO.getNumSlogansActuales());

		//Add another
		sloganDTO.setDescription("Work smarter not harder");

		response = invokeCreateSlogan(sloganDTO);
		responseSloganDTO = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), ResponseSloganDTO.class);

		Assert.assertEquals((Integer) 3, responseSloganDTO.getNumSlogansActuales());

		//Add another
		sloganDTO.setDescription("La tienda en casa");

		response = invokeCreateSlogan(sloganDTO);
		responseSloganDTO = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), ResponseSloganDTO.class);

		Assert.assertNull(responseSloganDTO.getNumSlogansActuales());
	}


	private ResultActions invokeCreateUser(UserDTO insertDto) throws Exception {
		ObjectMapper obj = new ObjectMapper();
		String jsonObject = obj.writeValueAsString(insertDto);
		return getMockMvc().perform(MockMvcRequestBuilders.post("/user")
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonObject)
			.accept(MediaType.APPLICATION_JSON));
	}

	private ResultActions invokeGetUser(Long userId) throws Exception {
		return getMockMvc().perform(MockMvcRequestBuilders.get("/user/"+userId)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));
	}

	private ResultActions invokeUpdateUser(UserDTO insertDto) throws Exception {
		ObjectMapper obj = new ObjectMapper();
		String jsonObject = obj.writeValueAsString(insertDto);
		return getMockMvc().perform(MockMvcRequestBuilders.put("/user")
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonObject)
			.accept(MediaType.APPLICATION_JSON));
	}

	private ResultActions invokeCreateSlogan(SloganDTO insertDto) throws Exception {
		ObjectMapper obj = new ObjectMapper();
		String jsonObject = obj.writeValueAsString(insertDto);
		return getMockMvc().perform(MockMvcRequestBuilders.post("/user/slogan")
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonObject)
			.accept(MediaType.APPLICATION_JSON));
	}


	public MockMvc getMockMvc() {
		return mockMvc;
	}

	public void setMockMvc(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}
}
