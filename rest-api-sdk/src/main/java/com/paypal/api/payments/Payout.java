package com.paypal.api.payments;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.paypal.base.Constants;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.HttpMethod;
import com.paypal.base.rest.JSONFormatter;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.base.rest.PayPalResource;
import com.paypal.base.rest.RESTUtil;
import com.paypal.base.sdk.info.SDKVersionImpl;

public class Payout {

	/**
	 * The original batch header as provided by the payment sender.
	 */
	private PayoutSenderBatchHeader senderBatchHeader;

	/**
	 * An array of payout items (that is, a set of individual payouts).
	 */
	private List<PayoutItem> items;
	
	/**
	 * 
	 */
	private List<Links> links;

	/**
	 * Returns the last request sent to the Service
	 *
	 * @return Last request sent to the server
	 */
	public static String getLastRequest() {
		return PayPalResource.getLastRequest();
	}

	/**
	 * Returns the last response returned by the Service
	 *
	 * @return Last response got from the Service
	 */
	public static String getLastResponse() {
		return PayPalResource.getLastResponse();
	}

	/**
	 * Initialize using InputStream(of a Properties file)
	 *
	 * @param is
	 *            InputStream
	 * @throws PayPalRESTException
	 * @return OAuthTokenCredential instance using client ID and client secret
	 *         loaded from configuration.
	 */
	public static OAuthTokenCredential initConfig(InputStream is)
			throws PayPalRESTException {
		return PayPalResource.initConfig(is);
	}

	/**
	 * Initialize using a File(Properties file)
	 *
	 * @param file
	 *            File object of a properties entity
	 * @throws PayPalRESTException
	 * @return OAuthTokenCredential instance using client ID and client secret
	 *         loaded from configuration.
	 */
	public static OAuthTokenCredential initConfig(File file)
			throws PayPalRESTException {
		return PayPalResource.initConfig(file);
	}

	/**
	 * Initialize using Properties
	 *
	 * @param properties
	 *            Properties object
	 * @return OAuthTokenCredential instance using client ID and client secret
	 *         loaded from configuration.
	 */
	public static OAuthTokenCredential initConfig(Properties properties) {
		return PayPalResource.initConfig(properties);
	}

	/**
	 * Default Constructor
	 */
	public Payout() {
	}

	/**
	 * Parameterized Constructor
	 */
	public Payout(PayoutSenderBatchHeader senderBatchHeader,
			List<PayoutItem> items) {
		this.senderBatchHeader = senderBatchHeader;
		this.items = items;
	}

	/**
	 * Setter for senderBatchHeader
	 */
	public Payout setSenderBatchHeader(PayoutSenderBatchHeader senderBatchHeader) {
		this.senderBatchHeader = senderBatchHeader;
		return this;
	}

	/**
	 * Getter for senderBatchHeader
	 */
	public PayoutSenderBatchHeader getSenderBatchHeader() {
		return this.senderBatchHeader;
	}

	/**
	 * Setter for items
	 */
	public Payout setItems(List<PayoutItem> items) {
		this.items = items;
		return this;
	}

	/**
	 * Getter for items
	 */
	public List<PayoutItem> getItems() {
		return this.items;
	}
	
	/**
	 * Setter for links
	 */
	public Payout setLinks(List<Links> links) {
		this.links = links;
		return this;
	}

	/**
	 * Getter for links
	 */
	public List<Links> getLinks() {
		return this.links;
	}

	/**
	 * You can submit a payout with a synchronous API call, which immediately returns the results of a PayPal payment.
	 * 
	 * @param accessToken
	 *            Access Token used for the API call.
	 * @return PayoutBatch
	 * @throws PayPalRESTException
	 */
	public PayoutBatch createSynchronous(String accessToken) throws PayPalRESTException {
		APIContext apiContext = new APIContext(accessToken);
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("sync_mode", "true");
		return create(apiContext, parameters);
	}
	
	/**
	 *  You can submit a payout with a synchronous API call, which immediately returns the results of a PayPal payment.
	 * 
	 * @param apiContext
	 *            {@link APIContext} used for the API call.
	 * @param parameters
	 *            Map<String, String>
	 * @return PayoutBatch
	 * @throws PayPalRESTException
	 */
	public PayoutBatch createSynchronous(APIContext apiContext) throws PayPalRESTException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("sync_mode", "true");
		return create(apiContext, parameters);
	}

	/**
	 * Create a payout batch resource by passing a sender_batch_header and an
	 * items array to the request URI. The sender_batch_header contains payout
	 * parameters that describe the handling of a batch resource while the items
	 * array conatins payout items.
	 * 
	 * @param accessToken
	 *            Access Token used for the API call.
	 * @param parameters
	 *            Map<String, String>
	 * @return PayoutBatch
	 * @throws PayPalRESTException
	 */
	public PayoutBatch create(String accessToken, Map<String, String> parameters) throws PayPalRESTException {
		APIContext apiContext = new APIContext(accessToken);
		return create(apiContext, parameters);
	}

	/**
	 * Create a payout batch resource by passing a sender_batch_header and an
	 * items array to the request URI. The sender_batch_header contains payout
	 * parameters that describe the handling of a batch resource while the items
	 * array conatins payout items.
	 * 
	 * @param apiContext
	 *            {@link APIContext} used for the API call.
	 * @param parameters
	 *            Map<String, String>
	 * @return PayoutBatch
	 * @throws PayPalRESTException
	 */
	public PayoutBatch create(APIContext apiContext, Map<String, String> parameters) throws PayPalRESTException {
		if (apiContext == null) {
			throw new IllegalArgumentException("APIContext cannot be null");
		}
		if (apiContext.getAccessToken() == null
				|| apiContext.getAccessToken().trim().length() <= 0) {
			throw new IllegalArgumentException(
					"AccessToken cannot be null or empty");
		}
		if (parameters == null) {
			throw new IllegalArgumentException("parameters cannot be null");
		}
		Object[] parametersObj = new Object[] {parameters};
		if (apiContext.getHTTPHeaders() == null) {
			apiContext.setHTTPHeaders(new HashMap<String, String>());
		}
		apiContext.getHTTPHeaders().put(Constants.HTTP_CONTENT_TYPE_HEADER,
				Constants.HTTP_CONTENT_TYPE_JSON);
		apiContext.setSdkVersion(new SDKVersionImpl());
		String pattern = "v1/payments/payouts?sync_mode={0}";
		String resourcePath = RESTUtil.formatURIPath(pattern, parametersObj);
		String payLoad = this.toJSON();
		return PayPalResource.configureAndExecute(apiContext, HttpMethod.POST,
				resourcePath, payLoad, PayoutBatch.class);
	}

	/**
	 * Obtain the status of a specific batch resource by passing the payout
	 * batch ID to the request URI. You can issue this call multiple times to
	 * get the current status.
	 * 
	 * @param accessToken
	 *            Access Token used for the API call.
	 * @param payoutBatchId
	 *            String
	 * @return PayoutBatch
	 * @throws PayPalRESTException
	 */
	public static PayoutBatch get(String accessToken, String payoutBatchId)
			throws PayPalRESTException {
		APIContext apiContext = new APIContext(accessToken);
		return get(apiContext, payoutBatchId);
	}

	/**
	 * Obtain the status of a specific batch resource by passing the payout
	 * batch ID to the request URI. You can issue this call multiple times to
	 * get the current status.
	 * 
	 * @param apiContext
	 *            {@link APIContext} used for the API call.
	 * @param payoutBatchId
	 *            String
	 * @return PayoutBatch
	 * @throws PayPalRESTException
	 */
	public static PayoutBatch get(APIContext apiContext, String payoutBatchId)
			throws PayPalRESTException {
		if (apiContext == null) {
			throw new IllegalArgumentException("APIContext cannot be null");
		}
		if (apiContext.getAccessToken() == null
				|| apiContext.getAccessToken().trim().length() <= 0) {
			throw new IllegalArgumentException(
					"AccessToken cannot be null or empty");
		}
		if (apiContext.getHTTPHeaders() == null) {
			apiContext.setHTTPHeaders(new HashMap<String, String>());
		}
		apiContext.getHTTPHeaders().put(Constants.HTTP_CONTENT_TYPE_HEADER,
				Constants.HTTP_CONTENT_TYPE_JSON);
		apiContext.setSdkVersion(new SDKVersionImpl());
		if (payoutBatchId == null) {
			throw new IllegalArgumentException("payoutBatchId cannot be null");
		}
		Object[] parameters = new Object[] { payoutBatchId };
		String pattern = "v1/payments/payouts/{0}";
		String resourcePath = RESTUtil.formatURIPath(pattern, parameters);
		String payLoad = "";
		return PayPalResource.configureAndExecute(apiContext, HttpMethod.GET,
				resourcePath, payLoad, PayoutBatch.class);
	}

	/**
	 * Returns a JSON string corresponding to object state
	 *
	 * @return JSON representation
	 */
	public String toJSON() {
		return JSONFormatter.toJSON(this);
	}

	@Override
	public String toString() {
		return toJSON();
	}
}