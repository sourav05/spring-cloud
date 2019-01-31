package com.example.gatewayservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.RoundRobinRule;

public class RibbonConfiguration {

	@Autowired
	private IClientConfig clientConfig;
	
	@Bean
	public IRule ribbonRule(IClientConfig clientConfig){
		return new RoundRobinRule();
	}
	
	@Bean
	public IPing ribbonPing(IClientConfig clientConfig){
		return new PingUrl();
	}

	public IClientConfig getClientConfig() {
		return clientConfig;
	}

	public void setClientConfig(IClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}
}
