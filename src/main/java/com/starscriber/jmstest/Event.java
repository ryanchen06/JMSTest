
package com.starscriber.jmstest;

/**
 *
 * @author hchen
 */
public class Event implements java.io.Serializable 
{
    protected String from;
    public String getFrom() {return from;}
    
    protected String to;
    public String getTo() {return to;}

    public String getKey() {return from + "_" + to;}
    
    protected final String id;
    public String getId() {return id;}
    
    protected final long time;
    public long getTime() {return time;}

    public Event(String id, long time)
    {
        this.id = id;
        this.time = time;
    }

    public int compareTo(Event other) 
    {
        return (int) (this.getTime() - other.getTime());
    }

    @Override
    public String toString() 
    {
        StringBuilder builder = new StringBuilder();
        builder.append("[Event] id=").append(getId()).append(", time=").append(getTime());
        builder.append(", key=").append(this.getKey());
        return builder.toString();
    }
}
