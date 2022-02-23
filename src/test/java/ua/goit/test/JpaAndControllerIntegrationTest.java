package ua.goit.test;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ua.goit.notes.AccessType;
import ua.goit.notes.NoteDto;
import ua.goit.notes.NoteService;
import ua.goit.roles.RoleDto;
import ua.goit.roles.RoleService;
import ua.goit.users.UserDto;
import ua.goit.users.UserService;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("dev")
@SpringBootTest
@AutoConfigureMockMvc
class JpaAndControllerIntegrationTest {

    private final RoleService roleService;
    private final UserService userService;
    private final NoteService noteService;
    private final MockMvc mockMvc;

    private NoteDto noteDto;
    private UserDto userDto;
    private RoleDto roleDto;

    private UserDto userWeb;

    @Autowired
    public JpaAndControllerIntegrationTest(NoteService noteService, RoleService roleService, UserService userService, MockMvc mockMvc) {
        this.roleService = roleService;
        this.userService = userService;
        this.noteService = noteService;
        this.mockMvc = mockMvc;
    }

    @BeforeAll
    public void initBefore() {
        userWeb = new UserDto();
        userWeb.setName("UserWeb");
        userWeb.setPassword("webpass");
        userService.create(userWeb);
    }

    @BeforeEach
    public void initBeforeEach() {
        roleDto = new RoleDto();
        roleDto.setName("test role");

        userDto = new UserDto();
        userDto.setName("test user");
        userDto.setPassword("test pass");

        noteDto = new NoteDto();
        noteDto.setName("test note");
        noteDto.setText("bla bla bla");
        noteDto.setAccessType(AccessType.PUBLIC);
    }

    @Test
    public void getLoginAndRegistration() throws Exception {
        mockMvc
                .perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
        mockMvc
                .perform(get("/registration"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }

    @Test
    public void postLoginFail() throws Exception {
        mockMvc
                .perform(post("/login")
                        .param("username", "admin")
                        .param("password", "pass"))
                .andDo(print())
                .andExpect(redirectedUrl("/login?error"));
    }

    @Test
    public void postLoginOk() throws Exception {
        mockMvc
                .perform(post("/login")
                        .param("username", userWeb.getName())
                        .param("password", userWeb.getPassword()))
                .andDo(print())
                .andExpect(redirectedUrl("/note/list"));
    }

    @Test
    public void postRegistrationFail_ShortUserName() throws Exception {
        mockMvc
                .perform(post("/registration")
                        .param("name", "usr")
                        .param("password", "password"))
                .andDo(print())
                .andExpect(content().string(Matchers.containsString("User name should be at least 5 character and maximum length of 50")))
                .andExpect(view().name("registration"));
    }

    @Test
    public void postRegistrationFail_IncorrectUserName() throws Exception {
        mockMvc
                .perform(post("/registration")
                        .param("name", "user 1")
                        .param("password", "password"))
                .andDo(print())
                .andExpect(content().string(Matchers.containsString("User name should be alphanumeric")))
                .andExpect(view().name("registration"));
    }

    @Test
    public void postRegistrationFail_IncorrectPassword() throws Exception {
        mockMvc
                .perform(post("/registration")
                        .param("name", "newUser")
                        .param("password", "pass"))
                .andDo(print())
                .andExpect(content().string(Matchers.containsString("Password should be at least 8 character and maximum length of 100")))
                .andExpect(view().name("registration"));
    }

    @Test
    public void postRegistrationOk() throws Exception {
        mockMvc
                .perform(post("/registration")
                        .param("name", "NewUser1")
                        .param("password", "New pass"))
                .andDo(print())
                .andExpect(redirectedUrl("/login"));
    }

    @WithMockUser("UserWeb")
    @Test
    public void getNoteList() throws Exception {
        mockMvc
                .perform(get("/note/list"))
                .andDo(print())
                .andExpect(view().name("note/notes"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("Removing note")));
    }

    @WithMockUser("UserWeb")
    @Test
    public void getCreateNoteList() throws Exception {
        mockMvc
                .perform(get("/note/create"))
                .andDo(print())
                .andExpect(view().name("note/create_note"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("/note/save_note")));
    }

    @WithMockUser("UserWeb")
    @Test
    public void postSaveNoteListOk() throws Exception {
        mockMvc
                .perform(post("/note/save_note")
                        .param("name", "new note")
                        .param("text", "some text")
                        .param("accessType", "PRIVATE")
                )
                .andDo(print())
                .andExpect(redirectedUrl("/note/list"));
    }

    @WithMockUser("UserWeb")
    @Test
    public void postSaveNoteListFail_ShortName() throws Exception {
        mockMvc
                .perform(post("/note/save_note")
                        .param("name", "n")
                        .param("text", "some text")
                        .param("accessType", "PRIVATE")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("note/create_note"));
    }

    @WithMockUser("UserWeb")
    @Test
    public void postSaveNoteListFail_ShortText() throws Exception {
        mockMvc
                .perform(post("/note/save_note")
                        .param("name", "some note")
                        .param("text", "so")
                        .param("accessType", "PRIVATE")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("note/create_note"));
    }

    @WithMockUser("user1")
    @Test
    public void insertRole() {
        RoleDto savedRole = roleService.create(roleDto);
        RoleDto foundRole = roleService.find(savedRole.getId());
        assertThat(foundRole).isEqualTo(savedRole);
    }

    @WithMockUser("user1")
    @Test
    public void insertUser() {
        userDto.setRoles(List.of(roleService.create(roleDto)));
        UserDto savedUser = userService.create(userDto);
        UserDto foundUser = userService.find(savedUser.getId());
        assertThat(foundUser).isEqualTo(savedUser);
    }

    @WithMockUser("user1")
    @Test
    public void insertNote() {
        NoteDto savedNote = noteService.create(noteDto);
        NoteDto foundNote = noteService.find(savedNote.getId());
        assertThat(foundNote).isEqualTo(savedNote);
    }

    @WithMockUser("user1")
    @Test
    public void updateRole() {
        RoleDto createdRole = roleService.create(roleDto);
        roleDto.setName("test test role");
        roleDto.setId(createdRole.getId());
        roleService.update(createdRole.getId(), roleDto);
        RoleDto updatedRole = roleService.find(createdRole.getId());
        assertThat(updatedRole).isEqualTo(roleDto);
    }

    @WithMockUser("user1")
    @Test
    public void updateUser() {
        userDto.setRoles(List.of(roleService.create(roleDto)));
        UserDto createdUser = userService.create(userDto);
        userDto.setName("test test user");
        userDto.setId(createdUser.getId());
        userService.update(createdUser.getId(), userDto);
        UserDto updatedUser = userService.find(createdUser.getId());
        assertThat(updatedUser).isEqualTo(userDto);
    }

    @WithMockUser("user1")
    @Test
    public void updateNote() {
        NoteDto createdNote = noteService.create(noteDto);
        noteDto.setAccessType(AccessType.PRIVATE);
        noteDto.setId(createdNote.getId());
        noteService.update(createdNote.getId(), noteDto);
        NoteDto updatedNote = noteService.find(createdNote.getId());
        assertThat(updatedNote).isEqualTo(noteDto);
    }

    @WithMockUser("user1")
    @Test
    public void getRole() {
        RoleDto createdRole = roleService.create(roleDto);
        RoleDto updatedRole = roleService.find(createdRole.getId());
        assertThat(updatedRole).isEqualTo(createdRole);
    }

    @WithMockUser("user1")
    @Test
    public void getUser() {
        UserDto createdUser = userService.create(userDto);
        UserDto updatedUser = userService.find(createdUser.getId());
        assertThat(updatedUser).isEqualTo(createdUser);
    }

    @WithMockUser("user1")
    @Test
    public void getNote() {
        NoteDto createdNote = noteService.create(noteDto);
        NoteDto updatedNote = noteService.find(createdNote.getId());
        assertThat(updatedNote).isEqualTo(createdNote);
    }

    @WithMockUser("user1")
    @Test
    public void deleteRole() {
        RoleDto createdRole = roleService.create(roleDto);
        UUID uuid = createdRole.getId();
        roleService.delete(uuid);
        RoleDto foundRole = roleService.find(uuid);
        assertThat(foundRole).isEqualTo(new RoleDto());
    }

    @WithMockUser("user1")
    @Test
    public void deleteUser() {
        UserDto createdUser = userService.create(userDto);
        UUID uuid = createdUser.getId();
        userService.delete(uuid);
        UserDto foundUser = userService.find(uuid);
        assertThat(foundUser).isEqualTo(new UserDto());
    }

    @WithMockUser("user1")
    @Test
    public void deleteNote() {
        NoteDto createdNote = noteService.create(noteDto);
        UUID uuid = createdNote.getId();
        noteService.delete(uuid);
        NoteDto foundNote = noteService.find(uuid);
        assertThat(foundNote).isEqualTo(new NoteDto());
    }
}