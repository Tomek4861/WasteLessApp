package com.example.wastelessapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.ProvideVicoTheme
import com.patrykandpatrick.vico.compose.common.VicoTheme
import com.patrykandpatrick.vico.core.cartesian.CartesianMeasuringContext
import com.patrykandpatrick.vico.core.cartesian.axis.Axis
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import kotlinx.serialization.Serializable

@Serializable
object StatisticsScreen

@Preview
@Composable
fun StatisticsScreen() {
    Column(){

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ){

            Text(
                "Your Statistics",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Last 30 days:",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            StatisticsRow("Items saved", "98")
            HorizontalDivider(thickness = 2.dp)
            StatisticsRow("Items lost", "2")
            HorizontalDivider(thickness = 2.dp)
            StatisticsRow("Percentage of items lost", "2.0%")
            // TODO change values to ones from queries

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "All time:",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            StatisticsRow("Items saved", "1576")
            HorizontalDivider(thickness = 2.dp)
            StatisticsRow("Items lost", "113")
            HorizontalDivider(thickness = 2.dp)
            StatisticsRow("Percentage of items lost", "6.7%")
            // TODO change values to ones from queries

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Lost items this year",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Chart()

        }
    }
}

@Composable
fun StatisticsRow(
    text: String,
    value: String
){
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = text,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )

        Text(
            text = value,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
    }
}

@Composable
fun Chart() {
//    val modelProducer = remember { CartesianChartModelProducer() }
//    LaunchedEffect(Unit) {
//        modelProducer.runTransaction { columnSeries { series(4, 12, 8, 16) } }
//    }
//    CartesianChartHost(
//        rememberCartesianChart(
//            rememberColumnCartesianLayer(),
//        ),
//        modelProducer,
//    )

    val modelProducer = remember { CartesianChartModelProducer() }

    LaunchedEffect(Unit) {
        modelProducer.runTransaction {
            columnSeries {
                series(
                    y = listOf(4, 12, 8, 16, 24, 78, 50, 63, 5, 13, 24, 46), // TODO change this list with a list of actual values from query
                    x = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
                )
            }
        }
    }

    CartesianChartHost(
        chart = rememberCartesianChart(
            rememberColumnCartesianLayer(),
            startAxis = VerticalAxis.rememberStart(),
            bottomAxis = HorizontalAxis.rememberBottom(valueFormatter = CartesianValueFormatter {context, value, verticalAxisPosition ->
                formatter(context, value, verticalAxisPosition) }),
        ),
        modelProducer,
    )

//    ProvideVicoTheme(
//        remember {
//            VicoTheme(
//                textColor = Color.Blue,
//                lineColor = Color.Gray,
//                columnCartesianLayerColors = listOf(Color.Blue),
//                lineCartesianLayerColors = listOf(Color.Blue),
//                candlestickCartesianLayerColors = VicoTheme.CandlestickCartesianLayerColors(
//                    bullish = Color.Green,
//                    neutral = Color.Yellow,
//                    bearish = Color.Red
//                ), // Doesn't change anything but required in class
//            )
//        }
//    ) {
//        CartesianChartHost(
//            chart = rememberCartesianChart(
//                rememberColumnCartesianLayer(),
//                startAxis = VerticalAxis.rememberStart(),
//                bottomAxis = HorizontalAxis.rememberBottom(),
//            ),
//            modelProducer,
//        )
//    }
}

fun formatter(
    context: CartesianMeasuringContext,
    value: Double,
    verticalAxisPosition: Axis.Position.Vertical?
): String{
    val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    val index = value.toInt() - 1
    return if (index in months.indices) months[index] else ""
}

