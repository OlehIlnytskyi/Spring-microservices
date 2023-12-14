package com.example.orders.communication;

import com.example.orders.dto.MachineResponse;
import com.example.orders.dto.MachinesListResponse;
import com.example.orders.service.discovery.ServiceUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class OrdersCommunicatior {

    @Autowired
    private RestTemplate restTemplate;

    public List<MachineResponse> getAllMachines() {
        String url = "lb://" + ServiceUrls.HANGAR_SERVICE + "/getAll";

        return restTemplate.getForEntity(url, MachinesListResponse.class)
                .getBody()
                .getMachineResponses();
    }

}
