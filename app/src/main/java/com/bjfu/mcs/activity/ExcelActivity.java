package com.bjfu.mcs.activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.column.ColumnInfo;
import com.bin.david.form.data.format.IFormat;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.format.count.ICountFormat;
import com.bin.david.form.data.format.draw.ImageResDrawFormat;
import com.bin.david.form.data.format.draw.TextImageDrawFormat;
import com.bin.david.form.data.format.tip.MultiLineBubbleTip;
import com.bin.david.form.data.format.title.TitleImageDrawFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.PageTableData;
import com.bin.david.form.listener.OnColumnClickListener;
import com.bin.david.form.listener.OnColumnItemClickListener;
import com.bin.david.form.utils.DensityUtils;
import com.bjfu.mcs.R;
import com.bjfu.mcs.bean.ChildData;
import com.bjfu.mcs.bean.MergeInfo;
import com.bjfu.mcs.bean.UserInfo;

import com.bjfu.mcs.utils.BaseDialog;
import com.daivd.chart.component.axis.BaseAxis;
import com.daivd.chart.component.base.IAxis;
import com.daivd.chart.component.base.IComponent;
import com.daivd.chart.core.LineChart;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.LineData;
import com.daivd.chart.data.style.PointStyle;
import com.daivd.chart.provider.component.cross.VerticalCross;
import com.daivd.chart.provider.component.level.LevelLine;
import com.daivd.chart.provider.component.mark.BubbleMarkView;
import com.daivd.chart.provider.component.point.Point;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExcelActivity extends AppCompatActivity implements View.OnClickListener {

    //private SmartTable<MergeInfo> table;
    private SmartTable<UserInfo> table;
    private PageTableData<UserInfo> tableData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel);
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this,15));
        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("EXCEL展示");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        table = (SmartTable<UserInfo>) findViewById(R.id.table);
//        List<MergeInfo> list = new ArrayList<>();
//        for(int i = 0;i <50; i++) {
//            list.add(new MergeInfo("张某某", 18, System.currentTimeMillis(),true,new ChildData("测试1")));
//            list.add(new MergeInfo("刘某某", 18, System.currentTimeMillis(),true,new ChildData("测试1")));
//            list.add(new MergeInfo("huang", 18, System.currentTimeMillis(),true,new ChildData("测试1")));
//            list.add(new MergeInfo("huang", 18, System.currentTimeMillis(),true,new ChildData("测试1")));
//            list.add(new MergeInfo("huang", 18, System.currentTimeMillis(),true,new ChildData("测试1")));
//            list.add(new MergeInfo("li", 23, System.currentTimeMillis(),false,null));
//            list.add(new MergeInfo("li", 23, System.currentTimeMillis(),false,null));
//            list.add(new MergeInfo("li", 23, System.currentTimeMillis(),false,null));
//            list.add(new MergeInfo("li", 23, System.currentTimeMillis(),false,null));
//        }
//        table = (SmartTable<MergeInfo>) findViewById(R.id.table);
//        table.setData(list);
//        table.getConfig().setShowTableTitle(false);
//        table.setZoom(true,2,0.2f);
        
        initdatas();
    }

    private void initdatas() {
        final List<UserInfo> testData = new ArrayList<>();
        Random random = new Random();
        for(int i = 0;i <500; i++) {
            testData.add(new UserInfo("用户行为"+(i+1), random.nextInt(70), System.currentTimeMillis()
                    - random.nextInt(70)*3600*1000*24,true,new ChildData("分钟数："+i)));
        }

        final Column<String> nameColumn = new Column<>("用户行为", "name");
        nameColumn.setAutoCount(true);
        final Column<Integer> ageColumn = new Column<>("时长", "age");
        ageColumn.setAutoCount(true);
        final IFormat<Long> format =  new IFormat<Long>() {
            @Override
            public String format(Long aLong) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(aLong);
                return calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
            }
        };
        final Column<Long> timeColumn = new Column<>("时间", "time",format);
        timeColumn.setCountFormat(new ICountFormat<Long, Long>() {
            private long maxTime;
            @Override
            public void count(Long aLong) {
                if(aLong > maxTime){
                    maxTime = aLong;
                }
            }

            @Override
            public Long getCount() {
                return maxTime;
            }

            @Override
            public String getCountString() {
                return format.format(maxTime);
            }

            @Override
            public void clearCount() {
                maxTime =0;
            }
        });
        int size = DensityUtils.dp2px(this,15);
        Column<Boolean> column5 = new Column<>("是否参与", "isCheck", new ImageResDrawFormat<Boolean>(size,size) {
            @Override
            protected Context getContext() {
                return ExcelActivity.this;
            }

            @Override
            protected int getResourceID(Boolean isCheck, String value, int position) {
                if(isCheck){
                    return R.mipmap.check;
                }
                return 0;
            }
        });
        Column<String> column4 = new Column<>("停留时长", "childData.child");
        column4.setAutoCount(true);
        Column<Boolean> column6 = new Column<>("停留开始时间", "isCheck", new TextImageDrawFormat<Boolean>(size,size, TextImageDrawFormat.LEFT,10) {
            @Override
            protected Context getContext() {
                return ExcelActivity.this;
            }

            @Override
            protected int getResourceID(Boolean isCheck, String value, int position) {
                if(isCheck){
                    return R.mipmap.clock_fill;
                }
                return 0;
            }
        });

        Column<Boolean> column7 = new Column<>("勾选3", "isCheck", new TextImageDrawFormat<Boolean>(size,size, TextImageDrawFormat.RIGHT,10) {
            @Override
            protected Context getContext() {
                return ExcelActivity.this;
            }

            @Override
            protected int getResourceID(Boolean isCheck, String value, int position) {
                if(isCheck){
                    return R.mipmap.activity_fill;
                }
                return 0;
            }
        });
        Column<Boolean> column8 = new Column<>("勾选4", "isCheck", new TextImageDrawFormat<Boolean>(size,size, TextImageDrawFormat.TOP,10) {
            @Override
            protected Context getContext() {
                return ExcelActivity.this;
            }

            @Override
            protected int getResourceID(Boolean isCheck, String value, int position) {
                if(isCheck){
                    return R.mipmap.brush_fill;
                }
                return 0;
            }
        });
        Column<Boolean> column9 = new Column<>("勾选5", "isCheck", new TextImageDrawFormat<Boolean>(size,size, TextImageDrawFormat.BOTTOM,10) {
            @Override
            protected Context getContext() {
                return ExcelActivity.this;
            }

            @Override
            protected int getResourceID(Boolean isCheck, String value, int position) {
                if(isCheck){
                    return R.mipmap.collection_fill;
                }
                return 0;
            }
        });
        Column totalColumn1 = new Column("总项1",nameColumn,ageColumn);
        Column totalColumn2 = new Column("总项2",nameColumn,ageColumn,timeColumn);
        Column totalColumn = new Column("总项",nameColumn,totalColumn1,totalColumn2,timeColumn);

        tableData = new PageTableData<>("详细数据展示",testData,nameColumn,column4,column5,column6,column7,column8,column9,totalColumn,totalColumn1,totalColumn2,timeColumn);


        tableData.setTitleDrawFormat(new TitleImageDrawFormat(size,size, TitleImageDrawFormat.RIGHT,10) {
            @Override
            protected Context getContext() {
                return ExcelActivity.this;
            }

            @Override
            protected int getResourceID(Column column) {
                if(!column.isParent()){
                    if(tableData.getSortColumn() == column){
                        setDirection(TextImageDrawFormat.RIGHT);
                        if(column.isReverseSort()){
                            return R.mipmap.sort_up;
                        }
                        return R.mipmap.sort_down;

                    }else{
                        setDirection(TextImageDrawFormat.LEFT);
                        if(column == nameColumn){
                            return R.mipmap.name;
                        }else if(column == ageColumn){
                            return R.mipmap.age;
                        }else if(column == timeColumn){
                            return R.mipmap.update;
                        }
                    }
                    return 0;
                }
                setDirection(TextImageDrawFormat.LEFT);
                int level = tableData.getTableInfo().getMaxLevel()-column.getLevel();
                if(level ==0){
                    return R.mipmap.level1;
                }else if(level ==1){
                    return R.mipmap.level2;
                }
                return 0;
            }
        });
        ageColumn.setOnColumnItemClickListener(new OnColumnItemClickListener<Integer>() {
            @Override
            public void onClick(Column<Integer> column, String value, Integer integer, int position) {
                Toast.makeText(ExcelActivity.this,"点击了"+value,Toast.LENGTH_SHORT).show();
            }
        });
        tableData.setShowCount(true);
        table.getConfig().setCountBackground(new BaseBackgroundFormat(getResources().getColor(R.color.windows_bg)))
                .setShowXSequence(false).setShowYSequence(false);
        FontStyle fontStyle = new FontStyle();
        fontStyle.setTextColor(getResources().getColor(android.R.color.white));
        MultiLineBubbleTip<Column> tip = new MultiLineBubbleTip<Column>(this,R.mipmap.round_rect,R.mipmap.triangle,fontStyle) {
            @Override
            public boolean isShowTip(Column column, int position) {
                if(column == nameColumn){
                    return true;
                }
                return false;
            }
            @Override
            public String[] format(Column column, int position) {
                UserInfo data = testData.get(position);
                String[] strings = {"批注","姓名："+data.getName(),"年龄："+data.getAge()};
                return strings;
            }
        };
        tip.setColorFilter(Color.parseColor("#FA8072"));
        tip.setAlpha(0.8f);
        table.getProvider().setTip(tip);
        table.setSortColumn(ageColumn,false);
        ageColumn.setComparator(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1- o2;
            }
        });
        table.setOnColumnClickListener(new OnColumnClickListener() {
            @Override
            public void onClick(ColumnInfo columnInfo) {
                if(!columnInfo.column.isParent()) {

                    if(columnInfo.column == ageColumn){
                        showChartDialog(tableData.getTableName(),nameColumn.getDatas(),ageColumn.getDatas());
                    }else{
                        table.setSortColumn(columnInfo.column, !columnInfo.column.isReverseSort());
                    }
                }
                Toast.makeText(ExcelActivity.this,"点击了"+columnInfo.column.getColumnName(),Toast.LENGTH_SHORT).show();
            }
        });
        table.getConfig().setTableTitleStyle(new FontStyle(this,15,getResources().getColor(R.color.arc1)));
        table.getConfig().setContentCellBackgroundFormat(new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                if(cellInfo.row %2 ==0) {
                    return ContextCompat.getColor(ExcelActivity.this, R.color.content_bg);
                }
                return TableConfig.INVALID_COLOR;
            }
        });

        tableData.setPageSize(10);
        table.setTableData(tableData);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.left:
                tableData.setCurrentPage(tableData.getCurrentPage()-1);
                table.notifyDataChanged();
                break;
            case R.id.right:
                tableData.setCurrentPage(tableData.getCurrentPage()+1);
                table.notifyDataChanged();
                break;
        }


    }



    /**
     * 测试是否可以兼容之前smartChart
     * @param tableName
     * @param chartYDataList
     * @param list
     */
    private void showChartDialog(String tableName,List<String> chartYDataList,List<Integer> list ){
        View chartView = View.inflate(this,R.layout.dialog_chart,null);
        LineChart lineChart = (LineChart) chartView.findViewById(R.id.lineChart);
        lineChart.setLineModel(LineChart.CURVE_MODEL);
        Resources res = getResources();
        com.daivd.chart.data.style.FontStyle.setDefaultTextSpSize(this,12);
        List<LineData> ColumnDatas = new ArrayList<>();
        ArrayList<Double> tempList1 = new ArrayList<>();
        ArrayList<String> ydataList = new ArrayList<>();
        for(int i = 0;i <30;i++){
            String value = chartYDataList.get(i);
            ydataList.add(value);
        }
        for(int i = 0;i <30;i++){
            int value = list.get(i);
            tempList1.add(Double.valueOf(value));
        }
        LineData columnData1 = new LineData(tableName,"", IAxis.AxisDirection.LEFT,getResources().getColor(R.color.arc1),tempList1);
        ColumnDatas.add(columnData1);
        ChartData<LineData> chartData2 = new ChartData<>("Area Chart",ydataList,ColumnDatas);
        lineChart.getChartTitle().setDirection(IComponent.TOP);
        lineChart.getLegend().setDirection(IComponent.BOTTOM);
        lineChart.setLineModel(LineChart.CURVE_MODEL);
        BaseAxis verticalAxis =  lineChart.getLeftVerticalAxis();
        BaseAxis horizontalAxis=  lineChart.getHorizontalAxis();
        //设置竖轴方向
        verticalAxis.setAxisDirection(IAxis.AxisDirection.LEFT);
        //设置网格
        verticalAxis.setDrawGrid(true);
        //设置横轴方向
        horizontalAxis.setAxisDirection(IAxis.AxisDirection.BOTTOM);
        horizontalAxis.setDrawGrid(true);
        //设置线条样式
        verticalAxis.getAxisStyle().setWidth(this,1);
        DashPathEffect effects = new DashPathEffect(new float[] { 1, 2, 4, 8}, 1);
        verticalAxis.getGridStyle().setWidth(this,1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
        horizontalAxis.getGridStyle().setWidth(this,1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
        lineChart.setZoom(true);
        //开启十字架
        lineChart.getProvider().setOpenCross(true);
        lineChart.getProvider().setCross(new VerticalCross());
        lineChart.getProvider().setShowText(true);
        //开启MarkView
        lineChart.getProvider().setOpenMark(true);
        //设置MarkView
        lineChart.getProvider().setMarkView(new BubbleMarkView(this));

        //设置显示标题
        lineChart.setShowChartName(true);
        //设置标题样式
        com.daivd.chart.data.style.FontStyle fontStyle = lineChart.getChartTitle().getFontStyle();
        fontStyle.setTextColor(res.getColor(R.color.arc_temp));
        fontStyle.setTextSpSize(this,15);
        LevelLine levelLine = new LevelLine(30);
        DashPathEffect effects2 = new DashPathEffect(new float[] { 1, 2,2,4}, 1);
        levelLine.getLineStyle().setWidth(this,1).setColor(res.getColor(R.color.arc23)).setEffect(effects);
        levelLine.getLineStyle().setEffect(effects2);
        lineChart.getProvider().addLevelLine(levelLine);
        Point legendPoint = (Point) lineChart.getLegend().getPoint();
        PointStyle style = legendPoint.getPointStyle();
        style.setShape(PointStyle.SQUARE);
        lineChart.getProvider().setArea(true);
        lineChart.getHorizontalAxis().setRotateAngle(90);
        lineChart.setChartData(chartData2);
        lineChart.startChartAnim(400);
        BaseDialog dialog = new  BaseDialog.Builder(this).setFillWidth(true).setContentView(chartView).create();
        dialog.show();
    }

}
