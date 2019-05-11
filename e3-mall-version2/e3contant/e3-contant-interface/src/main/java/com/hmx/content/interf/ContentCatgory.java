package com.hmx.content.interf;

import com.hmx.e3_common.pojo.EasyUiTreeNode;
import com.hmx.e3_common.pojo.TaotaoResult;

import java.util.List;

public interface ContentCatgory {
    List<EasyUiTreeNode> getCategoryCatList(long parentId);
    TaotaoResult addCategory(long parentId,String name);
    TaotaoResult deleteCategory(long id);
    TaotaoResult renameCategory(long parentId,String name);
}
