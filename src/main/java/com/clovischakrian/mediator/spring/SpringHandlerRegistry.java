package com.clovischakrian.mediator.spring;

import com.clovischakrian.mediator.core.IHandlerRegistry;
import com.clovischakrian.mediator.core.IRequest;
import com.clovischakrian.mediator.core.IRequestHandler;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SpringHandlerRegistry implements IHandlerRegistry {
    private final Map<Class<?>, IRequestHandler<?, ?>> registry = new ConcurrentHashMap<>();

    public void registerHandler(Class<?> requestType, IRequestHandler<?, ?> handler) {
        registry.put(requestType, handler);
    }

    @SuppressWarnings("unchecked")
    public <TRequest extends IRequest<TResponse>, TResponse> IRequestHandler<TRequest, TResponse> getHandler(Class<TRequest> requestType) {
        return (IRequestHandler<TRequest, TResponse>) registry.get(requestType);
    }

    public boolean containsHandler(Class<?> requestType) {
        return registry.containsKey(requestType);
    }
}