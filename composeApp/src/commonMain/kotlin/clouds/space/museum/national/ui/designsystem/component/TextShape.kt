package clouds.space.museum.national.ui.designsystem.component

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class TextShape(
    private val textLayoutResult: TextLayoutResult,
    private val horizontalPadding: Dp = 4.dp,
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val horizontalPaddingPx = with(density) {
            horizontalPadding.toPx()
        }

        return if (textLayoutResult.lineCount == 1) {
            Outline.Rectangle(Rect(-horizontalPaddingPx, 0F, size.width + horizontalPaddingPx, size.height))
        } else {
            val path = Path().apply {
                for (i in 0 until textLayoutResult.lineCount) {
                    addRect(textLayoutResult.getLineRect(i, horizontalPaddingPx))
                }

                close()
            }

            Outline.Generic(path)
        }
    }

    private fun TextLayoutResult.getLineRect(lineIndex: Int, horizontalPadding: Float) = Rect(
        getLineLeft(lineIndex) - horizontalPadding,
        getLineTop(lineIndex),
        getLineRight(lineIndex) + horizontalPadding,
        getLineBottom(lineIndex)
    )
}