package pizzamake.external;

import org.springframework.web.bind.annotation.PathVariable;
import pizzamake.OrderCanceled;
import pizzamake.Made;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@FeignClient(name="make", url="${feign.make.url}")
//@FeignClient(name="payment", , fallback = PaymentServiceFallback.class)

public interface MakeService {

    @RequestMapping(method= RequestMethod.GET, value="/makes/{orderId}")
    public Made cancelMakeService(@PathVariable("orderId") final Long orderId);

}
