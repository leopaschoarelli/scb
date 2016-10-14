package br.com.gori.scb.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Id;

public class Persistencia {

    public static Object getId(Object entidade) {
        try {
            Field f = getFieldId(entidade.getClass());
            f.setAccessible(true);
            return f.get(entidade);
        } catch (Exception ex) {
        }
        return null;
    }

    public static Field getFieldId(Class classe) {
        try {
            for (Field f : getAtributos(classe)) {
                if (f.isAnnotationPresent(Id.class)) {
                    return f;
                }
            }
        } catch (Exception ex) {
        }
        return null;
    }

    public static List<Field> getAtributos(Class classe) {
        List<Field> lista = new ArrayList<Field>();
        if (!classe.getSuperclass().equals(Object.class)) {
            lista.addAll(getAtributos(classe.getSuperclass()));
        }
        for (Field f : classe.getDeclaredFields()) {
            if (Modifier.isStatic(f.getModifiers())) {
                continue;
            }
            lista.add(f);
        }
        return lista;
    }

    public static Object getAttributeValue(Object entidade, String attrName) {
        try {
            String methodName = "get" + attrName.substring(0, 1).toUpperCase() + attrName.substring(1);
            return entidade.getClass().getMethod(methodName, new Class[]{}).invoke(entidade, new Object[]{});
        } catch (Exception ex) {
        }
        return null;
    }
}
