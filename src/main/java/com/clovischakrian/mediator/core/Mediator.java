package com.clovischakrian.mediator.core;

import com.clovischakrian.mediator.exceptions.HandlerNotFoundException;

public class Mediator implements IMediator {
    private final IHandlerRegistry handlerRegistry;

    public Mediator(IHandlerRegistry handlerRegistry) {
        this.handlerRegistry = handlerRegistry;
    }

    @Override
    public <TResponse> TResponse send(IRequest<TResponse> request) {
        try {
            @SuppressWarnings("unchecked")
            IRequestHandler<IRequest<TResponse>, TResponse> handler =
                    handlerRegistry.resolve(request.getClass());
            return handler.handle(request);
        } catch (Exception e) {
            throw new RuntimeException("Failed to handle request", e);
        }
    }
}