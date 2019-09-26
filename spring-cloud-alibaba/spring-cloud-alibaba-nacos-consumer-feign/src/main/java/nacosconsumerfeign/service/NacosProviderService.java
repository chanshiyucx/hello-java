package nacosconsumerfeign.service;

import nacosconsumerfeign.service.fallback.NacosProviderFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "nacos-provider", fallback = NacosProviderFallback.class)
public interface NacosProviderService {

    @GetMapping("/echo/{message}")
    public String echo(@PathVariable String message);

}
