package com.clovischakrian.mediator.core;

import com.clovischakrian.mediator.exceptions.HandlerNotFoundException;

public class Mediator implements IMediator {
    private final IHandlerRegistry handlerRegistry;

    public Mediator(IHandlerRegistry handlerRegistry) {
        this.handlerRegistry = handlerRegistry;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <TResponse> TResponse send(IRequest<TResponse> request) {
        try {
            IRequestHandler<IRequest<TResponse>, TResponse> handler = handlerRegistry.resolveHandler(request.getClass());
            if (handler == null) {
                throw new RuntimeException("No handler found for request type: " + request.getClass().getSimpleName());
            }
            return handler.handle(request);
        } catch (Exception e) {
            throw new RuntimeException("Error handling request: " + request.getClass().getSimpleName(), e);
        }
    }
}