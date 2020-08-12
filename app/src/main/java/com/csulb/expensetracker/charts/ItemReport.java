package com.csulb.expensetracker.charts;

import android.os.Bundle;
        import android.widget.Toast;

        import androidx.appcompat.app.AppCompatActivity;

        import com.anychart.AnyChart;
        import com.anychart.AnyChartView;
        import com.anychart.chart.common.dataentry.DataEntry;
        import com.anychart.chart.common.dataentry.ValueDataEntry;
        import com.anychart.chart.common.listener.Event;
        import com.anychart.chart.common.listener.ListenersInterface;
        import com.anychart.charts.Pie;
        import com.anychart.enums.Align;
        import com.anychart.enums.LegendLayout;

        import java.util.ArrayList;
        import java.util.List;

        import com.csulb.expensetracker.service.UserData;
        import com.csulb.expensetracker.service.ExpenseData;
        import com.csulb.quiztion.R;

public class ItemReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemized_report);

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Pie pie = AnyChart.pie();

        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(ItemReport.this, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });

        List<DataEntry> data = new ArrayList<>();
        ExpenseData Ex=new ExpenseData(getApplicationContext());
        UserData DBH=new UserData(getApplicationContext());
        String userid=DBH.getId();
        String temp[]=Ex.getitemized(userid).split(",");
        for(int i=0;i<temp.length;i++){
            int pri=Integer.parseInt(temp[i]);
            String itemnames=temp[++i];
            data.add(new ValueDataEntry(itemnames,pri));
        }

        pie.data(data);

        pie.title("Itemized Report");

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Items")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView.setChart(pie);
    }
}