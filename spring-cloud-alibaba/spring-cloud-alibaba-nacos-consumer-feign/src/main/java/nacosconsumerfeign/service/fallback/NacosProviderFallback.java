package nacosconsumerfeign.service.fallback;

import nacosconsumerfeign.service.NacosProviderService;
import org.springframework.stereotype.Component;

/**
 * @author shiyu
 * @date 2019/9/25 16:42
 * @Description
 */
@Component
public class NacosProviderFallback implements NacosProviderService {

    @Override
    public String echo(String message) {
        return "请求失败！";
    }
}
