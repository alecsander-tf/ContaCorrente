package br.com.contacorrente;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ChartActivity extends AppCompatActivity {

    private LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        chart = findViewById(R.id.chart1);

        iniciarGrafico();
        prepararGrafico(2);
    }

    private void iniciarGrafico() {

        //painel escuro no fundo do gráfico
        chart.setDrawGridBackground(false);

        //descrição do gráfico no canto esquerdo da parte de baixo dentro gráfico
        chart.getDescription().setEnabled(false);

        //bordas fora do gráfico
        chart.setDrawBorders(false);

        chart.getAxisLeft().setEnabled(true);
        chart.getAxisRight().setEnabled(false);

        //linha no eixo Y
        chart.getXAxis().setDrawAxisLine(true);

        //linhas do grid do gráfico
        chart.getXAxis().setDrawGridLines(true);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawAxisLine(true);

        //deixa a legenda do eixo no meio
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f); // one hour
        xAxis.setValueFormatter(new ValueFormatter() {

            private final SimpleDateFormat mFormat = new SimpleDateFormat("dd/MM HH:mm", Locale.ENGLISH);

            @Override
            public String getFormattedValue(float value) {

                long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format(new Date(millis));
            }
        });
    }

    private final int[] colors = new int[] {
            ColorTemplate.VORDIPLOM_COLORS[0],
            ColorTemplate.VORDIPLOM_COLORS[1],
            ColorTemplate.VORDIPLOM_COLORS[2]
    };

    void prepararGrafico(int numeroDeLinhas){
        chart.resetTracking();

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        for (int z = 0; z < numeroDeLinhas; z++) {

            ArrayList<Entry> values = new ArrayList<>();

            for (int i = 0; i < 3; i++) {
                values.add(new Entry(i, (int) (Math.random() * 65)));
            }

            LineDataSet d = new LineDataSet(values, "Sensor " + (z + 1));
            d.setLineWidth(2.5f);
            d.setCircleRadius(4f);

            int color = colors[z % colors.length];
            d.setColor(color);
            d.setCircleColor(color);
            dataSets.add(d);
        }

        LineData data = new LineData(dataSets);
        chart.setData(data);
        chart.invalidate();
    }

    public void novaEntrada(View view) {

        int valorAleatorio = (int) (Math.random() * 50) + 1;
        addEntry(0, valorAleatorio);
    }

    /**
     * @param index linha que será adicionada
     * @param value valor que será adicionado
     */
    private void addEntry(int index, int value) {

        LineData data = chart.getData();

        data.addEntry(new Entry(data.getDataSetByIndex(index).getEntryCount(), value), index);
        data.notifyDataChanged();

        // let the chart know it's data has changed
        chart.notifyDataSetChanged();

        chart.setVisibleXRangeMaximum(6);

        // this automatically refreshes the chart (calls invalidate())
        chart.moveViewTo(data.getEntryCount() - 7, 50f, YAxis.AxisDependency.LEFT);
    }
}
