package com.example.wastelessapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.wastelessapp.R
import com.example.wastelessapp.database.entities.inventory_item.InventoryItem
import com.example.wastelessapp.database.entities.inventory_item.InventoryItemDao
import com.example.wastelessapp.database.entities.inventory_item.ItemConverters
import com.example.wastelessapp.database.entities.product.Product
import com.example.wastelessapp.database.entities.product.ProductDao
import com.example.wastelessapp.database.entities.shopping_cart.ShoppingCartDao
import com.example.wastelessapp.database.entities.shopping_cart.ShoppingCartItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        InventoryItem::class,
        Product::class,
        ShoppingCartItem::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(ItemConverters::class)
abstract class WasteLessAppDatabase : RoomDatabase() {
    abstract val inventoryItemDao: InventoryItemDao
    abstract val productDao: ProductDao
    abstract val shoppingCartDao: ShoppingCartDao

    companion object {
        @Volatile
        private var INSTANCE: WasteLessAppDatabase? = null

        fun getInstance(context: Context): WasteLessAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WasteLessAppDatabase::class.java,
                    "wastelessapp.db"
                )
                    .addCallback(prepopulateCallback)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val prepopulateCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                CoroutineScope(Dispatchers.IO).launch {
                    INSTANCE?.productDao?.insertAll(basicProducts)
                }
            }
        }
    }
}

val basicProducts = listOf(
    Product(name = "Apple", iconResId = R.drawable.apple_fruits_icon),
    Product(name = "Banana", iconResId = R.drawable.banana_fruit_icon),
    Product(name = "Cherry", iconResId = R.drawable.cherry_fruit_icon),
    Product(name = "Orange", iconResId = R.drawable.orange_lemon_icon),
    Product(name = "Lemon", iconResId = R.drawable.orange_lemon_icon),
    Product(name = "Pear", iconResId = R.drawable.pear_papaya_icon),
    Product(name = "Papaya", iconResId = R.drawable.pear_papaya_icon),
    Product(name = "Pineapple", iconResId = R.drawable.pineapple_icon),
    Product(name = "Pomegranate", iconResId = R.drawable.pomegranate_icon),
    Product(name = "Grapes", iconResId = R.drawable.grapes_icon),
    Product(name = "Watermelon", iconResId = R.drawable.watermelon_icon),
    Product(name = "Strawberry", iconResId = R.drawable.strawberry_icon),
    Product(name = "Raspberry", iconResId = R.drawable.strawberry_icon),
    Product(name = "Blueberry", iconResId = R.drawable.strawberry_icon),

    Product(name = "Carrot", iconResId = R.drawable.carrot_vegetable_icon),
    Product(name = "Corn", iconResId = R.drawable.corn_vegetable_icon),
    Product(name = "Cucumber", iconResId = R.drawable.cucumber_vegetable_icon),
    Product(name = "Eggplant", iconResId = R.drawable.eggplant_vegetable_icon),
    Product(name = "Garlic", iconResId = R.drawable.garlic_vegetable_icon),
    Product(name = "Tomato", iconResId = R.drawable.tomato_vegetable_icon),
    Product(name = "Zucchini", iconResId = R.drawable.cucumber_vegetable_icon),
    Product(name = "Mushroom", iconResId = R.drawable.mushroom_icon),
    Product(name = "Pumpkin", iconResId = R.drawable.pumpkin_black_icon),
    Product(name = "Potato", iconResId = R.drawable.carrot_vegetable_icon),
    Product(name = "Onion", iconResId = R.drawable.garlic_vegetable_icon),

    Product(name = "Chicken Leg", iconResId = R.drawable.chicken_leg_icon),
    Product(name = "Chicken Breast", iconResId = R.drawable.chicken_rooster_icon),
    Product(name = "Crab", iconResId = R.drawable.crab_icon),
    Product(name = "Fish", iconResId = R.drawable.fish_icon),
    Product(name = "Salmon", iconResId = R.drawable.fish_icon),
    Product(name = "Steak", iconResId = R.drawable.steak_icon),
    Product(name = "Pork Chop", iconResId = R.drawable.steak_icon),

    Product(name = "Cheese", iconResId = R.drawable.cheese_icon),
    Product(name = "Egg", iconResId = R.drawable.egg_icon),
    Product(name = "Milk", iconResId = R.drawable.milk_bottle_icon),
    Product(name = "Peanut", iconResId = R.drawable.peanut_icon),
    Product(name = "Yogurt", iconResId = R.drawable.milk_bottle_icon),
    Product(name = "Butter", iconResId = R.drawable.cheese_icon),

    Product(name = "Bread", iconResId = R.drawable.bread_icon),
    Product(name = "Grain", iconResId = R.drawable.grain_wheat_icon),
    Product(name = "Pretzel", iconResId = R.drawable.pretzel_icon),
    Product(name = "Rice", iconResId = R.drawable.grain_wheat_icon),
    Product(name = "Pasta", iconResId = R.drawable.grain_wheat_icon),

    Product(name = "Chocolate", iconResId = R.drawable.chocolate_icon),
    Product(name = "Cookies", iconResId = R.drawable.cookies_icon),
    Product(name = "Donut", iconResId = R.drawable.donut_icon),
    Product(name = "Popcorn", iconResId = R.drawable.popcorn_icon),
    Product(name = "Snacks", iconResId = R.drawable.snacks_icon),
    Product(name = "Candy", iconResId = R.drawable.chocolate_icon),

    Product(name = "Cola", iconResId = R.drawable.cola_drink_plastic_icon),
    Product(name = "Coffee", iconResId = R.drawable.plastic_takeaway_coffee_icon),
    Product(name = "Juice", iconResId = R.drawable.cola_drink_plastic_icon),
    Product(name = "Tea", iconResId = R.drawable.plastic_takeaway_coffee_icon),

    Product(name = "Burger", iconResId = R.drawable.burger_icon),
    Product(name = "Pizza", iconResId = R.drawable.pizza_food_icon),
    Product(name = "Sandwich", iconResId = R.drawable.sandwich_icon),
    Product(name = "Sushi", iconResId = R.drawable.sushi_icon),
    Product(name = "Taco", iconResId = R.drawable.taco_icon),
    Product(name = "Hot Dog", iconResId = R.drawable.burger_icon),

    Product(name = "Cupcake", iconResId = R.drawable.cake_cup_icon),
    Product(name = "Wedding Cake", iconResId = R.drawable.wedding_cake_icon),
    Product(name = "Pie", iconResId = R.drawable.pie_dish_food_icon),
    Product(name = "Brownie", iconResId = R.drawable.cake_cup_icon),

    Product(name = "Bowl", iconResId = R.drawable.bowl_and_spoon_icon),
    Product(name = "Dish Cap", iconResId = R.drawable.dish_cap_icon),
    Product(name = "Dish Set", iconResId = R.drawable.dish_spoon_knife_icon),
    Product(name = "Restaurant Food", iconResId = R.drawable.food_restaurant_icon),
    Product(name = "Salt", iconResId = R.drawable.salt_seasoning_icon),
    Product(name = "Pepper", iconResId = R.drawable.salt_seasoning_icon),
    Product(name = "Spices", iconResId = R.drawable.salt_seasoning_icon)
)
