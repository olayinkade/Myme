package com.nitrogen.myme.tests.Business;

import com.nitrogen.myme.business.AccessTags;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccessTagsTest {
    private AccessTags accessTags;

    @Before
    public void setUp() {
        System.out.println("Starting tests for AccessTags...");
        accessTags = new AccessTags();
        assertNotNull(accessTags);
    }

    /* Method: getTags() */

    @Test
    public void testGetTags() {
        // Retrieve all tags in the database
        System.out.println("...Testing getTags()");
        assertTrue(accessTags.getTags().size() >= 0);
    }

    @After
    public void tearDown() {
        System.out.println("\nFinished tests.\n");
    }
}
