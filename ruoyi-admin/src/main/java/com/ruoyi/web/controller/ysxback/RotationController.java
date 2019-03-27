package com.ruoyi.web.controller.ysxback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.config.Global;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.yishengxin.domain.News;
import com.ruoyi.yishengxin.domain.Notice;
import com.ruoyi.yishengxin.domain.Project;
import com.ruoyi.yishengxin.service.INewsService;
import com.ruoyi.yishengxin.service.INoticeService;
import com.ruoyi.yishengxin.service.IProjectService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.yishengxin.domain.Rotation;
import com.ruoyi.yishengxin.service.IRotationService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * 轮播图 信息操作处理
 *
 * @author ruoyi
 * @date 2019-03-12
 */
@Controller
@RequestMapping("/yishengxin/rotation")
public class RotationController extends BaseController {
    private String prefix = "yishengxin/rotation";

    @Autowired
    private IRotationService rotationService;

    @Autowired
    private INewsService newsService;
    @Autowired
    private INoticeService noticeService;
    @Autowired
    private IProjectService projectService;

    @RequiresPermissions("yishengxin:rotation:view")
    @GetMapping()
    public String rotation() {
        return prefix + "/rotation";
    }

    /**
     * 查询轮播图列表
     */
    @RequiresPermissions("yishengxin:rotation:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Rotation rotation) {
        startPage();
        List<Rotation> list = rotationService.selectRotationList(rotation);
        return getDataTable(list);
    }


    /**
     * 导出轮播图列表
     */
    @RequiresPermissions("yishengxin:rotation:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Rotation rotation) {
        List<Rotation> list = rotationService.selectRotationList(rotation);
        ExcelUtil<Rotation> util = new ExcelUtil<Rotation>(Rotation.class);
        return util.exportExcel(list, "rotation");
    }

    /**
     * 新增轮播图
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存轮播图
     */
    @RequiresPermissions("yishengxin:rotation:add")
    @Log(title = "轮播图", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Rotation rotation) {
        rotation.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(rotationService.insertRotation(rotation));
    }

    /**
     * 修改轮播图
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        Rotation rotation = rotationService.selectRotationById(id);
        mmap.put("rotation", rotation);
        return prefix + "/edit";
    }

    /**
     * 修改保存轮播图
     */
    @RequiresPermissions("yishengxin:rotation:edit")
    @Log(title = "轮播图", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Rotation rotation) {
        rotation.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(rotationService.updateRotation(rotation));
    }

    /**
     * 删除轮播图
     */
    @RequiresPermissions("yishengxin:rotation:remove")
    @Log(title = "轮播图", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(rotationService.deleteRotationByIds(ids));
    }

    /**
     * 轮播图
     */
    @Log(title = "轮播图", businessType = BusinessType.UPDATE)
    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult updateAvatar(@RequestParam("file") MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                String avatar = FileUploadUtils.upload(Global.getAvatarPath(), file);
                return AjaxResult.success(Global.getAvatarPath() + avatar);
            }
            return error();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }


    @PostMapping("/cplb")
    @ResponseBody
    public AjaxResult cplb(@RequestParam(value = "type",defaultValue = "") String type) {

        List list = new ArrayList();
        AjaxResult ajaxResult = AjaxResult.success();
        //新闻
        if (type.equalsIgnoreCase("1")) {
            ajaxResult.put("link",Global.getConfig("tonghaijiaoyi.PREFIX_NEWS"));
            List<News> news = newsService.selectNewsList(new News());
            news.stream().forEach(news1 -> {
                Map map = new HashMap();
                map.put("title",news1.getNewsTitle());
                map.put("id",news1.getId());
                list.add(map);
            });
        }

        //公告
        if (type.equalsIgnoreCase("2")) {
            ajaxResult.put("link",Global.getConfig("tonghaijiaoyi.PREFIX_NOTICE"));
            List<Notice> notices = noticeService.selectNoticeList(new Notice());
            notices.stream().forEach(notice -> {
                Map map = new HashMap();
                map.put("title",notice.getNoticeTitle());
                map.put("id",notice.getId());
                list.add(map);
            });
        }

        //项目
        if (type.equalsIgnoreCase("3")) {
            ajaxResult.put("link",Global.getConfig("tonghaijiaoyi.PREFIX_PROJECT"));
            List<Project> projects = projectService.selectProjectList(new Project());
            projects.stream().forEach(project -> {
                Map map = new HashMap();
                map.put("title",project.getProjectTitle());
                map.put("id",project.getId());
                list.add(map);
            });
        }
        ajaxResult.put("data",list);
        return ajaxResult;
    }

}
