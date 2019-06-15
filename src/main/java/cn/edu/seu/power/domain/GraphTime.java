package cn.edu.seu.power.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by zhh on 2016/7/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GraphTime implements Serializable {
    private GraphParam start;
    private GraphParam end;

    public GraphParam getStart() {
        return start;
    }

    public void setStart(GraphParam start) {
        this.start = start;
    }

    public GraphParam getEnd() {
        return end;
    }

    public void setEnd(GraphParam end) {
        this.end = end;
    }
}
