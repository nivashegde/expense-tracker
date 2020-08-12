package com.csulb.expensetracker.charts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


        import com.anychart.AnyChart;
        import com.anychart.AnyChartView;
        import com.anychart.chart.common.dataentry.DataEntry;
        import com.anychart.chart.common.dataentry.ValueDataEntry;
        import com.anychart.charts.Cartesian;
        import com.anychart.core.cartesian.series.Column;
        import com.anychart.enums.Anchor;
        import com.anychart.enums.HoverMode;
        import com.anychart.enums.Position;
        import com.anychart.enums.TooltipPositionMode;

        import java.util.ArrayList;
        import java.util.List;

import com.csulb.expensetracker.service.UserData;
import com.csulb.expensetracker.service.ExpenseData;
import com.csulb.expensetracker.service.IncomeData;
import com.csulb.quiztion.R;

public class SavingsReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings_report);

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.column();
        IncomeData D1=new IncomeData(getApplicationContext());
        ExpenseData Ex=new ExpenseData(getApplicationContext());
        UserData DBH=new UserData(getApplicationContext());
        String userid=DBH.getId();
        String temp[]=Ex.getexpense(userid).split(",");
        float x1=((float) Integer.parseInt(D1.getAnnualIncome(userid))/365);
        List<DataEntry> data = new ArrayList<>();
        for(int i=0;i<temp.length;i++)
        {
            int t=(int)x1;
            int pri=t-Integer.parseInt(temp[i]);
            String dat=temp[++i];
            data.add(new ValueDataEntry(dat,pri));
        }

        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Daily Savings");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Date");
        cartesian.yAxis(0).title("Savings Price");

        anyChartView.setChart(cartesian);
    }
}