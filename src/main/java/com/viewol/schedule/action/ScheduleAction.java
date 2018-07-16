package com.viewol.schedule.action;

import com.viewol.service.IScheduleService;
import com.youguu.core.util.json.YouguuJsonHelper;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.ws.rs.*;

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
    public String queryNowHostSchedule() {

        scheduleService.queryNowHostSchedule();
        return YouguuJsonHelper.returnJSON("0000", "ok");
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
    public String getSchedule(@QueryParam("id") int id) {

        scheduleService.getSchedule(id);
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
