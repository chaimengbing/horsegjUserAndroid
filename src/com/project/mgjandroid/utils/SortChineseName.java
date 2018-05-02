package com.project.mgjandroid.utils;

import com.project.mgjandroid.bean.Agent;

import java.text.Collator;
import java.util.Comparator;

public class SortChineseName implements Comparator<Agent> {
    Collator cmp = Collator.getInstance(java.util.Locale.CHINA);

    @Override
    public int compare(Agent o1, Agent o2) {
        if (cmp.compare(o1.getSortArea(), o2.getSortArea()) > 0) {
            return 1;
        } else if (cmp.compare(o1.getSortArea(), o2.getSortArea()) < 0) {
            return -1;
        }
        return 0;
    }
}