package com.personal.topic.message;

public class Greeting extends TopicMessage
{
    private String content;

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static class Builder
    {
        private final Greeting greeting = new Greeting();

        public Builder content(String content)
        {
            greeting.setContent(content);
            return this;
        }

        public Greeting build()
        {
            return greeting;
        }
    }
}
