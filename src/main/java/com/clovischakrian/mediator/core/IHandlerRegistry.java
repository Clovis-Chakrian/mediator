package com.clovischakrian.mediator.core;

public interface IHandlerRegistry {
    <TRequest extends IRequest<TResponse>, TResponse> IRequestHandler<TRequest, TResponse> resolve(Class<TRequest> requestType);
}
