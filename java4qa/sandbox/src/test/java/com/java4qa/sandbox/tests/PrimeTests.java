package com.java4qa.sandbox.tests;

import com.java4qa.sandbox.Primes;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PrimeTests {

    @Test
    public void testPrime() {
        Assert.assertTrue(Primes.isPrime(Integer.MAX_VALUE));
    }

    @Test
    public void testNonPrime() {
        Assert.assertFalse(Primes.isPrime(Integer.MAX_VALUE - 2));
    }

    @Test(enabled = false)
    public void testPrimeWhile() {
        Assert.assertTrue(Primes.isPrimeWhile(Integer.MAX_VALUE));
    }

    @Test(enabled = false)
    public void testNonPrimeWhile() {
        Assert.assertFalse(Primes.isPrimeWhile(Integer.MAX_VALUE - 2));
    }

    @Test(enabled = false)
    public void testPrimeLong() {
        long n = Integer.MAX_VALUE;
        Assert.assertTrue(Primes.isPrime(n));
    }

    @Test
    public void testPrimeFast() {
        Assert.assertTrue(Primes.isPrimeFast(Integer.MAX_VALUE));
    }

    @Test
    public void testPrimeSuperFast() {
        Assert.assertTrue(Primes.isPrimeSuperFast(Integer.MAX_VALUE));
    }
}
