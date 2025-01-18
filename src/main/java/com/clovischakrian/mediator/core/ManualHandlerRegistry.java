package com.clovischakrian.mediator.core;

import com.clovischakrian.mediator.exceptions.HandlerNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class ManualHandlerRegistry implements IHandlerRegistry {
    private final Map<Class<?>, IRequestHandler<?, ?>> handlers = new HashMap<>();

    public <TRequest extends IRequest<TResponse>, TResponse> void register(
            Class<TRequest> requestType, IRequestHandler<TRequest, TResponse> handler) {
        handlers.put(requestType, handler);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <TRequest extends IRequest<TResponse>, TResponse> IRequestHandler<TRequest, TResponse> resolve(
            Class<TRequest> requestType) {
        IRequestHandler<?, ?> handler = handlers.get(requestType);
        if (handler == null) {
            throw new HandlerNotFoundException("No handler registered for " + requestType.getName());
        }
        return (IRequestHandler<TRequest, TResponse>) handler;
    }
}
