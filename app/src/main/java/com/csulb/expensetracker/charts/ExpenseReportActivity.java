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
        import com.csulb.quiztion.R;

public class ExpenseReportActivity extends AppCompatActivity {

    private ExpenseData expenseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_report);
        expenseData =new ExpenseData(getApplicationContext());
        UserData DBH=new UserData(getApplicationContext());
        String userid=DBH.getId();
        String temp[]= expenseData.getexpense(userid).split(",");
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        for(int i=0;i<temp.length;i++){
            int pri=Integer.parseInt(temp[i]);
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
        cartesian.title("Daily Expense");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Dates");
        cartesian.yAxis(0).title("Expense Prices");

        anyChartView.setChart(cartesian);
    }
}