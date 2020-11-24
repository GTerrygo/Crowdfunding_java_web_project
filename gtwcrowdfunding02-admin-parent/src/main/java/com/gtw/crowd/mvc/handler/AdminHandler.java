package com.gtw.crowd.mvc.handler;


import com.github.pagehelper.PageInfo;
import com.gtw.crowd.service.api.AdminService;
import com.gtw.crowd.entity.Admin;
import com.gtw.crowd.util.constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author
 * @create 2020-11-05-4:39
 */
@Controller
public class AdminHandler {

    @Autowired
    private AdminService adminService;

//    @RequestMapping("/admin/test")
//    public String test(){
//        Admin adminById = adminService.getAdminById(2);
//        System.out.println(adminById);
//        return "admin-main";
//    }

    @RequestMapping("/admin/page.html")
    public String user(@RequestParam(value = "keyword",required = false,defaultValue = "")String keyword,
                       @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                       @RequestParam(value = "pageSize",required = false,defaultValue = "5")Integer pageSize,
                       HttpServletRequest request){
        PageInfo adminPage = adminService.getAdminPage(keyword, pageNum, pageSize);
        request.setAttribute(Constant.ATTR_NAME_PAGE_INFO,adminPage);
        return "admin-page";
    }

    @RequestMapping("admin/save.html")
    public String saveAdmin(Admin admin){
        adminService.saveAdmin(admin);
        return "redirect:/admin/page.html?pageNum="+Integer.MAX_VALUE;
    }

    @RequestMapping("admin/to/edit/page.html")
    public String toEditAdminPage(@RequestParam("id") Integer id, HttpServletRequest request){
        Admin admin = adminService.getAdminById(id);
        request.setAttribute("admin",admin);
        return "admin-edit";
    }

    @RequestMapping("admin/edit.html")
    public String editAdmin(Admin admin,HttpServletRequest request){
        adminService.editAdmin(admin);
        return "redirect:/admin/page.html?pageNum="+request.getParameter("pageNum")+"&keyword="+request.getParameter("keyword");
    }

    @RequestMapping("admin/delete.html")
    public String deleteAdmin(@RequestParam("id") Integer id,HttpServletRequest request){
        adminService.deleteAdmin(id);
        return "redirect:/admin/page.html?pageNum="+request.getParameter("pageNum")+"&keyword="+request.getParameter("keyword");
    }
}
