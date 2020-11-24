package com.gtw.crowd.util;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

import java.util.*;

/**
 * @author
 * @create 2020-11-23-20:50
 */
public class PayPalClient {

    /**
     *
     * @param clientId
     * @param clientSecret
     * @param mode  sandbox or live
     * @return
     * @throws PayPalRESTException
     */
    public static APIContext creatAPIContext(String clientId, String clientSecret, String mode) throws PayPalRESTException {
        //1. set mode
        Map<String, String> sdkConfig = new HashMap<>();
        sdkConfig.put("mode", mode);
        //2.set Credential
        OAuthTokenCredential oAuthTokenCredential = new OAuthTokenCredential(clientId, clientSecret, sdkConfig);
        //3.get apiContext
        APIContext apiContext = new APIContext(oAuthTokenCredential.getAccessToken());
        apiContext.setConfigurationMap(sdkConfig);
        return apiContext;
    }


    /**
     * creat a payment
     * @param total
     * @param currency
     * @param orderId
     * @param method
     * @param intent
     * @param description
     * @param cancelUrl
     * @param successUrl
     * @param clientId
     * @param clientSecret
     * @param mode
     * @return
     * @throws PayPalRESTException
     */
    public static Payment createPayment(
            Double total,
            String currency,
            String orderId,
            String method,
            String intent,
            String description,
            String cancelUrl,
            String successUrl,
            String clientId,
            String clientSecret,
            String mode) throws PayPalRESTException{
        //set total price
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format("%.2f", total));

        /** set detail price
         *  Transaction amount details (subtotal, tax, shipping) must add up to specified amount total
         */
//        Details details = new Details();
//        // item price
//       details.setSubtotal("111");
//        // tex
//        details.setTax("2");
//        details.setShipping("10");
//        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);
        transaction.setCustom(orderId);
//        ItemList itemList = new ItemList();

        /**
         * ship address and phoneNumber
         */
//        ShippingAddress shippingAddress = new ShippingAddress();
//        shippingAddress.setCountryCode("CA");
//        shippingAddress.setState("canada");
//        shippingAddress.setCity("montreal");
//        shippingAddress.setLine1("1272-A");
//        shippingAddress.setPhone("987654321");
//        shippingAddress.setPostalCode("H3H2H8");
//
//        itemList.setShippingAddress(shippingAddress);
//        itemList.setShippingPhoneNumber("123456789");

        /**
         * item details
         */

//        List<Item> items = new ArrayList<>();
//        for (int i=1;i<2;i++) {
//            Item item = new Item();
//            item.setSku("1112223");
//            item.setName("SHOES");
//            item.setPrice("25");
//            item.setQuantity("2");
//            item.setCurrency("USD");
//            items.add(item);
//        }
//        itemList.setItems(items);
//        transaction.setItemList(itemList);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method);

        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);
        APIContext apiContext = creatAPIContext(clientId,clientSecret, mode);
        return payment.create(apiContext);
    }

    public static String pay(
                      Double total,
                      String currency,
                      String orderId,
                      String method,
                      String intent,
                      String description,
                      String cancelUrl,
                      String successUrl,
                      String clientId,
                      String clientSecret,
                      String mode){

        try {
            Payment payment = createPayment(total,currency,orderId,method,intent,description,cancelUrl,successUrl,clientId,clientSecret,mode);
            for(Links links : payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    return "redirect:" + links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
           //set by yourself
        }
        return "redirect:/";//set by yourself
    }


    public static Payment executePayment(String paymentId,
                                         String payerId,
                                         String clientId,
                                         String clientSecret,
                                         String mode) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        APIContext apiContext = creatAPIContext(clientId, clientSecret, mode);
        return payment.execute(apiContext, paymentExecute);
    }





}
