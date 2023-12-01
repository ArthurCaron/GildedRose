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
                    else -> Other(name, sellIn, quality).oneDayPasses()
                }
            }
        }
    }

    abstract class GeneralItem(val name: String, var sellIn: Int, _quality: Int) {
        var quality = _quality
            set(newQuality) {
                field = if (newQuality > 50) 50 else newQuality
            }
    }

    class AgedBrie(name: String, sellIn: Int, quality: Int) : GeneralItem(name, sellIn, quality) {
        fun oneDayPasses(): Item {
            quality++
            sellIn--

            if (sellIn < 0) {
                quality++
            }
            return Item(name, sellIn, quality)
        }
    }

    class Backstage(name: String, sellIn: Int, quality: Int) : GeneralItem(name, sellIn, quality) {
        fun oneDayPasses(): Item {
            quality++

            if (sellIn < 11) {
                quality++
            }

            if (sellIn < 6) {
                quality++
            }

            sellIn--

            if (sellIn < 0) {
                quality = 0
            }
            return Item(name, sellIn, quality)
        }
    }

    class Sulfuras(name: String, sellIn: Int, quality: Int) : GeneralItem(name, sellIn, quality) {
        fun oneDayPasses() = Item(name, sellIn, quality)
    }

    class Other(name: String, sellIn: Int, quality: Int) : GeneralItem(name, sellIn, quality) {
        fun oneDayPasses(): Item {
            if (quality > 0) {
                quality--
            }

            sellIn--

            if (sellIn < 0 && quality > 0) {
                quality--
            }
            return Item(name, sellIn, quality)
        }
    }
}

