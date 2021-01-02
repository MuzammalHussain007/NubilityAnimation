package com.example.nubilityanimation.Modal;

public class TopicClass {
    private String tpoicid,TopicName;

    public TopicClass()
    {

    }
    public TopicClass(String tpoicid, String topicName) {
        this.tpoicid = tpoicid;
        TopicName = topicName;
    }

    public String getTpoicid() {
        return tpoicid;
    }

    public void setTpoicid(String tpoicid) {
        this.tpoicid = tpoicid;
    }

    public String getTopicName() {
        return TopicName;
    }

    public void setTopicName(String topicName) {
        TopicName = topicName;
    }
}
