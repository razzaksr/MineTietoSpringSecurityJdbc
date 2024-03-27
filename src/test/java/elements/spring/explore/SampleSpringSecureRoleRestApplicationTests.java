package elements.spring.explore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class SampleSpringSecureRoleRestApplicationTests {

	@MockBean
	private MyBankService myBankService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser(username="testuser", authorities={"manager"})
	public void testManagerAccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/credit/view"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

//	failure testcase as per expectation
//	@Test
//	@WithMockUser(username="testuser", authorities={"admin"})
//	public void testManagerAccess() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.get("/credit/view"))
//				.andExpect(MockMvcResultMatchers.status().isOk());
//	}

	@Test
	@WithMockUser(username="testuser", authorities={"viewer"})
	public void testViewerAccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/credit/one/1"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithMockUser(username="testuser", authorities={"admin"})
	public void testAdminAccess() throws Exception {
		String request="{\n" +
				"    \"creditcardNumber\": 56787676767,\n" +
				"    \"creditcardCvv\": 932,\n" +
				"    \"creditcardExpiry\": \"2035-10-21\",\n" +
				"    \"creditcardPin\": 984,\n" +
				"    \"creditcardLimit\": 50000,\n" +
				"    \"creditcardUsage\": 11500,\n" +
				"    \"creditcardAvailable\": 38500,\n" +
				"    \"creditcardStatus\": true,\n" +
				"    \"creditcardHolder\": \"Medhini\"\n" +
				"}";
		mockMvc.perform(MockMvcRequestBuilders.put("/credit/swipe/2").contentType(MediaType.APPLICATION_JSON).content(request))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

//	failed as per our expectation
//	@Test
//	@WithMockUser(username="testuser", authorities={"admin"})
//	public void testAdminAccess() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.put("/credit/one/1"))
//				.andExpect(MockMvcResultMatchers.status().isOk());
//	}

	@Test
	@WithMockUser(username="testuser", authorities={"admin"})
	public void testAdminPostAccess() throws Exception {
		String request="{\n" +
				"    \"creditcardNumber\": 56787676767,\n" +
				"    \"creditcardCvv\": 932,\n" +
				"    \"creditcardExpiry\": \"2035-10-21\",\n" +
				"    \"creditcardPin\": 984,\n" +
				"    \"creditcardLimit\": 50000,\n" +
				"    \"creditcardUsage\": 11500,\n" +
				"    \"creditcardAvailable\": 38500,\n" +
				"    \"creditcardStatus\": true,\n" +
				"    \"creditcardHolder\": \"Medhini\"\n" +
				"}";
		mockMvc.perform(MockMvcRequestBuilders.post("/credit/new").contentType(MediaType.APPLICATION_JSON).content(request))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithMockUser(username="testuser")
	public void testUnauthorizedAccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/credit/view"))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

}
