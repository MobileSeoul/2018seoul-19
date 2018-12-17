package com.tensionup.seoul_story.item;

/**
 * A POJO that contains some properties to show in the list
 *
 * @author marvinlabs
 */
public class FavoriteCardData {

    private String m_name;

    public FavoriteCardData(String name) {
        super();
        this.m_name = name;
    }

    public String getName() {
        return m_name;
    }

    public void setName(String name) {
        this.m_name = name;
    }
}
