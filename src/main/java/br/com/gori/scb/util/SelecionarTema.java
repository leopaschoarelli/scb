package br.com.gori.scb.util;

import java.io.Serializable;
import java.util.TreeMap;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Leonardo
 */
@ManagedBean
@SessionScoped
public class SelecionarTema implements Serializable {

    private TreeMap<String, String> temas;
    private PreferenciaTema preferenciaTema = new PreferenciaTema();
    private String tema;

    public SelecionarTema() {
        tema = preferenciaTema.getTema();

        temas = new TreeMap<String, String>();
        temas.put("Afterdark", "afterdark");
        temas.put("Afternoon", "afternoon");
        temas.put("Afterwork", "afterwork");
        temas.put("Aristo", "aristo");
        temas.put("Black-Tie", "black-tie");
        temas.put("Blitzer", "blitzer");
        temas.put("Bluesky", "bluesky");
        temas.put("Bootstrap", "bootstrap");
        temas.put("Casablanca", "casablanca");
        temas.put("Cupertino", "cupertino");
        temas.put("Cruze", "cruze");
        temas.put("Dark-Hive", "dark-hive");
        temas.put("Delta", "delta");
        temas.put("Dot-Luv", "dot-luv");
        temas.put("Eggplant", "eggplant");
        temas.put("Excite-Bike", "excite-bike");
        temas.put("Flick", "flick");
        temas.put("Glass-X", "glass-x");
        temas.put("Home", "home");
        temas.put("Hot-Sneaks", "hot-sneaks");
        temas.put("Humanity", "humanity");
        temas.put("Le-Frog", "le-frog");
        temas.put("Midnight", "midnight");
        temas.put("Mint-Choc", "mint-choc");
        temas.put("Overcast", "overcast");
        temas.put("Pepper-Grinder", "pepper-grinder");
        temas.put("Redmond", "redmond");
        temas.put("Rocket", "rocket");
        temas.put("Sam", "sam");
        temas.put("Smoothness", "smoothness");
        temas.put("South-Street", "south-street");
        temas.put("Start", "start");
        temas.put("Sunny", "sunny");
        temas.put("Swanky-Purse", "swanky-purse");
        temas.put("Trontastic", "trontastic");
        temas.put("UI-Darkness", "ui-darkness");
        temas.put("UI-Lightness", "ui-lightness");
        temas.put("Vader", "vader");
    }

    public TreeMap<String, String> getTemas() {
        return temas;
    }

    public void setTemas(TreeMap<String, String> temas) {
        this.temas = temas;
    }

    public PreferenciaTema getPreferenciaTema() {
        return preferenciaTema;
    }

    public void setPreferenciaTema(PreferenciaTema preferenciaTema) {
        this.preferenciaTema = preferenciaTema;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public void salvarTema() {
        preferenciaTema.setTema(tema);
    }
}
