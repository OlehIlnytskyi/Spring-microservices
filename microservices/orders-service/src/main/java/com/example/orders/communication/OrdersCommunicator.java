package com.example.orders.communication;

import com.example.orders.dto.MachineResponseListWrapper;
import com.example.orders.service.discovery.ServiceUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrdersCommunicator {

    @Autowired
    private RestTemplate restTemplate;

    public MachineResponseListWrapper getAllMachinesFromHangar() {
        String url = "lb://" + ServiceUrls.HANGAR_SERVICE + "/getAll";

        return restTemplate.getForEntity(url, MachineResponseListWrapper.class)
                .getBody();
    }

}
