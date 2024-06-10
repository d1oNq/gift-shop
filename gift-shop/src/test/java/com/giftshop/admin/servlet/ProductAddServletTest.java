package com.giftshop.admin.servlet;

import com.giftshop.dao.ProductDAOImpl;
import org.junit.jupiter.api.*;
import org.mockito.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.http.Part;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProductAddServletTest {

    @InjectMocks
    private ProductAddServlet servlet;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private ProductDAOImpl productDAO;
    @Mock
    private Part part;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private ServletContext servletContext;

    @BeforeEach
    void setUp() throws ServletException {
        MockitoAnnotations.openMocks(this);
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        servlet.init(servletConfig);
    }

    @Test
    void testDoPostSuccess() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn("Test Product");
        when(request.getParameter("category")).thenReturn("Test Category");
        when(request.getParameter("weight")).thenReturn("10");
        when(request.getParameter("price")).thenReturn("50");
        when(request.getParameter("status")).thenReturn("Active");
        when(request.getPart("image")).thenReturn(part);
        when(part.getSubmittedFileName()).thenReturn("test_image.jpg");
        when(productDAO.addProduct(any())).thenReturn(true);
        when(request.getSession()).thenReturn(session);
        when(servletContext.getRealPath(anyString())).thenReturn("test/path");

        servlet.doPost(request, response);

        verify(session).setAttribute("successMsg", "Товар успішно добавлений");
        verify(response).sendRedirect("admin/add_product.jsp");
    }

    @Test
    void testDoPostFailure() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn("Test Product");
        when(request.getParameter("category")).thenReturn("Test Category");
        when(request.getParameter("weight")).thenReturn("10");
        when(request.getParameter("price")).thenReturn("50");
        when(request.getParameter("status")).thenReturn("Active");
        when(request.getPart("image")).thenReturn(part);
        when(part.getSubmittedFileName()).thenReturn("test_image.jpg");
        when(productDAO.addProduct(any())).thenReturn(false);
        when(request.getSession()).thenReturn(session);
        when(servletContext.getRealPath(anyString())).thenReturn("test/path");

        servlet.doPost(request, response);

        verify(session).setAttribute("failedMsg", "Щось пішло не так");
        verify(response).sendRedirect("admin/add_product.jsp");
    }

    @Test
    void testDoPostException() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn("Test Product");
        when(request.getParameter("category")).thenReturn("Test Category");
        when(request.getParameter("weight")).thenReturn("10");
        when(request.getParameter("price")).thenReturn("50");
        when(request.getParameter("status")).thenReturn("Active");
        when(request.getPart("image")).thenReturn(part);
        when(part.getSubmittedFileName()).thenReturn("test_image.jpg");
        when(productDAO.addProduct(any())).thenThrow(new RuntimeException("Database connection error"));
        when(request.getSession()).thenReturn(session);

        Assertions.assertThrows(ServletException.class, () -> {
            servlet.doPost(request, response);
        });

        verify(session, never()).setAttribute(anyString(), anyString());
    }
}
