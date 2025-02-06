package com.study.wallet.repositories;

import com.study.wallet.domain.user.User;
import com.study.wallet.domain.user.UserType;
import com.study.wallet.dtos.UserDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get User successfully from DB")
    void findUserByDocumentSuccess() {
        String document = "999999901";
        UserDTO data = new UserDTO("Jamie",
                "Jones",
                document,
                "jamie@email.com",
                "1234",
                UserType.COMMON,
                new BigDecimal(10));

        this.createUser(data);

        Optional<User> result = this.userRepository.findUserByDocument(document);

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get User successfully from DB when user not exists")
    void findUserByDocumentFailed() {
        String document = "999999901";

        Optional<User> result = this.userRepository.findUserByDocument(document);

        assertThat(result.isPresent()).isFalse();
    }

    private User createUser(UserDTO data) {
        User newUser = new User(data);
        this.entityManager.persist((newUser));

        return newUser;
    }
}