package au.edu.qut.processmining.log.graph;

/**
 * Created by Adriano on 15/06/2016.
 */
public class EventNode implements Comparable {
    protected String id;
    protected String label;

    protected int frequency;
    protected int startFrequency;
    protected int endFrequency;

    public EventNode() {
        id = Long.toString(System.currentTimeMillis());
        label = "null";
        frequency = 0;
        startFrequency = 0;
        endFrequency = 0;
    }

    public EventNode(String label) {
        id = Long.toString(System.currentTimeMillis());
        frequency = 0;
        startFrequency = 0;
        endFrequency = 0;
        this.label = label;
    }

    public EventNode(int frequency) {
        id = Long.toString(System.currentTimeMillis());
        startFrequency = 0;
        endFrequency = 0;
        label = "null";
        this.frequency = frequency;
    }

    public EventNode(String label, int frequency) {
        id = Long.toString(System.currentTimeMillis());
        startFrequency = 0;
        endFrequency = 0;
        this.label = label;
        this.frequency = frequency;
    }

    public String getID() { return id; }

    public void setLabel(String label) { this.label = label; }
    public String getLabel() { return label; }

    public void increaseFrequency() { frequency++; }
    public void increaseFrequency(int amount) { frequency += amount; }

    public int getFrequency(){ return frequency; }

    public void incStartFrequency() { startFrequency++; }
    public void incEndFrequency() { endFrequency++; }

    public int getStartFrequency(){ return startFrequency;}
    public int getEndFrequency(){ return endFrequency;}

    public boolean isStartEvent() { return startFrequency != 0; }
    public boolean isEndEvent() { return endFrequency != 0; }

    @Override
    public int compareTo(Object o) {
        if( o instanceof EventNode) return id.compareTo(((EventNode)o).getID());
        else return -1;
    }

    @Override
    public boolean equals(Object o) {
        if( o instanceof EventNode) return id.equals(((EventNode)o).getID());
        else return false;
    }

}