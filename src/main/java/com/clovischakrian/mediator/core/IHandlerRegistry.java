package com.clovischakrian.mediator.core;

public interface IHandlerRegistry {
    public <TRequest extends IRequest<TResponse>, TResponse> IRequestHandler<TRequest, TResponse> resolveHandler(Class<TRequest> requestType);
}
