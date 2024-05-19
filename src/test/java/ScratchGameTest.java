import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class ScratchGameTest {
    private final Gson gson = new Gson();
    private final Double bet_amount = 100D;

    @Test
    void test_lost_game() {
        Config config = gson.fromJson(config_string, Config.class);
        ScratchGame scratch_game = new ScratchGame(config);
        String matrix_str = "" +
                "[[\"A\",\"B\",\"C\"],\n" +
                "[\"E\",\"B\",\"5x\"],\n" +
                "[\"F\",\"D\",\"C\"]]";
        String[][] matrix_mock = gson.fromJson(matrix_str, String[][].class);
        Map<String, Integer> m_count_symbols_mock = new HashMap<>();
        m_count_symbols_mock.put("A", 1);
        m_count_symbols_mock.put("B", 2);
        m_count_symbols_mock.put("C", 2);
        m_count_symbols_mock.put("D", 1);
        m_count_symbols_mock.put("E", 1);
        m_count_symbols_mock.put("F", 1);
        m_count_symbols_mock.put("5x", 1);

        scratch_game.setMatrix(matrix_mock);
        scratch_game.setM_count_symbols(m_count_symbols_mock);
        scratch_game.setBonus_symbol("5x");
        Output out_put = scratch_game.start(bet_amount);
        assertNull(out_put.getApplied_bonus_symbol());
        assertEquals(0, out_put.getReward());
    }

    @Test
    void test_count_symbol_matrix() {
        Config config = gson.fromJson(config_string, Config.class);
        ScratchGame scratch_game = new ScratchGame(config);
        String[][] matrix = scratch_game.getMatrix();
        Map<String, Integer> count_symbols = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                String symbol = matrix[i][j];
                if (!count_symbols.containsKey(symbol)) {
                    count_symbols.put(symbol, 1);
                } else {
                    count_symbols.put(symbol, count_symbols.get(symbol) + 1);
                }
            }
        }
        Map<String, Integer> a = scratch_game.getM_count_symbols();
        scratch_game.getM_count_symbols().forEach((key, value) -> assertEquals(count_symbols.get(key), value));
        assertDoesNotThrow(() -> scratch_game.start(bet_amount));

    }


    @Test
    void test_all_symbol_E_and_5x_bonus_2_0() {
        Config config = gson.fromJson(config_string, Config.class);
        ScratchGame scratch_game = new ScratchGame(config);
        String matrix_str = "" +
                "[[\"E\",\"E\",\"E\"],\n" +
                "[\"E\",\"E\",\"E\"],\n" +
                "[\"5x\",\"E\",\"E\"]]";
        String[][] matrix_mock = gson.fromJson(matrix_str, String[][].class);
        Map<String, Integer> m_count_symbols_mock = new HashMap<>();
        m_count_symbols_mock.put("E", 8);
        m_count_symbols_mock.put("5x", 1);


        scratch_game.setMatrix(matrix_mock);
        scratch_game.setM_count_symbols(m_count_symbols_mock);
        scratch_game.setBonus_symbol("5x");
        Output out_put = scratch_game.start(bet_amount);
        assertEquals("5x", out_put.getApplied_bonus_symbol());
        assertEquals(300000, out_put.getReward());
        assertEquals(4, out_put.getApplied_winning_combinations().get("E").size());
        assertTrue(out_put.getApplied_winning_combinations().get("E").contains("same_symbol_8_times"));
        assertTrue(out_put.getApplied_winning_combinations().get("E").contains("same_symbols_vertically"));
        assertTrue(out_put.getApplied_winning_combinations().get("E").contains("same_symbols_diagonally_left_to_right"));
        assertTrue(out_put.getApplied_winning_combinations().get("E").contains("same_symbols_horizontally"));
    }

    @Test
    void test_all_symbol_E_and_5x_bonus_0_0() {
        Config config = gson.fromJson(config_string, Config.class);
        ScratchGame scratch_game = new ScratchGame(config);
        String matrix_str = "" +
                "[[\"5x\",\"E\",\"E\"],\n" +
                "[\"E\",\"E\",\"E\"],\n" +
                "[\"E\",\"E\",\"E\"]]";
        String[][] matrix_mock = gson.fromJson(matrix_str, String[][].class);
        Map<String, Integer> m_count_symbols_mock = new HashMap<>();
        m_count_symbols_mock.put("E", 8);
        m_count_symbols_mock.put("5x", 1);


        scratch_game.setMatrix(matrix_mock);
        scratch_game.setM_count_symbols(m_count_symbols_mock);
        scratch_game.setBonus_symbol("5x");
        Output out_put = scratch_game.start(bet_amount);
        assertEquals("5x", out_put.getApplied_bonus_symbol());
        assertEquals(300000, out_put.getReward());
        assertEquals(4, out_put.getApplied_winning_combinations().get("E").size());
        assertTrue(out_put.getApplied_winning_combinations().get("E").contains("same_symbol_8_times"));
        assertTrue(out_put.getApplied_winning_combinations().get("E").contains("same_symbols_vertically"));
        assertTrue(out_put.getApplied_winning_combinations().get("E").contains("same_symbols_diagonally_right_to_left"));
        assertTrue(out_put.getApplied_winning_combinations().get("E").contains("same_symbols_horizontally"));
    }

    @Test
    void test_all_symbol_E_and_5x_bonus_1_1() {
        Config config = gson.fromJson(config_string, Config.class);
        ScratchGame scratch_game = new ScratchGame(config);
        String matrix_str = "" +
                "[[\"E\",\"E\",\"E\"],\n" +
                "[\"E\",\"5x\",\"E\"],\n" +
                "[\"E\",\"E\",\"E\"]]";
        String[][] matrix_mock = gson.fromJson(matrix_str, String[][].class);
        Map<String, Integer> m_count_symbols_mock = new HashMap<>();
        m_count_symbols_mock.put("E", 8);
        m_count_symbols_mock.put("5x", 1);


        scratch_game.setMatrix(matrix_mock);
        scratch_game.setM_count_symbols(m_count_symbols_mock);
        scratch_game.setBonus_symbol("5x");
        Output out_put = scratch_game.start(bet_amount);
        assertEquals("5x", out_put.getApplied_bonus_symbol());
        assertEquals(60000, out_put.getReward());
        assertEquals(3, out_put.getApplied_winning_combinations().get("E").size());
        assertTrue(out_put.getApplied_winning_combinations().get("E").contains("same_symbol_8_times"));
        assertTrue(out_put.getApplied_winning_combinations().get("E").contains("same_symbols_vertically"));
        assertTrue(out_put.getApplied_winning_combinations().get("E").contains("same_symbols_horizontally"));
    }

    @Test
    void test_all_symbol_E_and_plus_1000_bonus() {
        Config config = gson.fromJson(config_string, Config.class);
        ScratchGame scratch_game = new ScratchGame(config);
        String matrix_str = "" +
                "[[\"E\",\"E\",\"E\"],\n" +
                "[\"E\",\"+1000\",\"E\"],\n" +
                "[\"E\",\"E\",\"E\"]]";
        String[][] matrix_mock = gson.fromJson(matrix_str, String[][].class);
        Map<String, Integer> m_count_symbols_mock = new HashMap<>();
        m_count_symbols_mock.put("E", 8);
        m_count_symbols_mock.put("+1000", 1);


        scratch_game.setMatrix(matrix_mock);
        scratch_game.setM_count_symbols(m_count_symbols_mock);
        scratch_game.setBonus_symbol("+1000");
        Output out_put = scratch_game.start(bet_amount);
        assertEquals("+1000", out_put.getApplied_bonus_symbol());
        assertEquals(13000, out_put.getReward());
        assertEquals(3, out_put.getApplied_winning_combinations().get("E").size());
        assertTrue(out_put.getApplied_winning_combinations().get("E").contains("same_symbol_8_times"));
        assertTrue(out_put.getApplied_winning_combinations().get("E").contains("same_symbols_vertically"));
        assertTrue(out_put.getApplied_winning_combinations().get("E").contains("same_symbols_horizontally"));
    }

    @Test
    void test_all_symbol_E_and_MISS_bonus() {
        Config config = gson.fromJson(config_string, Config.class);
        ScratchGame scratch_game = new ScratchGame(config);
        String matrix_str = "" +
                "[[\"E\",\"E\",\"E\"],\n" +
                "[\"E\",\"MISS\",\"E\"],\n" +
                "[\"E\",\"E\",\"E\"]]";
        String[][] matrix_mock = gson.fromJson(matrix_str, String[][].class);
        Map<String, Integer> m_count_symbols_mock = new HashMap<>();
        m_count_symbols_mock.put("E", 8);
        m_count_symbols_mock.put("MISS", 1);


        scratch_game.setMatrix(matrix_mock);
        scratch_game.setM_count_symbols(m_count_symbols_mock);
        scratch_game.setBonus_symbol("MISS");
        Output out_put = scratch_game.start(bet_amount);
        assertEquals("MISS", out_put.getApplied_bonus_symbol());
        assertEquals(12000, out_put.getReward());
        assertEquals(3, out_put.getApplied_winning_combinations().get("E").size());
        assertTrue(out_put.getApplied_winning_combinations().get("E").contains("same_symbol_8_times"));
        assertTrue(out_put.getApplied_winning_combinations().get("E").contains("same_symbols_vertically"));
        assertTrue(out_put.getApplied_winning_combinations().get("E").contains("same_symbols_horizontally"));
    }

    @Test
    void test_multiple_symbol_win() {
        Config config = gson.fromJson(config_string, Config.class);
        ScratchGame scratch_game = new ScratchGame(config);
        String matrix_str = "" +
                "[[\"E\",\"E\",\"B\"],\n" +
                "[\"E\",\"+500\",\"B\"],\n" +
                "[\"E\",\"C\",\"B\"]]";
        String[][] matrix_mock = gson.fromJson(matrix_str, String[][].class);
        Map<String, Integer> m_count_symbols_mock = new HashMap<>();
        m_count_symbols_mock.put("B", 3);
        m_count_symbols_mock.put("C", 1);
        m_count_symbols_mock.put("E", 4);
        m_count_symbols_mock.put("+500", 1);


        scratch_game.setMatrix(matrix_mock);
        scratch_game.setM_count_symbols(m_count_symbols_mock);
        scratch_game.setBonus_symbol("+500");
        Output out_put = scratch_game.start(bet_amount);
        assertEquals("+500", out_put.getApplied_bonus_symbol());
        assertEquals(6400, out_put.getReward());
        assertEquals(2, out_put.getApplied_winning_combinations().get("E").size());
        assertNull(out_put.getApplied_winning_combinations().get("C"));
        assertTrue(out_put.getApplied_winning_combinations().get("B").contains("same_symbol_3_times"));
        assertTrue(out_put.getApplied_winning_combinations().get("B").contains("same_symbols_vertically"));
        assertTrue(out_put.getApplied_winning_combinations().get("E").contains("same_symbol_4_times"));
        assertTrue(out_put.getApplied_winning_combinations().get("E").contains("same_symbols_vertically"));

    }

    @Test
    void test_multiple_symbol_win_diagonally() {
        Config config = gson.fromJson(config_string, Config.class);
        ScratchGame scratch_game = new ScratchGame(config);
        String matrix_str = "" +
                "[[\"E\",\"MISS\",\"B\"],\n" +
                "[\"C\",\"E\",\"B\"],\n" +
                "[\"C\",\"C\",\"E\"]]";
        String[][] matrix_mock = gson.fromJson(matrix_str, String[][].class);
        Map<String, Integer> m_count_symbols_mock = new HashMap<>();
        m_count_symbols_mock.put("B", 2);
        m_count_symbols_mock.put("C", 3);
        m_count_symbols_mock.put("E", 3);
        m_count_symbols_mock.put("MISS", 1);


        scratch_game.setMatrix(matrix_mock);
        scratch_game.setM_count_symbols(m_count_symbols_mock);
        scratch_game.setBonus_symbol("MISS");
        Output out_put = scratch_game.start(bet_amount);
        assertEquals("MISS", out_put.getApplied_bonus_symbol());
        assertEquals(2500, out_put.getReward());
        assertEquals(2, out_put.getApplied_winning_combinations().get("E").size());
        assertEquals(1, out_put.getApplied_winning_combinations().get("C").size());
        assertNull(out_put.getApplied_winning_combinations().get("B"));
        assertTrue(out_put.getApplied_winning_combinations().get("C").contains("same_symbol_3_times"));
        assertTrue(out_put.getApplied_winning_combinations().get("E").contains("same_symbol_3_times"));
        assertTrue(out_put.getApplied_winning_combinations().get("E").contains("same_symbols_diagonally_left_to_right"));

    }

    @Test
    void test_multiple_symbol_win_vertical() {
        Config config = gson.fromJson(config_string, Config.class);
        ScratchGame scratch_game = new ScratchGame(config);
        String matrix_str = "" +
                "[[\"F\",\"10x\",\"D\"],\n" +
                "[\"D\",\"D\",\"D\"],\n" +
                "[\"F\",\"F\",\"F\"]]";
        String[][] matrix_mock = gson.fromJson(matrix_str, String[][].class);
        Map<String, Integer> m_count_symbols_mock = new HashMap<>();
        m_count_symbols_mock.put("D", 4);
        m_count_symbols_mock.put("F", 4);
        m_count_symbols_mock.put("10x", 1);


        scratch_game.setMatrix(matrix_mock);
        scratch_game.setM_count_symbols(m_count_symbols_mock);
        scratch_game.setBonus_symbol("10x");
        Output out_put = scratch_game.start(bet_amount);
        assertEquals("10x", out_put.getApplied_bonus_symbol());
        assertEquals(19500, out_put.getReward());
        assertEquals(2, out_put.getApplied_winning_combinations().get("D").size());
        assertEquals(2, out_put.getApplied_winning_combinations().get("F").size());
        assertTrue(out_put.getApplied_winning_combinations().get("D").contains("same_symbol_4_times"));
        assertTrue(out_put.getApplied_winning_combinations().get("F").contains("same_symbol_4_times"));
        assertTrue(out_put.getApplied_winning_combinations().get("D").contains("same_symbols_horizontally"));
        assertTrue(out_put.getApplied_winning_combinations().get("F").contains("same_symbols_horizontally"));

    }

    @Test
    void test_matrix_axb() {
        Config config = gson.fromJson(config_string_4x3, Config.class);
        ScratchGame scratch_game = new ScratchGame(config);
        String matrix_str = "" +
                "[[\"E\",\"5x\",\"E\"],\n" +
                "[\"E\",\"E\",\"E\"],\n" +
                "[\"E\",\"E\",\"E\"],\n" +
                "[\"E\",\"E\",\"E\"]]";
        String[][] matrix_mock = gson.fromJson(matrix_str, String[][].class);
        Map<String, Integer> m_count_symbols_mock = new HashMap<>();
        m_count_symbols_mock.put("E", 11);
        m_count_symbols_mock.put("5x", 1);
        scratch_game.setMatrix(matrix_mock);
        scratch_game.setM_count_symbols(m_count_symbols_mock);
        scratch_game.setBonus_symbol("5x");
        Output out_put = scratch_game.start(bet_amount);
        assertFalse(out_put.getApplied_winning_combinations().get("E").contains("same_symbols_diagonally_left_to_right")
                || out_put.getApplied_winning_combinations().get("E").contains("same_symbols_diagonally_right_to_left"));
    }


    private final String config_string = "{\n" +
            "  \"columns\": 3,\n" +
            "  \"rows\": 3,\n" +
            "  \"symbols\": {\n" +
            "    \"A\": {\n" +
            "      \"reward_multiplier\": 50,\n" +
            "      \"type\": \"standard\"\n" +
            "    },\n" +
            "    \"B\": {\n" +
            "      \"reward_multiplier\": 25,\n" +
            "      \"type\": \"standard\"\n" +
            "    },\n" +
            "    \"C\": {\n" +
            "      \"reward_multiplier\": 10,\n" +
            "      \"type\": \"standard\"\n" +
            "    },\n" +
            "    \"D\": {\n" +
            "      \"reward_multiplier\": 5,\n" +
            "      \"type\": \"standard\"\n" +
            "    },\n" +
            "    \"E\": {\n" +
            "      \"reward_multiplier\": 3,\n" +
            "      \"type\": \"standard\"\n" +
            "    },\n" +
            "    \"F\": {\n" +
            "      \"reward_multiplier\": 1.5,\n" +
            "      \"type\": \"standard\"\n" +
            "    },\n" +
            "    \"10x\": {\n" +
            "      \"reward_multiplier\": 10,\n" +
            "      \"type\": \"bonus\",\n" +
            "      \"impact\": \"multiply_reward\"\n" +
            "    },\n" +
            "    \"5x\": {\n" +
            "      \"reward_multiplier\": 5,\n" +
            "      \"type\": \"bonus\",\n" +
            "      \"impact\": \"multiply_reward\"\n" +
            "    },\n" +
            "    \"+1000\": {\n" +
            "      \"extra\": 1000,\n" +
            "      \"type\": \"bonus\",\n" +
            "      \"impact\": \"extra_bonus\"\n" +
            "    },\n" +
            "    \"+500\": {\n" +
            "      \"extra\": 500,\n" +
            "      \"type\": \"bonus\",\n" +
            "      \"impact\": \"extra_bonus\"\n" +
            "    },\n" +
            "    \"MISS\": {\n" +
            "      \"type\": \"bonus\",\n" +
            "      \"impact\": \"miss\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"probabilities\": {\n" +
            "    \"standard_symbols\": [\n" +
            "      {\n" +
            "        \"column\": 0,\n" +
            "        \"row\": 0,\n" +
            "        \"symbols\": {\n" +
            "          \"A\": 1,\n" +
            "          \"B\": 2,\n" +
            "          \"C\": 3,\n" +
            "          \"D\": 4,\n" +
            "          \"E\": 5,\n" +
            "          \"F\": 6\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"column\": 0,\n" +
            "        \"row\": 1,\n" +
            "        \"symbols\": {\n" +
            "          \"A\": 1,\n" +
            "          \"B\": 2,\n" +
            "          \"C\": 3,\n" +
            "          \"D\": 4,\n" +
            "          \"E\": 5,\n" +
            "          \"F\": 6\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"column\": 0,\n" +
            "        \"row\": 2,\n" +
            "        \"symbols\": {\n" +
            "          \"A\": 1,\n" +
            "          \"B\": 2,\n" +
            "          \"C\": 3,\n" +
            "          \"D\": 4,\n" +
            "          \"E\": 5,\n" +
            "          \"F\": 6\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"column\": 1,\n" +
            "        \"row\": 0,\n" +
            "        \"symbols\": {\n" +
            "          \"A\": 1,\n" +
            "          \"B\": 2,\n" +
            "          \"C\": 3,\n" +
            "          \"D\": 4,\n" +
            "          \"E\": 5,\n" +
            "          \"F\": 6\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"column\": 1,\n" +
            "        \"row\": 1,\n" +
            "        \"symbols\": {\n" +
            "          \"A\": 1,\n" +
            "          \"B\": 2,\n" +
            "          \"C\": 3,\n" +
            "          \"D\": 4,\n" +
            "          \"E\": 5,\n" +
            "          \"F\": 6\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"column\": 1,\n" +
            "        \"row\": 2,\n" +
            "        \"symbols\": {\n" +
            "          \"A\": 1,\n" +
            "          \"B\": 2,\n" +
            "          \"C\": 3,\n" +
            "          \"D\": 4,\n" +
            "          \"E\": 5,\n" +
            "          \"F\": 6\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"column\": 2,\n" +
            "        \"row\": 0,\n" +
            "        \"symbols\": {\n" +
            "          \"A\": 1,\n" +
            "          \"B\": 2,\n" +
            "          \"C\": 3,\n" +
            "          \"D\": 4,\n" +
            "          \"E\": 5,\n" +
            "          \"F\": 6\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"column\": 2,\n" +
            "        \"row\": 1,\n" +
            "        \"symbols\": {\n" +
            "          \"A\": 1,\n" +
            "          \"B\": 2,\n" +
            "          \"C\": 3,\n" +
            "          \"D\": 4,\n" +
            "          \"E\": 5,\n" +
            "          \"F\": 6\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"column\": 2,\n" +
            "        \"row\": 2,\n" +
            "        \"symbols\": {\n" +
            "          \"A\": 1,\n" +
            "          \"B\": 2,\n" +
            "          \"C\": 3,\n" +
            "          \"D\": 4,\n" +
            "          \"E\": 5,\n" +
            "          \"F\": 6\n" +
            "        }\n" +
            "      }\n" +
            "    ],\n" +
            "    \"bonus_symbols\": {\n" +
            "      \"symbols\": {\n" +
            "        \"10x\": 1,\n" +
            "        \"5x\": 2,\n" +
            "        \"+1000\": 3,\n" +
            "        \"+500\": 4,\n" +
            "        \"MISS\": 5\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  \"win_combinations\": {\n" +
            "    \"same_symbol_3_times\": {\n" +
            "      \"reward_multiplier\": 1,\n" +
            "      \"when\": \"same_symbols\",\n" +
            "      \"count\": 3,\n" +
            "      \"group\": \"same_symbols\"\n" +
            "    },\n" +
            "    \"same_symbol_4_times\": {\n" +
            "      \"reward_multiplier\": 1.5,\n" +
            "      \"when\": \"same_symbols\",\n" +
            "      \"count\": 4,\n" +
            "      \"group\": \"same_symbols\"\n" +
            "    },\n" +
            "    \"same_symbol_5_times\": {\n" +
            "      \"reward_multiplier\": 2,\n" +
            "      \"when\": \"same_symbols\",\n" +
            "      \"count\": 5,\n" +
            "      \"group\": \"same_symbols\"\n" +
            "    },\n" +
            "    \"same_symbol_6_times\": {\n" +
            "      \"reward_multiplier\": 3,\n" +
            "      \"when\": \"same_symbols\",\n" +
            "      \"count\": 6,\n" +
            "      \"group\": \"same_symbols\"\n" +
            "    },\n" +
            "    \"same_symbol_7_times\": {\n" +
            "      \"reward_multiplier\": 5,\n" +
            "      \"when\": \"same_symbols\",\n" +
            "      \"count\": 7,\n" +
            "      \"group\": \"same_symbols\"\n" +
            "    },\n" +
            "    \"same_symbol_8_times\": {\n" +
            "      \"reward_multiplier\": 10,\n" +
            "      \"when\": \"same_symbols\",\n" +
            "      \"count\": 8,\n" +
            "      \"group\": \"same_symbols\"\n" +
            "    },\n" +
            "    \"same_symbol_9_times\": {\n" +
            "      \"reward_multiplier\": 20,\n" +
            "      \"when\": \"same_symbols\",\n" +
            "      \"count\": 9,\n" +
            "      \"group\": \"same_symbols\"\n" +
            "    },\n" +
            "    \"same_symbols_horizontally\": {\n" +
            "      \"reward_multiplier\": 2,\n" +
            "      \"when\": \"linear_symbols\",\n" +
            "      \"group\": \"horizontally_linear_symbols\",\n" +
            "      \"covered_areas\": [\n" +
            "        [\n" +
            "          \"0:0\",\n" +
            "          \"0:1\",\n" +
            "          \"0:2\"\n" +
            "        ],\n" +
            "        [\n" +
            "          \"1:0\",\n" +
            "          \"1:1\",\n" +
            "          \"1:2\"\n" +
            "        ],\n" +
            "        [\n" +
            "          \"2:0\",\n" +
            "          \"2:1\",\n" +
            "          \"2:2\"\n" +
            "        ]\n" +
            "      ]\n" +
            "    },\n" +
            "    \"same_symbols_vertically\": {\n" +
            "      \"reward_multiplier\": 2,\n" +
            "      \"when\": \"linear_symbols\",\n" +
            "      \"group\": \"vertically_linear_symbols\",\n" +
            "      \"covered_areas\": [\n" +
            "        [\n" +
            "          \"0:0\",\n" +
            "          \"1:0\",\n" +
            "          \"2:0\"\n" +
            "        ],\n" +
            "        [\n" +
            "          \"0:1\",\n" +
            "          \"1:1\",\n" +
            "          \"2:1\"\n" +
            "        ],\n" +
            "        [\n" +
            "          \"0:2\",\n" +
            "          \"1:2\",\n" +
            "          \"2:2\"\n" +
            "        ]\n" +
            "      ]\n" +
            "    },\n" +
            "    \"same_symbols_diagonally_left_to_right\": {\n" +
            "      \"reward_multiplier\": 5,\n" +
            "      \"when\": \"linear_symbols\",\n" +
            "      \"group\": \"ltr_diagonally_linear_symbols\",\n" +
            "      \"covered_areas\": [\n" +
            "        [\n" +
            "          \"0:0\",\n" +
            "          \"1:1\",\n" +
            "          \"2:2\"\n" +
            "        ]\n" +
            "      ]\n" +
            "    },\n" +
            "    \"same_symbols_diagonally_right_to_left\": {\n" +
            "      \"reward_multiplier\": 5,\n" +
            "      \"when\": \"linear_symbols\",\n" +
            "      \"group\": \"rtl_diagonally_linear_symbols\",\n" +
            "      \"covered_areas\": [\n" +
            "        [\n" +
            "          \"0:2\",\n" +
            "          \"1:1\",\n" +
            "          \"2:0\"\n" +
            "        ]\n" +
            "      ]\n" +
            "    }\n" +
            "  }\n" +
            "}";

    private final String config_string_4x3 = "{\n" +
            "  \"columns\": 3,\n" +
            "  \"rows\": 4,\n" +
            "  \"symbols\": {\n" +
            "    \"A\": {\n" +
            "      \"reward_multiplier\": 50,\n" +
            "      \"type\": \"standard\"\n" +
            "    },\n" +
            "    \"B\": {\n" +
            "      \"reward_multiplier\": 25,\n" +
            "      \"type\": \"standard\"\n" +
            "    },\n" +
            "    \"C\": {\n" +
            "      \"reward_multiplier\": 10,\n" +
            "      \"type\": \"standard\"\n" +
            "    },\n" +
            "    \"D\": {\n" +
            "      \"reward_multiplier\": 5,\n" +
            "      \"type\": \"standard\"\n" +
            "    },\n" +
            "    \"E\": {\n" +
            "      \"reward_multiplier\": 3,\n" +
            "      \"type\": \"standard\"\n" +
            "    },\n" +
            "    \"F\": {\n" +
            "      \"reward_multiplier\": 1.5,\n" +
            "      \"type\": \"standard\"\n" +
            "    },\n" +
            "    \"10x\": {\n" +
            "      \"reward_multiplier\": 10,\n" +
            "      \"type\": \"bonus\",\n" +
            "      \"impact\": \"multiply_reward\"\n" +
            "    },\n" +
            "    \"5x\": {\n" +
            "      \"reward_multiplier\": 5,\n" +
            "      \"type\": \"bonus\",\n" +
            "      \"impact\": \"multiply_reward\"\n" +
            "    },\n" +
            "    \"+1000\": {\n" +
            "      \"extra\": 1000,\n" +
            "      \"type\": \"bonus\",\n" +
            "      \"impact\": \"extra_bonus\"\n" +
            "    },\n" +
            "    \"+500\": {\n" +
            "      \"extra\": 500,\n" +
            "      \"type\": \"bonus\",\n" +
            "      \"impact\": \"extra_bonus\"\n" +
            "    },\n" +
            "    \"MISS\": {\n" +
            "      \"type\": \"bonus\",\n" +
            "      \"impact\": \"miss\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"probabilities\": {\n" +
            "    \"standard_symbols\": [\n" +
            "      {\n" +
            "        \"column\": 0,\n" +
            "        \"row\": 0,\n" +
            "        \"symbols\": {\n" +
            "          \"A\": 1,\n" +
            "          \"B\": 2,\n" +
            "          \"C\": 3,\n" +
            "          \"D\": 4,\n" +
            "          \"E\": 5,\n" +
            "          \"F\": 6\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"column\": 0,\n" +
            "        \"row\": 1,\n" +
            "        \"symbols\": {\n" +
            "          \"A\": 1,\n" +
            "          \"B\": 2,\n" +
            "          \"C\": 3,\n" +
            "          \"D\": 4,\n" +
            "          \"E\": 5,\n" +
            "          \"F\": 6\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"column\": 0,\n" +
            "        \"row\": 2,\n" +
            "        \"symbols\": {\n" +
            "          \"A\": 1,\n" +
            "          \"B\": 2,\n" +
            "          \"C\": 3,\n" +
            "          \"D\": 4,\n" +
            "          \"E\": 5,\n" +
            "          \"F\": 6\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"column\": 0,\n" +
            "        \"row\": 3,\n" +
            "        \"symbols\": {\n" +
            "          \"A\": 1,\n" +
            "          \"B\": 2,\n" +
            "          \"C\": 3,\n" +
            "          \"D\": 4,\n" +
            "          \"E\": 5,\n" +
            "          \"F\": 6\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"column\": 1,\n" +
            "        \"row\": 0,\n" +
            "        \"symbols\": {\n" +
            "          \"A\": 1,\n" +
            "          \"B\": 2,\n" +
            "          \"C\": 3,\n" +
            "          \"D\": 4,\n" +
            "          \"E\": 5,\n" +
            "          \"F\": 6\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"column\": 1,\n" +
            "        \"row\": 1,\n" +
            "        \"symbols\": {\n" +
            "          \"A\": 1,\n" +
            "          \"B\": 2,\n" +
            "          \"C\": 3,\n" +
            "          \"D\": 4,\n" +
            "          \"E\": 5,\n" +
            "          \"F\": 6\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"column\": 1,\n" +
            "        \"row\": 2,\n" +
            "        \"symbols\": {\n" +
            "          \"A\": 1,\n" +
            "          \"B\": 2,\n" +
            "          \"C\": 3,\n" +
            "          \"D\": 4,\n" +
            "          \"E\": 5,\n" +
            "          \"F\": 6\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"column\": 1,\n" +
            "        \"row\": 3,\n" +
            "        \"symbols\": {\n" +
            "          \"A\": 1,\n" +
            "          \"B\": 2,\n" +
            "          \"C\": 3,\n" +
            "          \"D\": 4,\n" +
            "          \"E\": 5,\n" +
            "          \"F\": 6\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"column\": 2,\n" +
            "        \"row\": 0,\n" +
            "        \"symbols\": {\n" +
            "          \"A\": 1,\n" +
            "          \"B\": 2,\n" +
            "          \"C\": 3,\n" +
            "          \"D\": 4,\n" +
            "          \"E\": 5,\n" +
            "          \"F\": 6\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"column\": 2,\n" +
            "        \"row\": 1,\n" +
            "        \"symbols\": {\n" +
            "          \"A\": 1,\n" +
            "          \"B\": 2,\n" +
            "          \"C\": 3,\n" +
            "          \"D\": 4,\n" +
            "          \"E\": 5,\n" +
            "          \"F\": 6\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"column\": 2,\n" +
            "        \"row\": 2,\n" +
            "        \"symbols\": {\n" +
            "          \"A\": 1,\n" +
            "          \"B\": 2,\n" +
            "          \"C\": 3,\n" +
            "          \"D\": 4,\n" +
            "          \"E\": 5,\n" +
            "          \"F\": 6\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"column\": 2,\n" +
            "        \"row\": 3,\n" +
            "        \"symbols\": {\n" +
            "          \"A\": 1,\n" +
            "          \"B\": 2,\n" +
            "          \"C\": 3,\n" +
            "          \"D\": 4,\n" +
            "          \"E\": 5,\n" +
            "          \"F\": 6\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"column\": 3,\n" +
            "        \"row\": 0,\n" +
            "        \"symbols\": {\n" +
            "          \"A\": 1,\n" +
            "          \"B\": 2,\n" +
            "          \"C\": 3,\n" +
            "          \"D\": 4,\n" +
            "          \"E\": 5,\n" +
            "          \"F\": 6\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"column\": 3,\n" +
            "        \"row\": 1,\n" +
            "        \"symbols\": {\n" +
            "          \"A\": 1,\n" +
            "          \"B\": 2,\n" +
            "          \"C\": 3,\n" +
            "          \"D\": 4,\n" +
            "          \"E\": 5,\n" +
            "          \"F\": 6\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"column\": 3,\n" +
            "        \"row\": 2,\n" +
            "        \"symbols\": {\n" +
            "          \"A\": 1,\n" +
            "          \"B\": 2,\n" +
            "          \"C\": 3,\n" +
            "          \"D\": 4,\n" +
            "          \"E\": 5,\n" +
            "          \"F\": 6\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"column\": 3,\n" +
            "        \"row\": 3,\n" +
            "        \"symbols\": {\n" +
            "          \"A\": 1,\n" +
            "          \"B\": 2,\n" +
            "          \"C\": 3,\n" +
            "          \"D\": 4,\n" +
            "          \"E\": 5,\n" +
            "          \"F\": 6\n" +
            "        }\n" +
            "      }\n" +
            "    ],\n" +
            "    \"bonus_symbols\": {\n" +
            "      \"symbols\": {\n" +
            "        \"10x\": 1,\n" +
            "        \"5x\": 2,\n" +
            "        \"+1000\": 3,\n" +
            "        \"+500\": 4,\n" +
            "        \"MISS\": 5\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  \"win_combinations\": {\n" +
            "    \"same_symbol_3_times\": {\n" +
            "      \"reward_multiplier\": 1,\n" +
            "      \"when\": \"same_symbols\",\n" +
            "      \"count\": 3,\n" +
            "      \"group\": \"same_symbols\"\n" +
            "    },\n" +
            "    \"same_symbol_4_times\": {\n" +
            "      \"reward_multiplier\": 1.5,\n" +
            "      \"when\": \"same_symbols\",\n" +
            "      \"count\": 4,\n" +
            "      \"group\": \"same_symbols\"\n" +
            "    },\n" +
            "    \"same_symbol_5_times\": {\n" +
            "      \"reward_multiplier\": 2,\n" +
            "      \"when\": \"same_symbols\",\n" +
            "      \"count\": 5,\n" +
            "      \"group\": \"same_symbols\"\n" +
            "    },\n" +
            "    \"same_symbol_6_times\": {\n" +
            "      \"reward_multiplier\": 3,\n" +
            "      \"when\": \"same_symbols\",\n" +
            "      \"count\": 6,\n" +
            "      \"group\": \"same_symbols\"\n" +
            "    },\n" +
            "    \"same_symbol_7_times\": {\n" +
            "      \"reward_multiplier\": 5,\n" +
            "      \"when\": \"same_symbols\",\n" +
            "      \"count\": 7,\n" +
            "      \"group\": \"same_symbols\"\n" +
            "    },\n" +
            "    \"same_symbol_8_times\": {\n" +
            "      \"reward_multiplier\": 10,\n" +
            "      \"when\": \"same_symbols\",\n" +
            "      \"count\": 8,\n" +
            "      \"group\": \"same_symbols\"\n" +
            "    },\n" +
            "    \"same_symbol_9_times\": {\n" +
            "      \"reward_multiplier\": 20,\n" +
            "      \"when\": \"same_symbols\",\n" +
            "      \"count\": 9,\n" +
            "      \"group\": \"same_symbols\"\n" +
            "    },\n" +
            "    \"same_symbols_horizontally\": {\n" +
            "      \"reward_multiplier\": 2,\n" +
            "      \"when\": \"linear_symbols\",\n" +
            "      \"group\": \"horizontally_linear_symbols\",\n" +
            "      \"covered_areas\": [\n" +
            "        [\n" +
            "          \"0:0\",\n" +
            "          \"0:1\",\n" +
            "          \"0:2\"\n" +
            "        ],\n" +
            "        [\n" +
            "          \"1:0\",\n" +
            "          \"1:1\",\n" +
            "          \"1:2\"\n" +
            "        ],\n" +
            "        [\n" +
            "          \"2:0\",\n" +
            "          \"2:1\",\n" +
            "          \"2:2\"\n" +
            "        ],\n" +
            "        [\n" +
            "          \"3:0\",\n" +
            "          \"3:1\",\n" +
            "          \"3:2\"\n" +
            "        ]\n" +
            "      ]\n" +
            "    },\n" +
            "    \"same_symbols_vertically\": {\n" +
            "      \"reward_multiplier\": 2,\n" +
            "      \"when\": \"linear_symbols\",\n" +
            "      \"group\": \"vertically_linear_symbols\",\n" +
            "      \"covered_areas\": [\n" +
            "        [\n" +
            "          \"0:0\",\n" +
            "          \"1:0\",\n" +
            "          \"2:0\",\n" +
            "          \"3:0\"\n" +
            "        ],\n" +
            "        [\n" +
            "          \"0:1\",\n" +
            "          \"1:1\",\n" +
            "          \"2:1\",\n" +
            "          \"3:1\"\n" +
            "        ],\n" +
            "        [\n" +
            "          \"0:2\",\n" +
            "          \"1:2\",\n" +
            "          \"2:2\",\n" +
            "          \"3:2\"\n" +
            "        ]\n" +
            "      ]\n" +
            "    },\n" +
            "    \"same_symbols_diagonally_left_to_right\": {\n" +
            "      \"reward_multiplier\": 5,\n" +
            "      \"when\": \"linear_symbols\",\n" +
            "      \"group\": \"ltr_diagonally_linear_symbols\",\n" +
            "      \"covered_areas\": [\n" +
            "        [\n" +
            "          \"0:0\",\n" +
            "          \"1:1\",\n" +
            "          \"2:2\"\n" +
            "        ]\n" +
            "      ]\n" +
            "    },\n" +
            "    \"same_symbols_diagonally_right_to_left\": {\n" +
            "      \"reward_multiplier\": 5,\n" +
            "      \"when\": \"linear_symbols\",\n" +
            "      \"group\": \"rtl_diagonally_linear_symbols\",\n" +
            "      \"covered_areas\": [\n" +
            "        [\n" +
            "          \"0:2\",\n" +
            "          \"1:1\",\n" +
            "          \"2:0\"\n" +
            "        ]\n" +
            "      ]\n" +
            "    }\n" +
            "  }\n" +
            "}";
}
