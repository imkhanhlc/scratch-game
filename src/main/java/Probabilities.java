import java.util.List;
import java.util.Map;

public class Probabilities {
    private List<SymbolsProbabilities> standard_symbols;
    private SymbolsProbabilities bonus_symbols;

    public List<SymbolsProbabilities> getStandard_symbols() {
        return standard_symbols;
    }

    public void setStandard_symbols(List<SymbolsProbabilities> standard_symbols) {
        this.standard_symbols = standard_symbols;
    }

    public SymbolsProbabilities getBonus_symbols() {
        return bonus_symbols;
    }

    public void setBonus_symbols(SymbolsProbabilities bonus_symbols) {
        this.bonus_symbols = bonus_symbols;
    }

    public static class SymbolsProbabilities {
        private Integer column;
        private Integer row;
        private Map<String, Integer> symbols;

        public Integer getColumn() {
            return column;
        }

        public void setColumn(Integer column) {
            this.column = column;
        }

        public Integer getRow() {
            return row;
        }

        public void setRow(Integer row) {
            this.row = row;
        }

        public Map<String, Integer> getSymbols() {
            return symbols;
        }

        public void setSymbols(Map<String, Integer> symbols) {
            this.symbols = symbols;
        }
    }

}
