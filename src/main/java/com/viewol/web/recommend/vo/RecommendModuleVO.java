package com.viewol.web.recommend.vo;

import com.viewol.web.common.Response;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by lenovo on 2018/7/18.
 */
public class RecommendModuleVO extends Response {

    @ApiModelProperty(value = "推荐的列表")
    private List<RecommendVO> result;

    public List<RecommendVO> getResult() {
        return result;
    }

    public void setResult(List<RecommendVO> result) {
        this.result = result;
    }
}
