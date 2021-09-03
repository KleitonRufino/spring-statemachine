package com.sysmap.firstcall.proxy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Component
public class ManagerProxy {

	@Value("${spring.security.oauth2.client.registration.app.client-name}")
	private String clientRegistrationName;
	@Value("${spring.security.oauth2.client.registration.app.client-id}")
	private String clientId;

	private final ResourceProxy resourceProxy;
	private final ManagerToken tokenManager;
	
	public String proxyResourceCallGetMsisdn(Long id) throws Exception{
		String token = this.tokenManager.getBearerToken();
		String s = this.resourceProxy.getMsisdn(id, token);
		return s;
	}
	
}
