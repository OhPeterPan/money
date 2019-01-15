package com.zrdb.app.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.zrdb.app.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.tvActTitle)
    TextView tvActTitle;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tvActRightTitle)
    TextView tvActRightTitle;
    @BindView(R.id.ivToolbarRight)
    ImageView ivToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ivHosDetailPic)
    ImageView ivHosDetailPic;
    @BindView(R.id.tvHosDetailName)
    TextView tvHosDetailName;
    @BindView(R.id.tvHosDetailLev)
    TextView tvHosDetailLev;
    @BindView(R.id.tvHosDetailCate)
    TextView tvHosDetailCate;
    @BindView(R.id.tvHosDetailAddress)
    TextView tvHosDetailAddress;
    @BindView(R.id.expandable_text)
    TextView expandableText;
    @BindView(R.id.expand_collapse)
    ImageButton expandCollapse;
    @BindView(R.id.expand_text_view)
    ExpandableTextView expandTextView;
    @BindView(R.id.llHead)
    LinearLayout llHead;
    @BindView(R.id.tvHosDocSec)
    TextView tvHosDocSec;
    @BindView(R.id.ivHosDocSec)
    ImageView ivHosDocSec;
    @BindView(R.id.llHosDocSec)
    LinearLayout llHosDocSec;
    @BindView(R.id.tvHosDocTec)
    TextView tvHosDocTec;
    @BindView(R.id.ivHosDocTec)
    ImageView ivHosDocTec;
    @BindView(R.id.llHosDocTec)
    LinearLayout llHosDocTec;
    @BindView(R.id.llHosHeadFilter)
    LinearLayout llHosHeadFilter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvHosApply)
    TextView tvHosApply;
    public List<String> testList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 50; i++) {
            testList.add("一个测试:" + i);
        }
        expandTextView.setText("哈尔滨医科大学附属第二医院创建于1954年，是集医疗、教学、科研、预防、保健和康复为一体的大型综合性三级甲等医院。</p><p>　　医院占地面积50万平方米，建筑面积53万平方米，拥有1个门诊部、11个住院部及4所“院中院”——风湿病医院、心血管病医院、五官医院、糖尿病医院。全院职工4534人，其中卫生技术人员4008名，正、副高职930人，博士生导师62人，联合博导2名，硕士生导师346人,享受国务院特殊津贴18人，“龙江学者”7人、“国家杰出青年基金”获得者1人、“教育部新世纪优秀人才”1人；1人入选“长江学者”青年学者；1人入选中组部“万人计划青年拔尖人才”。开放床位6005张，年出院病人20万人次，年手术例数11万人次，年门诊量近200万人次。医院设有44个临床科室、13个医技科室、101个病房；拥有国家新药临床实验研究中心、国家药品临床研究机构、省部共建教育部心肌缺血和诊疗技术重点实验室、黑龙江省器官移植中心、产前诊断与遗传病诊断中心、血液净化中心、风湿病研究所、心血管外科研究所、黑龙江省麻醉与危重病学重点实验室、黑龙江省高校药物研究重点实验室、麻醉基础理论与应用重点实验室。医院拥有PET-CT、伽玛刀、X刀、3.0TMRI、256排螺旋CT、2.0DSA、ECT、双版DR、蔡司手术显微系统、直线加速器等先进医疗设备。</p><p>　　医院坚持开展高、精、尖医疗新技术，成功地完成心脏移植、肺移植、肝脏移植、脾脏移植、肾脏移植、睾丸移植、心肺联合移植、胰肾联合移植、甲状腺-甲状旁腺-胸腺联合移植、角膜移植、骨髓移植等移植工作，使我院在器官移植种类、技术和质量等方面都居国内领先地位。“换心人”于文峰术后现已存活22年，创造了心脏移植术后生命质量最好、生存时间最长的亚洲记录。心脏移植课题组在2002年荣获国家科技进步二等奖。肝脏移植病人也已健康存活19年。2006年成功完成全主动脉置换手术，成为国内能够开展此项技术的两家医院之一。“十二五”期间获省级“医疗新技术”一等奖139项。医院以“病员至上，细心医护”为院训，重视医德医风建设，开展“以病人为中心”的整体护理工作，满足、方便了不同层次患者的医疗、保健、康复需求。2001年4月，哈尔滨医科大学护理学院在我院成立，同年5月，我院原护理部李秋洁主任荣获“第38届国际南丁格尔奖”。获卫生部“2010年优质护理服务考核优秀医院”。</p><p>　　作为哈尔滨医科大学第二临床医学院，拥有一级学科博士点3个、二级学科博士点21个，三级学科博士、硕士点33个。临床医学博士后科研流动站和临床药学博士后科研工作站各1个,卫生部专科医师培训专业基地31个。获国家临床重点专科项目14项，省医疗质量控制中心14个，国家重点(培育)学科1个，省重点学科14个,省领军人才梯队10个，省特聘教授岗位学科8个，卫生部内镜专业技术培训基地4个，卫生部心血管介入技术培训基地3个，是卫生部临床药师培训基地。国家级特色专业2个，省重点专业3个，建设国家级精品资源共享课4门、省级精品课程6门、省级精品视频公开课1门。省教学名师1人，省师范先进个人3人；获国家级教学成果奖1项。承担普通教育(五年制、七年制、留学生)、研究生教育(博士、硕士)、成人和继续教育等多轨道教学任务。医院拥有5200平方米独立教学大楼、5000平方米“国家级实验教学示范中心”和“国家级虚拟仿真实验教学中心”、2.2万平米“全科医师临床培训示范基地”、1.4万平米本科生公寓和1.6万平米研究生公寓。“十二•五”以来主编国家规划教材、视听教材18部、副主编12部、参编47部。近三年共获批厅级以上教学课题51项，其中CMB项目1项；获得厅级以上教学成果19项；发表国家级教学论文94篇。</p><p>　　医院重视科研工作，近五年获各级、各类课题共1071项，其中科技部重点研发计划1项、国家自然科学基金重点项目3项、重大国际合作项目1项、国家“973”分课题1项、国家科技重大专项(子课题)1项、科技部国际合作与交流项目1项、国家自然科学基金项目139项；获得科研成果奖45项，其中教育部优秀成果奖3项、中华医学科技进步二等奖1项、黑龙江省政府科学技术进步一等奖3项、二等奖22项、三等奖9项、地市、厅局级科技进步奖7项；现任职中华医学会副主委以上4名、其他国家级副主任委员以上31名；在各类期刊学会上发表学术论文3577篇，被SCI收录904篇；积极开展对外交流与协作，与美国匹兹堡大学、迈阿密大学、加拿大多伦多大学等26所大学与医学院有着广泛的联系，并开展了多项科研合作，10年来仅在美国匹兹堡大学培训科研人员已达100余人次。美国匹兹堡大学、迈阿密大学、加拿大多伦多大学已成为我院国外培训的重要基地。</p><p>　　几年来，医院加强了内、外环境的改造。2008年新门诊大楼投入使用。2012年新建的科研楼、干部病房楼、综合病房楼总计17万平方米全部投入使用。2.2万平方米全科医师培训基地建成并投入使用。实现了病房“宾馆化”、庭院“花园化”，为病人营造了一个温馨、舒适的就医环境。</p><p>　　一流的技术、一流的服务赢得了社会广泛赞誉。医院重视三下乡工作，义诊足迹遍布黑龙江省各个市县，2010年医院购置国内首辆“汽车医院”，将三下乡工作开展到社区、村镇。医院曾 多次荣获国务院、国家劳动人事部、卫生部、省政府嘉奖；先后荣获“全国百佳医院”、“全国卫生系统先进集体”、“全国卫生系统行业作风建设先进集体”、“全国绿化四百佳单位”、国家十四部委授予“全国科技、文化、卫生‘三下乡’先进集体”、“全国五一劳动奖状”等光荣称号。</p><p>今天的哈医大二院已成为学科齐全、设备先进、技术精湛、环境优美的现代化医院和临床医学院。展望未来，我们将坚持“质量建院、创新立院、人才强院、科教兴院”的建院方针，坚持“以病人为中心，提高医疗服务质量，打造专科品牌特色，提升综合实力，保证医院可持续发展”的办院方向，为早日实现国内一流、国际有影响的医院而努力奋斗。");
        TestAdapter adapter = new TestAdapter(testList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private class TestAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public TestAdapter(@Nullable List<String> data) {
            super(R.layout.adapter_city, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tvAdapterClassRight, item);
        }
    }
}
