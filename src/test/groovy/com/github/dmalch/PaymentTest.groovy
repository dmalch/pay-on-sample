package com.github.dmalch

import org.junit.Test

class PaymentTest {
    @Test
    public void testGenerateToken() throws Exception {
        def generator = new TokenGenerator();

        def token = generator.generateToken()

        generator.executePayment(token)
    }
}
