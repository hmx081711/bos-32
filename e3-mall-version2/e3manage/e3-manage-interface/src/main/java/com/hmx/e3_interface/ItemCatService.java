package com.hmx.e3_interface;

import com.hmx.e3_common.pojo.EasyUiTreeNode;

import java.util.List;

public interface ItemCatService {
    List<EasyUiTreeNode> getCatList(long parentId);
}
