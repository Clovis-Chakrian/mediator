package com.clovischakrian.mediator.spring;

import com.clovischakrian.mediator.core.IHandlerResolver;
import com.clovischakrian.mediator.core.IRequestHandler;
import com.clovischakrian.mediator.exceptions.HandlerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

@Component
public class SpringHandlerResolver implements IHandlerResolver {
    private final ApplicationContext context;
    private final SpringHandlerRegistry handlerRegistry;

    @Autowired
    public SpringHandlerResolver(ApplicationContext context, SpringHandlerRegistry handlerRegistry) {
        this.context = context;
        this.handlerRegistry = handlerRegistry;
        registerAllHandlers();
    }

    private void registerAllHandlers() {
        Map<String, IRequestHandler> handlers = context.getBeansOfType(IRequestHandler.class);
        for (IRequestHandler<?, ?> handler : handlers.values()) {
            Class<?> requestType = getRequestType(handler);
//            handlerRegistry.registerHandler(requestType, handler);
        }
    }

    private Class<?> getRequestType(IRequestHandler<?, ?> handler) {
        return (Class<?>) ((ParameterizedType) handler.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }

    @Override
    public <T> T resolve(Class<T> handlerType) {
        return context.getBean(handlerType);
    }
}