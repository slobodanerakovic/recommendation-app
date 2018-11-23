package com.recommendation.web;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.recommendation.manager.ProductManager;
import com.recommendation.model.Product;
import com.recommendation.model.enums.Interaction;
import com.recommendation.util.StandardLib;

/**
 * @author Slobodan Erakovic
 */
@Controller
@Path("/api/interaction")
@Consumes("application/json;charset=UTF-8")
@Produces("application/json;charset=UTF-8")
public class InteractionsEndpoint {

	// NOTE: Currently only registered users can use our recommendation
	private static final Logger LOG = LoggerFactory.getLogger(InteractionsEndpoint.class);

	@Autowired
	private ProductManager productManager;

	/**
	 * Make sense that, we can delivered Products with user's mixed interaction
	 * Viewed & Bought, Bough & Commented. I am assuming that user is able to
	 * comment only on the purchased products. Also, View & Bought & Commented
	 * is not needed since, second interaction cover that (Bough & Commented),
	 * since product cannot be bought if it was not first displayed/viewed to
	 * user. Get products which are single interacted by customer viewed,
	 * commented or bought only. All sensitive informations should be encrypted
	 * during communication process, using established algorithm. Here I am
	 * using some custom implementation for customerId encryption
	 *
	 * @param Interaction
	 *            interaction
	 * @param int
	 *            length
	 * @param String
	 *            customerid
	 * @param Cookie
	 *            cookie
	 * @return List<Product>
	 */
	@GET
	@Path("/{interaction}/{customerid}")
	public Response interactedByCustomer(@PathParam("interaction") Interaction interaction,
			@QueryParam("length") int length, @PathParam("customerid") String customerid) {

		long decryptedId = StandardLib.decodeId(customerid);
		LOG.info("customerid={}, decrypted={}, interaction={}", customerid, decryptedId, interaction);

		List<Product> products = productManager.provideProductsByInteraction(decryptedId, interaction, length);
		return Response.ok().entity(products).build();
	}

}
