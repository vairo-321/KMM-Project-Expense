import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import data.SessionCache


@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(primary = Color.Black),
        shapes = MaterialTheme.shapes.copy(
            small = AbsoluteCutCornerShape(0.dp),
            medium = AbsoluteCutCornerShape(0.dp),
            large = AbsoluteCutCornerShape(16.dp)
        )
    ) {
        content()
    }
}

@Composable
fun getColorsTheme(): DarkModeColors {
    val isDarkMode : Boolean = SessionCache.isDarkMode()

    val purple = Color(0xFF6A66FF)
    val colorExpenseItem: Color = if (isDarkMode) Color(0xFF090808) else Color(0xFFF1F1F1)
    val backgroundColor: Color = if (isDarkMode) Color(0xFF1E1C1C) else Color.White
    val textColor: Color = if (isDarkMode) Color.White else Color.Black
    val addIconColor: Color = if (isDarkMode) purple else Color.Black
    val colorArrowRound: Color = if (isDarkMode) purple else Color.Gray.copy(alpha = .2f)

    return DarkModeColors(
        purple = purple,
        colorExpenseItem = colorExpenseItem,
        backgroundColor = backgroundColor,
        textColor = textColor,
        addIconColor = addIconColor,
        colorArrowRound = colorArrowRound
    )

}

data class DarkModeColors(
    val purple: Color,
    val colorExpenseItem: Color,
    val backgroundColor: Color,
    val textColor: Color,
    val addIconColor: Color,
    val colorArrowRound: Color
)