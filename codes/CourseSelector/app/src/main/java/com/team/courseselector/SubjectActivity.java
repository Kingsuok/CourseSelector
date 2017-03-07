package com.team.courseselector;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.SearchView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubjectActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    SubjectAdapter mSubjectAdapter;
    Toolbar mToolbar;
    String[] bakStringList;
    String[] bakStringShown;
    private static final String SUBJECT_NAME="Subject";
    String[] mStringVPA=new String[]{"AB", "ACINTY","ARCHL","ASTRN", "AVIA","BOT","CEDEV","COMPT",
            "COMST","CONST","COOP","CULT","DEVIS","ELPE","FILM","GEOL","GLOBAL","HEBRW","HS","INTERN","KPE","EVSY",
            "LANG","MEDST","MI","MISC","NES","PD","PED","RELC","RSCH","SE","SOCWL","THTRE","UNIV","URBAN","UU",
            "WKRPT","ZOOL"};
    String[] mStringART=new String[]{"ACC","AFM","ANTH","ARBUS","ART","ARTS","CCIV","CLAS","COGSCI","CROAT"
            ,"CULMG","DAC","DANCE","DEI","DRAMA","DUTCH","ECON","ENGL","FINAN","FINE","FR","FRCS","GBDA","GER","GGOV"
            ,"GRK","HIST","HRCS", "HRM","HUNGN","IFS","INTST","INTTS","JS","LAT","LATAM","LS","LSC","MEDVL","MES","PAS",
            "PERST","PHIL","POLSH","PORT","PS", "PSCI","PSYCH","REES","RS","RUSS","SOC","SPAN","SPCOM","TAX","UKRAN",
            "VCULT","WS"};
    String[] mStringMAT=new String[]{"ACTSC","AMATH","STAT","SEQ","BUS","CM","CO","COMM","CS","MATBUS",
            "MATH","MTHEL","PMATH"};
    String[] mStringENG=new String[]{"ADMGT","APHYS","SWREN","ARCH","BE","BET","BME","CHE","CIVE","DES","DM","ECE","ELE",
            "ENVE","GENE","GEOE","ME","MSCI","MSE","MTE", "NE","PDARCH","PDENG","STV","SYDE","TPM","TPPE","UN"};
    String[] mStringENV=new String[]{"AES","EBUS","ENBUS","ENVS","ERS","GEMCC","GEOG","INDEV","INTEG",
            "LED","MENV","PLAN","SOCIN","SUSM","TOUR"};
    String[] mStringAHS=new String[]{"AHS","GERON","HLTH","HSG","KIN","PHS","REC","SWK"};
    String[] mStringREN=new String[]{"APPLS","ASIAN","BASE","CHINA","EASIA","EFAS","EMLS","ESL","ISS","JAPAN","KOREA",
            "SDS","SI","SOCWK"};
    String[] mStringSCI=new String[]{"BIOL","CHEM","EARTH","MNS","OPTOM","PDPHRM","PHARM","PHYS","SCBUS","SCI","WHMIS"};
    String[] mStringSTP=new String[]{"CDNST","NATST","SIPAR","SPD"};
    String[] mStringCGC=new String[]{"CMW","MUSIC","PACS","TS"};
    String[] mStringSTJ=new String[]{"CT","HUMSC","ITAL","ITALST","SMF"};
    String[] mStringGRAD=new String[]{"GRAD","GS","NANO","QIC","TN","WATER"};
    String[] mStringIS=new String[]{"IS"};

    String[] mStringVPA2=new String[]{"Arabic (WLU)", "Academic Integrity","Archeology (WLU)","Astronomy (WLU)", "Aviation","Botany (WLU)","Children's Education & Development (WLU)","Computing (WLU)",
            "Communication Studies (WLU)","Contemporary Studies (WLU)","Co-op","Cultural Studies","Development & International Studies (WLU)","English Language Proficiency Examination","Film (WLU)","Geology (WLU)","Global Studies (WLU)","Hebrew (WLU)","High School","Internship","Kinesiology and Physical Education (WLU)"
            ,"Environment and Society","Language (WLU)","Media Studies (WLU)","Mediterranean Studies (WLU)","Miscellaneous","Near Eastern Studies (WLU)","Professional Development","Physical Education (WLU)","Religion & Culture (WLU)","Research","Software Engineering","Social Welfare (WLU)","Theatre (WLU)","University","Urban Studies (WLU)","University Interdisciplinary Studies (WLU)",
            "Work-term Report","Zoology (WLU)"};
    String[] mStringART2=new String[]{"Accounting","Accounting and Financial Managment","Anthropology","Arts and Business","Art","Arts","Classical Civilization","Classical Studies","Cognitive Science","Croatian"
            ,"Cultural Management","Digital Arts Communication","Dance","Digital Experience Innovation","Drama","Dutch","Economics","English","Finance","Fine Arts","French Studies","French Cultural Studies","Global Business and Digital Arts","German","Global Governance"
            ,"Greek","History","Human Relations & Counselling Studies", "Human Resources Management","Hungarian","Inter-Faculty Studies","International Studies","International Trade Seminars","Jewish Studies","Latin","Latin American Studies","Legal Studies","Legal Studies and Criminology","Medieval Studies","Middle Eastern Studies","Personnel & Administration Studies",
            "Personnel Studies","Philosophy","Polish","Portuguese","Public Serivce", "Political Science","Psychology","Russian and Eastern European Studies","Religious Studies","Russian","Sociology"," Spanish","Speech Communication","Taxation","Ukrainian",
            "Visual Culture","Women's Studies"};
    String[] mStringMAT2=new String[]{"Actuarial Science","Applied Mathematics","Statistics","Co-op Sequence","Business","Computational Mathematics","Combinatorics and Optimization","Commerce","Computer Science","Mathematical Business",
            "MATH","Mathematics Elective","Pure Mathematics"};
    String[] mStringENG2=new String[]{"Advanced Management","Applied Physics","Social Work (Bachelor of Social Work)","Architecture","Business Entrepreneurship","Business, Entrepreneurship and Technology","Biomedical Engineering","Chemical Engineering","Civil Engineering","Design","Design and Manufacturing","Electrical and Computer Engineering","Electrical Engineering",
            "Environmental Engineering","General Engineering","Geological Engineering","Mechanical Engineering","Management Sciences","Management & Systems","Mechatronics Engineering", "Nanotechnology Engineering","Professional Development for Architecture Students","Professional Development for Engineering Students","Society, Technology and Values","Systems Design Engineering","Technical Presentation Milestone","Technical Presentation Proficiency Requirement","Nuclear Engineering"};
    String[] mStringENV2=new String[]{"Applied Environmental Studies","Environment and Business","Environment and Business","Environmental Studies","Environment and Resource Studies","Geography and Environmental Mgmt, Climate Change","Geography","International Development","Integrated Studies",
            "Local Economic Development","Man Environment","Planning","Social Innovation","Sustainability Management","Tourism"};
    String[] mStringAHS2=new String[]{"Applied Health Sciences","Gerontology","Health Studies","Health Studies and Gerontology","Kinesiology","Public Health Sciences","Recreation and Leisure Studies","Social Work"};
    String[] mStringREN2=new String[]{"Applied Language Studies","Asian Studies","Bridge to Academic Success in English","Chinese","East Asian Studies","English for Academic Success","English for Multilingual Speakers","English as a Second Language","Interdisciplinary Social Sciences","Japanese","Korean",
            "Social Development Studies","Studies in Islam","Social Work (Social Development Studies)"};
    String[] mStringSCI2=new String[]{"Biology","Chemistry","Earth Science","Materials and Nano-Sciences","Optometry","PDPHRM","Pharmacy","Physics","Science and Business","Science","Workplace Hazardous Materials Information Systems"};
    String[] mStringSTP2=new String[]{"Canadian Studies","Native Studies","Studies in Personality & Religion","Spirituality and Personal Development"};
    String[] mStringCGC2=new String[]{"Church Music and Worship","Music","Peace and Conflict Studies","Theological Studies"};
    String[] mStringSTJ2=new String[]{"Catholic Thought","Human Sciences","Italian","Italian Studies","Sexuality, Marriage and the Family"};
    String[] mStringGRAD2=new String[]{"Continuing Graduate Studies","Graduate Studies","Nanotechnology","Quantum Information and Computation","Theoretical Neuroscience","Water"};
    String[] mStringIS2=new String[]{"Independent Studies"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        mToolbar=(Toolbar)findViewById(R.id.tb_appbar_subject);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView mRecyclerView=(RecyclerView)findViewById(R.id.rv_subject);
        LinearLayoutManager mLinearLayoutManager=new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        Intent mIntent=getIntent();
        String mString=mIntent.getStringExtra(SUBJECT_NAME);
        setTitle(mString);
        Log.i("tamier log",mString);
        //switch (mString){
            if( mString.equals("VPA")) {
                mSubjectAdapter = new SubjectAdapter(mStringVPA,mStringVPA2);
            }
            else if(mString.equals("ART")){
                mSubjectAdapter = new SubjectAdapter(mStringART,mStringART2);
            }
            else if(mString.equals("MAT")){
                mSubjectAdapter = new SubjectAdapter(mStringMAT,mStringMAT2);
            }
            else if(mString.equals("ENG")){
                mSubjectAdapter = new SubjectAdapter(mStringENG,mStringENG2);
            }
            else if(mString.equals("ENV")){
                mSubjectAdapter = new SubjectAdapter(mStringENV,mStringENV2);
            }
            else if(mString.equals("AHS")){
                mSubjectAdapter = new SubjectAdapter(mStringAHS,mStringAHS2);
            }
            else if(mString.equals("REN")){
                mSubjectAdapter = new SubjectAdapter(mStringREN,mStringREN2);
            }
            else if(mString.equals("SCI")){
                mSubjectAdapter = new SubjectAdapter(mStringSCI,mStringSCI2);
            }
            else if(mString.equals("STP")){
                mSubjectAdapter = new SubjectAdapter(mStringSTP,mStringSTP2);
            }
            else if(mString.equals("CGC")){
                mSubjectAdapter = new SubjectAdapter(mStringCGC,mStringCGC2);
            }
            else if(mString.equals("STJ")){
                mSubjectAdapter = new SubjectAdapter(mStringSTJ,mStringSTJ2);
            }
            else if(mString.equals("GRAD")){
                mSubjectAdapter = new SubjectAdapter(mStringGRAD,mStringGRAD2);
            }
            else if(mString.equals("IS")){
                mSubjectAdapter = new SubjectAdapter(mStringIS,mStringIS2);
            }
            bakStringList=mSubjectAdapter.getmStringList();
            bakStringShown=mSubjectAdapter.getmStringShown();
        //}
        mSubjectAdapter.SetOnItemClickListener(new SubjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String subjectname) {
                Intent mGoToCourseListIntent=new Intent(SubjectActivity.this,CourseListActivity.class);
                mGoToCourseListIntent.putExtra(SUBJECT_NAME,subjectname);
                //Toast.makeText(SubjectActivity.this,subjectname,Toast.LENGTH_SHORT).show();
                startActivity(mGoToCourseListIntent);
            }
        });
        mRecyclerView.setAdapter(mSubjectAdapter);

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<String> mList=new ArrayList<String>();
        List<String> mListShown=new ArrayList<String>();
        Log.i("tamier log", bakStringList.toString());
        Log.i("tamier log","newText="+newText);
        FilteredHolder resultHolder=filter(bakStringShown,newText);
        mList=resultHolder.getList();
        mListShown=resultHolder.getListShown();
        //mList=filter(mSubjectAdapter.getmStringList(),newText);
        //mList.add("result1");
        //mList.add("result2");
        //mList.add("result3");

        String[] filteredString=new String[mList.size()];
        String[] filteredStringShown=new String[mList.size()];
        int i;
        for(i=0;i<mList.size();i++){
            filteredString[i]=mList.get(i);
            filteredStringShown[i]=mListShown.get(i);
            Log.i("tamier log",filteredString[i]);
        }

        mSubjectAdapter.setFilter(filteredString,filteredStringShown);
        return true;
    }

    public FilteredHolder filter(String[] originalString,String query){
        FilteredHolder mFilterHolder=new FilteredHolder();
        List<String> list=new ArrayList<String>();
        List<String> listShown=new ArrayList<String>();
        query=query.toLowerCase();
        int i;
        //for(String s:originalString){
        for(i=0;i<originalString.length;i++){
            //if (s.toLowerCase().contains(query)){
            if(originalString[i].toLowerCase().contains(query)){
                //Log.i("tamier sq",s);
                //Log.i("tamier sq",query);
                list.add(bakStringList[i]);
                listShown.add(originalString[i]);
            }

        }
        mFilterHolder.setList(list);
        mFilterHolder.setListShown(listShown);
        //list.add("res1");
        return mFilterHolder;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_subject, menu);
        final MenuItem item=menu.findItem(R.id.menu_search);
        final SearchView mSearchView=(SearchView) MenuItemCompat.getActionView(item);
        mSearchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                mSubjectAdapter.setFilter(bakStringList,bakStringShown);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_search) {
            return true;
        }
        else if(id==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
