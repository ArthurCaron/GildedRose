package com.gildedrose

const val AGED_BRIE = "Aged Brie"
const val BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert"
const val SULFURAS = "Sulfuras, Hand of Ragnaros"
const val OTHER = "Other"

class GildedRose(var items: Array<Item>) {
    fun updateQuality() {
        for (i in items.indices) {
            with(items[i]) {
                items[i] = when (name) {
                    AGED_BRIE -> AgedBrie(name, sellIn, quality).oneDayPasses()
                    BACKSTAGE -> Backstage(name, sellIn, quality).oneDayPasses()
                    SULFURAS -> Sulfuras(name, sellIn, quality).oneDayPasses()
                    else -> GeneralItem(name, sellIn, quality).oneDayPasses()
                }
            }
        }
    }

    open class GeneralItem(val name: String, var sellIn: Int, _quality: Int) {
        var quality = _quality
            set(newQuality) {
                field = if (newQuality > 50)
                    50
                else if (newQuality < 0)
                    0
                else
                    newQuality
            }

        open fun updateSellIn() {
            sellIn--
        }

        open fun updateQuality() {
            quality -= if (sellIn < 0) 2 else 1
        }

        fun oneDayPasses(): Item {
            updateSellIn()
            updateQuality()
            return Item(name, sellIn, quality)
        }
    }

    class AgedBrie(name: String, sellIn: Int, quality: Int) : GeneralItem(name, sellIn, quality) {
        override fun updateQuality() {
            quality++
            if (sellIn < 0) quality++
        }
    }

    class Backstage(name: String, sellIn: Int, quality: Int) : GeneralItem(name, sellIn, quality) {
        override fun updateQuality() {
            quality++
            if (sellIn < 10) quality++
            if (sellIn < 5) quality++
            if (sellIn < 0) quality = 0
        }
    }

    class Sulfuras(name: String, sellIn: Int, quality: Int) : GeneralItem(name, sellIn, quality) {
        override fun updateSellIn() {}
        override fun updateQuality() {}
    }
}

