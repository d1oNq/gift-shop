package com.giftshop.dao;

import com.giftshop.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserDAOImplTest {

    @Mock
    private Connection conn;

    @Mock
    private PreparedStatement ps;

    @Mock
    private ResultSet rs;

    @InjectMocks
    private UserDAOImpl userDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUserRegister() throws Exception {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");

        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        boolean result = userDAO.userRegister(user);
        assertTrue(result);
        verify(ps, times(1)).executeUpdate();
    }

    @Test
    void testUserLogin() throws Exception {
        String email = "john.doe@example.com";
        String password = "password";

        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(1);
        when(rs.getString(2)).thenReturn("John Doe");
        when(rs.getString(3)).thenReturn(email);
        when(rs.getString(4)).thenReturn(password);

        User result = userDAO.userLogin(email, password);
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(ps, times(1)).executeQuery();
    }

    @Test
    void testUpdateProfile() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPhone("123456789");
        user.setPassword("password");

        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        boolean result = userDAO.updateProfile(user);
        assertTrue(result);
        verify(ps, times(1)).executeUpdate();
    }

    @Test
    void testUpdateAddress() throws Exception {
        User user = new User();
        user.setId(1);
        user.setAddress("123 Main St");
        user.setCity("Springfield");
        user.setZip("12345");

        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        boolean result = userDAO.updateAddress(user);
        assertTrue(result);
        verify(ps, times(1)).executeUpdate();
    }

    @Test
    void testCheckUser() throws Exception {
        String email = "john.doe@example.com";

        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);

        boolean result = userDAO.checkUser(email);
        assertFalse(result);
        verify(ps, times(1)).executeQuery();
    }
}
