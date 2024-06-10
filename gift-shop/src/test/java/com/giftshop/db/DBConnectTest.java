package com.giftshop.db;

import com.giftshop.log.GiftLogger;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DBConnectTest {
    private MockedStatic<DriverManager> mockedDriverManager;
    private MockedStatic<GiftLogger> mockedGiftLogger;

    @BeforeEach
    void setUp() {
        mockedDriverManager = mockStatic(DriverManager.class);
        mockedGiftLogger = mockStatic(GiftLogger.class);
    }

    @AfterEach
    void tearDown() {
        mockedDriverManager.close();
        mockedGiftLogger.close();
    }

    @Test
    void testGetConnSuccess() throws SQLException, ClassNotFoundException {
        Connection mockConnection = mock(Connection.class);
        when(DriverManager.getConnection(anyString(), anyString(), anyString())).thenReturn(mockConnection);

        Connection conn = DBConnect.getConn();

        assertNotNull(conn);
        assertEquals(mockConnection, conn);
        mockedGiftLogger.verify(() -> GiftLogger.logInfo("Database connection established successfully"));
    }

    @Test
    void testGetConnSQLException() throws SQLException {
        when(DriverManager.getConnection(anyString(), anyString(), anyString())).thenThrow(SQLException.class);

        Connection conn = DBConnect.getConn();

        assertNull(conn);
        mockedGiftLogger.verify(() -> GiftLogger.logFatal(anyString()), times(1));
    }
}
