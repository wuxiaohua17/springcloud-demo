package cn.com.ut.demo.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.ut.demo.entity.Order;

@FeignClient(value = "mycloud-order")
public interface OrderClient {

    @GetMapping(value = "/order/getByOrderId")
    public Order getByOrderId(@RequestParam(value = "orderId") String orderId);

}
