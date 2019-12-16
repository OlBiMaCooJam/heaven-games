package com.olbimacoojam.heaven.service;

import com.olbimacoojam.heaven.domain.User;
import com.olbimacoojam.heaven.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void save() {
        User user = mock(User.class);

        userService.save(user);

        verify(userRepository).save(user);
    }
}