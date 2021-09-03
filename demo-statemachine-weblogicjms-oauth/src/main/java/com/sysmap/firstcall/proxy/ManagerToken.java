package com.sysmap.firstcall.proxy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ManagerToken {

  @Value("${spring.security.oauth2.client.registration.app.client-name}")
  private String clientRegistrationName;

  @Value("${spring.security.oauth2.client.registration.app.client-id}")
  private String clientId;

  private final OAuth2AuthorizedClientManager authorizedClientManager;

  public String getBearerToken() {
	  return "Bearer " + getAccessToken();
  }

  private String getAccessToken() {
    OAuth2AuthorizeRequest authorizeRequest =
            OAuth2AuthorizeRequest.withClientRegistrationId(clientRegistrationName)
                .principal(clientId)
                .build();

    OAuth2AuthorizedClient authorizedClient =
            this.authorizedClientManager.authorize(authorizeRequest);

    OAuth2AccessToken accessToken = authorizedClient.getAccessToken();

    return accessToken.getTokenValue();
  }
}
