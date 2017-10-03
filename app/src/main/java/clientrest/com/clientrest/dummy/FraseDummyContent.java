package clientrest.com.clientrest.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class FraseDummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();


    public static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(Integer.toString(item.id), item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public Integer id;
        public String name;
        public String categoria;
        public String autor;
        public Integer favoritos;
        public Integer nEnvios;

        public DummyItem() {
        }

        public DummyItem(Integer id, String name, String categoria, String autor, Integer favoritos, Integer nEnvios) {
            this.id = id;
            this.name = name;
            this.categoria = categoria;
            this.autor = autor;
            this.favoritos = favoritos;
            this.nEnvios = nEnvios;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCategoria() {
            return categoria;
        }

        public void setCategoria(String categoria) {
            this.categoria = categoria;
        }

        public String getAutor() {
            return autor;
        }

        public void setAutor(String autor) {
            this.autor = autor;
        }

        public Integer getFavoritos() {
            return favoritos;
        }

        public void setFavoritos(Integer favoritos) {
            this.favoritos = favoritos;
        }

        public Integer getnEnvios() {
            return nEnvios;
        }

        public void setnEnvios(Integer nEnvios) {
            this.nEnvios = nEnvios;
        }

        public boolean getFavorito() {
            return this.favoritos == 1 ? true : false;
        }

        public void setFavorito(Boolean f) {
            this.favoritos = f == true ? 1 : 0;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
