package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream


class GildedRoseTest {
    companion object {
        @JvmStatic
        fun parameterizedValues(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(AGED_BRIE, Initial(1, 0), AfterOneDay(0, 1)),
                    Arguments.of(AGED_BRIE, Initial(0, 0), AfterOneDay(-1, 2)),
                    Arguments.of(AGED_BRIE, Initial(-1, 0), AfterOneDay(-2, 2)),
                    Arguments.of(AGED_BRIE, Initial(-1, 50), AfterOneDay(-2, 50)),
                    Arguments.of(AGED_BRIE, Initial(-1, 49), AfterOneDay(-2, 50)),
                    Arguments.of(BACKSTAGE, Initial(1, 0), AfterOneDay(0, 3)),
                    Arguments.of(BACKSTAGE, Initial(6, 0), AfterOneDay(5, 2)),
                    Arguments.of(BACKSTAGE, Initial(11, 0), AfterOneDay(10, 1)),
                    Arguments.of(BACKSTAGE, Initial(1, 49), AfterOneDay(0, 50)),
                    Arguments.of(BACKSTAGE, Initial(0, 25), AfterOneDay(-1, 0)),
                    Arguments.of(SULFURAS, Initial(0, 25), AfterOneDay(0, 25)),
                    Arguments.of(SULFURAS, Initial(-1, 25), AfterOneDay(-1, 25)),
                    Arguments.of(SULFURAS, Initial(0, 55), AfterOneDay(0, 55)),
                    Arguments.of(OTHER, Initial(0, 25), AfterOneDay(-1, 23)),
                    Arguments.of(OTHER, Initial(1, 25), AfterOneDay(0, 24)),
                    Arguments.of(CONJURED, Initial(0, 25), AfterOneDay(-1, 21)),
                    Arguments.of(CONJURED, Initial(1, 25), AfterOneDay(0, 23)),
            )
        }
    }

    data class Initial(val sellIn: Int, val quality: Int)
    data class AfterOneDay(val sellIn: Int, val quality: Int)

    @ParameterizedTest
    @MethodSource("parameterizedValues")
    fun `Works as intended`(name: String, initial: Initial, afterOneDay: AfterOneDay) {
        val items = arrayOf(Item(name, initial.sellIn, initial.quality))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(name, app.items[0].name)
        assertEquals(afterOneDay.sellIn, app.items[0].sellIn)
        assertEquals(afterOneDay.quality, app.items[0].quality)
    }
}
