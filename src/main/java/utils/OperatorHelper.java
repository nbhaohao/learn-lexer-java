package utils;

import java.util.HashMap;
import java.util.Map;

public class OperatorHelper {
    public static class OperatorConfig {
        private final int code;
        private final Character[] combineOperators;

        public OperatorConfig(int code, Character[] combineOperators) {
            this.code = code;
            this.combineOperators = combineOperators;
        }

        public int getCode() {
            return code;
        }

        public Character[] getCombineOperators() {
            return combineOperators;
        }
    }

    public static Map<Character, OperatorConfig> operatorConfigMap = initOperatorConfigMap();

    private static Map<Character, OperatorConfig> initOperatorConfigMap() {
        Map<Character, OperatorConfig> map = new HashMap<>();
        map.put('+', new OperatorConfig(1, new Character[]{'+', '='}));
        map.put('-', new OperatorConfig(2, new Character[]{'-', '='}));
        map.put('*', new OperatorConfig(3, new Character[]{'='}));
        map.put('/', new OperatorConfig(4, new Character[]{'='}));
        map.put('>', new OperatorConfig(5, new Character[]{'=', '>'}));
        map.put('<', new OperatorConfig(6, new Character[]{'=', '<'}));
        map.put('=', new OperatorConfig(7, new Character[]{'='}));
        map.put('!', new OperatorConfig(8, new Character[]{'='}));
        map.put('&', new OperatorConfig(9, new Character[]{'=', '&'}));
        map.put('|', new OperatorConfig(10, new Character[]{'=', '|'}));
        map.put('^', new OperatorConfig(11, new Character[]{'=', '^'}));
        map.put('%', new OperatorConfig(11, new Character[]{'=', '%'}));
        return map;
    }
}
