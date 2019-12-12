package com.xiaoyi.bis.xiaoyi.util;

import com.xiaoyi.bis.common.utils.StringUtils;
import com.xiaoyi.bis.xiaoyi.domain.LiveCourse;
import com.xiaoyi.bis.xiaoyi.domain.RecordCourse;
import com.xiaoyi.bis.xiaoyi.dto.AddRecordCourseRequest;

import java.util.List;

/**
 * 拼接富文本内容工具类
 * @author CJ
 * @date 2019/10/21
 */
public class HtmlUtil {

    public static void processCourseHtml(LiveCourse course){
        /*String courseImage = HtmlUtil.getCourseImage(course.getCoverImg());
        course.setCoverImg(courseImage);*/
        //课程信息
        String desc = HtmlUtil.getCourseInfo(course.getClassDifficult(), course.getClassInfo(), course.getCourseObj(), course.getTextBook());
        course.setDesc(desc);
        //师资介绍
        String teacherInfo = HtmlUtil.getTeacherInfo(course.getTeacherInfo(),course.getTeacherName(),course.getCourseIntroduceImg());
        course.setTeacherInfo(teacherInfo);
        //课程亮点
        String courseFeature = HtmlUtil.getCourseFeatureInfo(course.getCourseFeature());
        course.setCourseFeature(courseFeature);
        //学习内容
        String contentInfo = HtmlUtil.getCourseContentInfo(course.getCourseContent());
        course.setCourseContent(contentInfo);
        //观看方式
        /*String viewTypeInfo = HtmlUtil.getViewTypeInfo();
        course.setCoursePrompt(viewTypeInfo);*/
        //课程咨询
        String consultantInfo = HtmlUtil.getCourseConsultantInfo();
        course.setCourseConsultant(consultantInfo);
        //温馨提示
        String coursePromptInfo = HtmlUtil.getCoursePromptInfo();
        course.setCoursePrompt(coursePromptInfo);
    }

    public static String getClassDifficult(Integer classDifficultCount){
        if(classDifficultCount == 2){
            return "★★";
        }else if(classDifficultCount == 3){
            return "★★★";
        }else if(classDifficultCount == 4){
            return "★★★★";
        }else if(classDifficultCount == 5){
            return "★★★★★";
        }else{
            return "★";
        }
    }

    public static void processCourseHtml(String classDifficult,RecordCourse course, com.xiaoyi.bis.xiaoyi.xiaoyiApi.AddRecordCourseRequest request){
        //课程信息
        String desc = HtmlUtil.getCourseInfo(classDifficult,course.getClassInfo(),course.getCourseObj(),course.getTextBook());
        request.setCourseInformation(desc);
        //师资介绍
        String teacherInfo = HtmlUtil.getTeacherInfo(course.getTeacherInfo(),course.getTeacheName(),course.getTeacherImage());
        request.setCourseTeachersHighlight(teacherInfo);
        //课程亮点
        String courseFeature = HtmlUtil.getCourseFeatureInfo(course.getCourseFeature());
        request.setCourseHighlight(courseFeature);
        //学习内容
        String contentInfo = HtmlUtil.getCourseContentInfo(course.getCourseContent());
        request.setCourseLearningContent(contentInfo);
        //观看方式
        String viewTypeInfo = HtmlUtil.getViewTypeInfo();
        request.setCourseObservationStyle(viewTypeInfo);
        //课程咨询
        String consultantInfo = HtmlUtil.getCourseConsultantInfo();
        request.setCourseConsultant(consultantInfo);
        //温馨提示
        String coursePromptInfo = HtmlUtil.getRecordCoursePromptInfo();
        request.setCourseWarmPrompt(coursePromptInfo);
    }

    public static String processTags(List<String> classTags){
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < classTags.size(); i++) {
            if (i == classTags.size() - 1) {
                stringBuffer.append(classTags.get(i));
            } else {
                stringBuffer.append(classTags.get(i) + "&");
            }
        }
        if(!StringUtils.isEmpty(stringBuffer)){
            return stringBuffer.toString();
        }else{
            return null;
        }
    }

    /**
     * @param courseCasePath 课程主图路径
     * @return 课程主图Html
     */
    public static String getCourseImage(String courseCasePath){
        return "   <div class=\"cover-info\" style=\"height:345px ;width: 510px;\">\n" +
                "             <img height=\"345px\" width=\"510px\" src="+courseCasePath+" />\n" +
                "   </div>";
    }


    /**
     * @return 温馨提示Html
     */
    public static String getCoursePromptInfo(){
        return "<table width=\"1063\" cellspacing=\"0\">\n" +
                "   <tbody>\n" +
                "    <tr class=\"firstRow\">\n" +
                "     <td style=\"padding: 0px; word-break: break-all;\"><p style=\"margin-bottom: 10px; margin-left: 18px; height: 32px; width: 8px; background-color: rgb(86, 180, 48); float: left;\"><br />&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p><p style=\"margin-bottom: 10px; margin-left: 15px; font-size: 28px; color: rgb(76, 76, 76); height: 32px; line-height: 32px; float: left;\">温馨提示</p></td>\n" +
                "    </tr>\n" +
                "   </tbody>\n" +
                "  </table>\n" +
                "  <p style=\"margin-left: 30px; white-space: normal; font-size: 20px;\"><span style=\"color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 20px; background-color: rgb(255, 255, 255);\">1、</span><span style=\"box-sizing: border-box; color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 20px; background-color: rgb(255, 255, 255);\">本课程为短期直播课程，</span><span style=\"box-sizing: border-box; font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 20px; background-color: rgb(255, 255, 255); color: rgb(255, 0, 0);\"><span style=\"box-sizing: border-box;\">直播</span><span style=\"box-sizing: border-box;\">开课之后</span>，概不退款，</span><span style=\"box-sizing: border-box; color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 20px; background-color: rgb(255, 255, 255);\">望谨慎购买。</span></p>\n" +
                "  <p style=\"margin-left: 30px; white-space: normal; font-size: 20px;\"><br /></p>\n" +
                "  <p><br /></p>";
    }

    public static String getRecordCoursePromptInfo(){
        return "<table width=\"1063\" cellspacing=\"0\">\n" +
                "   <tbody>\n" +
                "    <tr class=\"firstRow\">\n" +
                "     <td style=\"padding: 0px; word-break: break-all;\"><p style=\"margin-bottom: 10px; margin-left: 18px; height: 32px; width: 8px; background-color: rgb(86, 180, 48); float: left;\"><br />&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p><p style=\"margin-bottom: 10px; margin-left: 15px; font-size: 28px; color: rgb(76, 76, 76); height: 32px; line-height: 32px; float: left;\">温馨提示</p></td>\n" +
                "    </tr>\n" +
                "   </tbody>\n" +
                "  </table>\n" +
                "<p style=\"margin-left: 30px; white-space: normal; font-size: 20px;\"><span style=\"color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 20px; background-color: rgb(255, 255, 255);\">1、</span><span style=\"box-sizing: border-box; color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 20px; background-color: rgb(255, 255, 255);\">本课程为录播课程，</span><span style=\"box-sizing: border-box; color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 20px; background-color: rgb(255, 255, 255);\">望谨慎购买。</span></p>\n" +
                "  <p style=\"margin-left: 30px; white-space: normal; font-size: 20px;\"><br /></p>\n" +
                "  <p><br /></p>";
    }

    /**
     * @return 课程咨询Html
     */
    public static String getCourseConsultantInfo(){
        return "<table border=\"0\" cellspacing=\"0\" style=\"width:100%;border-style: hidden\"><tbody><tr class=\"firstRow\"><td style=\"padding: 0px; word-break: break-all;\"><p style=\"margin:0px 0 10px 18px;height: 32px;width: 8px;background-color: #56b430;float:left\"><br>\n" +
                " &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p><p style=\"font-size: 28px;color: #4c4c4c;margin:0px 0 10px 15px;height: 32px;line-height:32px;float:left;\">课程咨询</p></td></tr></tbody></table><table border=\"0\" cellspacing=\"0\" align=\"center\" frame=\"void\" style=\"font-size: 20px;border-collapse: separate;padding: 0 18px;border:  none;border-style: hidden;width: 100%;margin-bottom: 0;\"><tbody rules=\"none\"><tr rules=\"none\" class=\"firstRow\"><td width=\"50%\" valign=\"top\" style=\"text-align: center; border-style: hidden; padding: 4px 12px 4px 0; white-space: nowrap; vertical-align: middle; color: rgb(255, 255, 255); word-break: break-all;\"><p style=\"background-color:rgb(238,247,234);\"><img style=\"width: 100%;\" src=\"/libs/ueditor/upload/image/20181128/1543374753245012386.png\" title=\"\" alt=\"\">\n" +
                " &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p></td><td width=\"50%\" valign=\"top\" style=\"text-align: center; border-style: hidden; padding: 4px 0px 4px 12px; white-space: nowrap; vertical-align: middle; color: rgb(255, 255, 255); word-break: break-all;\"><p style=\"background-color:rgb(238,247,234);\"><img style=\"width: 100%;\" src=\"/libs/ueditor/upload/image/20181128/1543374775069088898.png\" title=\"\" alt=\"\">\n" +
                " &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p></td></tr></tbody></table><p><br></p>";
    }

    /**
     * @return 观看方式Html (yy默认)
     */
    public static String getViewTypeInfo(){
        return "<table style=\"width:100%;border-style: hidden\" width=\"493\" cellspacing=\"0\" border=\"0\"><tbody><tr class=\"firstRow\"><td style=\"padding: 0px; word-break: break-all;\" width=\"112\"><p style=\"margin:0px 0 10px 18px;height: 32px;width: 8px;background-color: #56b430;float:left\"><br>\n" +
                " &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p><p style=\"font-size: 28px;color: #4c4c4c;margin:0px 0 10px 15px;height: 32px;line-height:32px;float:left;\">观看方式</p></td></tr></tbody></table><table frame=\"void\" style=\"font-size: 20px;border-collapse: separate;padding: 0 18px;border:  none;border-style: hidden;width: 100%;margin-bottom: 0;\" width=\"493\" cellspacing=\"0\" border=\"0\" align=\"center\"><tbody rules=\"none\"><tr rules=\"none\" class=\"firstRow\"><td style=\"text-align: center; border-style: hidden; padding: 10px 0px; white-space: nowrap; vertical-align: middle; color: rgb(255, 255, 255); word-break: break-all;\" valign=\"top\"><img style=\"width: 100%;\" src=\"/libs/ueditor/upload/image/20181128/1543461377189051992.png\" title=\"\" alt=\"\">\n" +
                " &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</td></tr></tbody></table><p><br></p>";
    }

    /**
     * @param courseContentInfo 学习内容
     * @return 学习内容Html
     */
    public static String getCourseContentInfo(String courseContentInfo){
        return courseContentTitleInfo() + " <table border=\"0\" cellspacing=\"0\" align=\"center\" frame=\"void\" style=\"font-size: 20px;border-collapse: separate;border:none;border-style: hidden;padding: 0 18px;width: 100%;margin-bottom: 20px;table-layout: fixed;\" width=\"493\">\n" +
                "   <tbody rules=\"none\">\n" +
                "    <tr rules=\"none\" class=\"firstRow\">\n" +
                "     <td width=\"22\" valign=\"top\" style=\"border-style: hidden;background-color: rgb(255,255,255);color: #fff;padding-top: 8px;\"><p style=\"width:22px;height:22px;background-color:rgb(252,206,47);\"><br /> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p></td>\n" +
                "     <td valign=\"top\" style=\"border-style: hidden; background-color: rgb(255, 255, 255); padding: 5px 10px; vertical-align: middle; color: rgb(255, 255, 255); word-break: break-all;\"><p style=\"color:#666666;letter-spacing: 1.8px;\">" +
                courseContentInfo + "</p></td>\n" +
                "    </tr>\n" +
                "   </tbody>\n" +
                "  </table>";
    }

    private static String courseContentTitleInfo(){
        return "<table border=\"0\" cellspacing=\"0\" style=\"margin-top:20px;width:100%;border-style: hidden\" width=\"493\"><tbody><tr class=\"firstRow\"><td style=\"padding: 0px; word-break: break-all;\" width=\"112\"><p style=\"margin:0px 0 10px 18px;height: 35px;width: 8px;background-color: #56b430;float:left\"><br>\n" +
                " &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p><p style=\"font-size: 28px;color: #4c4c4c;margin:0px 0 10px 15px;height: 35px;line-height:35px;float:left;\">学习内容</p><p style=\"font-size: 28px;color: #4c4c4c;margin:0px 0 10px 15px;height: 35px;line-height:35px;float:left;\"><br>\n" +
                " &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p><p style=\"font-size: 28px;color: #4c4c4c;margin:0px 0 10px 15px;height: 35px;line-height:35px;float:left;\"><br>\n" +
                " &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p></td></tr></tbody></table>";
    }

    /**
     * @param courseFeature 课程亮点
     * @return 课程亮点Html
     */
    public static String getCourseFeatureInfo(String courseFeature){
        String prefix="<table border=\"0\" cellspacing=\"0\" align=\"center\" frame=\"void\" style=\"font-size: 20px;border-collapse: separate;padding: 0 18px;border:none;border-style: hidden;width: 100%;margin-bottom: 0;table-layout: fixed;\" width=\"493\">\n" +
                "   <tbody rules=\"none\">\n" +
                "    <tr rules=\"none\" class=\"firstRow\">\n";

        String suffix="    </tr>\n" +
                "   </tbody>\n" +
                "  </table>";
        if(!StringUtils.isEmpty(courseFeature)){
            String[] split = courseFeature.split("&");
            StringBuffer sb=new StringBuffer();
            for (String s : split) {
                if(!StringUtils.isEmpty(s)&&!StringUtils.isEmpty(s.trim())&&!"|".equals(s)){
                    sb.append("<td width=\"33.3%\" valign=\"top\" style=\"border-style: hidden; padding: 8px 12px 0px 0px; background-color: rgb(255, 255, 255); vertical-align: middle; color: rgb(255, 255, 255); word-break: break-all;\"><p style=\"background-color: rgb(86,180,48);padding: 15px 10px;word-break: normal;letter-spacing: 1.8px;\">"+s+"</p></td>");
                }
            }
            return courseFeatureTitleInfo() + prefix + sb + suffix;
        }else {
            String str = "<td width=\"33.3%\" valign=\"top\" style=\"border-style: hidden; padding: 8px 12px 0px 0px; background-color: rgb(255, 255, 255); vertical-align: middle; color: rgb(255, 255, 255); word-break: break-all;\"><p style=\"background-color: rgb(86,180,48);padding: 15px 10px;word-break: normal;letter-spacing: 1.8px;\"> 暂无 </p></td>";
            return courseFeatureTitleInfo() + prefix + str + suffix;
        }
    }

    private static String courseFeatureTitleInfo(){
        return "<table border=\"0\" cellspacing=\"0\" style=\"width:100%;border-style: hidden\" width=\"493\">\n" +
                "   <tbody>\n" +
                "    <tr class=\"firstRow\">\n" +
                "     <td style=\"padding: 0px; word-break: break-all;\" width=\"112\"><p style=\"margin:0px 0 10px 18px;height: 32px;width: 8px;background-color: #56b430;float:left\"><br /> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p><p style=\"font-size: 28px;color: #4c4c4c;margin:0px 0 10px 15px;height: 32px;line-height:32px;float:left;\">课程亮点</p></td>\n" +
                "    </tr>\n" +
                "   </tbody>\n" +
                "  </table>";
    }

    /**
     * @param teacherInfo
     * @param teacherUrl
     * @return 师资介绍Html
     */
    public static String getTeacherInfo(String teacherInfo,String teacherName,String teacherUrl){
        return teacherTitleInfo() + "<table border=\"0\" cellspacing=\"0\" align=\"center\" frame=\"void\" style=\"font-size: 20px;border-collapse: separate;padding: 0 18px;border:  none;border-style: hidden;width: 100%;margin-bottom: 0;\" width=\"493\">\n" +
                "   <tbody rules=\"none\">\n" +
                "    <tr rules=\"none\" style=\"background-color: rgb(238,247,234);\" class=\"firstRow\">\n" +
                "     <td valign=\"top\" style=\"text-align: center; border-style: hidden; padding: 30px; white-space: nowrap; vertical-align: middle; color: rgb(255, 255, 255); word-break: break-all;\"><p style=\"background-color:rgb(252,206,47);border-radius: 100px;width:  144px;height: 148px;\">" +
                "<img style=\"width:144px;height:144px;border-radius:100px;\" " +
                "src='"+teacherUrl+"' title=\"1568684038013085371.png\" alt=\"1.png\" /> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p></td>\n" +
                "     <td valign=\"top\" style=\"word-break: break-all;border-style: hidden;padding: 30px 30px 30px 0;\"><p style=\"background-color:rgb(86,180,48);width: 170px;height: 42px;font-size:25px;border-radius:30px;color: #fff;text-align:  center;line-height: 42px;\">" +
                teacherName+"</p><p style=\"\n" +
                "    padding-top: 16px;\n" + "\">" +
                teacherInfo+"</p></td>\n" +
                "    </tr>\n" +
                "   </tbody>\n" +
                "  </table>";
    }

    private static String teacherTitleInfo(){
        return "<table id=\"teachers-highlight\" border=\"0\" cellspacing=\"0\" style=\"width:100%;border-style: hidden\" width=\"493\">\n" +
                "   <tbody>\n" +
                "    <tr class=\"firstRow\">\n" +
                "     <td style=\"padding: 0px; word-break: break-all;\" width=\"235\"><p style=\"margin:0px 0 10px 18px;height: 35px;width: 8px;background-color: #56b430;float:left\"><br /> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p><p style=\"font-size: 28px;color: #4c4c4c;margin:0px 0 10px 15px;height: 35px;line-height:35px;float:left;\">师资介绍</p></td>\n" +
                "    </tr>\n" +
                "   </tbody>\n" +
                "  </table>";
    }

    /**
     * @param classDifficult 难度说明
     * @param classInfo 适合学员
     * @param courseObj 学习目标
     * @param textBook 配套讲义
     * @return 课程信息Html
     */
    public static String getCourseInfo(String classDifficult,String classInfo,String courseObj,String textBook){
        String titleInfoHtml=CourseTitleInfo();
        String classDifficultInfoHtml=classDifficultInfo(classDifficult);
        String classInfoHtml=classInfo(classInfo);
        String courseObjHtml=courseObj(courseObj);
        String textBookHtml=textBook(textBook);
        return titleInfoHtml + classDifficultInfoHtml + classInfoHtml + courseObjHtml + textBookHtml;
    }

    private static String CourseTitleInfo(){
        return "" +
                "<table class=\"course-title-table\" border=\"0\" cellspacing=\"0\" style=\"width:100%;border-style: hidden\">\n" +
                "   <tbody>\n" +
                "    <tr class=\"firstRow\">\n" +
                "     <td style=\"\n" +
                "        padding: 0 0 10px 0;\n" +
                "        \"><p style=\"margin: 35px 0 10px 18px;height: 32px;width: 8px;background-color: #56b430;float:left;\"><br /> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p><p style=\"font-size: 28px;color: #4c4c4c;margin: 35px 0 10px 15px;height: 32px;line-height:32px;float:left;\">课程信息</p></td>\n" +
                "                </tr>\n" +
                "   </tbody>\n" +
                "  </table>";
    }

    private static String classDifficultInfo(String classDifficult){
        int count=0;
        if(!StringUtils.isEmpty(classDifficult)){
            String[] split = classDifficult.trim().split("");
            for (String s : split) {
                if("★".equals(s)){
                    count++;
                }
            }
        }
        StringBuffer sb=new StringBuffer();
        String image="<img src=\"/libs/ueditor/upload/image/20181128/1543545333508059337.png\" title=\"\" alt=\"\" style=\"font-size: 19.2px;white-space: normal;vertical-align: -3px;\" />" ;
        for (int i=0;i<count;i++){
            sb.append(image);
        }
        String prefix="<table class=\"course-information-table\" border=\"0\" cellspacing=\"0\" align=\"center\" frame=\"void\" style=\"font-size: 28px;border-collapse: separate;padding: 0px 18px;border:  none;border-style: hidden;width: 100%;margin-bottom: 0;\">\n" +
                "   <tbody rules=\"none\">\n" +
                "    <tr rules=\"none\" class=\"firstRow\">\n" +
                "     <td width=\"1%\" valign=\"top\" style=\"text-align: center;border-style: hidden;background-color: rgb(86, 180, 48);padding: 10px;white-space: nowrap;vertical-align: middle;color: rgb(255, 255, 255);word-break: break-all;\">难度说明</td>\n" +
                "     <td width=\"99%\" valign=\"top\" style=\"border-style: hidden; background: rgb(242, 242, 242); padding: 20px 28px; word-break: break-all;\">" ;
        String suffix= "</tr></tbody></table>";
        return prefix+sb+suffix;
    }

    private static String classInfo(String classInfo) {
        return "<table border=\"0\" cellspacing=\"0\" align=\"center\" frame=\"void\" style=\"font-size: 28px;border-collapse: separate;padding: 0 18px;border:  none;border-style: hidden;width: 100%;margin-bottom: 0;\">\n" +
                "   <tbody rules=\"none\">\n" +
                "    <tr rules=\"none\" class=\"firstRow\">\n" +
                "     <td width=\"1%\" valign=\"top\" style=\"text-align: center;border-style: hidden;background-color: rgb(86,180,48);padding: 10px;white-space:nowrap;vertical-align: middle;color: #fff;\">适合学员</td>\n" +
                "     <td width=\"99%\" valign=\"top\" style=\"font-size: 22px;word-break: break-all;border-style: hidden;background: white;padding: .8em .5em;vertical-align: middle;\">\n" +
                "      <ul style=\"list-style-type: disc;\" class=\"list-ul list-paddingleft-2\">\n" +
                "       <li><p>"+classInfo+"</p></li>\n" +
                "      </ul></td>\n" +
                "    </tr>\n" +
                "   </tbody>\n" +
                "  </table>";
    }

    private static String courseObj(String courseObj) {
        return "<table border=\"0\" cellspacing=\"0\" align=\"center\" frame=\"void\" style=\"font-size: 28px;border-collapse: separate;padding: 0 18px;border:  none;border-style: hidden;width: 100%;margin-bottom: 0;\">\n" +
                "   <tbody rules=\"none\">\n" +
                "    <tr rules=\"none\" class=\"firstRow\">\n" +
                "     <td width=\"1%\" valign=\"top\" style=\"text-align: center;border-style: hidden;background-color: rgb(86,180,48);padding: 10px;white-space:nowrap;vertical-align: middle;color: #fff;\">学习目标</td>\n" +
                "     <td width=\"99%\" valign=\"top\" style=\"word-break: break-all;border-style: hidden;background: rgb(242,242,242);padding: .8em .5em;vertical-align: middle;font-size: 22px;\">\n" +
                "      <ul style=\"list-style-type: disc;\" class=\"list-ul-li list-paddingleft-2\">\n" +
                "       <li><p>"+courseObj+"</p></li>\n" +
                "      </ul></td>\n" +
                "    </tr>\n" +
                "   </tbody>\n" +
                "  </table>";
    }

    private static String textBook(String textBook) {
        return "<table border=\"0\" cellspacing=\"0\" align=\"center\" frame=\"void\" style=\"font-size: 28px;border-collapse: separate;padding: 0 18px;border:  none;border-style: hidden;width: 100%;margin-bottom: 20px;\">\n" +
                "   <tbody rules=\"none\">\n" +
                "    <tr rules=\"none\" class=\"firstRow\">\n" +
                "     <td width=\"1%\" valign=\"top\" style=\"text-align: center;border-style: hidden;background-color: rgb(86,180,48);padding: 10px;white-space:nowrap;vertical-align: middle;color: #fff;\">配套讲义</td>\n" +
                "     <td width=\"99%\" valign=\"top\" style=\"word-break: break-all;border-style: hidden;background: rgb(242,242,242);/* padding: .8em .8em; */padding: 20px 28px;vertical-align:  middle;font-size: 22px;\">" +
                textBook+"</td>\n" +
                "    </tr>\n" +
                "   </tbody>\n" +
                "  </table>";
    }

}
