package br.com.gori.scb.util;

import br.com.gori.scb.dao.AbstractDAO;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author Leonardo
 */
public class ConverterAutoComplete implements Converter, Serializable {

    private AbstractDAO abstractDAO;
    private Class classe;

    public ConverterAutoComplete(Class classe, AbstractDAO abstractDAO) {
        this.abstractDAO = abstractDAO;
        this.classe = classe;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            Object chave = Persistencia.getFieldId(classe).getType().getConstructor(String.class).newInstance(value);
            return abstractDAO.find(classe, chave);
        } catch (Exception ex) {
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        } else {
            if (value instanceof Long) {
                return String.valueOf(value);
            } else {
                try {
                    return Persistencia.getAttributeValue(value, Persistencia.getFieldId(classe).getName()).toString();
                } catch (Exception e) {
                    return String.valueOf(value);
                }
            }
        }
    }
}
