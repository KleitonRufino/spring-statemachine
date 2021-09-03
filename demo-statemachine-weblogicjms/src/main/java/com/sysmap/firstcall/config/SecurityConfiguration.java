package com.sysmap.firstcall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.oauth2Client();
		http.authorizeRequests().anyRequest().permitAll();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
				"/configuration/security", "/swagger-ui.html", "/webjars/**", "/h2-console/**", "/admin/**",
				"/actuator/**");
	}

	@Bean
	public ClientRegistrationRepository clientRepository() {
		ClientRegistration c = clientRegistration();
		return new InMemoryClientRegistrationRepository(c);
	}

	private ClientRegistration clientRegistration() {
		return ClientRegistration.withRegistrationId("app").clientId("client1").clientSecret("secret1").scope("read")
				.tokenUri("http://localhost:6060/oauth/token").clientName("app")
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS).build();
	}

	@Bean
	public OAuth2AuthorizedClientManager authorizedClientManager(
			ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientService clientService) {

		OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
				.clientCredentials().build();

		AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(
				clientRegistrationRepository, clientService);
		authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

		return authorizedClientManager;
	}

//	@Bean
//	public OAuth2AuthorizedClientManager authorizedClientManager(
//			ClientRegistrationRepository clientRegistrationRepository,
//			OAuth2AuthorizedClientRepository authorizedClientRepository) {
//
//		OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
//				.clientCredentials().build();
//
//		DefaultOAuth2AuthorizedClientManager authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
//				clientRegistrationRepository, authorizedClientRepository);
//
//		authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
//
//		return authorizedClientManager;
//	}

//	@Bean
//	WebClient webClient(OAuth2AuthorizedClientManager authorizedClientManager)
//	{
//	    ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
//	        new ServletOAuth2AuthorizedClientExchangeFilterFunction(
//	            authorizedClientManager);
//	    oauth2.setDefaultClientRegistrationId("keycloak");
//	    return WebClient.builder().apply(oauth2.oauth2Configuration()).build();
//	}

}
