package com.siot;

public class ListItem {

    private String[] mData;

    public ListItem(String[] data){
        mData = data;
    }

    public ListItem(String STORE_NAME, String STORE_ADDRESS){
        mData = new String[2];
        mData[0] = STORE_NAME;
        mData[1] = STORE_ADDRESS;
    }

    public String[] getData(){
        return mData;
    }

    public String getData(int index){
        return mData[index];
    }

    public void setData(String[] data){
        mData = data;
    }

}
