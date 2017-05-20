package librarySystem.util;

import java.util.HashMap;
import java.util.Map;

public class Result {
    public static Map<String, Object> ok() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "ok");
        return map;
    }

    public static Map<String, Object> error() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "error");
        return map;
    }

    public static Map<String,Object> emailPatternError(){
        Map<String, Object> map = new HashMap<>();
        map.put("message", "emailPatternError");
        return map;
    }

    public static Map<String, Object> emailExistedError() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "emailExistedError");
        return map;
    }
}
