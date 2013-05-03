package com.github.dmalch

import org.junit.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.is

class PaymentTest {
    @Test
    public void testGenerateToken() throws Exception {
        def generator = new CopyAndPay();

        def token = generator.generateToken()

        println "token: ${token}"

        generator.executePayment(token)

        def status = generator.getStatus(token)

        assertThat status, is(true)
    }
}
