package com.learning.journalApplication.service.testsources.userServiceTestSources;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import com.learning.journalApplication.entity.User;

import java.util.List;
import java.util.stream.Stream;

public class FindUserByUserNameTestSource implements ArgumentsProvider {
    @Override

    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
            Arguments.of(User.builder().userName("john").password("testpassword").roles(List.of("USER")).build(), "john"),
            Arguments.of(User.builder().userName("jane").password("testpassword").roles(List.of("USER")).build(), "jane"),
            Arguments.of(User.builder().userName("johnson").password("testpassword").roles(List.of("USER")).build(), "johnson")
        );
    }
}
