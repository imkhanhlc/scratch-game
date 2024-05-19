import java.util.List;
import java.util.Map;

public interface Reward {
    Map<String, List<String>> find_win_combinations(String[][] matrix, Map<String, WinCombination> win_combination_config);
}
