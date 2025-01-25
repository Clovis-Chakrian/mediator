package com.clovischakrian.mediator.core;

import com.clovischakrian.mediator.exceptions.HandlerNotFoundException;

public interface IHandlerResolver {
    <T> T resolve(Class<T> handlerType) throws HandlerNotFoundException;
}