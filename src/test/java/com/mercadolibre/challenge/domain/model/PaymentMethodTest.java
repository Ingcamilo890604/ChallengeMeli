package com.mercadolibre.challenge.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentMethodTest {

    @Test
    void testPaymentMethodBuilder() {
        String id = "pm-001";
        String name = "Credit Card";
        String description = "Visa, Mastercard, American Express";

        PaymentMethod paymentMethod = PaymentMethod.builder()
                .id(id)
                .name(name)
                .description(description)
                .build();

        assertEquals(id, paymentMethod.getId());
        assertEquals(name, paymentMethod.getName());
        assertEquals(description, paymentMethod.getDescription());
    }

    @Test
    void testGettersAndSetters() {
        PaymentMethod paymentMethod = new PaymentMethod();
        String id = "pm-002";
        String name = "PayPal";
        String description = "Online payment";

        paymentMethod.setId(id);
        paymentMethod.setName(name);
        paymentMethod.setDescription(description);

        assertEquals(id, paymentMethod.getId());
        assertEquals(name, paymentMethod.getName());
        assertEquals(description, paymentMethod.getDescription());
    }

    @Test
    void testEqualsAndHashCode() {
        PaymentMethod paymentMethod1 = PaymentMethod.builder()
                .id("pm-001")
                .name("Credit Card")
                .description("Visa, Mastercard")
                .build();

        PaymentMethod paymentMethod2 = PaymentMethod.builder()
                .id("pm-001")
                .name("Credit Card")
                .description("Visa, Mastercard")
                .build();

        PaymentMethod paymentMethod3 = PaymentMethod.builder()
                .id("pm-002")
                .name("PayPal")
                .description("Online payment")
                .build();

        assertEquals(paymentMethod1, paymentMethod2);
        assertEquals(paymentMethod1.hashCode(), paymentMethod2.hashCode());
        assertNotEquals(paymentMethod1, paymentMethod3);
        assertNotEquals(paymentMethod1.hashCode(), paymentMethod3.hashCode());
    }

    @Test
    void testToString() {
        PaymentMethod paymentMethod = PaymentMethod.builder()
                .id("pm-001")
                .name("Credit Card")
                .build();

        String toString = paymentMethod.toString();

        assertTrue(toString.contains("id=pm-001"));
        assertTrue(toString.contains("name=Credit Card"));
    }
}