package com.recommendation.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.recommendation.manager.UserManager;
import com.recommendation.model.enums.Status;
import com.recommendation.util.StandardLib;

/**
 * @author Slobodan Erakovic - Requests for web service, concerning product
 *         management MUST be authorized. Here we are doing authorization of the
 *         customer performing such a management, and return appropriate
 *         response, based on the authorization level
 */
@Component
@Aspect
public class ProductsEndpointAuthorization {

	private static final Logger LOG = LoggerFactory.getLogger(ProductsEndpointAuthorization.class);

	@Autowired
	private UserManager userManager;

	@Around("execution(* com.recommendation.web.ProductsEndpoint.*All(*))")
	public Object productAllAdvice(ProceedingJoinPoint joinPoint) {

		Object[] args = joinPoint.getArgs();

		String adminid = (String) args[0];
		long decryptedId = StandardLib.decodeId(adminid);
		LOG.info("productAllAdvice, adminid={}, decryptedId={}", adminid, decryptedId);

		boolean isAuthorized = userManager.authenticateUserAction(decryptedId);

		if (!isAuthorized)
			return Status.AUTHORISATION_FAILED;

		Object response = null;
		try {
			response = joinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return response;
	}

	@Around("execution(* com.recommendation.web.ProductsEndpoint.*ProductId(..))")
	public Object particularProductAdvice(ProceedingJoinPoint joinPoint) {

		Object[] args = joinPoint.getArgs();

		String adminid = (String) args[1];
		long decryptedId = StandardLib.decodeId(adminid);
		LOG.info("particularProductAdvice, adminid={}, decryptedId={}", adminid, decryptedId);

		boolean isAuthorized = userManager.authenticateUserAction(decryptedId);

		if (!isAuthorized)
			return Status.AUTHORISATION_FAILED;

		Object response = null;
		try {
			response = joinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return response;
	}
}
