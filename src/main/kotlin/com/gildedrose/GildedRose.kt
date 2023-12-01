package com.gildedrose

const val AGED_BRIE = "Aged Brie"
const val BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert"
const val SULFURAS = "Sulfuras, Hand of Ragnaros"
const val OTHER = "Other"

class GildedRose(var items: Array<Item>) {
    fun updateQuality() {
        for (i in items.indices) {
            when (items[i].name) {
                AGED_BRIE -> doAgedBrie(items[i])
                BACKSTAGE -> doBackstage(items[i])
                SULFURAS -> doSulfuras(items[i])
                OTHER -> doOther(items[i])
            }
        }
    }

    private fun doAgedBrie(item: Item) {
        with(item) {
            if (quality < 50) {
                quality = quality + 1
            }

            sellIn = sellIn - 1

            if (sellIn < 0) {
                if (quality < 50) {
                    quality = quality + 1
                }
            }
        }
    }

    private fun doBackstage(item: Item) {
        with(item) {
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
        }
    }

    private fun doSulfuras(item: Item) {
        // Do nothing
    }

    private fun doOther(item: Item) {
        with(item) {
            if (quality > 0) {
                quality = quality - 1
            }

            sellIn = sellIn - 1

            if (sellIn < 0) {
                if (quality > 0) {
                    quality = quality - 1
                }
            }
        }
    }
}

