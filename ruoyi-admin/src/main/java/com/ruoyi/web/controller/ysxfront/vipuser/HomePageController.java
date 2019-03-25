package com.ruoyi.web.controller.ysxfront.vipuser;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReUtil;
import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.utils.RegexUtils;
import com.ruoyi.yishengxin.domain.*;
import com.ruoyi.yishengxin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页相关的接口
 */
@RestController
@RequestMapping("/front/homePage")
public class HomePageController {

    @Autowired
    private IRotationService rotationService;
    @Autowired
    private IRollMessageService rollMessageService;
    @Autowired
    private INewsService newsService;
    @Autowired
    private IProjectService projectService;
    @Autowired
    private INoticeService noticeService;
    @Autowired
    private ITradeExplainService tradeExplainService;

    /**
     * 轮播图
     * @return
     */
    @GetMapping("/rotation")
    public ResponseResult rotation(){
        Rotation rotation = new Rotation();
        rotation.setIsShow(CustomerConstants.YES);
        List<Rotation> rotations = rotationService.selectRotationList(rotation);

        List list = new ArrayList();
        if(rotations.size() > 0){
            rotations.stream().forEach(rotation1 -> {
                Map map = new HashMap();
                map.put("address",rotation1.getPicDetail());
                map.put("link",rotation1.getPicLink());
                list.add(map);
            });
        }
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);
    }

    /**
     * 滚动消息
     * @return
     */
    @GetMapping("/rollMsg")
    public ResponseResult rollMsg(){

        RollMessage rollMessage = new RollMessage();
        rollMessage.setIsShow(CustomerConstants.YES);
        List<RollMessage> rollMessages = rollMessageService.selectRollMessageList(rollMessage);

        List list = new ArrayList();
        if(rollMessages.size() > 0){
            rollMessages.stream().forEach(rollMessage1 -> {
                Map map = new HashMap();
                map.put("id",rollMessage1.getId());
                map.put("content",rollMessage1.getRollContent());
                list.add(map);
            });
        }
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);
    }

    /**
     * 新闻资讯
     * @param currentNum  当前页数
     * @param pageSize      每页显示的记录数
     * @return
     */
    @GetMapping("/news")
    public ResponseResult news(@RequestParam("currentNum") String currentNum,@RequestParam(value = "pageSize",required = false) String pageSize){

        if(!ReUtil.isMatch(RegexUtils.INTEGER_REGEX,currentNum) ){
            //默认初始页为0
            currentNum = "0";
        }

        if(!ReUtil.isMatch(RegexUtils.INTEGER_REGEX,pageSize)) {
            pageSize = CustomerConstants.PAGE_SIZE;
        }

        //当前页初始值
        int beginNumber = (Integer.parseInt(currentNum)-1) * Integer.parseInt(pageSize);
        int endNumber =  beginNumber + Integer.parseInt(pageSize);

        News news2 = new News();
        news2.getParams().put("News"," order by news_time desc limit "+beginNumber+"," + (endNumber-1) );
        List<News> news = newsService.selectNewsList(news2);
        List list = new ArrayList();
        if(news.size() > 0){
            news.stream().forEach(news1 -> {
                Map map = new HashMap();
                map.put("id",news1.getId());
                map.put("introduction",news1.getNewsIntroduction());
                map.put("pic",news1.getNewsPic());
                map.put("time",news1.getNewsTime());
                map.put("title",news1.getNewsTitle());
                list.add(map);
            });
        }

        Map map = new HashMap();
        int total = newsService.selectTotal();  //查询记录数
        map.put("total",total);
        map.put("pageSize",pageSize);

        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list,map);
    }


    /**
     * 新闻详情
     * @param id
     * @return
     */
    @PostMapping("/newsDetail")
    public ResponseResult newsDetail(@RequestParam("id") String id){

        News news = newsService.selectNewsById(Integer.parseInt(id));

        Map map = new HashMap();
        map.put("id",news.getId());
        map.put("introduction",news.getNewsIntroduction());
        map.put("pic",news.getNewsPic());
        map.put("time",news.getNewsTime());
        map.put("title",news.getNewsTitle());
        map.put("content",news.getNewsContent());
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
    }

    /**
     * 项目展示
     * @param currentNum  当前页数
     * @param pageSize      每页显示的记录数
     * @return
     */
    @GetMapping("/project")
    public ResponseResult project(@RequestParam(value = "currentNum",defaultValue = "1") String currentNum,@RequestParam(value = "pageSize",required = false,defaultValue = CustomerConstants.PAGE_SIZE) String pageSize){

        //当前页初始值
        int beginNumber = (Integer.parseInt(currentNum)-1) * Integer.parseInt(pageSize);
        int endNumber =  beginNumber + Integer.parseInt(pageSize);

        Project project1 = new Project();
        project1.getParams().put("Project"," order by project_time desc limit "+beginNumber+"," + (endNumber-1)  );
        List<Project> projects = projectService.selectProjectList(project1);

        List list = new ArrayList();
        if(projects.size() > 0){
            projects.stream().forEach(project -> {
                Map map = new HashMap();
                map.put("id",project.getId());
                map.put("introduction",project.getProjectIntroduction());
                map.put("pic",project.getProjectPic());
                map.put("time",project.getProjectTime());
                map.put("title",project.getProjectTitle());
                list.add(map);
            });
        }
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);
    }

    /**
     * 项目详情
     * @param id
     * @return
     */
    @PostMapping("/projectDetail")
    public ResponseResult projectDetail(@RequestParam("id") String id){

        Project project = projectService.selectProjectById(Integer.parseInt(id));
        Map map = new HashMap();
        map.put("id",project.getId());
        map.put("introduction",project.getProjectIntroduction());
        map.put("pic",project.getProjectPic());
        map.put("time",project.getProjectTime());
        map.put("title",project.getProjectTitle());
        map.put("content",project.getProjectContent());
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
    }

    /**
     * 公告中心
     * @param currentNum  当前页数
     * @param pageSize      每页显示的记录数
     * @return
     */
    @GetMapping("/notice")
    public ResponseResult notice(@RequestParam("currentNum") String currentNum,@RequestParam(value = "pageSize",required = false) String pageSize){

        if(!ReUtil.isMatch(RegexUtils.INTEGER_REGEX,currentNum) ){
            //默认初始页为0
            currentNum = "0";
        }

        if(!ReUtil.isMatch(RegexUtils.INTEGER_REGEX,pageSize)) {
            pageSize = CustomerConstants.PAGE_SIZE;
        }
        //当前页初始值
        int beginNumber = (Integer.parseInt(currentNum)-1) * Integer.parseInt(pageSize);
        int endNumber =  beginNumber + Integer.parseInt(pageSize);

        Notice notice = new Notice();
        notice.getParams().put("Notice"," order by notice_time desc limit "+beginNumber+"," + (endNumber-1) );

        List<Notice> notices = noticeService.selectNoticeList(notice);
        List list = new ArrayList();
        if(notices.size() > 0){
            notices.stream().forEach(notice1 -> {
                Map map = new HashMap();
                map.put("id",notice1.getId());
                map.put("introduction",notice1.getNoticeIntroduction());
                map.put("time",notice1.getNoticeTime());
                map.put("title",notice1.getNoticeTitle());
                list.add(map);
            });
        }

        Map map = new HashMap();
        int total = newsService.selectTotal();  //查询记录数
        map.put("total",total);
        map.put("pageSize",pageSize);

        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list,map);
    }

    /**
     * 公告详情
     * @param id
     * @return
     */
    @PostMapping("/noticeDetail")
    public ResponseResult noticeDetail(@RequestParam("id") String id){

        Notice notice = noticeService.selectNoticeById(Integer.parseInt(id));
        Map map = new HashMap();
        map.put("id",notice.getId());
        map.put("introduction",notice.getNoticeIntroduction());
        map.put("time",notice.getNoticeTime());
        map.put("title",notice.getNoticeTitle());
        map.put("content",notice.getNoticeContent());
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
    }


    /**
     * 首页下拉框的交易说明
     * @return
     */
    @RequestMapping("/tradeExplainList")
    public ResponseResult tradeExplainList(){
        TradeExplain tradeExplain = new TradeExplain();

        List<TradeExplain> tradeExplains = tradeExplainService.selectTradeExplainList(new TradeExplain());
        List list = new ArrayList();
        tradeExplains.stream().forEach(tradeExplain1 -> {
            Map map = new HashMap();
            map.put("title",tradeExplain1.getTitle());
            map.put("content",tradeExplain1.getContent());
            list.add(map);
        });
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);
    }

}
