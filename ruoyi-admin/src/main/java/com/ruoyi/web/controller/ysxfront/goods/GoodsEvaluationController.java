package com.ruoyi.web.controller.ysxfront.goods;

import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.utils.Uuid;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.Vo.GoodsEvalutionVo;
import com.ruoyi.yishengxin.Vo.VipUserEvaluation;
import com.ruoyi.yishengxin.domain.goods.GoodsEvaluation;
import com.ruoyi.yishengxin.domain.goods.GoodsOrder;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.service.IGoodsOrderService;
import com.ruoyi.yishengxin.service.IVipUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.yishengxin.service.IGoodsEvaluationService;
import com.ruoyi.common.base.AjaxResult;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 商品评价 信息操作处理
 *
 * @author ruoyi
 * @date 2019-03-04
 */
@Controller
@RequestMapping("/front/goodsEvaluation")
public class GoodsEvaluationController extends BaseFrontController {

    @Autowired
    private IGoodsEvaluationService goodsEvaluationService;

    @Autowired
    private IVipUserService iVipUserService;

    @Autowired
    private IGoodsOrderService goodsOrderService;


    /**
     * 上传文件
     * @param file
     * @return  图片路径
     * @throws IOException
     */
    @PostMapping("/upload")
    @ResponseBody
    protected String uploadFile(MultipartFile file) throws IOException {
        //生成唯一标识
        String id = Uuid.getId();
        //图片存放路径，

        String images = "192.168.1.100:8080/1/" + id ;
        String images1 = "D:/1/" + id ;
        file.transferTo(new File(images1));
        return images;
    }


    /**
     * 用户查询商品评价列表
     */
    @PostMapping("/list")
    @ResponseBody
    public ResponseResult list(GoodsEvaluation goodsEvaluation) {
        //传商品id gid

        List<GoodsEvaluation> goodsEvaluations = goodsEvaluationService.selectGoodsEvaluationList(goodsEvaluation);
        List<VipUserEvaluation> vipUserEvaluations = new ArrayList<>();

        for (int i = 0; i <goodsEvaluations.size() ; i++) {

            VipUserEvaluation vipUserEvaluation = new VipUserEvaluation();

            GoodsEvaluation goodsEvaluation1 = goodsEvaluations.get(i);
            GoodsEvalutionVo goodsEvalutionVo = new GoodsEvalutionVo();
            String evaluationImage = goodsEvaluation1.getEvaluationImage();
            if (null != evaluationImage || "".equals(evaluationImage)){
                String[] split = evaluationImage.split(",");
                goodsEvalutionVo.setEvaluationImage(split);
            }

                            goodsEvalutionVo.setDescribeEvaluation(goodsEvaluation1.getDescribeEvaluation());
                            goodsEvalutionVo.setEvaluationContent(goodsEvaluation1.getEvaluationContent());

            Integer uid = goodsEvaluation1.getUid();
            VipUser vipUser = iVipUserService.selectVipUserById(uid);
                vipUserEvaluation.setVipUser(vipUser);
                vipUserEvaluation.setGoodsEvalutionVo(goodsEvalutionVo);
                 vipUserEvaluations.add(vipUserEvaluation);

        }
        return ResponseResult.responseResult(ResponseEnum.SUCCESS,vipUserEvaluations);
    }


    /**
     * 新增保存商品评价
     */

    @PostMapping("/add")
    @ResponseBody
    public ResponseResult addSave(@RequestHeader("token")String token, GoodsEvaluation goodsEvaluation, MultipartFile[] filename) throws IOException {
        // 校验登录状态
        VipUser vipUser = userExist(token);

        if (vipUser == null) {
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        //校验传参
        if (null == token || "".equals(token)) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
        }

        goodsEvaluation.setUid(vipUser.getId());
        List<GoodsEvaluation> goodsEvaluations = goodsEvaluationService.selectGoodsEvaluationList(goodsEvaluation);

        if (goodsEvaluations.size() > 0){
            return ResponseResult.responseResult(ResponseEnum.SUCCESS,"已评价");
        }


        String imagesPath = "";
        for (int i = 0; i < filename.length; i++) {

            if (filename.length == 0) {
                int i1 = goodsEvaluationService.insertGoodsEvaluation(goodsEvaluation);
                if (i1 > 0) {
                    return ResponseResult.responseResult(ResponseEnum.SUCCESS);
                }
                return ResponseResult.responseResult(ResponseEnum.GOODS_EVALUATION_ADDERROR);
            }
            String images = "e:/" + filename[i].getOriginalFilename() + ",";

            filename[i].transferTo(new File(images));
            imagesPath = imagesPath + images;
        }
        goodsEvaluation.setEvaluationImage(imagesPath);
        goodsEvaluation.setUid(vipUser.getId());
        goodsEvaluation.setCreateTime(new Date());


        int i = goodsEvaluationService.insertGoodsEvaluation(goodsEvaluation);
        if (i > 0 ){
            GoodsOrder goodsOrder = goodsOrderService.selectGoodsOrderById(goodsEvaluation.getOid());
            goodsOrder.setGoodsStatus("已评价");
            goodsOrderService.updateGoodsOrder(goodsOrder);
            return ResponseResult.responseResult(ResponseEnum.SUCCESS);
        }

        return ResponseResult.responseResult(ResponseEnum.GOODS_EVALUATION_ADDERROR);
    }

    /**
     * 修改商品评价
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        GoodsEvaluation goodsEvaluation = goodsEvaluationService.selectGoodsEvaluationById(id);
        mmap.put("goodsEvaluation", goodsEvaluation);
        return "/edit";
    }

    /**
     * 修改保存商品评价
     */
    @RequiresPermissions("yishengxin:goodsEvaluation:edit")
    @Log(title = "商品评价", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(GoodsEvaluation goodsEvaluation) {

        return null;
    }

    /**
     * 删除商品评价
     */

    @PostMapping("/remove")
    @ResponseBody
    public ResponseResult remove(String ids) {

        return null;
    }
}
