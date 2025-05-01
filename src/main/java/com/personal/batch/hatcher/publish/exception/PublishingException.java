package com.personal.batch.hatcher.publish.exception;

public class PublishingException extends Exception
{
    public PublishingException(String message)
    {
        this(message, null);
    }

    public PublishingException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
