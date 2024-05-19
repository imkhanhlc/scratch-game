public class WinCombination {
    private Double reward_multiplier;
    private String when;
    private String group;
    private Integer count;
    private String covered_areas[][];

    public Double getReward_multiplier() {
        return reward_multiplier;
    }

    public void setReward_multiplier(Double reward_multiplier) {
        this.reward_multiplier = reward_multiplier;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String[][] getCovered_areas() {
        return covered_areas;
    }

    public void setCovered_areas(String[][] covered_areas) {
        this.covered_areas = covered_areas;
    }
}
