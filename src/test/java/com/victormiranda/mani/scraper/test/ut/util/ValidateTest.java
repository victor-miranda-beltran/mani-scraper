package com.victormiranda.mani.scraper.test.ut.util;

import com.victormiranda.mani.scraper.util.Validate;
import org.junit.Assert;
import org.junit.Test;

public class ValidateTest {

    @Test
    public void testValidateNotEmpty() {
        Assert.assertTrue(Validate.notEmpty("asd"));
    }

    @Test
    public void testValidateNotEmptyWithEmpty() {
        Assert.assertFalse(Validate.notEmpty(""));
    }

    @Test
    public void testValidateNotEmptyWithNull() {
        String nullStr = null;
        Assert.assertFalse(Validate.notEmpty(nullStr));
    }

    @Test
    public void testValidateMultipleNotEmpty() {
        String str1 = "a";
        String str2 = "b";
        Assert.assertTrue(Validate.notEmpty(str1, str2));
    }

    @Test
    public void testValidateMultipleNotEmptySomeNull() {
        String str1 = "a";
        String str2 = null;
        Assert.assertFalse(Validate.notEmpty(str1, str2));
    }
}
