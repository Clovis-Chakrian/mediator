package com.clovischakrian.mediator.core;

public interface IHandlerRegistry {
    void registerHandler(Class<?> requestType, IRequestHandler<?, ?> handler);

    @SuppressWarnings("unchecked")
    <TRequest extends IRequest<TResponse>, TResponse> IRequestHandler<TRequest, TResponse> getHandler(Class<TRequest> requestType);

    boolean containsHandler(Class<?> requestType);
}
