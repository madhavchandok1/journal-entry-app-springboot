package com.learning.journalApplication.service.testsources.userServiceTestSources;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import com.learning.journalApplication.entity.User;

import java.util.List;
import java.util.stream.Stream;

public class GetAllUsersTestSource implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
            Arguments.of(List.of(
                    User.builder().userName("john").password("testpassword").roles(List.of("USER")).build(),
                    User.builder().userName("jane").password("testpassword").roles(List.of("USER")).build(),
                    User.builder().userName("joseph").password("testpassword").roles(List.of("USER")).build()
            ), 3, 1),
            Arguments.of(List.of(
                    User.builder().userName("admin").password("testpassword").roles(List.of("USER", "ADMIN")).build(),
                    User.builder().userName("guest").password("testpassword").roles(List.of("USER", "ADMIN")).build(),
                    User.builder().userName("administrator").password("testpassword").roles(List.of("USER", "ADMIN")).build()
            ), 3, 2)
        );
    }
}
