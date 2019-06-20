package com.myframe.test.pojo;

public class AggUserBean {
    private Long maxId;
    private Long minId;
    private Integer avgAge;

    public Long getMaxId() {
        return maxId;
    }

    public void setMaxId(Long maxId) {
        this.maxId = maxId;
    }

    public Long getMinId() {
        return minId;
    }

    public void setMinId(Long minId) {
        this.minId = minId;
    }

    public Integer getAvgAge() {
        return avgAge;
    }

    public void setAvgAge(Integer avgAge) {
        this.avgAge = avgAge;
    }

    @Override
    public String toString() {
        return "AggUserBean{" +
                "maxId=" + maxId +
                ", minId=" + minId +
                ", avgAge=" + avgAge +
                '}';
    }
}
