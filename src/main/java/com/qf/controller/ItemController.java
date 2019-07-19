package com.qf.controller;

import com.qf.pojo.Item;
import com.qf.service.ItemService;
import com.qf.util.PageInfo;
import com.qf.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.qf.constant.SSMConstant.*;

/**
 * @author bbj 2019/7/16 11:12
 * @version 1.0
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Value("${pic_types}")
    private String picType;

    @PostMapping("/update")
    private String update(Item item,HttpServletRequest request,RedirectAttributes redirectAttributes) throws IOException {

        MultipartFile picFile = item.getPicFile();
        if (picFile != null&&picFile.getSize()>0) {
            UploadPic uploadPic = new UploadPic(request, redirectAttributes, picFile).invoke();
            if (uploadPic.is()) return REDIRECT + "/item/update/"+item.getId();
            String pic = uploadPic.getPic();
            item.setPic(pic);
        }

        Integer count = itemService.update(item);
        if (count==1) {
            return REDIRECT + ITEM_LIST;
        }else {
            redirectAttributes.addAttribute("picInfo", "更新商品失败,请联系管理员");
            return REDIRECT + "/item/update/"+item.getId();
        }
    }


    //跳转更新页面
    @GetMapping("/update/{id}")
    public String updateUI(@PathVariable Long id,String picInfo,Model model){
        Item item = itemService.findById(id);
        model.addAttribute("item", item);
        model.addAttribute("picInfo", picInfo);
        return "item/item_update";
    }

    //根据id删除商品
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public ResultVO del(@PathVariable Long id){
        //调用service删除商品
        Integer count = itemService.del(id);
        //根据结果给页面响应json
        if (count == 1) {
            //删除成功
            return new ResultVO(0, "删除成功", null);
        }else {
            return new ResultVO(-1, "删除失败", null);
        }
    }

    //添加商品信息
    @PostMapping("/add")
    public String add(@Valid Item item, BindingResult bindingResult, HttpServletRequest request, Model model,
                      RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            String msg = bindingResult.getFieldError().getDefaultMessage();
            redirectAttributes.addAttribute("picInfo", msg);
            return REDIRECT + ITEM_ADD_UI;
        }


        MultipartFile picFile = item.getPicFile();
        UploadPic uploadPic = new UploadPic(request, redirectAttributes, picFile).invoke();
        if (uploadPic.is()) return REDIRECT + ITEM_ADD_UI;
        String pic = uploadPic.getPic();

        //===========================================================================
        //将pic放入item中
        item.setPic(pic);
        //保存商品
        Integer count = itemService.save(item);
        if (count == 1) {

            return REDIRECT + ITEM_LIST;
        }else {
            //添加商品信息失败
            redirectAttributes.addAttribute("picInfo", "添加商品失败,请联系管理员");
            return REDIRECT + ITEM_ADD_UI;
        }

    }


    //跳转到添加商品页面
    @GetMapping("/add-ui")
    public String addUI(String picInfo,Model model){
        model.addAttribute("picInfo", picInfo);
        return "item/item_add";
    }


    //商品的首页
    @GetMapping("/list")
    public String list(String name,
                       @RequestParam(value = "page",defaultValue = "1")Integer page,
                       @RequestParam(value = "size",defaultValue = "5")Integer size,
                       Model model){
        //1.调用service查询数据
        PageInfo<Item> pageInfo = itemService.findItemAndLimit(name,page,size);

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("name", name);

        return "item/item_list";
    }

    private class UploadPic {
        private boolean myResult;
        private HttpServletRequest request;
        private RedirectAttributes redirectAttributes;
        private MultipartFile picFile;
        private String pic;

        public UploadPic(HttpServletRequest request, RedirectAttributes redirectAttributes, MultipartFile picFile) {
            this.request = request;
            this.redirectAttributes = redirectAttributes;
            this.picFile = picFile;
        }

        boolean is() {
            return myResult;
        }

        public String getPic() {
            return pic;
        }

        public UploadPic invoke() throws IOException {
            //1.对图片大小做校验
            long size = picFile.getSize();
            if (size >= 5242880) {
                //图片过大
                redirectAttributes.addAttribute("picInfo", "图片过大,小于5M");
                myResult = true;
                return this;
            }
            //2.对图片类型做校验 jpg png gif
            boolean flag = false;
            String[] types = picType.split(",");
            for (String type : types) {
                if (StringUtils.endsWithIgnoreCase(picFile.getOriginalFilename(), type)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                //图片类型不正确
                redirectAttributes.addAttribute("picInfo", "图片类型不正确,要求:"+picType);
                myResult = true;
                return this;
            }
            //3.判断图片是否损坏
            BufferedImage bufferedImage = ImageIO.read(picFile.getInputStream());
            if (bufferedImage == null) {
                //图片损坏
                redirectAttributes.addAttribute("picInfo", "图片损坏");
                myResult = true;
                return this;
            }

            //开始上传图片=====================================================
            //1.给图片起一个新名字
            String prefix = UUID.randomUUID().toString();
            String suffix = StringUtils.substringAfterLast(picFile.getOriginalFilename(), ".");
            String newName = prefix + "." + suffix;
            //2.将图片保存到本地
            String path = request.getServletContext().getRealPath("\\static\\img")+"\\"+newName;
            System.out.println(path);
            File file = new File(path);
            if (file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            picFile.transferTo(file);
            //3.封装图片的访问路径
            pic = "http://localhost/static/img/" + newName;
            myResult = false;
            return this;
        }
    }
}
