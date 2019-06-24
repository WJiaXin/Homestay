package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.VerifyCode;
import com.example.demo.service.UserServiceImpl;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@RequestMapping(value = "/user")
@Controller
public class UserController {
    //依赖注入
    @Autowired
    UserServiceImpl userService;


    @RequestMapping(value = "/createImg")
    public void createImg(HttpServletResponse response, HttpServletRequest request) throws IOException {
        //创建对象
        VerifyCode vc = new VerifyCode();
        //获取图片对象
        BufferedImage bi = vc.getImage();
        //获得图片的文本内容
        String text = vc.getText();
        // 将系统生成的文本内容保存到session中
        request.getSession().setAttribute("text", text);
        //向浏览器输出图片
        vc.output(bi, response.getOutputStream());
    }

    //注册后台方法
    @RequestMapping(value = "/register")
    public void register(HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        User user = new User();//创建一个user对象
        PrintWriter printWriter = response.getWriter();
        //获取前端由ajax传过来的值
        String user_id = request.getParameter("user_id");
        String user_name = request.getParameter("user_name");
        String user_pwd = request.getParameter("user_pwd1");
        String inputCordZ = request.getParameter("inputCordZ");
        String text = (String) session.getAttribute("text") ;//获取验证码的值
        int flag = userService.verification(user_id);//查询用户是否已经注册
        if (!inputCordZ.equals(text)){
            printWriter.print(1);//验证码错误返回1
        }
        else if(flag != 0) { //判断用户是否已经注册， //判断验证码是否输入正确
            printWriter.print(0);//用户已经注册，返回0
        }else{
            //实例化user对象
            user.setUser_id(user_id);
            user.setUser_name(user_name);
            user.setUser_pwd(user_pwd);
            //调用Service层方法将数据插入数据库
            userService.insertUser(user);
            printWriter.print(9);//用户已经注册，返回9
            //创建用户文件夹
            File file = new File(URLDecoder.decode(this.getClass().getClassLoader().getResource("").getPath())+"static/img/user/" + user_id);
            System.out.println(URLDecoder.decode(this.getClass().getClassLoader().getResource("").getPath())+"static/img/user/" + user_id);
            // 指定路径如果没有则创建并添加
            //获取父目录
            File fileParent = file.getParentFile();
          //判断是否存在
            if (!fileParent.exists()) {
                //创建父目录文件
                fileParent.mkdirs();
            }
            file.mkdir();
        }

    }

    //登录后台方法
    @RequestMapping(value = "/login")
    public void login(HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        User user = new User();//创建一个user对象
        PrintWriter printWriter = response.getWriter();
        //获取前端由ajax传过来的值
        String user_id = request.getParameter("inputPhone");
        String user_pwd = request.getParameter("inputPassword");
        String inputCordZ = request.getParameter("inputCord1");
        //获取验证码的值
        String text = (String) session.getAttribute("text") ;//获取验证码的值
        int flag = userService.verification(user_id);//查看该id在user表中是否有记录。
        //判断验证码是否输入正确
        if (flag != 0) {
            user = userService.findUserById(user_id); //通过id，找到用户所有信息
            if (!inputCordZ.equals(text) && user.getUser_pwd().equals(user_pwd)) {//判断验证码和密码是否输入正确
                JSONObject json = JSONObject.fromObject(user);
                printWriter.print(json.toString());
                request.setAttribute("userLogin", user);//密码正确，将用户信息发送带缓存。
                session.setAttribute("userinfo",user);

            }
        }
    }
    //获取信息
    @RequestMapping(value = "/getInFo")
    @ResponseBody
    public User getInFo(HttpServletResponse response, HttpServletRequest request,HttpSession session) throws IOException{
        User user =new User();
        //获取登录信息
        String user_id = request.getParameter("inputPhone");
        User user1 = (User)session.getAttribute("userinfo") ;
        return user1;
    }
    @RequestMapping(value = "/upName")
    @ResponseBody
    public User upName(HttpServletResponse response, HttpServletRequest request,HttpSession session) throws IOException{
        //获取id
        User user = (User)session.getAttribute("userinfo") ;
        String user_id =user.getUser_id();
        //获取修改的名字
        String user_name = request.getParameter("inputName");
        user.setUser_name(user_name);
        //调用sql将数据更新到数据库
        userService.upName(user_name,user_id);
        //更新user数据
        return user;
    }
   //图片上传及更换图片的方法
    @RequestMapping(value = "/pimg")                       //上传图片
    public void img(HttpServletResponse httpServletResponse,@RequestParam("file") MultipartFile file,HttpSession session,HttpServletRequest request) throws IOException {
        //获取id
        User user = (User)session.getAttribute("userinfo") ;
        String user_id =user.getUser_id();
        PrintWriter printWriter = httpServletResponse.getWriter();
        System.out.println("长度:"+file.isEmpty());
        File savePath = new File((this.getClass().getClassLoader().getResource("").getPath())+"/static/img/user/" + user_id);
        System.out.println(savePath.getPath());
        String fileName=file.getOriginalFilename();
        try {

            if (fileName.indexOf("\\") >= 0) {
                long date = new Date().getTime();
                fileName=date+fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());

                System.out.println(fileName);
            }else {
                long date = new Date().getTime();
                fileName=date+fileName;
            }
            System.out.println("1:"+fileName);
            // 写入文件到保存路径中
            Files.copy(file.getInputStream(), new File(savePath, fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("失败");

        }
        if(file==null) {
            printWriter.print("0");
        }
        else {
            printWriter.print(fileName);
        }
        //调用sql将数据更新到数据库
        userService.upUserImg(fileName,user_id);
        user.setUser_picture(fileName);
        request.setAttribute("userinfo", user);//密码正确，将用户信息发送带缓存。
    }
    @RequestMapping(value = "/upPwd")
    public void upPwd(HttpServletResponse response, HttpServletRequest request,HttpSession session) throws IOException{
        PrintWriter printWriter = response.getWriter();
        //获取id
        User user = (User)session.getAttribute("userinfo") ;
        String user_id =user.getUser_id();
        //获取修改的密码
        String user_pwd = request.getParameter("uppwd1");
        String inputCordZ=request.getParameter("inputCordZ");
        String pwd=request.getParameter("pwd");
        String text = (String) session.getAttribute("text") ;//获取验证码的值
        if (!inputCordZ.equals(text)){
            printWriter.print(0);//验证码错误返回0
        }else if(!user.getUser_pwd().equals(pwd)){
            printWriter.print(1);//密码错误，返回1
        }else {
            //调用sql将数据更新到数据库
            userService.upPwd(user_pwd, user_id);
            //更新缓存中user的值
            user.setUser_name(user_pwd);
            printWriter.print(2);//输入正确返回2
        }
    }
}
