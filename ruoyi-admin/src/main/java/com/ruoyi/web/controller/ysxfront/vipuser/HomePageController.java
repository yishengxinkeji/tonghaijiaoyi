package com.ruoyi.web.controller.ysxfront.vipuser;

import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.ResponseEnum;
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

    /**
     * 轮播图
     * @return
     */
    @PostMapping("/rotation")
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
                list.add(list);
            });
        }
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);
    }

    /**
     * 滚动消息
     * @return
     */
    @PostMapping("/rollMsg")
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
                list.add(list);
            });
        }
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);
    }

    /**
     * 新闻资讯
     * @return
     */
    @PostMapping("/news")
    public ResponseResult news(){

        List<News> news = newsService.selectNewsList(new News());

        List list = new ArrayList();
        if(news.size() > 0){
            news.stream().forEach(news1 -> {
                Map map = new HashMap();
                map.put("id",news1.getId());
                map.put("introduction",news1.getNewsIntroduction());
                map.put("pic",news1.getNewsPic());
                map.put("time",news1.getNewsTime());
                map.put("title",news1.getNewsTitle());
                list.add(list);
            });
        }
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);
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
     * @return
     */
    @GetMapping("/project")
    public ResponseResult project(){

        List<Project> projects = projectService.selectProjectList(new Project());

        List list = new ArrayList();
        if(projects.size() > 0){
            projects.stream().forEach(project -> {
                Map map = new HashMap();
                map.put("id",project.getId());
                map.put("introduction",project.getProjectIntroduction());
                map.put("pic",project.getProjectPic());
                map.put("time",project.getProjectTime());
                map.put("title",project.getProjectTitle());
                list.add(list);
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
     * @return
     */
    @GetMapping("/notice")
    public ResponseResult notice(){

        List<Notice> notices = noticeService.selectNoticeList(new Notice());

        List list = new ArrayList();
        if(notices.size() > 0){
            notices.stream().forEach(notice -> {
                Map map = new HashMap();
                map.put("id",notice.getId());
                map.put("introduction",notice.getNoticeIntroduction());
                map.put("time",notice.getNoticeTime());
                map.put("title",notice.getNoticeTitle());
                list.add(list);
            });
        }
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list);
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


}