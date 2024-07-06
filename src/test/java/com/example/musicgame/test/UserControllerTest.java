package com.example.musicgame.test;

import com.example.musicgame.controller.UserController;
import com.example.musicgame.model.User;
import com.example.musicgame.service.UserService;
import com.example.musicgame.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testRegisterUser_Success() throws Exception {
        User user = new User("testuser", "testpassword");
        when(userService.registerUser(anyString(), anyString())).thenReturn(user);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"testpassword\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterUser_ExistingUser() throws Exception {
        when(userService.registerUser(anyString(), anyString())).thenThrow(new RuntimeException("User already exists"));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"existinguser\", \"password\": \"password\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLoginUser_Success() throws Exception {
        User user = new User("testuser", "testpassword");
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("USER")
                .build();
        when(userService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(bCryptPasswordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtUtil.generateToken(anyString())).thenReturn("testtoken");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"testpassword\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginUser_InvalidCredentials() throws Exception {
        when(userService.loadUserByUsername(anyString())).thenReturn(null);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"wronguser\", \"password\": \"wrongpassword\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testChangePassword_Success() throws Exception {
        User user = new User("testuser", "oldpassword");
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("USER")
                .build();
        when(userService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(bCryptPasswordEncoder.matches(anyString(), anyString())).thenReturn(true);

        mockMvc.perform(post("/auth/changePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"oldPassword\": \"oldpassword\", \"newPassword\": \"newpassword\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testChangePassword_InvalidOldPassword() throws Exception {
        User user = new User("testuser", "oldpassword");
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("USER")
                .build();
        when(userService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(bCryptPasswordEncoder.matches(anyString(), anyString())).thenReturn(false);

        mockMvc.perform(post("/auth/changePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"oldPassword\": \"wrongpassword\", \"newPassword\": \"newpassword\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDeleteUser_Success() throws Exception {
        mockMvc.perform(post("/auth/deleteUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUser_NonExistentUser() throws Exception {
        doThrow(new RuntimeException("User does not exist")).when(userService).deleteUser(anyString());

        mockMvc.perform(post("/auth/deleteUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"nonexistentuser\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRegisterUser_MissingFields() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"\", \"password\": \"\"}"))
                .andExpect(status().isBadRequest());
    }
}
