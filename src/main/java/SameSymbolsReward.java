import java.util.*;

public class SameSymbolsReward implements Reward {
    private Map<String, Integer> map_count;

    private SameSymbolsReward() {
    }

    public SameSymbolsReward(Map<String, Integer> map_count) {
        this.map_count = map_count;
    }

    @Override
    public Map<String, List<String>> find_win_combinations(String[][] matrix, Map<String, WinCombination> win_combination_config) {
        Map<String, List<String>> result = new HashMap<>();
        win_combination_config.entrySet().stream().filter(w -> w.getValue().getWhen().equals("same_symbols"))
                .forEach(entry -> {
                    for (Map.Entry<String, Integer> m_count : map_count.entrySet()) {
                        if (Objects.equals(m_count.getValue(), entry.getValue().getCount())) {
                            List<String> win = new ArrayList<>();
                            win.add(entry.getKey());
                            result.put(m_count.getKey(), win);
                        }
                    }
                });
        return result;
    }
}