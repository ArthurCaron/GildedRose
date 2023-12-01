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
                    SULFURAS -> GeneralItem(name, sellIn, quality).oneDayPasses()
                    else -> Other(name, sellIn, quality).oneDayPasses()
                }
            }
        }
    }

    open class GeneralItem(val name: String, var sellIn: Int, _quality: Int) {
        var quality = _quality
            set(newQuality) {
                field = if (newQuality > 50) 50 else newQuality
            }

        open fun oneDayPasses(): Item = Item(name, sellIn, quality)
    }

    class AgedBrie(name: String, sellIn: Int, quality: Int) : GeneralItem(name, sellIn, quality) {
        override fun oneDayPasses(): Item {
            sellIn--
            quality += if (sellIn < 0) 2 else 1
            return Item(name, sellIn, quality)
        }
    }

    class Backstage(name: String, sellIn: Int, quality: Int) : GeneralItem(name, sellIn, quality) {
        override fun oneDayPasses(): Item {
            sellIn--
            quality = if (sellIn >= 10) {
                quality + 1
            } else if (sellIn >= 5) {
                quality + 2
            } else if (sellIn >= 0) {
                quality + 3
            } else {
                0
            }
            return Item(name, sellIn, quality)
        }
    }

    class Other(name: String, sellIn: Int, quality: Int) : GeneralItem(name, sellIn, quality) {
        override fun oneDayPasses(): Item {
            sellIn--
            quality -= if (sellIn < 0 && quality > 1) 2 else if (quality > 0) 1 else 0
            return Item(name, sellIn, quality)
        }
    }
}

