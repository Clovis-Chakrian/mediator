package com.clovischakrian.mediator.spring;

import com.clovischakrian.mediator.core.IHandlerRegistry;
import com.clovischakrian.mediator.core.IRequest;
import com.clovischakrian.mediator.core.IRequestHandler;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public class SpringHandlerRegistry implements IHandlerRegistry {
    private final ApplicationContext context;

    public SpringHandlerRegistry(ApplicationContext context) {
        this.context = context;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <TRequest extends IRequest<TResponse>, TResponse> IRequestHandler<TRequest, TResponse> resolve(
            Class<TRequest> requestType) {
        Map<String, IRequestHandler> handlers = context.getBeansOfType(IRequestHandler.class);

        for (IRequestHandler<?, ?> handler : handlers.values()) {
            Type[] interfaces = handler.getClass().getGenericInterfaces();
            for (Type type : interfaces) {
                if (type instanceof ParameterizedType) {
                    Type[] typeArguments = ((ParameterizedType) type).getActualTypeArguments();
                    if (typeArguments[0].equals(requestType)) {
                        return (IRequestHandler<TRequest, TResponse>) handler;
                    }
                }
            }
        }

        throw new IllegalArgumentException("No handler found for " + requestType.getName());
    }
}