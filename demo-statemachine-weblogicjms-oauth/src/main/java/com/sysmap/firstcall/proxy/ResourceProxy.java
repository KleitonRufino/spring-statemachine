package com.sysmap.firstcall.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="resource-app", url = "http://localhost:7070")
public interface ResourceProxy {

	@GetMapping(value = "/resource/findMsisdn/{id}")
	public String getMsisdn(@PathVariable Long id, @RequestHeader("Authorization") String authorization);
}
