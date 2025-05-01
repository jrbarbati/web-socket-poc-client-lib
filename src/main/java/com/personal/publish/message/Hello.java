package com.personal.publish.message;

public class Hello extends PublishableMessage
{
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static class Builder
    {
        private final Hello message = new Hello();

        public Builder name(String name)
        {
            message.setName(name);
            return this;
        }

        public Hello build()
        {
            return message;
        }
    }
}
