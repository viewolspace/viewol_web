package com.viewol.web.schedule.action;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.viewol.pojo.Schedule;
import com.viewol.pojo.ScheduleVO;
import com.viewol.service.IScheduleService;
import com.viewol.web.common.Response;
import com.viewol.web.schedule.vo.RecommendScheduleResponse;
import com.viewol.web.schedule.vo.ScheduleResponse;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.ws.rs.*;
import java.util.ArrayList;
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
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String queryNowHostSchedule() {
        try{
            List<Schedule> scheduleList = scheduleService.queryNowHostSchedule();
            RecommendScheduleResponse rs = new RecommendScheduleResponse();

            if(null == scheduleList || scheduleList.size() == 0){
                rs.setStatus("0000");
                rs.setMessage("查询成功");
                return rs.toJSONString();
            }

            List<RecommendScheduleResponse.ScheduleVO> voList = new ArrayList<>();
            for(Schedule schedule : scheduleList){
                RecommendScheduleResponse.ScheduleVO vo = rs.new ScheduleVO();
                vo.setId(schedule.getId());
                vo.setTitle(schedule.getTitle());
                vo.setCompanyId(schedule.getCompanyId());
                vo.setCompanyName(schedule.getCompanyName());
                vo.setCreateTime(schedule.getsTime());
                voList.add(vo);
            }

            rs.setResult(voList);

            JSONObject.DEFFAULT_DATE_FORMAT="yyyy.MM.dd";//设置日期格式

            return JSONObject.toJSONString(rs, SerializerFeature.WriteMapNullValue,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);

//            return rs.toJSONString();
        } catch (Exception e){
            Response rs = new Response();
            rs.setStatus("0001");
            rs.setMessage("系统异常");
            return rs.toJSONString();
        }

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
            @ApiResponse(code = "0000", message = "请求成功", response = RecommendScheduleResponse.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String queryNowRecommendSchedule(@ApiParam(value = "推荐类型：1-置顶；2-推荐", required = true) @QueryParam("type") int type) {
        if (type != 1 && type != 2) {
            Response rs = new Response();
            rs.setStatus("0002");
            rs.setMessage("参数错误");
            return rs.toJSONString();
        }

        try{
            List<ScheduleVO> scheduleVOList = scheduleService.queryNowRecommendSchedule(type);
            RecommendScheduleResponse rs = new RecommendScheduleResponse();

            if(null == scheduleVOList || scheduleVOList.size() == 0){
                rs.setStatus("0000");
                rs.setMessage("查询成功");
                return rs.toJSONString();
            }

            List<RecommendScheduleResponse.ScheduleVO> voList = new ArrayList<>();
            for(ScheduleVO scheduleVO : scheduleVOList){
                RecommendScheduleResponse.ScheduleVO vo = rs.new ScheduleVO();
                vo.setId(scheduleVO.getId());
                vo.setTitle(scheduleVO.getTitle());
                vo.setCompanyId(scheduleVO.getCompanyId());
                vo.setCompanyName(scheduleVO.getCompanyName());
                vo.setCreateTime(scheduleVO.getsTime());
                voList.add(vo);
            }

            rs.setResult(voList);
            JSONObject.DEFFAULT_DATE_FORMAT="yyyy.MM.dd";//设置日期格式

            return JSONObject.toJSONString(rs, SerializerFeature.WriteMapNullValue,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);

        } catch (Exception e){
            Response rs = new Response();
            rs.setStatus("0001");
            rs.setMessage("系统异常");
            return rs.toJSONString();
        }
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
            @ApiResponse(code = "0000", message = "请求成功", response = RecommendScheduleResponse.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String listSchedule(@ApiParam(value = "格式 yyyy-MM-dd HH:mm:ss") @QueryParam("time") String time,
                               @ApiParam(value = "格式 yyyy-MM-dd") @QueryParam("date") String date,
                               @ApiParam(value = "发布人类型，-1全部;0-主办方；1-展商; ") @QueryParam("type") int type,
                               @ApiParam(value = "关键词（可匹配主题和展商名称）") @QueryParam("keyword") String keyword,
                               @ApiParam(value = "最后一条记录的seq 第一页不需要传", defaultValue = "", required = false) @QueryParam("lastSeq") long lastSeq,
                               @ApiParam(value = "每次返回多少条记录", required = true) @QueryParam("num") int num) {

        List<Schedule> scheduleList = scheduleService.listSchedule(time, date, type, keyword, lastSeq,num);

        try{
            RecommendScheduleResponse rs = new RecommendScheduleResponse();
            rs.setStatus("0000");
            rs.setMessage("查询成功");
            if(null == scheduleList || scheduleList.size() == 0){

                return rs.toJSONString();
            }

            List<RecommendScheduleResponse.ScheduleVO> voList = new ArrayList<>();
            for(Schedule schedule : scheduleList){
                RecommendScheduleResponse.ScheduleVO vo = rs.new ScheduleVO();
                vo.setId(schedule.getId());
                vo.setTitle(schedule.getTitle());
                vo.setCompanyId(schedule.getCompanyId());
                vo.setCompanyName(schedule.getCompanyName());
                vo.setCreateTime(schedule.getsTime());
                vo.setSeq(schedule.getSeq());
                voList.add(vo);
            }

            rs.setResult(voList);
            JSONObject.DEFFAULT_DATE_FORMAT="yyyy.MM.dd";//设置日期格式

            return JSONObject.toJSONString(rs, SerializerFeature.WriteMapNullValue,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);

        } catch (Exception e){
            e.printStackTrace();
            Response rs = new Response();
            rs.setStatus("0001");
            rs.setMessage("系统异常");
            return rs.toJSONString();
        }
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
            @ApiResponse(code = "0000", message = "请求成功", response = ScheduleResponse.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String getSchedule(@ApiParam(value = "活动ID", required = true) @QueryParam("id") int id,
                              @ApiParam(value = "客户ID", required = true) @QueryParam("userId") int userId) {

        try {
            ScheduleVO scheduleVO = scheduleService.getScheduleByUid(id, userId);

            ScheduleResponse rs = new ScheduleResponse();
            if(null == scheduleVO){
                rs.setStatus("0000");
                rs.setMessage("活动不存在");
                return rs.toJSONString();
            }

            ScheduleResponse.ScheduleDetailVO vo = rs.new ScheduleDetailVO();
            vo.setId(scheduleVO.getId());
            vo.setCompanyId(scheduleVO.getCompanyId());
            vo.setCompanyName(scheduleVO.getCompanyName());
            vo.setTitle(scheduleVO.getTitle());
            vo.setCreateTime(scheduleVO.getcTime());
            vo.setType(scheduleVO.getType());
            vo.setStatus(scheduleVO.getStatus());
            vo.setStartTime(scheduleVO.getsTime());
            vo.setEndTime(scheduleVO.geteTime());
            vo.setPlace(scheduleVO.getPlace());
            vo.setVtype(scheduleVO.getvType());
            vo.setSeq(scheduleVO.getSeq());
            vo.setContentView(scheduleVO.getContentView());
            vo.setRecommendSTime(scheduleVO.getRecommendSTime());
            vo.setRecommendETime(scheduleVO.getRecommendETime());
            vo.setApplyStatus(scheduleVO.getApplyStatus());

            rs.setStatus("0000");
            rs.setMessage("查询成功");
            rs.setResult(vo);

            JSONObject.DEFFAULT_DATE_FORMAT="yyyy.MM.dd";//设置日期格式

            return JSONObject.toJSONString(rs, SerializerFeature.WriteMapNullValue,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);


        } catch (Exception e){
            Response rs = new Response();
            rs.setStatus("0001");
            rs.setMessage("系统异常");
            return rs.toJSONString();
        }
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
            @ApiResponse(code = "0000", message = "报名成功", response = Response.class),
            @ApiResponse(code = "0002", message = "报名失败", response = Response.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String applyJoin(@ApiParam(value = "客户ID", required = true) @FormParam("userId") int userId,
                            @ApiParam(value = "活动ID", required = true) @FormParam("scheduleId") int scheduleId,
                            @ApiParam(value = "是否需要提醒", required = true) @FormParam("needReminder") boolean needReminder) {

        Response rs = new Response();

        try{
            int result = scheduleService.applyJoin(userId, scheduleId, needReminder);
            if(result>0){
                rs.setStatus("0000");
                rs.setMessage("报名成功");
            }else {
                rs.setStatus("0002");
                rs.setMessage("报名失败");
            }
        } catch (Exception e) {
            rs.setStatus("0001");
            rs.setMessage("系统异常");

        }
        return rs.toJSONString();
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
            @ApiResponse(code = "0000", message = "申请成功", response = Response.class),
            @ApiResponse(code = "0002", message = "申请失败", response = Response.class),
            @ApiResponse(code = "0001", message = "系统异常", response = Response.class)
    })
    public String applySchedule(@ApiParam(value = "展商ID", required = true) @FormParam("companyId") int companyId,
                                @ApiParam(value = "活动标题", required = true) @FormParam("title") String title,
                                @ApiParam(value = "活动位置", required = true) @FormParam("place") String place,
                                @ApiParam(value = "活动详情", required = true) @FormParam("content") String content,
                                @ApiParam(value = "活动开始时间，格式 yyyy-MM-dd HH:mm:ss", required = true) @FormParam("startTime") String startTime,
                                @ApiParam(value = "活动结束时间，格式 yyyy-MM-dd HH:mm:ss", required = true)  @FormParam("endTime") String endTime) {

        Response rs = new Response();

        try{
            int result = scheduleService.applySchedule(companyId, title, place, content, startTime, endTime);
            if(result>0){
                rs.setStatus("0000");
                rs.setMessage("申请成功");
            }else {
                rs.setStatus("0002");
                rs.setMessage("申请失败");
            }
        } catch (Exception e) {
            rs.setStatus("0001");
            rs.setMessage("系统异常");

        }
        return rs.toJSONString();

    }

}
