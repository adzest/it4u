package com.java4qa.soap;

import net.webservicex.GeoIP;
import net.webservicex.GeoIPService;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class GeoIpServiceTests {

  @Test
  public void testGetMyIp() {
    GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("77.122.16.24");
    assertEquals(geoIP.getCountryCode(), "UKR");
  }

  @Test
  public void testGetInvalidIp() {
    GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("77.122.16.xxx");
    assertEquals(geoIP.getCountryCode(), null);
  }
}
