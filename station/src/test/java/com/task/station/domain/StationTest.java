package com.task.station.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class StationTest {

    private Station original, originalClone;

    @Before
    public void setUp() {
        original = new Station();
        original.setStationId("test-id");
        original.setHdEnabled(true);
        original.setName("FM 103");
        original.setCallSign("na");

        originalClone = new Station();
        originalClone.setStationId("test-id");
        originalClone.setHdEnabled(true);
        originalClone.setName("FM 103");
        originalClone.setCallSign("na");
    }

    @Test
    public void equalsPass() {
        Assert.assertEquals(original, originalClone);
    }

    @Test
    public void equalsInstanceFailTest() {
        Assert.assertNotEquals(original, "wrong type");
    }

    @Test
    public void equalsIdFailTest() {
        originalClone.setStationId("wrong id");
        Assert.assertNotEquals(original, originalClone);
    }

    @Test
    public void equalsNameFailTest() {
        originalClone.setName("wrong name");
        Assert.assertNotEquals(original, originalClone);
    }

    @Test
    public void equalsCallSignFailTest() {
        originalClone.setCallSign("wrong value");
        Assert.assertNotEquals(original, originalClone);
    }

    @Test
    public void equalsHdEnabledFailTest() {
        originalClone.setHdEnabled(false);
        Assert.assertNotEquals(original, originalClone);
    }

    @Test
    public void hashTest() {
        Assert.assertEquals(original.hashCode(), originalClone.hashCode());
    }
}

