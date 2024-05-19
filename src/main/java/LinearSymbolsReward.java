import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinearSymbolsReward implements Reward {
    @Override
    public Map<String, List<String>> find_win_combinations(String[][] matrix, Map<String, WinCombination> win_combination_config) {
        Map<String, List<String>> map_result = new HashMap<>();
        win_combination_config.entrySet().stream()
                .filter(w -> w.getValue().getWhen().equals("linear_symbols"))
                .forEach(entry -> {
                            WinCombination linear_symbols = entry.getValue();
                            if (linear_symbols.getGroup().equals("ltr_diagonally_linear_symbols")
                                    || linear_symbols.getGroup().equals("rtl_diagonally_linear_symbols")) {
                                if (matrix.length != matrix[0].length) {
                                    return;
                                }
                            }
                            String[][] covered_areas = linear_symbols.getCovered_areas();
                            for (String[] covered_area : covered_areas) {
                                String temp_symbols = "";
                                boolean is_covered = true;
                                for (String s : covered_area) {
                                    String[] row_column = s.split(":");
                                    int row = Integer.parseInt(row_column[0]);
                                    int column = Integer.parseInt(row_column[1]);
                                    if (row >= matrix.length || column >= matrix[0].length) {
                                        is_covered = false;
                                        break;
                                    }
                                    String symbols = matrix[row][column];
                                    if (temp_symbols.isEmpty()) {
                                        temp_symbols = symbols;
                                    } else if (!symbols.equals(temp_symbols)) {
                                        is_covered = false;
                                        break;
                                    }
                                }
                                if (is_covered) {
                                    if (!map_result.containsKey(temp_symbols)) {
                                        List<String> win_combinations = new ArrayList<>();
                                        win_combinations.add(entry.getKey());
                                        map_result.put(temp_symbols, win_combinations);
                                    } else {
                                        if (!map_result.get(temp_symbols).contains(entry.getKey())) {
                                            map_result.get(temp_symbols).add(entry.getKey());
                                        }
                                    }
                                }
                            }

                        }
                );
        return map_result;
    }
}
