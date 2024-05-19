import java.util.List;
import java.util.Map;

public class Output {
    private Map<String, List<String>> applied_winning_combinations;
    private String[][] matrix;
    private Double reward = 0D;
    private String applied_bonus_symbol;

    public Map<String, List<String>> getApplied_winning_combinations() {
        return applied_winning_combinations;
    }

    public void setApplied_winning_combinations(Map<String, List<String>> applied_winning_combinations) {
        this.applied_winning_combinations = applied_winning_combinations;
    }

    public String[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(String[][] matrix) {
        this.matrix = matrix;
    }

    public Double getReward() {
        return reward;
    }

    public void setReward(Double reward) {
        this.reward = reward;
    }

    public String getApplied_bonus_symbol() {
        return applied_bonus_symbol;
    }

    public void setApplied_bonus_symbol(String applied_bonus_symbol) {
        this.applied_bonus_symbol = applied_bonus_symbol;
    }
}
