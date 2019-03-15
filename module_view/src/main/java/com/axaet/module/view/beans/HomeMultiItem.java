package com.axaet.module.view.beans;


import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * date: 2019/1/19
 *
 * @author yuShu
 */
public class HomeMultiItem implements MultiItemEntity {

    /**
     * 单个item
     */
    public static final int VIEW_TYPE_SINGLE_ITEM = 0x11;
    /**
     * viewpager
     */
    public static final int VIEW_TYPE_VIEWPAGER = 0x12;
    /**
     * 多个item
     */
    public static final int VIEW_TYPE_MULTIPLE_ITEM = 0x13;
    /**
     * 標題
     */
    public static final int VIEW_TYPE_TITLE = 0x14;
    /**
     * 两个item
     */
    public static final int VIEW_TYPE_TWO_ITEM = 0x15;

    /**
     * 类型
     */
    private int itemType;

    /**
     * title
     */
    private String title;

    /**
     * object
     */
    private Object object;

    public HomeMultiItem(int itemType, String title, Object object) {
        this.itemType = itemType;
        this.title = title;
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    @Override
    public String toString() {
        return "HomeMultiItem{" +
                "itemType=" + itemType +
                ", title='" + title + '\'' +
                ", object=" + object +
                '}';
    }
}
