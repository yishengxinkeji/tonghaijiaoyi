package com.ruoyi.web.controller.ysxfront.vipuser;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReUtil;
import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.constant.CustomerConstants;
import com.ruoyi.common.enums.CustomerType;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.enums.TradeType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.RegexUtils;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.domain.*;
import com.ruoyi.yishengxin.domain.vipUser.VipTrade;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 首页相关的接口
 */
@RestController
@RequestMapping("/front/homePage")
public class HomePageController extends BaseFrontController {

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
    @Autowired
    private IPlatDataService platDataService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ITransferService transferService;
    @Autowired
    private IVipUserService vipUserService;
    @Autowired
    private IVipTradeService vipTradeService;

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
                map.put("pcLink",rotation1.getPicLink());
                map.put("mobileLink",rotation1.getMobileLink());
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
            currentNum = "1";
        }

        if(!ReUtil.isMatch(RegexUtils.INTEGER_REGEX,pageSize)) {
            pageSize = CustomerConstants.PAGE_SIZE;
        }

        //当前页初始值
        int beginNumber = (Integer.parseInt(currentNum)-1) * Integer.parseInt(pageSize);
        int endNumber =  beginNumber + Integer.parseInt(pageSize);

        News news2 = new News();
        news2.getParams().put("News"," order by news_time desc limit "+beginNumber+"," + endNumber );
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
    @GetMapping("/newsDetail")
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
        project1.getParams().put("Project"," order by project_time desc limit "+beginNumber+"," + endNumber  );
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
    @GetMapping("/projectDetail")
    public ResponseResult projectDetail(@RequestParam("id") String id){

        Project project = projectService.selectProjectById(Integer.parseInt(id));
        Map map = new HashMap();
        map.put("id",project.getId());
        map.put("introduction",project.getProjectIntroduction());
        map.put("pic",project.getProjectPic());
        map.put("time",project.getProjectTime());
        map.put("title",project.getProjectTitle());
        map.put("content",project.getProjectContent());
        map.put("unitPrice",project.getUnitPrice());
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
            //默认初始页为1
            currentNum = "1";
        }

        if(!ReUtil.isMatch(RegexUtils.INTEGER_REGEX,pageSize)) {
            pageSize = CustomerConstants.PAGE_SIZE;
        }
        //当前页初始值
        int beginNumber = (Integer.parseInt(currentNum)-1) * Integer.parseInt(pageSize);
        int endNumber =  beginNumber + Integer.parseInt(pageSize);

        Notice notice = new Notice();
        notice.getParams().put("Notice"," order by notice_time desc limit "+beginNumber+"," + endNumber );

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
        int total = noticeService.selectTotal();  //查询记录数
        map.put("total",total);
        map.put("pageSize",pageSize);

        return ResponseResult.responseResult(ResponseEnum.SUCCESS,list,map);
    }

    /**
     * 公告详情
     * @param id
     * @return
     */
    @GetMapping("/noticeDetail")
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
    @GetMapping("/tradeExplainList")
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

    /**
     * 首页新闻或者公告上面的大图
     * @param type   news:新闻, notice:公告
     * @return
     */
    @GetMapping("/platPic")
    public ResponseResult platPic(@RequestParam(value = "type",defaultValue = "news") String type){

        PlatData platData = platDataService.selectPlatDataList(new PlatData()).get(0);
        Map map = new HashMap();
        if(type.equalsIgnoreCase("news")){
            map.put("pic",platData.getNewsBigPic());
            return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
        }else if(type.equalsIgnoreCase("notice")){
            map.put("pic",platData.getNoticeBigPic());
            return ResponseResult.responseResult(ResponseEnum.SUCCESS,map);
        }
        return ResponseResult.success();
    }

    /**
     * 联系我们
     * @return
     */
    @GetMapping("/contactUs")
    public ResponseResult contactUs() {
        List list = new ArrayList();
        Customer customer = new Customer();
        customer.setCustomerType(CustomerType.PLAT.getCode());
        List<Customer> customers = customerService.selectCustomerList(customer);
        if (customers.size() > 0) {
            customers.stream().forEach(customer1 -> {
                Map map = new HashMap();
                map.put("phone", customer1.getPhone());
                map.put("address", customer1.getAddress());
                map.put("email", customer1.getEmail());
                list.add(map);
            });
        }
        return ResponseResult.responseResult(ResponseEnum.SUCCESS, list);
    }

    /**
     * 购买/兑换区客服电话
     * @param type      exchange:兑换, buy:购买
     * @return
     */
    @GetMapping("/exchangeOrBuy")
    public ResponseResult exchangeOrBuy(@RequestParam("type") String type) {

        List list = new ArrayList();
        Customer customer = new Customer();
        //兑换客服
        if(type.equalsIgnoreCase("exchange")){
            customer.setCustomerType(CustomerType.EXCHANGE.getCode());
            List<Customer> customers = customerService.selectCustomerList(customer);
            if (customers.size() > 0) {
                customers.stream().forEach(customer1 -> {
                    Map map = new HashMap();
                    map.put("phone", customer1.getPhone());
                    list.add(map);
                });
            }
        }else if(type.equalsIgnoreCase("buy")){
            customer.setCustomerType(CustomerType.BUY.getCode());
            List<Customer> customers = customerService.selectCustomerList(customer);
            if (customers.size() > 0) {
                customers.stream().forEach(customer1 -> {
                    Map map = new HashMap();
                    map.put("phone", customer1.getPhone());
                    list.add(map);
                });
            }
        }
        return ResponseResult.responseResult(ResponseEnum.SUCCESS, list);
    }


    /**
     * 转入转出说明
     * @param id
     * @return
     */
    @GetMapping("/transfer")
    public ResponseResult transfer(@RequestParam("id") Integer id) {

        Transfer transfers = transferService.selectTransferById(id);
        Map map = new HashMap();
        map.put("title", transfers.getTitle());
        map.put("content", transfers.getContent());
        return ResponseResult.responseResult(ResponseEnum.SUCCESS, map);
    }

    /**
     * 用户在项目中进行认购
     * @param token
     * @param projectId     项目id
     * @param number  数量
     * @return
     */
    @PostMapping("/takeUp")
    public ResponseResult takeUp (@RequestHeader("token") String token,
                                  @RequestParam("projectId") String projectId,
                                  @RequestParam("number") String number
    ){
        VipUser vipUser = userExist(token);
        if(vipUser == null){
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }

        if(vipUser.getIsMark().equals(CustomerConstants.NO)){
            return ResponseResult.responseResult(ResponseEnum.IDCARD_NO_IDENTIFY);
        }

        Project project = projectService.selectProjectById(Integer.parseInt(projectId));
        String unitPrice = project.getUnitPrice();
        String maxNumber = project.getMaxNumber();
        String minNumber = project.getMinNumber();
        //数字不正确
        if(!Pattern.matches(RegexUtils.INTEGER_REGEX,number) && !Pattern.matches(RegexUtils.DECIMAL_REGEX,number) ){
            return ResponseResult.responseResult(ResponseEnum.NUMBER_TRANCT_ERROR);
        }

        if(Double.parseDouble(maxNumber) < Double.parseDouble(number)){
            return ResponseResult.responseResult(ResponseEnum.MAX_BUY);
        }
        if(Double.parseDouble(minNumber) > Double.parseDouble(number)){
            return ResponseResult.responseResult(ResponseEnum.MIN_BUY);
        }

        double mul = NumberUtil.mul(Double.parseDouble(unitPrice), Double.parseDouble(number));     //认购总价
        String hkdMoney = vipUser.getHkdMoney();
        //hkd不足
        if(NumberUtil.sub(Double.parseDouble(hkdMoney),mul) < 0) {
            return ResponseResult.responseResult(ResponseEnum.VIP_USER_HKDINSUFFICIENT);
        }

        //应得的ssl和应扣的hkd
        String s = NumberUtil.roundStr(String.valueOf(NumberUtil.add(vipUser.getSslMoney(), number)), CustomerConstants.ROUND_NUMBER);
        String s1 = NumberUtil.roundStr(String.valueOf(NumberUtil.sub(Double.parseDouble(vipUser.getHkdMoney()), mul)), CustomerConstants.ROUND_NUMBER);
        //更新用户信息
        vipUser.setSslMoney(s);
        vipUser.setHkdMoney(s1);
        //更新用户信息
        int i = vipUserService.updateVipUser(vipUser);
        if(i > 0){
            VipTrade vipTrade = new VipTrade();
            vipTrade.setVipTrade(TradeType.BACK_BUY.getCode());     //平台购买
            vipTrade.setVipId(vipUser.getId());
            vipTrade.setTradeTime(DateUtil.format(new Date(), DateUtils.YYYY_MM_DD_HH_MM));
            vipTrade.setTradeNumber(number);
            vipTradeService.insertVipTrade(vipTrade);
        }
        return ResponseResult.success();
    }
}
