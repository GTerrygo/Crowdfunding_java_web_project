package com.gtw.crowd.filter;

import com.gtw.crowd.constant.AccessResource;
import com.gtw.crowd.constant.Constant;
import com.gtw.crowd.entity.vo.MemberLoginVO;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Collections;
import java.util.Set;

/**
 * @author
 * @create 2020-11-18-0:52
 */
@Component
public class MyLogFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        Set<URI> uris = exchange.getAttributeOrDefault(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR, Collections.emptySet());
//        String originalUri =uris.isEmpty()?exchange.getRequest().getURI().toString():uris.iterator().next().toString();
//        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
//        URI routeUri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);

        //get request path such as /xx/xxx
        String uri = exchange.getRequest().getURI().getPath();

        //check the path if static or accessed resource
        if(AccessResource.judgeAccess(uri)==true)
            return chain.filter(exchange);

        return exchange.getSession().flatMap(webSession -> {
                //check memberLogin if exists in redis session
                MemberLoginVO gtw =(MemberLoginVO) webSession.getAttribute(Constant.ATTR_NAME_MEMBER);
                if(gtw==null){
                    //redirect login page
                    String redirectUri="http://localhost/auth/member/to/login/page";
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.SEE_OTHER);
                    response.getHeaders().set(HttpHeaders.LOCATION,redirectUri);
                    response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
                    return response.setComplete();
                }
                //allowed to target page
                return chain.filter(exchange);
        });


    }

    @Override
    public int getOrder() {
        return 0;
    }
}
