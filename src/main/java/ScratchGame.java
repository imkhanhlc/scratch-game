import java.util.*;
import java.util.stream.Collectors;

public class ScratchGame {
    private Config config;
    private String[][] matrix;
    private Map<String, Integer> m_count_symbols;
    private String bonus_symbol;

    private ScratchGame() {
    }

    public ScratchGame(Config config) {
        this.config = config;
        matrix = new String[config.getRows()][config.getColumns()];
        m_count_symbols = new HashMap<>();
        for (Probabilities.SymbolsProbabilities prob_symbols : config.getProbabilities().getStandard_symbols()) {
            if (prob_symbols.getRow() >= config.getRows() || prob_symbols.getColumn() >= config.getColumns()) {
                continue;
            }
            String symbol = generate_symbols(prob_symbols.getSymbols());
            matrix[prob_symbols.getRow()][prob_symbols.getColumn()] = symbol;
            if (m_count_symbols.containsKey(symbol)) {
                m_count_symbols.put(symbol, m_count_symbols.get(symbol) + 1);
            } else {
                m_count_symbols.put(symbol, 1);
            }
        }

        int random_bonus_row = new Random().nextInt(config.getRows());
        int random_bonus_column = new Random().nextInt(config.getColumns());
        String curr_symbols_random_cell = matrix[random_bonus_row][random_bonus_column];
        if (m_count_symbols.get(curr_symbols_random_cell) <= 1) {
            m_count_symbols.remove(curr_symbols_random_cell);
        } else {
            m_count_symbols.put(curr_symbols_random_cell, m_count_symbols.get(curr_symbols_random_cell) - 1);
        }
        bonus_symbol = generate_symbols(config.getProbabilities().getBonus_symbols().getSymbols());
        matrix[random_bonus_row][random_bonus_column] = bonus_symbol;
        m_count_symbols.put(bonus_symbol, 1);
    }

    public Output start(Double bet_amount) {
        Map<String, List<String>> win_combinations = find_win_combinations();
        Output outPut = new Output();
        outPut.setMatrix(matrix);

        Map<String, Symbols> m_symbols_win = config.getSymbols().entrySet().stream()
                .filter(symbol -> win_combinations.containsKey(symbol.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (!m_symbols_win.isEmpty()) {
            double reward = m_symbols_win.entrySet()
                    .stream()
                    .map(symbol -> {
                        double cal = bet_amount * symbol.getValue().getReward_multiplier();
                        List<String> wins = win_combinations.get(symbol.getKey());
                        for (String name : wins) {
                            cal = cal * config.getWin_combinations().get(name).getReward_multiplier();
                        }
                        return cal;
                    }).mapToDouble(Double::doubleValue).sum();

            Symbols bonus = config.getSymbols().get(bonus_symbol);
            if (bonus.getImpact().equals("extra_bonus")) {
                reward += bonus.getExtra();
            } else if (bonus.getImpact().equals("multiply_reward")) {
                reward *= bonus.getReward_multiplier();
            }
            outPut.setApplied_bonus_symbol(bonus_symbol);
            outPut.setReward(reward);
            outPut.setApplied_winning_combinations(win_combinations);
        }
        return outPut;
    }

    private Map<String, List<String>> find_win_combinations() {
        Map<String, List<String>> m_same_symbol_win = new SameSymbolsReward(m_count_symbols)
                .find_win_combinations(matrix, config.getWin_combinations());

        Map<String, List<String>> m_linear_win = new LinearSymbolsReward()
                .find_win_combinations(matrix, config.getWin_combinations());

        Map<String, List<String>> win_combinations = new HashMap<>(m_same_symbol_win);
        m_linear_win.forEach((key, value) -> {
            if (win_combinations.containsKey(key)) {
                win_combinations.get(key).addAll(m_linear_win.get(key));
            } else {
                win_combinations.put(key, value);
            }
        });
        return win_combinations;
    }

    private String generate_symbols(Map<String, Integer> probabilities_symbols) {
        double temp_probability_percentage = 0;
        double total_probability = probabilities_symbols.values().stream().mapToDouble(i -> i).sum();
        Map<String, Double> map_probabilities_percentage = new HashMap<>();
        for (Map.Entry<String, Integer> m : probabilities_symbols.entrySet()) {
            temp_probability_percentage += (m.getValue() / total_probability);
            map_probabilities_percentage.put(m.getKey(), temp_probability_percentage);
        }
        double random_val = new Random().nextDouble();
        Optional<String> oKey = map_probabilities_percentage.entrySet().stream()
                .filter(m -> random_val < m.getValue())
                .findFirst()
                .map(Map.Entry::getKey);
        return oKey.orElse(null);
    }


    public String[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(String[][] matrix) {
        this.matrix = matrix;
    }

    public Map<String, Integer> getM_count_symbols() {
        return m_count_symbols;
    }

    public void setM_count_symbols(Map<String, Integer> m_count_symbols) {
        this.m_count_symbols = m_count_symbols;
    }

    public String getBonus_symbol() {
        return bonus_symbol;
    }

    public void setBonus_symbol(String bonus_symbol) {
        this.bonus_symbol = bonus_symbol;
    }
}
