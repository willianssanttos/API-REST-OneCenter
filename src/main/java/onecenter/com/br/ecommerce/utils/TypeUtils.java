package onecenter.com.br.ecommerce.utils;

import java.util.Map;
import java.util.Optional;

public class TypeUtils {

    /**
     * Tenta converter um Object para Map<String, Object> de forma segura.
     *
     * @param obj o objeto a ser convertido
     * @return Optional contendo o Map se for possível fazer o cast, senão Optional.empty()
     */
    @SuppressWarnings("unchecked")
    public static Optional<Map<String, Object>> safaCastToMap(Object obj){
        if (obj instanceof Map<?,?> map){
            try {
                return Optional.of((Map<String, Object>) map);
            } catch (ClassCastException e){
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}
