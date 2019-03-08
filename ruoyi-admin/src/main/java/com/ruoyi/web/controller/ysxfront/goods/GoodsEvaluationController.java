package com.ruoyi.web.controller.ysxfront.goods;

import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.enums.ResponseEnum;
import com.ruoyi.common.utils.SplitString;
import com.ruoyi.web.controller.ysxfront.BaseFrontController;
import com.ruoyi.yishengxin.Vo.GoodsEvalutionVo;
import com.ruoyi.yishengxin.Vo.VipUserEvaluation;
import com.ruoyi.yishengxin.domain.goods.GoodsEvaluation;
import com.ruoyi.yishengxin.domain.vipUser.VipUser;
import com.ruoyi.yishengxin.service.IVipUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.yishengxin.service.IGoodsEvaluationService;
import com.ruoyi.common.base.AjaxResult;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

            String evaluationImage = goodsEvaluations.get(i).getEvaluationImage();
            List<String> strings = SplitString.diveString(evaluationImage, ",");

            GoodsEvalutionVo goodsEvalutionVo = new GoodsEvalutionVo();
                            goodsEvalutionVo.setDescribeEvaluation(goodsEvaluation1.getDescribeEvaluation());
                            goodsEvalutionVo.setEvaluationContent(goodsEvaluation1.getEvaluationContent());
                            goodsEvalutionVo.setEvaluationImage(strings);
            Integer uid = goodsEvaluations.get(i).getUid();
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
    public ResponseResult addSave(String token, GoodsEvaluation goodsEvaluation, MultipartFile[] filename) throws IOException {
        // 校验登录状态
        VipUser vipUser = userExist(token);

        if (vipUser == null) {
            return ResponseResult.responseResult(ResponseEnum.VIP_TOKEN_FAIL);
        }
        //校验传参
        if (null == token || "".equals(token)) {
            return ResponseResult.responseResult(ResponseEnum.COODS_COLLECTION_PARAMETER);
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
        int i = goodsEvaluationService.insertGoodsEvaluation(goodsEvaluation);
        if (i > 0 ){
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
    public AjaxResult remove(String ids) {
        return null;
    }
}
