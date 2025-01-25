package com.clovischakrian.mediator.spring;

import com.clovischakrian.mediator.core.IHandlerRegistry;
import com.clovischakrian.mediator.core.IRequest;
import com.clovischakrian.mediator.core.IRequestHandler;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpringHandlerRegistry implements IHandlerRegistry {
    private final ApplicationContext applicationContext;
    private final Map<Class<?>, IRequestHandler<?, ?>> handlerCache = new ConcurrentHashMap<>();

    public SpringHandlerRegistry(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public <TRequest extends IRequest<TResponse>, TResponse> IRequestHandler<TRequest, TResponse> resolveHandler(Class<TRequest> requestType) {
        if (handlerCache.containsKey(requestType)) {
            return (IRequestHandler<TRequest, TResponse>) handlerCache.get(requestType);
        }

        Map<String, IRequestHandler> beansOfType = applicationContext.getBeansOfType(IRequestHandler.class);
        for (IRequestHandler<?, ?> handler : beansOfType.values()) {
            Class<?> handlerRequestType = getRequestTypeFromHandler(handler);
            if (handlerRequestType != null && handlerRequestType.equals(requestType)) {
                handlerCache.put(requestType, handler);
                return (IRequestHandler<TRequest, TResponse>) handler;
            }
        }

        throw new IllegalStateException("No handler found for request type: " + requestType.getName());
    }

    private Class<?> getRequestTypeFromHandler(IRequestHandler<?, ?> handler) {
        Type[] genericInterfaces = handler.getClass().getGenericInterfaces();
        for (Type type : genericInterfaces) {
            if (type instanceof ParameterizedType parameterizedType) {
                if (IRequestHandler.class.equals(parameterizedType.getRawType())) {
                    return (Class<?>) parameterizedType.getActualTypeArguments()[0];
                }
            }
        }
        return null;
    }
}