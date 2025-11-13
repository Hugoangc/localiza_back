package com.practice.localiza.service;

import com.practice.localiza.auth.LoginRepository;
import com.practice.localiza.dto.ChargeResult;
import com.practice.localiza.dto.PaymentRequestDTO;
import com.practice.localiza.entity.Payment;
import com.practice.localiza.entity.User;
import com.practice.localiza.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository; //

    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private FakeGatewayService fakeGateway;


    @Transactional
    public Payment processPayment(User user, PaymentRequestDTO paymentDetails, Double amount) {

        if (paymentDetails.getSaveCard()) {

            String customerId = user.getPaymentGatewayCustomerId();

            if (customerId == null) {
                customerId = fakeGateway.createGatewayCustomer(user);
                user.setPaymentGatewayCustomerId(customerId);
                loginRepository.save(user);
            } else {
                fakeGateway.updateGatewayCustomer(customerId, paymentDetails);
            }
        }

        ChargeResult chargeResult = fakeGateway.executeCharge(paymentDetails, amount);


        if (chargeResult.isSuccessful()) {
            Payment payment = new Payment();
            payment.setMoment(Instant.now());
            payment.setTransactionId(chargeResult.getTransactionId());
            return paymentRepository.save(payment);
        } else {
            throw new RuntimeException("Pagamento falhou: " + chargeResult.getFailureMessage());
        }
    }
}