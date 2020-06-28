package org.example.util;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

@Component
@PropertySource("classpath:application.properties")
public class IpUtil {
    private DatabaseReader reader;

    @Autowired
    public IpUtil(@Value("${geo.database.path}") String fileName) throws IOException {
        File file = ResourceUtils.getFile("classpath:" + fileName);
        this.reader = new DatabaseReader.Builder(file).build();
    }

    public String getCountryByIpAddress(InetAddress ipAddress) throws GeoIp2Exception, IOException {
        CountryResponse response = reader.country(ipAddress);
        return response.getCountry().getName();
    }
}
