package kotcity.ui.map

import javafx.scene.paint.Color
import kotcity.data.BlockCoordinate
import kotcity.data.CityMap

class CrimeRenderer(private val cityRenderer: CityRenderer, private val cityMap: CityMap) {

    companion object {
        val NEUTRAL_COLOR: Color = Color.TRANSPARENT
        val NEGATIVE_COLOR: Color = Color.RED
        val POSITIVE_COLOR: Color = Color.BLUE
    }

    fun render() {
        val (startBlock, endBlock) = cityRenderer.visibleBlockRange()

        BlockCoordinate.iterate(startBlock, endBlock) { coord ->
            val crimeScore = cityMap.crimeLayer[coord] ?: 0.0
            val presenceScore = cityMap.policePresenceLayer[coord] ?: 0.0
            cityRenderer.apply {
                val dX = (coord.x - blockOffsetX) * blockSize()
                val dY = (coord.y - blockOffsetY) * blockSize()
                canvas.graphicsContext2D.apply {
                    val crimeColor = NEUTRAL_COLOR.interpolate(NEGATIVE_COLOR, crimeScore)
                    val presenceColor = NEUTRAL_COLOR.interpolate(POSITIVE_COLOR, presenceScore)
                    fill = crimeColor.interpolate(presenceColor, 0.5)
                    fillRect(dX, dY, blockSize(), blockSize())
                }
            }
        }
    }
}
