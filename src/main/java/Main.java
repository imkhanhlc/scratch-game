import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String config_str = null;
        double bet_amount = 0D;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--config")) {
                config_str = args[i + 1];
                i++;
            }
            if (args[i].equals("--betting-amount")) {
                bet_amount = Double.parseDouble(args[i + 1]);
                i++;
            }
        }
        if (bet_amount == 0) {
            throw new IllegalArgumentException("The betting-amount argument is required and must be greater than 0");
        }
        if (config_str == null || config_str.isEmpty()) {
            throw new IllegalArgumentException("The config argument is required");
        }

        Config config;
        try (FileReader reader = new FileReader(config_str)) {
            System.out.println("Input: ");
            System.out.println("```");
            System.out.println("     \"bet_amount\": " + bet_amount);
            System.out.println("```");

            Gson gson = new Gson();
            config = gson.fromJson(reader, Config.class);
            validate_config(config);
            ScratchGame scratch_game = new ScratchGame(config);
            Output out_put = scratch_game.start(bet_amount);

            System.out.println("Output: ");
            System.out.println("```");
            System.out.println(gson.toJson(out_put));
            System.out.println("```");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void validate_config(Config config) {
        if (config == null) {
            throw new IllegalArgumentException("Config must not be null");
        }
        int standard_symbol_prob_config_size = config.getProbabilities().getStandard_symbols().size();
        int total_cells_number = config.getRows() * config.getColumns();
        if (standard_symbol_prob_config_size < total_cells_number) {
            throw new RuntimeException("The size of standard symbol probabilities must be greater than or equal to the total number of cells.");
        }
    }
}
