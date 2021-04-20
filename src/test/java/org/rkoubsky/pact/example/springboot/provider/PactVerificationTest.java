package org.rkoubsky.pact.example.springboot.provider;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rkoubsky.pact.example.springboot.provider.rest.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;

@ExtendWith(SpringExtension.class)
@Provider("book_provider")
@PactBroker(enablePendingPacts = "true", providerTags = {"master"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:git.properties")
public class PactVerificationTest {

    @Autowired
    private Environment env;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @MockBean
    private BookRepository mockRepository;

    @BeforeAll
    public static void beforeAll(@Autowired Environment env) {
        System.setProperty("pact.verifier.publishResults", "true");
        System.setProperty("pact.provider.tag", env.getProperty("git.branch"));
        System.setProperty("pact.provider.version", "1.0.0");
        System.setProperty("pactbroker.url", env.getProperty("pactbroker.url"));
        System.setProperty("pactbroker.auth.username", env.getProperty("pactbroker.auth.username"));
        System.setProperty("pactbroker.auth.password", env.getProperty("pactbroker.auth.password"));
        System.setProperty("pactbroker.consumerversionselectors.tags", "master");
        System.setProperty("pactbroker.includeWipPactsSince", "2021-04-18");
    }

    @BeforeEach
    public void beforeEach(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", Integer.parseInt(env.getProperty("server.port"))));
    }

    @State("book exists for given isbn")
    public void bookExistsForGivenIsbn() {
        when(mockRepository.getBook(anyString())).thenReturn(BookDto.builder()
                                                                            .title("Designing Data-Intensive Applications")
                                                                            .author("Martin Kleppmann")
                                                                            .isbn("978-1449373320")
                                                                            .published(LocalDate.of(2019, 4, 18))
                                                                            .build());
    }

    @State("book does not exist for given isbn")
    public void bookDoesNotExistForGivenIsbn() {
        when(mockRepository.getBook(anyString())).thenThrow(new NoEntityException("no entity found"));
    }

}
