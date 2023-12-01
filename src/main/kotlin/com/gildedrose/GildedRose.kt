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

    class AgedBrie(name: String, sellIn: Int, quality: Int) : Item(name, sellIn, quality) {
        fun oneDayPasses(): Item {
            if (quality < 50) {
                quality = quality + 1
            }

            sellIn = sellIn - 1

            if (sellIn < 0) {
                if (quality < 50) {
                    quality = quality + 1
                }
            }
            return this
        }
    }

    class Backstage(name: String, sellIn: Int, quality: Int) : Item(name, sellIn, quality) {
        fun oneDayPasses(): Item {
            if (quality < 50) {
                quality = quality + 1

                if (sellIn < 11) {
                    if (quality < 50) {
                        quality = quality + 1
                    }
                }

                if (sellIn < 6) {
                    if (quality < 50) {
                        quality = quality + 1
                    }
                }
            }

            sellIn = sellIn - 1

            if (sellIn < 0) {
                quality = quality - quality
            }
            return this
        }
    }

    class Sulfuras(name: String, sellIn: Int, quality: Int) : Item(name, sellIn, quality) {
        fun oneDayPasses() = this
    }

    class Other(name: String, sellIn: Int, quality: Int) : Item(name, sellIn, quality) {
        fun oneDayPasses(): Item {
            if (quality > 0) {
                quality = quality - 1
            }

            sellIn = sellIn - 1

            if (sellIn < 0) {
                if (quality > 0) {
                    quality = quality - 1
                }
            }
            return this
        }
    }
}

