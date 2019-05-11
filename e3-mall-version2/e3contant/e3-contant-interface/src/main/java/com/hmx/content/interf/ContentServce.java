package com.hmx.content.interf;

import com.hmx.e3_common.pojo.EasyUiDataGridresult;
import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.e3_pojo.TbContent;

import java.util.List;

public interface ContentServce {
    EasyUiDataGridresult showContentList(long categoryId,Integer page, Integer rows);
    TaotaoResult addContent(TbContent tbContent);
    TaotaoResult editContent(TbContent tbContent);
    TaotaoResult deleteContent(long ids);
    List<TbContent> getContentBycid(Long cid);

}
