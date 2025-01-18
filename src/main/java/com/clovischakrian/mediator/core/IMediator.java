package com.clovischakrian.mediator.core;

import java.lang.reflect.InvocationTargetException;

public interface IMediator {
    <TResponse> TResponse send(IRequest<TResponse> request) throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, Exception;
}
