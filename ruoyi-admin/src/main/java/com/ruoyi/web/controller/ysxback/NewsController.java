package com.ruoyi.web.controller.ysxback;

import java.util.List;

import com.ruoyi.common.config.Global;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.yishengxin.domain.News;
import com.ruoyi.yishengxin.service.INewsService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * 新闻资讯 信息操作处理
 *
 * @author ruoyi
 * @date 2019-03-12
 */
@Controller
@RequestMapping("/yishengxin/news")
public class NewsController extends BaseController {
    private String prefix = "yishengxin/news";

    @Autowired
    private INewsService newsService;

    @RequiresPermissions("yishengxin:news:view")
    @GetMapping()
    public String news() {
        return prefix + "/news";
    }

    /**
     * 查询新闻资讯列表
     */
    @RequiresPermissions("yishengxin:news:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(News news) {
        startPage();
        List<News> list = newsService.selectNewsList(news);
        return getDataTable(list);
    }


    /**
     * 导出新闻资讯列表
     */
    @RequiresPermissions("yishengxin:news:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(News news) {
        List<News> list = newsService.selectNewsList(news);
        ExcelUtil<News> util = new ExcelUtil<News>(News.class);
        return util.exportExcel(list, "news");
    }

    /**
     * 新增新闻资讯
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存新闻资讯
     */
    @RequiresPermissions("yishengxin:news:add")
    @Log(title = "新闻资讯", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(News news) {
        news.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(newsService.insertNews(news));
    }

    /**
     * 修改新闻资讯
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        News news = newsService.selectNewsById(id);
        mmap.put("news", news);
        return prefix + "/edit";
    }

    /**
     * 修改保存新闻资讯
     */
    @RequiresPermissions("yishengxin:news:edit")
    @Log(title = "新闻资讯", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(News news) {
        news.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(newsService.updateNews(news));
    }

    /**
     * 删除新闻资讯
     */
    @RequiresPermissions("yishengxin:news:remove")
    @Log(title = "新闻资讯", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(newsService.deleteNewsByIds(ids));
    }

    /**
     * 新闻资讯凭证
     */
    @Log(title = "新闻资讯", businessType = BusinessType.UPDATE)
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

}
