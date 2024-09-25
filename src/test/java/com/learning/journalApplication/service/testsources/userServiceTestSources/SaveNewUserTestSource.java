package com.learning.journalApplication.service.testsources.userServiceTestSources;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import com.learning.journalApplication.entity.User;

import java.util.List;
import java.util.stream.Stream;

public class SaveNewUserTestSource implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
            Arguments.of(User.builder().userName("john").password("testpassword").build(), "testpassword"),
            Arguments.of(User.builder().userName("jane").password("testpassword").build(), "testpassword"),
            Arguments.of(User.builder().userName("johnson").password("testpassword").build(), "testpassword")
        );
    }
}
