package com.recommendation.web;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.recommendation.manager.ProductManager;
import com.recommendation.model.Product;
import com.recommendation.model.Product.ProductStatus;
import com.recommendation.model.enums.Status;
import com.recommendation.repository.ProductRepository;

import jersey.repackaged.com.google.common.base.Preconditions;

/**
 * @author Slobodan Erakovic - Admin used ws
 */
@Controller
@Path("/api/products")
@Consumes("application/json;charset=UTF-8")
@Produces("application/json;charset=UTF-8")
public class ProductsEndpoint {

	private static final Logger LOG = LoggerFactory.getLogger(ProductsEndpoint.class);

	@Autowired
	private ProductManager productManager;

	/**
	 * Every customerId must be encrypted with some encryption algorithm, during
	 * HTTP communication
	 * 
	 * @param long
	 *            productid
	 * @return Status
	 */
	@GET
	@Path("/restore/{productid}")
	public Status restoreProductId(@PathParam("productid") Long productid, @QueryParam("adminid") String adminid) {
		Preconditions.checkNotNull(productid);
		LOG.info("Restoring productid={}", productid);

		Status status = productManager.changeProductAvailibility(productid, ProductStatus.ACTIVE);
		LOG.info("Restoring product={} went={}", productid, status);

		return status;
	}

	/**
	 * @return Status
	 */
	@GET
	@Path("/restore/all")
	public Status restoreAll(@QueryParam("adminid") String adminid) {
		LOG.info("Restoring all products");

		Status status = productManager.changeProductAvailibility(null, ProductStatus.ACTIVE);
		LOG.info("Restoring all products, went={}", status);

		return status;
	}

	/**
	 * 
	 * @param long
	 *            productid
	 * @return Status
	 */
	@GET
	@Path("/block/{productid}")
	public Status blockProductId(@PathParam("productid") Long productid, @QueryParam("adminid") String adminid) {
		Preconditions.checkNotNull(productid);
		LOG.info("Blocking productid={}", productid);

		Status status = productManager.changeProductAvailibility(productid, ProductStatus.INACTIVE);
		LOG.info("Blocking productid went={}", productid, status);

		return status;
	}

	/**
	 * @return Status
	 */
	@GET
	@Path("/block/all")
	public Status blockAll(@QueryParam("adminid") String adminid) {
		LOG.info("Restoring all products");

		Status status = productManager.changeProductAvailibility(null, ProductStatus.INACTIVE);
		LOG.info("Restoring all products, went={}", status);

		return status;
	}
}
