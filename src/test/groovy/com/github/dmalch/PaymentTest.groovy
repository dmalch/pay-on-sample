package com.github.dmalch

import org.junit.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.is

class PaymentTest {
    @Test
    public void testGenerateToken() throws Exception {
        def token = CopyAndPay.generateToken(11.97, "EUR")
        println "token: ${token}"

        CopyAndPay.executePayment(token, 4200000000000000, 7, 2014, "Dmitry Malchikov", 333, "https%3A%2F%2Ftest.ctpe.net%2Ffrontend%2FIntegrationguide%2FCOPYandPAY_Thanks.html")

        def status = CopyAndPay.getStatus(token)
        println status

        assertThat isSuccessful(status), is(true)
    }

    public static Boolean isSuccessful(final Object status) {
        status.transaction.processing.result == "ACK"
    }
}
