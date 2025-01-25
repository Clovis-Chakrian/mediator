package com.clovischakrian.mediator.core;
import com.clovischakrian.mediator.exceptions.FailedToHandleRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;


public class Mediator implements IMediator {
    private final IHandlerRegistry handlerRegistry;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public Mediator(IHandlerRegistry handlerRegistry, TransactionTemplate transactionTemplate) {
        this.handlerRegistry = handlerRegistry;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public <TResponse> TResponse send(IRequest<TResponse> request) {
        return transactionTemplate.execute(status -> {
            try {
                @SuppressWarnings("unchecked")
                IRequestHandler<IRequest<TResponse>, TResponse> handler =
                        handlerRegistry.resolve(request.getClass());
                return handler.handle(request);
            } catch (Exception e) {
                throw new FailedToHandleRequestException("Failed to handle request", e);
            }
        });
    }
}