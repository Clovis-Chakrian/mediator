package com.clovischakrian.mediator.core;

public interface IRequestHandler<TRequest extends IRequest<TResponse>, TResponse> {
    public TResponse handle(TRequest request);
}