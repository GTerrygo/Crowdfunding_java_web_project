package com.gtw.paypal.service;


import com.gtw.paypal.config.PaypalPaymentIntent;
import com.gtw.paypal.config.PaypalPaymentMethod;
import com.gtw.paypal.config.PaypalProperties;
import com.gtw.paypal.config.PaypalUtil;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author
 * @create 2020-11-22-2:33
 */
@Service
public class PaypalService {
    @Autowired
    private PaypalProperties paypalProperties;

    public Payment createPayment(
            Double total,
            String currency,
            PaypalPaymentMethod method,
            PaypalPaymentIntent intent,
            String description,
            String cancelUrl,
            String successUrl) throws PayPalRESTException{
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);
        String orderId="7HT24954DA48"+ UUID.randomUUID().toString().replace("-", "")+"Y";
        transaction.setCustom(orderId);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());

        Payment payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);
        APIContext apiContext = PaypalUtil.creatAPIContext(paypalProperties.getClientId(), paypalProperties.getClientSecret(), paypalProperties.getMode());
        return payment.create(apiContext);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        System.out.println(payerId);
        System.out.println(paymentId);
        APIContext apiContext = PaypalUtil.creatAPIContext(paypalProperties.getClientId(), paypalProperties.getClientSecret(), paypalProperties.getMode());
        return payment.execute(apiContext, paymentExecute);
    }
}
