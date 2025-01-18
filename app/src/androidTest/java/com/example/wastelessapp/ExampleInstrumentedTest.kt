import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.wastelessapp.R
import com.example.wastelessapp.database.entities.inventory_item.ItemUnit
import com.example.wastelessapp.database.entities.product.ProductState
import com.example.wastelessapp.ui.components.BaseItem
import com.example.wastelessapp.ui.components.BottomSheet
import com.example.wastelessapp.ui.components.CustomButton
import com.example.wastelessapp.ui.components.CustomTopAppBar
import com.example.wastelessapp.ui.components.FoodInventoryItem
import com.example.wastelessapp.ui.components.FoodItem
import com.example.wastelessapp.ui.components.ShoppingItem
import com.example.wastelessapp.ui.components.ShoppingListItem
import com.example.wastelessapp.ui.components.formatSortTypeName
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
class AllTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `test convert valid date string to LocalDateTime`() {
        val input = "2025-01-01"
        val expected = LocalDateTime.of(2025, 1, 1, 0, 0)
        assertEquals(expected, convertToLocalDateTime(input))
    }

    @Test(expected = java.time.format.DateTimeParseException::class)
    fun `test convert invalid date string throws exception`() {
        val input = "invalid-date"
        convertToLocalDateTime(input)
    }

    @Test
    fun `test getDaysLeft for future expiry`() {
        val today = LocalDateTime.now()
        val item = FoodItem(1, "Milk", 1.0f, ItemUnit.LITERS, 0, null, today.plusDays(5), today)
        assertEquals(5, item.getDaysLeft())
    }

    @Test
    fun `test getDaysLeft for expired item`() {
        val today = LocalDateTime.now()
        val item = FoodItem(1, "Milk", 1.0f, ItemUnit.LITERS, 0, null, today.minusDays(3), today)
        assertEquals(-3, item.getDaysLeft())
    }

    @Test
    fun `test expireMessage for various dates`() {
        val today = LocalDateTime.now()
        val itemToday = FoodItem(1, "Milk", 1.0f, ItemUnit.LITERS, 0, null, today, today)
        assertEquals("Expires Today!", itemToday.expireMessage())

        val itemTomorrow =
            FoodItem(1, "Milk", 1.0f, ItemUnit.LITERS, 0, null, today.plusDays(1), today)
        assertEquals("Expires in 1 day", itemTomorrow.expireMessage())

        val itemExpired =
            FoodItem(1, "Milk", 1.0f, ItemUnit.LITERS, 0, null, today.minusDays(2), today)
        assertEquals("Expired 2 days ago", itemExpired.expireMessage())
    }

    @Test
    fun `test getQtyString for grams`() {
        val item = BaseItem(1, "Sugar", 500f, ItemUnit.GRAMS, 0)
        assertEquals("500.0g", item.getQtyString())
    }

    @Test
    fun `test getQtyString for kilograms`() {
        val item = BaseItem(1, "Flour", 2.5f, ItemUnit.KILOGRAMS, 0)
        assertEquals("2.5kg", item.getQtyString())
    }

    @Test
    fun `test getQtyString for pieces`() {
        val item = BaseItem(1, "Eggs", 12.5f, ItemUnit.PIECES, 0)
        assertEquals("12.5 pcs", item.getQtyString())
    }

    @Test
    fun `test formatSortTypeName correctly formats strings`() {
        assertEquals("Expiration Date", formatSortTypeName("EXPIRATION_DATE"))
        assertEquals("Price", formatSortTypeName("PRICE"))
        assertEquals("Name", formatSortTypeName("NAME"))
    }

    @Test
    fun `test BottomSheet displays title and labels`() {
        composeTestRule.setContent {
            BottomSheet(sheetState = rememberModalBottomSheetState(),
                isOpen = true,
                onDismissRequest = {},
                state = ProductState(name = "", iconResId = 0, products = emptyList()),
                onEvent = {})
        }
        composeTestRule.onNodeWithText("Add New Product").assertExists()
        composeTestRule.onNodeWithText("Product Name").assertExists()
        composeTestRule.onNodeWithText("Select Icon").assertExists()
    }

    @Test
    fun `CustomButton OnClick`() {
        var clicked = false
        composeTestRule.setContent {
            CustomButton(
                text = "Click Me",
                onClick = { clicked = true },
                backgroundColor = Color.Blue,
                contentColor = Color.White
            )
        }
        composeTestRule.onNodeWithText("Click Me").performClick()
        assertTrue(clicked)
    }

    @Test
    fun `FoodInventoryItem displays correct background based on expiry`() {
        val item = FoodItem(
            id = 1,
            name = "Milk",
            quantity = 1.0f,
            unit = ItemUnit.LITERS,
            iconId = R.drawable.milk_bottle_icon,
            price = 3.0f,
            expiryDate = LocalDateTime.now().plusDays(1),
            purchaseDate = LocalDateTime.now()
        )
        composeTestRule.setContent {
            FoodInventoryItem(item = item, onCheck = {}, onDelete = {})
        }
        composeTestRule.onNodeWithText("Milk").assertExists()
        composeTestRule.onNodeWithText("Expires in 1 day").assertExists()
    }

    @Test
    fun `ShoppingListItem displays text and icons`() {
        val item = ShoppingItem(
            id = 1,
            name = "Apples",
            quantity = 2.5f,
            unit = ItemUnit.KILOGRAMS,
            iconId = R.drawable.apple_fruits_icon,
            isChecked = false
        )
        composeTestRule.setContent {
            ShoppingListItem(item = item, onCheck = {}, onDelete = {})
        }
        composeTestRule.onNodeWithText("Apples").assertExists()
        composeTestRule.onNodeWithText("2.5kg").assertExists()
        composeTestRule.onNodeWithContentDescription("Check").assertExists()
        composeTestRule.onNodeWithContentDescription("Close").assertExists()
    }

    @Test
    fun `BottomSheet does not display when isOpen is false`() {
        composeTestRule.setContent {
            BottomSheet(sheetState = rememberModalBottomSheetState(),
                isOpen = false,
                onDismissRequest = {},
                state = ProductState(name = "", iconResId = 0, products = emptyList()),
                onEvent = {})
        }
        composeTestRule.onNodeWithText("Add New Product").assertDoesNotExist()
    }

    @Test
    fun `CustomTopAppBar displays correct title`() {
        composeTestRule.setContent {
            CustomTopAppBar(pageName = "Home")
        }
        composeTestRule.onNodeWithText("Home").assertExists()
    }

    @Test
    fun `ShoppingListItem triggers onCheck when check icon clicked`() {
        val item = ShoppingItem(
            id = 1,
            name = "Apples",
            quantity = 2.5f,
            unit = ItemUnit.KILOGRAMS,
            iconId = R.drawable.apple_fruits_icon,
            isChecked = false
        )
        var checkedItem: ShoppingItem? = null
        composeTestRule.setContent {
            ShoppingListItem(item = item, onCheck = { checkedItem = it }, onDelete = {})
        }
        composeTestRule.onNodeWithContentDescription("Check").performClick()
        assertEquals(item, checkedItem)
    }

    @Test
    fun `FoodInventoryItem displays expired message for expired item`() {
        val expiredItem = FoodItem(
            id = 2,
            name = "Yogurt",
            quantity = 1.0f,
            unit = ItemUnit.PIECES,
            iconId = R.drawable.milk_bottle_icon,
            price = 5.0f,
            expiryDate = LocalDateTime.now().minusDays(2),
            purchaseDate = LocalDateTime.now().minusDays(10)
        )
        composeTestRule.setContent {
            FoodInventoryItem(item = expiredItem, onCheck = {}, onDelete = {})
        }
        composeTestRule.onNodeWithText("Yogurt").assertExists()
        composeTestRule.onNodeWithText("Expired 2 days ago").assertExists()
    }
}
