package com.xiaoyi.bis.xiaoyi.constant;

import java.util.Calendar;

/**
 * 第三方接口调用所用常量类
 * @author CJ
 * @date 2019/10/12
 */
public class APIConstant {

    //**************添翼申学接口配置参数**************
    //md5前拼接参数后缀
    public static final String API_PREFIX="liveCourse!@#$%19";
    //des秘钥
    public static final String API_ENCRYPE="tyxs9sx";
    //平台
    public static final String API_PLATFORMTYPE="liveCourseConnect";
    //添加教师Code
    public static final String API_AddTEACHERCODE="addTeacher";
    //添加教师URL
    public static final String API_AddTEACHERURL="https://www.ty-sx.com/thirdparty/liveCourseMaintenance/addTeacher";
    //添加直播课程Code
    public static final String API_AddLIVECOURSECODE="addLiveCourse";
    //添加直播课程URL
    public static final String API_AddLIVECOURSEURL="https://www.ty-sx.com/thirdparty/liveCourseMaintenance/addLiveClass";
    //添加直播课节Code
    public static final String API_AddLESSONCODE="addLesson";
    //添加直播课节Url
    public static final String API_AddLESSONURL="https://www.ty-sx.com/thirdparty/liveCourseMaintenance/addLesson";
    //查询直播课程详情Code
    public static final String API_GETCLASSDETAILCODE="getClassDetail";
    //查询直播课程详情URL
    public static final String API_GETCLASSDETAILURL="https://www.ty-sx.com/thirdparty/liveCourseMaintenance/getClassDetail";
    //查询课程订单Code
    public static final String API_GETCLASSORDERCODE="getClassOrder";
    //查询课程订单URL
    public static final String API_GETCLASSORDERURL="https://www.ty-sx.com/thirdparty/liveCourseMaintenance/getClassOrder";
    //添加录播课程Code
    public static final String API_AddRECORDCODE="addRecordClass";
    //添加录播课程URL(测试地址)
    //public static final String API_AddRECORDURL="http://frp.o-learn.cn:51085/thirdparty/liveCourseMaintenance/addRecordClass";
    //添加录播课程URL(正式地址)
    //public static final String API_AddRECORDURL="http://www.ty-sx.com/thirdparty/liveCourseMaintenance/addRecordClass";



    //**************保利威接口配置参数**************
    //用户编号(待确认)
    public static final String API_USER_ID="aef3afd3d0";
    //应用编号
    public static final String API_APP_ID="esacskqwxf";
    public static final String API_SECRET_KEY="SoOP5Ph28L";
    public static final String API_APP_SECRET_KEY="676fb4d7f8704b35a66af31431320e38";
    //当前时间的秒级时间戳（13位）
    public static final String API_TIMESTAMP_VALUE=String.valueOf(Calendar.getInstance().getTimeInMillis());

    //**************添翼申学接口参数名称**************
    public static final String API_FUNNTIONCODE="functionCode";
    public static final String API_NOTE="note";
    public static final String API_USERID="userId";
    public static final String API_SITENAME="siteName";
    public static final String API_DEFAULTVALUE="添翼申学";
    public static final String API_SITENAMEVALUE="正承教育";
    public static final String API_NAME="name";
    public static final String API_ORIENT="orient";
    public static final String API_PLATFORM="platform";
    public static final String API_TIMESTAMP="timestamp";
    public static final String API_KEY="key";
    public static final String API_MAXNUM="maxNum";
    public static final String API_CODE="code";
    public static final String API_COVERIMALINK="coverImgLink";
    public static final String API_ENROLLSTARTDATE="enrollStartDate";
    public static final String API_ENROLLENDDATE="enrollEndDate";
    public static final String API_CLASSHOUR="classHour";
    public static final String API_CLASSTYPE="classType";
    public static final String API_PRIMECOST="primeCost";
    public static final String API_COST="cost";
    public static final String API_COURSEINTRODUCEIMG="courseIntroduceImg";
    public static final String API_COURSEINFORMATION="courseInformation";
    public static final String API_COURSETEACHERSHIGHLIGHT="courseTeachersHighlight";
    public static final String API_COURSEHIGHLIGHT="courseHighlight";
    public static final String API_COURSELEARNINGCONTENT="courseLearningContent";
    public static final String API_COURSEOBSERVATIONSTYLE="courseObservationStyle";
    public static final String API_COURSECONSULTANT="courseConsultant";
    public static final String API_COURSEWARMPROMPT="courseWarmPrompt";
    public static final String API_TEACHERID="teacherId";
    public static final String API_CLASSID="classId";
    public static final String API_LIVECONTENT="liveContent";
    public static final String API_LIVESTARTDATE="liveStartDate";
    public static final String API_LIVEENDDATE="liveEndDate";
    public static final String API_LESSONHOUR="lessonHour";
    public static final String API_LIVEMANNUMBER="liveManNumber";
    public static final String API_SHOWEVALUATION="showEvaluation";
    public static final String API_EXPIRATIONDURATION="expirationDuration";

}
