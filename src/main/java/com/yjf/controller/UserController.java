package com.yjf.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjf.entity.Page;
import com.yjf.entity.User;
import com.yjf.services.UserService;
import com.yjf.utils.JsonUtils;
import javafx.beans.binding.ObjectExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/10/15 12:06
 * @Description
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

   @RequestMapping(value = "getUser",method = RequestMethod.GET)
    public String getUser(int id, Model model){
        User user = userService.getUser(id);
        model.addAttribute("user",user);
        return "user/user";
    }

    @RequestMapping(value = "getUserById",method = RequestMethod.GET)
    @ResponseBody
    public User getUserById( int id){
        User user = userService.getUser(id);
        return user;
    }

    @RequestMapping(value = "getListUser",method = RequestMethod.GET)
    public String getListUser(){
        return "user/list";
    }

    /**
     *@Description TODO:分页查询
     *@author 余俊锋
     *@date 2020/10/17 13:27
     *@params
     *@return java.util.List<com.yjf.entity.User>
     */
    @RequestMapping(value = "getListAll",method = RequestMethod.POST)
    @ResponseBody
    public Page<List<User>> getListAll(@RequestBody Map<String ,Object> map){

        Object object = map.get("page");
        String s = JsonUtils.pojoToJson(object);
        Page<List<User>> page = JsonUtils.jsonToPojo(s, Page.class);
        String name=(String) map.get("name");
        page.setCount(userService.getCount(name));
        List<User> list = userService.getUsersByName(name,page);
        page.setData(list);
        return page;
    }

    @RequestMapping(value = "toAddUser",method = RequestMethod.GET)
    public String toAddUser(){
       return "user/add";
    }




    @RequestMapping(value = "addUser",method = RequestMethod.POST)
    @Transactional
    @ResponseBody
    public Map<String, Object> addUser(@RequestBody User user){
      int id= userService.addUser(user);
        Map<String,Object> map=new HashMap<>();
      if (id>0){
          map.put("code","200");
          map.put("msg","添加成功");
      }else {
          map.put("msg","添加失败");
      }
        return map;

    }

    @RequestMapping(value = "toUpdateUser",method = RequestMethod.GET)
    public String toUpdateUser(Integer id,Model model){
        model.addAttribute("id",id);
        return "user/update";
    }

    @RequestMapping(value = "updateUser",method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Map<String, Object> updateUser(@RequestBody  User user){
        ModelAndView modelAndView = new ModelAndView();

        int id = userService.updateUser(user);
        Map<String,Object> map=new HashMap<>();
        if (id>0){
            map.put("code","200");
            map.put("msg","修改成功");
        }else {
            map.put("msg","修改失败");
        }
           return map;
    }

    @RequestMapping(value = "deleteUser",method = RequestMethod.GET)
    @Transactional
    public String deleteUser(Integer id, HttpServletRequest request){
        userService.deleteUser(id);
        return "redirect:/user/getListUser";
    }


}
