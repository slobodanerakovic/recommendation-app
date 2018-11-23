package com.recommendation.web.util;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Slobodan Erakovic End point which should be used by monitoring tool,
 *         like icinga, for example, checking the life of the application.
 *         Simply calling: host:port/recommendation-app/ping, will return string
 *         pong, if app is up.
 */
@Configuration
public class HealthConfiguration {

	public static class PingEndpoint extends AbstractEndpoint<String> {

		public PingEndpoint() {
			super("ping", false);
		}

		@Override
		public String invoke() {
			return "pong";
		}
	}

	@Bean
	public PingEndpoint ping() {
		return new PingEndpoint();
	}
}
