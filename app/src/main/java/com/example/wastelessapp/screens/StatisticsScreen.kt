package com.example.wastelessapp.screens

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wastelessapp.database.entities.inventory_item.InventoryItemViewModel
import com.example.wastelessapp.database.entities.inventory_item.ItemState
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.CartesianMeasuringContext
import com.patrykandpatrick.vico.core.cartesian.axis.Axis
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Serializable
object StatisticsScreen

@Composable
fun StatisticsScreen(
    inventoryItemViewModel: InventoryItemViewModel
) {

    val state by inventoryItemViewModel.state.collectAsState()

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
                "Last 30 days:",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            val savedIn30Days = kotlinx.coroutines.runBlocking {
                inventoryItemViewModel.countItemsInLast30DaysByState(ItemState.SAVED)
            }
            val lostIn30Days = kotlinx.coroutines.runBlocking {
                inventoryItemViewModel.countItemsInLast30DaysByState(ItemState.EXPIRED)
            }

            StatisticsRow("Items saved", savedIn30Days.toString())
            HorizontalDivider(thickness = 2.dp)
            StatisticsRow("Items lost", lostIn30Days.toString())
            HorizontalDivider(thickness = 2.dp)
            StatisticsRow("Percentage of items lost", (
                    if(lostIn30Days+savedIn30Days > 0) {
                BigDecimal(lostIn30Days*100/(lostIn30Days+savedIn30Days))
                .setScale(1, RoundingMode.HALF_EVEN)
            }
            else 0.0
                    ).toString() + "%")
            HorizontalDivider(thickness = 2.dp)
            //StatisticsRow("Money lost", "135" + " PLN")
            // TODO change values to ones from queries (last month)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "All time:",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            val savedAtAllTime = kotlinx.coroutines.runBlocking {
                inventoryItemViewModel.countAllItemsByState(ItemState.SAVED)
            }
            val lostAtAllTime = kotlinx.coroutines.runBlocking {
                inventoryItemViewModel.countAllItemsByState(ItemState.EXPIRED)
            }

            StatisticsRow("Items saved", savedAtAllTime.toString())
            HorizontalDivider(thickness = 2.dp)
            StatisticsRow("Items lost", lostAtAllTime.toString())
            HorizontalDivider(thickness = 2.dp)
            StatisticsRow("Percentage of items lost", (
                    if(lostAtAllTime+savedAtAllTime > 0) {
                        BigDecimal(lostAtAllTime*100/(lostAtAllTime+savedAtAllTime))
                        .setScale(1, RoundingMode.HALF_EVEN)
            }
            else 0.0
                ).toString() + "%")
            HorizontalDivider(thickness = 2.dp)
            //StatisticsRow("Money lost", "1638" + " PLN")
            // TODO change values to ones from queries (all time)

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Lost items in the last 12 months",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            val monthlyStats = kotlinx.coroutines.runBlocking {
                inventoryItemViewModel.getMonthlyStatistics()
            }

            // Generate the last 12 months dynamically
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM")
            val monthsOrder = (0 until 12).map { offset ->
                LocalDate.now().minusMonths(offset.toLong()).format(formatter)
            }.reversed()

            val expiredCounts = monthsOrder.map { month ->
                monthlyStats.find { it.month == month && it.state == "EXPIRED" }?.count ?: 0
            }

            val startMonth = monthsOrder.first().substring(5, 7).toInt()
            val monthNumbers = monthsOrder.mapIndexed { index, _ ->
                startMonth + index
            }



            Chart(expiredCounts, monthNumbers)

            Spacer(modifier = Modifier.height(16.dp))
//            Text(
//                "Lost money this year",
//                fontWeight = FontWeight.Medium,
//                fontSize = 18.sp
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Chart(listOf(4, 12, 8, 16, 24, 78, 50, 63, 5, 13, 24, 46)) // TODO change this list with a list of actual values from query (lost money)

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
fun Chart(
    Yvalues: Collection<Number>,
    Xvalues: Collection<Number>
) {

    val modelProducer = remember { CartesianChartModelProducer() }

    LaunchedEffect(Unit) {
        modelProducer.runTransaction {
            columnSeries {
                series(
                    y = Yvalues,
                    x = Xvalues,
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
    return months[index%12]
}

