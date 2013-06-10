
package com.starscriber.jmstest;

/**
 *
 * @author hchen
 */
public class Response extends Event implements java.io.Serializable
{
    public static enum Type
    {
        CALL,
        TEXT;
    }
    
    /**
     * Text-back duration is -1.
     */
    private long duration = -1;
    public long getDuration(){return duration;}
   
    private final Type type;
    public Type getType(){return type;}

    /**
     * TEXT Constructor
     *
     * @param id
     * @param time
     * @param type
     */
    public Response(String id, long time, Type type, String from, String to)
    {
        super(id, time);
        this.type = type;
        this.from = from;
        this.to = to;
    }

    /**
     * CALL Constructor
     *
     * @param id unique id
     * @param time
     * @param type
     */
    public Response(String id, long time, Type type, String from, String to, long duration)
    {
        super(id, time);
        this.type = type;
        this.from = from;
        this.to = to;
        this.duration = duration;
    }

    /**
     * Get the response key
     *
     * @return pattern: to_from
     */
    @Override public String getKey() {return to + "_" + from;}

    @Override 
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        builder.append(this.getId()).append("_");
        builder.append(this.getTime()).append("_");
        builder.append(this.type).append("_");
        builder.append(this.getKey()).append("_");
        builder.append(this.getDuration());

        return builder.toString();
    }
}
