package com.prowo.drools.dynamic1.template;

import org.drools.template.DataProvider;

import java.util.Iterator;
import java.util.List;

/**
 * Created by linan on 2017/8/15.
 */
public class MyDataProvider implements DataProvider {

    private Iterator<String[]> iterator;

    public MyDataProvider(List<String[]> rows) {
        this.iterator = rows.iterator();
    }

    public boolean hasNext() {
        return false;
    }

    public String[] next() {
        return new String[0];
    }
}
