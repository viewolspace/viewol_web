package com.viewol.web.schedule.action;

import com.viewol.web.common.Response;
import com.viewol.pojo.Schedule;
import com.viewol.pojo.ScheduleVO;
import com.viewol.web.schedule.vo.RecommendScheduleResponse;
import com.viewol.service.IScheduleService;
import com.youguu.core.util.json.YouguuJsonHelper;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.ws.rs.*;
import java.util.List;

@SwaggerDefinition(
        tags = {
                @Tag(name="v1.0",description="日程")
        }
)
@Api(value = "ScheduleAction")
@Path(value = "schedule")
@Controller("scheduleAction")
public class ScheduleAction {

    @Resource
    private IScheduleService scheduleService;

    /**
     * 查询主办方的活动
     *
     * @return
     */
    @GET
    @Path(value = "/queryNowHostSchedule")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "查询首页主办方活动列表", notes = "查询主办方的活动列表，首页跑马灯使用", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功", response = RecommendScheduleResponse.class),
            @ApiResponse(code = "0013", message = "系统异常", response = Response.class)
    })
    public String queryNowHostSchedule() {
        List<Schedule> scheduleList = scheduleService.queryNowHostSchedule();
        RecommendScheduleResponse rs = new RecommendScheduleResponse();
        rs.setStatus("0000");
        rs.setMessage("查询成功");
        rs.setResult(scheduleList);
        return rs.toJSONString();
    }

    /**
     * 查询展商的活动
     *
     * @param type 1 置顶 , 2 推荐
     * @return
     */
    @GET
    @Path(value = "/queryNowRecommendSchedule")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "查询首页展商活动列表", notes = "版面只显示3条活动，第一条为置顶活动，第二、三条为跑马灯。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功"),

    })
    public String queryNowRecommendSchedule(@QueryParam("type") int type) {

        scheduleService.queryNowRecommendSchedule(type);
        return YouguuJsonHelper.returnJSON("0000", "ok");
    }


    /**
     * 查询日程列表，日程主页调用
     *
     * @param time    格式 yyyy-MM-dd HH:mi:ss
     * @param date    格式 yyyy-MM-dd
     * @param type    0 主办方 1展商
     * @param keyword 关键词（可匹配主题和展商名称）
     * @param num     每次返回多少条
     * @return
     */
    @GET
    @Path(value = "/listSchedule")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "查询日程活动列表", notes = "查询日程列表，支持搜索(搜索条件：时间、发布人类型，关键词)，上拉可以加载更多。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功"),

    })
    public String listSchedule(@QueryParam("time") String time,
                               @QueryParam("date") String date,
                               @QueryParam("type") int type,
                               @QueryParam("keyword") String keyword,
                               @QueryParam("num") int num) {

        scheduleService.listSchedule(time, date, type, keyword, num);
        return YouguuJsonHelper.returnJSON("0000", "ok");
    }

    /**
     * 查询活动详情
     *
     * @param id 活动(日程)ID
     * @return
     */
    @GET
    @Path(value = "/getSchedule")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "查询活动详情", notes = "点击活动列表中某个活动，进入活动详情页面。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功"),

    })
    public String getSchedule(@QueryParam("id") int id,
                              @QueryParam("userId") int userId) {

        ScheduleVO scheduleVO = scheduleService.getScheduleByUid(id, userId);
        return YouguuJsonHelper.returnJSON("0000", "ok");
    }

    /**
     * 客户报名参加活动
     *
     * @param userId       用户ID
     * @param scheduleId   日程ID
     * @param needReminder 是否需要提醒，关注公众号后 活动开始前10分钟会进行提醒
     * @return
     */
    @POST
    @Path(value = "/applyJoin")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "报名参加活动", notes = "客户通过活动详情页面下方的\"报名参加\"按钮，点击报名，可以选择是否开启定时提醒。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功"),

    })
    public String applyJoin(@FormParam("userId") int userId,
                            @FormParam("scheduleId") int scheduleId,
                            @FormParam("needReminder") boolean needReminder) {

        scheduleService.applyJoin(userId, scheduleId, needReminder);
        return YouguuJsonHelper.returnJSON("0000", "ok");
    }

    /**
     * 展商申请发布活动
     * @param companyId
     * @param title
     * @param place
     * @param content
     * @param startTime
     * @param endTime
     * @return
     */
    @POST
    @Path(value = "/applySchedule")
    @Produces("text/html;charset=UTF-8")
    @ApiOperation(value = "展商创建活动接口", notes = "展商通过H5页面，可以申请创建活动，活动默认未审核。", author = "更新于 2018-07-16")
    @ApiResponses(value = {
            @ApiResponse(code = "0000", message = "请求成功"),

    })
    public String applySchedule(@FormParam("companyId") int companyId,
                                @FormParam("title") String title,
                                @FormParam("place") String place,
                                @FormParam("content") String content,
                                @FormParam("startTime") String startTime,
                                @FormParam("endTime") String endTime) {

        scheduleService.applySchedule(companyId, title, place, content, startTime, endTime);
        return YouguuJsonHelper.returnJSON("0000", "ok");
    }

}
