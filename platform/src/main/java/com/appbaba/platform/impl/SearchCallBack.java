package com.appbaba.platform.impl;

import java.util.List;

/**
 * Created by ruby on 2016/6/15.
 */
public interface SearchCallBack {
    void Update(String word, String sort, List<String> styleList,List<String> spaceList);
}
