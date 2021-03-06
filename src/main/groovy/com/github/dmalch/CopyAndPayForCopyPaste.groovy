package com.github.dmalch

import groovy.json.JsonSlurper

class CopyAndPayForCopyPaste {

    static String generateToken() {
        def url = "https://test.ctpe.net/frontend/GenerateToken".toURL()
        def connection = url.openConnection()

        connection.setRequestMethod("POST")
        connection.doOutput = true

        String parameters = "SECURITY.SENDER=696a8f0fabffea91517d0eb0a0bf9c33" +
                "&TRANSACTION.CHANNEL=52275ebaf361f20a76b038ba4c806991" +
                "&TRANSACTION.MODE=INTEGRATOR_TEST" +
                "&USER.LOGIN=1143238d620a572a726fe92eede0d1ab" +
                "&USER.PWD=demo" +
                "&PAYMENT.TYPE=DB" +
                "&PRESENTATION.AMOUNT=11.97" +
                "&PRESENTATION.CURRENCY=EUR"

        connection.outputStream << parameters

        def json = new JsonSlurper().parseText(connection.inputStream.text)

        json.transaction.token
    }

    static void executePayment(final String token) {
        def address = "https://test.ctpe.net/frontend/ExecutePayment;jsessionid=${token}"

        def url = address.toURL()
        def connection = url.openConnection()

        connection.setRequestMethod("POST")
        connection.doOutput = true

        String parameters =
            "ACCOUNT.BRAND=VISA" +
                    "&ACCOUNT.NUMBER=4200000000000000" +
                    "&ACCOUNT.EXPIRY_MONTH=7" +
                    "&ACCOUNT.EXPIRY_YEAR=2018" +
                    "&ACCOUNT.HOLDER=Dmitry Malchikov" +
                    "&ACCOUNT.VERIFICATION=333" +
                    "&PAYMENT.METHOD=CC" +
                    "&FRONTEND.RESPONSE_URL=https%3A%2F%2Ftest.ctpe.net%2Ffrontend%2FIntegrationguide%2FCOPYandPAY_Thanks.html" +
                    "&FRONTEND.VERSION=2&FRONTEND.MODE=ASYNC"

        connection.outputStream << parameters
        connection.inputStream.text
    }

    static Boolean isSuccessful(final String token) {
        def address = "https://test.ctpe.net/frontend/GetStatus;jsessionid=${token}"

        def url = address.toURL()
        def connection = url.openConnection()

        connection.setRequestMethod("POST")
        connection.doOutput = true

        def json = new JsonSlurper().parseText(connection.inputStream.text)

        json.transaction.processing.result == "ACK"
    }
}
