package ua.goit.notes;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.goit.configuration.MvcConfiguration;
import ua.goit.configuration.SpringSecurityConfiguration;
import ua.goit.configuration.converters.RoleStringConverter;
import ua.goit.configuration.converters.StringRoleConverter;
import ua.goit.roles.RoleService;
import ua.goit.users.UserController;
import ua.goit.users.UserDto;
import ua.goit.users.UserService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
//@Import(UserController.class)
//@WebMvcTest(UserController.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "ADMIN")
//@ContextConfiguration(classes = {ConfigurationTest.class})
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    @MockBean
    private RoleService roleService;

//    @Before
//    public void setup()
//    {
//        //Init MockMvc Object and build
//        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }

    @Test
    //@WithMockUser(roles = "ADMIN")
    public void givenEmployees_whenGetEmployees_thenReturnJsonArray()
            throws Exception {

        UserDto user = new UserDto();
        user.setName("test");
        user.setPassword("pass");

        List<UserDto> allEmployees = List.of(user);

        given(service.findAll()).willReturn(allEmployees);

        mvc.perform(MockMvcRequestBuilders.get("/registration")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

}
