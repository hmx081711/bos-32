package com.hmx.content.interf;

import com.hmx.e3_common.pojo.EasyUiTreeNode;

import java.util.List;

public interface ContentCatgory {
    List<EasyUiTreeNode> getCategoryCatList(long parentId);
}
