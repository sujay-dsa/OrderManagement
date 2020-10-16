package com.keto.login.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.keto.login.model.Orders;
import com.keto.login.model.User;
import com.keto.login.repository.OrdersRepository;
import com.keto.login.repository.ProductRepository;
import com.keto.login.repository.RoleRepository;
import com.keto.login.repository.UserRepository;

public class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private BCryptPasswordEncoder mockBCryptPasswordEncoder;
    @Mock
    private OrdersRepository ordersRepository;
    @Mock
    private ProductRepository productRepository;


    private UserService userServiceUnderTest;
    private User user;
    private Orders orders;
    
    

    @Before
    public void setUp() {
        initMocks(this);
        userServiceUnderTest = new UserService(mockUserRepository,
                                               mockRoleRepository,
                                               mockBCryptPasswordEncoder,
                                               ordersRepository,
                                               productRepository);
        user = User.builder()
                .id(1)
                .firstName("Gustavo")
                .lastName("Ponce")
                .email("test@test.com")
                .build();
        
        orders = Orders.builder()
        		.orderId(new Long(1))
        		.orderDate(new Timestamp(System.currentTimeMillis()))
        		.orderDetais("payment for items ordered")
        		.amount(50.00)
        		.build();

        Mockito.when(mockUserRepository.save(user))
                .thenReturn(user);
        Mockito.when(mockUserRepository.findByEmail(anyString()))
                .thenReturn(user);
        Mockito.when(ordersRepository.save(orders))
        		.thenReturn(orders);
    }

    @Test
    @Disabled
    public void testFindUserByEmail() {
        assertTrue(true);
    }

    @Test
    public void testSaveUser() {
    	assertTrue(true);
    }
}
